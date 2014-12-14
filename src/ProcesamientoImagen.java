
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.codec.binary.Base64OutputStream;

/**
 *
 * @author Luis
 */
public class ProcesamientoImagen {
	
    private File archivoSeleccionado;
    private String nombreDelArchivo;
    private BufferedImage imagenActual;
    
    //Método que devuelve una imagen abierta desde archivo
    //Retorna un objeto BufferedImagen
    public BufferedImage abrirImagen(){
        //Creamos la variable que será devuelta (la creamos como null)
        BufferedImage bmp=null;
        //Creamos un nuevo cuadro de diálogo para seleccionar imagen
        JFileChooser selector=new JFileChooser();
        //Le damos un título
        selector.setDialogTitle("Seleccione una imagen");
        //Filtramos los tipos de archivos
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("JPG & GIF & BMP", "jpg", "gif", "bmp");
        selector.setFileFilter(filtroImagen);
        //Abrimos el cuadro de diálog
        int flag=selector.showOpenDialog(null);
        //Comprobamos que pulse en aceptar
        if(flag==JFileChooser.APPROVE_OPTION){
            try {
                //Devuelve el fichero seleccionado
                archivoSeleccionado=selector.getSelectedFile();
                //Asignamos a la variable bmp la imagen leida
                bmp = ImageIO.read(archivoSeleccionado);
            } catch (Exception e) {
            }
                 
        }
        //Asignamos la imagen cargada a la propiedad imageActual
        imagenActual=bmp;
        
        nombreDelArchivo = getNombreImagen(null);
        //Retornamos el valor
        return bmp;
    }
    
    public BufferedImage escalaGrises(){
        //Variables que almacenarán los píxeles
        int mediaPixel,colorSRGB;
        Color colorAux;
                
        //Recorremos la imagen píxel a píxel
        for( int i = 0; i < imagenActual.getWidth(); i++ ){
            for( int j = 0; j < imagenActual.getHeight(); j++ ){
                //Almacenamos el color del píxel
                colorAux=new Color(this.imagenActual.getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                mediaPixel=(int)((colorAux.getRed()+colorAux.getGreen()+colorAux.getBlue())/3);
                //Cambiamos a formato sRGB
                colorSRGB=(mediaPixel << 16) | (mediaPixel << 8) | mediaPixel;
                //Asignamos el nuevo valor al BufferedImage
                imagenActual.setRGB(i, j,colorSRGB);
            }
        }
        //Retornamos la imagen
        return imagenActual;
    }
    
    public BufferedImage aplicarFiltroConvolucion(){
    	float[] matrix = { 0.0F, 1.0F, 0.0F, 1.0F, -4.0F, 1.0F, 0.0F, 1.0F,
				0.0F };
		// Create the kernel.
		KernelJAI kernel = new KernelJAI(3, 3, matrix);
		// Create the convolve operation.
		PlanarImage output = JAI.create("convolve", imagenActual, kernel);
		
		nombreDelArchivo = getNombreImagen("convolucion");
		
		this.imagenActual = output.getAsBufferedImage();
		
    	return imagenActual;
    }

	public void guardarEnBase() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStream b64 = new Base64OutputStream(os);
		
		Histograma histograma = new Histograma();
		int [][] histogramaArray = histograma.completarHistograma(imagenActual);
		Color colorPromedio = obtenerColorPromedio(imagenActual);
		
		try {
			ImageIO.write(imagenActual, "png", b64);
			String base64 = os.toString("UTF-8");
			
			createInsertQuery(base64, nombreDelArchivo, histogramaArray, colorPromedio.getRGB());
		
		} catch (Exception e) {
			System.out.println("NO SE PUDO GUARDAR EN LA BASE");
			e.printStackTrace();
		}
		
	}

	private String getNombreImagen(String filtro) {
		
		nombreDelArchivo = archivoSeleccionado.getName().split("\\.")[0];
		
		if(filtro != null && !filtro.isEmpty()){
			
			nombreDelArchivo += "_" + filtro;
		}
		
		return nombreDelArchivo;
	}
	
	public static void createInsertQuery(String base64, String nombre, int [][] histogramaArray, int rgb) throws SQLException {

		Connection connection = connectToDb();
		if (connection != null) {

			String histograma = parsearHistograma(histogramaArray);
			Statement statement = connection.createStatement();
			String sql = "INSERT INTO imagenes_filtradas (Nombre,Imagen,Histograma) "
					+ "VALUES ('"+ nombre +"','" + base64 + "',('" + nombre + "_histograma" +"','" + histograma +"', "+ rgb +"));";
			statement.executeUpdate(sql);
			
			
			System.out.println("Se guardo la imagen");

			statement.close();
			connection.close();
		} else {

			System.out.println("Fallo la conexion");
		}
		
	}
	
	private static String parsearHistograma(int[][] histogramaArray) {
		String array = "{";
		
		for(int i = 0; i<3; i++){
			array += Arrays.toString(histogramaArray[i]).replace("[", "{").replace("]", "}");
			if(i<2){
				array+=",";
			}
		}
		
		array += "}";
		
		return array;
	}

	public static Connection connectToDb() {

		Connection c = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(
					"jdbc:postgresql://localhost:8080/Prueba", "postgres",
					"asasas");
			c.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
		return c;
	}

	private static Color obtenerColorPromedio(BufferedImage imagen) {

		int rojos = 0;
		int verdes = 0;
		int azules = 0;
		int cantidadPixeles = 0;

		for (int y = 0; y < imagen.getHeight(); y++) {
			for (int x = 0; x < imagen.getWidth(); x++) {
				Color RGBEnEsaPosicion = new Color(imagen.getRGB(x, y));

				cantidadPixeles++;
				rojos += RGBEnEsaPosicion.getRed();
				verdes += RGBEnEsaPosicion.getGreen();
				azules += RGBEnEsaPosicion.getBlue();
			}
		}
		Color colorPromedio = new Color(
				Integer.valueOf(rojos / cantidadPixeles),
				Integer.valueOf(verdes / cantidadPixeles),
				Integer.valueOf(azules / cantidadPixeles));

		return colorPromedio;
	}
	public void dibujarHistograma() {

		new Formulario(imagenActual);
	}
}
