import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.postgresql.util.Base64;

public class ImageDecoder {

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		createConvolutionedImageAndSave();
		getImageFromDB(1);
		// createCannyFilteredImage();
	}

	
	/**
	 * Crea la imagen convolucionada del cerebro y la guarda en la base de datos Postgresql.
	 * @throws SQLException
	 */
	private static void createConvolutionedImageAndSave() throws SQLException {

		// matriz de convolución
		float[] matrix = { 0.0F, 1.0F, 0.0F, 1.0F, -4.0F, 1.0F, 0.0F, 1.0F,
				0.0F };

		BufferedImage image = null;
		try {

			image = ImageIO.read(new File("pictures/cerebro.png"));
		} catch (IOException e) {

			System.out.println("NO SE PUDO LEER");
		}

		// Create the kernel.
		KernelJAI kernel = new KernelJAI(3, 3, matrix);
		// Create the convolve operation.
		PlanarImage output = JAI.create("convolve", image, kernel);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStream b64 = new Base64OutputStream(os);
		try {
			ImageIO.write(output, "png", b64);
			String base64 = os.toString("UTF-8");
			
			createInsertQuery(base64, "tomografia cerebro");
		
		} catch (IOException e) {
			System.out.println("NO SE PUDO GUARDAR EN LA BASE");
			e.printStackTrace();
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

	/**
	 * Devuelve la imagen del cerebro (id = 1) de la base y la guarda en la carpeta pictures.
	 * @param id
	 * @throws SQLException
	 */
	public static void getImageFromDB(int id) throws SQLException {

		String base64 = "";
		Connection connection = connectToDb();

		if (connection != null) {

			Statement statement = connection.createStatement();
			ResultSet rs = statement
					.executeQuery("SELECT * FROM imagenes_filtradas where id="
							+ id + ";");
			while (rs.next()) {
				base64 = rs.getString("imagen");
			}

			rs.close();
			statement.close();
			connection.close();
			
			BufferedImage image = decodeBase64(base64);
			
			// matriz de convolución
			float[] matrix = { 0.0F, 1.0F, 0.0F, 1.0F, -4.0F, 1.0F, 0.0F, 1.0F,
					0.0F };

			// Create the kernel.
			KernelJAI kernel = new KernelJAI(3, 3, matrix);
			// Create the convolve operation.
			PlanarImage output = JAI.create("convolve", image, kernel);
			File outputfile = new File("pictures/resultJAI.png");
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			OutputStream b64 = new Base64OutputStream(os);
			try {
				ImageIO.write(output, "png", outputfile);
			} catch (IOException e) {
				System.out.println("NO SE PUDO CREAR EL FILE");
				e.printStackTrace();
			}
		} else {

			System.out.println("Fallo la conexion");
		}
	}

	private static BufferedImage decodeBase64(String base64) {

		BufferedImage image = null;
	    byte[] imageByte;
	    try {
	        imageByte = Base64.decode(base64);
	        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
	        image = ImageIO.read(bis);
	        bis.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return image;
	}

//	private static void createCannyFilteredImage() {
//
//		// create the detector
//		CannyEdgeDetector detector = new CannyEdgeDetector();
//
//		// adjust its parameters as desired
//		detector.setLowThreshold(0.5f);
//		detector.setHighThreshold(1f);
//		// get image
//		BufferedImage image = null;
//		try {
//			image = ImageIO.read(new File("/pictures/cerebro.png"));
//		} catch (IOException e) {
//
//			System.out.println("NO SE PUDO LEER");
//		}
//
//		// apply it to an image
//		detector.setSourceImage(image);
//		detector.process();
//		BufferedImage edges = detector.getEdgesImage();
//		File outputfile = new File("/pictures/resultCerebro.png");
//		try {
//			ImageIO.write(edges, "jpg", outputfile);
//		} catch (IOException e) {
//			System.out.println("NO SE PUDO GUARDAR");
//			e.printStackTrace();
//		}
//	}
}
