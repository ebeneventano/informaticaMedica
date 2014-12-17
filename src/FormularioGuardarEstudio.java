import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

import org.apache.commons.codec.binary.Base64OutputStream;
/*
 * Created by JFormDesigner on Wed Dec 17 18:55:59 GFT 2014
 */

@SuppressWarnings("serial")
public class FormularioGuardarEstudio extends JFrame {
	
	public FormularioGuardarEstudio(BufferedImage imagen) {
		initComponents();
		this.setVisible(true);
		this.imagen = imagen;
	}

	private void clickGuardarEstudio(ActionEvent e) {
		
		if (!nombreEstudio.getText().toString().trim().isEmpty() && pacientesCombo.getSelectedItem()!=null){
			
			Estudio estudio = new Estudio(imagen, nombreEstudio.getText().toString().trim(), descripcion.getText().toString().trim(), notas.getText().toString().trim());
			guardarEstudioEnBase(estudio);
		} else {
			
			mostrarMensaje("Ingrese un nombre de estudio", Color.RED);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - verdura henrion
		label1 = new JLabel();
		pacientesCombo = new JComboBox(obtenerPacientes().toArray());
		label2 = new JLabel();
		label3 = new JLabel();
		label4 = new JLabel();
		label5 = new JLabel();
		nombreEstudio = new JTextField();
		scrollPane1 = new JScrollPane();
		descripcion = new JTextArea();
		descripcion.setLineWrap( true );
		descripcion.setWrapStyleWord( true );
		notas = new JTextArea();
		notas.setLineWrap( true );
		notas.setWrapStyleWord( true );
		button1 = new JButton();
		mensajeRespuesta = new JLabel();

		//======== this ========
		setTitle("Guardar Estudio");
		Container contentPane = getContentPane();

		//---- label1 ----
		label1.setText("Paciente:");
		label1.setFont(new Font("Calibri", Font.BOLD, 14));

		//---- label2 ----
		label2.setText("Datos del Estudio");
		label2.setFont(new Font("Calibri", Font.BOLD, 14));

		//---- label3 ----
		label3.setText("Nombre Del Estudio:");

		//---- label4 ----
		label4.setText("Descripci\u00f3n:");

		//---- label5 ----
		label5.setText("Notas:");

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(descripcion);
		}

		//---- button1 ----
		button1.setText("Guardar Estudio");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clickGuardarEstudio(e);
			}
		});

		//---- mensajeRespuesta ----
		mensajeRespuesta.setText("_");
		mensajeRespuesta.setHorizontalAlignment(SwingConstants.CENTER);

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(label4)
								.addComponent(label1, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
								.addComponent(pacientesCombo, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
								.addComponent(label2)
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(label3)
									.addGap(18, 18, 18)
									.addComponent(nombreEstudio, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(label5)
									.addGroup(contentPaneLayout.createParallelGroup()
										.addGroup(contentPaneLayout.createSequentialGroup()
											.addGap(83, 83, 83)
											.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
												.addComponent(notas, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)))
										.addGroup(contentPaneLayout.createSequentialGroup()
											.addGap(48, 48, 48)
											.addComponent(mensajeRespuesta, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE))))))
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGap(142, 142, 142)
							.addComponent(button1)))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(label1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(pacientesCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18, 18, 18)
					.addComponent(label2)
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label3)
						.addComponent(nombreEstudio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(label4)
						.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
					.addGap(11, 11, 11)
					.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(label5)
						.addComponent(notas, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(mensajeRespuesta, GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(button1)
					.addContainerGap())
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}
	
	private void guardarEstudioEnBase(Estudio estudio){

		Connection connection = ProcesamientoImagen.connectToDb();

		if (connection != null) {

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			OutputStream b64 = new Base64OutputStream(os);
			
			Histograma histograma = new Histograma();
			int [][] histogramaArray = histograma.completarHistograma(estudio.getImagen());
			Color colorPromedio = ProcesamientoImagen.obtenerColorPromedio(estudio.getImagen());
			
			try {
				ImageIO.write(estudio.getImagen(), "png", b64);
				String base64 = os.toString("UTF-8");
				
				ejecutarInsertQuery(estudio, base64, histogramaArray, colorPromedio, connection);
			
			} catch (Exception e) {
				
				mostrarMensaje("NO SE PUDO GUARDAR EN LA BASE", Color.RED);
				e.printStackTrace();
			}
				
		} else {

			mostrarMensaje("Fallo la conexion", Color.RED);
		}
	}
	
	private List<Paciente> obtenerPacientes(){
		
		Connection connection = ProcesamientoImagen.connectToDb();
		List<Paciente> pacientes= new LinkedList<Paciente>();
		
		if (connection != null) {

			try {
				
				pacientes = ejecutarQueryObtenerPacientes(connection);
			
			} catch (Exception e) {
				
				mostrarMensaje("NO SE PUDO OBTENER LA LISTA DE PACIENTES DE LA BASE", Color.RED);
				e.printStackTrace();
			}
				
		} else {

			mostrarMensaje("Fallo la conexion", Color.RED);
		}
		
		return pacientes;
	}
	
	private List<Paciente> ejecutarQueryObtenerPacientes(Connection connection) throws SQLException{
		
		Statement statement = connection.createStatement();

		String sql = "SELECT * FROM Paciente";
		ResultSet rs = statement.executeQuery(sql);
		
		List<Paciente> pacientes = new LinkedList<Paciente>();
		
	    while ( rs.next() ){
	    	
	      Paciente paciente = new Paciente();
	      paciente.setId( rs.getInt("id"));
	      paciente.setNombre( rs.getString("nombre") );
	      paciente.setApellido( rs.getString("apellido") );
	      paciente.setFechaNacimiento( rs.getString("fecha_nacimiento") );

	      pacientes.add(paciente);
	    }
		
		statement.close();
		connection.close();
		
		return pacientes;
	}

	private void ejecutarInsertQuery(Estudio estudio, String base64, int [][] histogramaArray, Color colorPromedio, Connection connection)
			throws SQLException {
		
		Statement statement = connection.createStatement();
		String histograma = Histograma.parsearHistograma(histogramaArray);

		Integer idPaciente = ((Paciente)pacientesCombo.getSelectedItem()).getId();
		
		String sql = "INSERT INTO imagenes_filtradas (Nombre,Imagen,Histograma,Descripcion,Notas,Id_Paciente) "
				+ "VALUES ('"+ estudio.getNombre() +"','" + base64 + "',('" + estudio.getNombre() + "_histograma" +"','" + histograma +"', "+ colorPromedio.getRGB() +"), '"
				+ estudio.getDescripcion() +"','" + estudio.getNotas() +"',"+ idPaciente +");";
		statement.executeUpdate(sql);
		
		mostrarMensaje("Se guardo el estudio", Color.BLUE);

		statement.close();
		connection.close();
	}
	
	private void mostrarMensaje(String mensaje, Color color) {
		
		System.out.println(mensaje);
		mensajeRespuesta.setVisible(true);
		mensajeRespuesta.setText(mensaje);
		mensajeRespuesta.setForeground(color);
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - verdura henrion
	private JLabel label1;
	@SuppressWarnings("rawtypes")
	private JComboBox pacientesCombo;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JTextField nombreEstudio;
	private JScrollPane scrollPane1;
	private JTextArea descripcion;
	private JTextArea notas;
	private JButton button1;
	private JLabel mensajeRespuesta;
	private BufferedImage imagen;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
