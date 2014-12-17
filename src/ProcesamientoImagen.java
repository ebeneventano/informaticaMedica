
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

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

	public void guardarImagenEnLaBase() {
		
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
			
			String histograma = Histograma.parsearHistograma(histogramaArray);
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

	public static Color obtenerColorPromedio(BufferedImage imagen) {

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
	
	public static double compararHistogramas(BufferedImage imagen1, BufferedImage imagen2){
		
		Mat histograma1 = crearHistogramaOpenCv(imagen1);
		Mat histograma2 = crearHistogramaOpenCv(imagen2);
		double comparacion = Imgproc.compareHist(histograma1, histograma2, Imgproc.CV_COMP_CORREL);
		
		return comparacion;
	}

	private static Mat crearHistogramaOpenCv(BufferedImage imagenA) {
		Mat imagen = new Mat();
		
		BufferedImage image = imagenA;
		byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		imagen = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
		imagen.put(0, 0, data);
		
		List<Mat> matList = new LinkedList<Mat>();
		matList.add(imagen);
		Mat histogram = new Mat();
		MatOfFloat ranges=new MatOfFloat(0,256);
		MatOfInt histSize = new MatOfInt(255);
		Imgproc.calcHist(matList, new MatOfInt(0), new Mat(), histogram,
				histSize, ranges);

		// Create space for histogram image
		Mat histImage = Mat.zeros( 100, (int)histSize.get(0, 0)[0], CvType.CV_8UC1);
		// Normalize histogram                          
		Core.normalize(histogram, histogram, 1, histImage.rows() , Core.NORM_MINMAX, -1, new Mat() );   
		// Draw lines for histogram points
		for( int i = 0; i < (int)histSize.get(0, 0)[0]; i++ )
		{                   
		        Core.line(
		                histImage,
		                new org.opencv.core.Point( i, histImage.rows() ),
		                new org.opencv.core.Point( i, histImage.rows()-Math.round( histogram.get(i,0)[0] )) ,
		                new Scalar( 255, 255, 255),
		                1, 8, 0 );
		}
		return histogram;
	}

	public BufferedImage getImagenActual() {
		return imagenActual;
	}

}
