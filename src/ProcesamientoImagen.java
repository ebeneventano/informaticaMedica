
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
    private BufferedImage imageActual;
    
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
        imageActual=bmp;
        
        nombreDelArchivo = getNombreImagen(null);
        //Retornamos el valor
        return bmp;
    }
    
    public BufferedImage escalaGrises(){
        //Variables que almacenarán los píxeles
        int mediaPixel,colorSRGB;
        Color colorAux;
                
        //Recorremos la imagen píxel a píxel
        for( int i = 0; i < imageActual.getWidth(); i++ ){
            for( int j = 0; j < imageActual.getHeight(); j++ ){
                //Almacenamos el color del píxel
                colorAux=new Color(this.imageActual.getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                mediaPixel=(int)((colorAux.getRed()+colorAux.getGreen()+colorAux.getBlue())/3);
                //Cambiamos a formato sRGB
                colorSRGB=(mediaPixel << 16) | (mediaPixel << 8) | mediaPixel;
                //Asignamos el nuevo valor al BufferedImage
                imageActual.setRGB(i, j,colorSRGB);
            }
        }
        //Retornamos la imagen
        return imageActual;
    }
    
    public BufferedImage aplicarFiltroConvolucion(){
    	float[] matrix = { 0.0F, 1.0F, 0.0F, 1.0F, -4.0F, 1.0F, 0.0F, 1.0F,
				0.0F };
		// Create the kernel.
		KernelJAI kernel = new KernelJAI(3, 3, matrix);
		// Create the convolve operation.
		PlanarImage output = JAI.create("convolve", imageActual, kernel);
		
		nombreDelArchivo = getNombreImagen("convolucion");
		
		this.imageActual = output.getAsBufferedImage();
		
    	return imageActual;
    }

	public void guardarEnBase() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStream b64 = new Base64OutputStream(os);
		try {
			ImageIO.write(imageActual, "png", b64);
			String base64 = os.toString("UTF-8");
			
			createInsertQuery(base64, nombreDelArchivo);
		
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
	
	public static void createInsertQuery(String result, String nombre) throws SQLException {

		Connection connection = connectToDb();
		if (connection != null) {

			Statement statement = connection.createStatement();
			String sql = "INSERT INTO imagenes_filtradas (Nombre,Imagen) "
					+ "VALUES ('"+ nombre +"','" + result + "');";
			statement.executeUpdate(sql);

			System.out.println("Se guardo la imagen");

			statement.close();
			connection.close();
		} else {

			System.out.println("Fallo la conexion");
		}
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

	public void dibujarHistograma() {

		new Formulario(imageActual);
	}
}
