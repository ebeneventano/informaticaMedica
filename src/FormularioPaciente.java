import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

/*
 * Created by JFormDesigner on Wed Dec 17 17:38:24 GFT 2014
 */

@SuppressWarnings("serial")
public class FormularioPaciente extends JFrame {
	public FormularioPaciente() {
		this.setVisible(true);
		initComponents();
	}

	private void clickGuardarPaciente(ActionEvent e) {

		if (sonDatosValidos()) {

			Paciente pacienteNuevo = new Paciente();
			pacienteNuevo.setApellido(apellido.getText().toString());
			pacienteNuevo.setNombre(nombre.getText().toString());
			pacienteNuevo.setFechaNacimiento(fechaDeNacimiento.getText()
					.toString());

			try {

				guardarPacienteEnBase(pacienteNuevo);
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.err.println("Fallo guardar paciente en base");
			}
		} else {
			
			mostrarMensaje("Ingrese datos válidos", Color.BLACK);
		}
	}

	private boolean sonDatosValidos() {
		return !nombre.getText().trim().isEmpty()
				&& !apellido.getText().trim().isEmpty()
				&& !fechaDeNacimiento.getText().trim().isEmpty() 
				&& validarFecha(fechaDeNacimiento.getText().toString());
	}

	private boolean validarFecha(String fechaNacimiento) {

		return fechaNacimiento.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
	}

	private void guardarPacienteEnBase(Paciente paciente) throws SQLException {

		Connection connection = ProcesamientoImagen.connectToDb();

		if (connection != null) {

			Statement statement = connection.createStatement();
			String sql = "INSERT INTO paciente (Nombre,Apellido,Fecha_Nacimiento) "
					+ "VALUES ('"
					+ paciente.getNombre()
					+ "', '"
					+ paciente.getApellido()
					+ "','"
					+ paciente.getFechaNacimiento() + "');";
			statement.executeUpdate(sql);

			mostrarMensaje("Se guardo el paciente correctamente", Color.BLUE);

			statement.close();
			connection.close();
		} else {

			mostrarMensaje("Fallo la conexion", Color.RED);
		}
	}
	
	private void mostrarMensaje(String mensaje, Color color) {
		
		System.out.println(mensaje);
		mensajeRespuesta.setVisible(true);
		mensajeRespuesta.setText(mensaje);
		mensajeRespuesta.setForeground(color);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - 
		nombre = new JTextField();
		apellido = new JTextField();
		fechaDeNacimiento = new JTextField();
		button1 = new JButton();
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		label4 = new JLabel();
		mensajeRespuesta = new JLabel();

		//======== this ========
		setBackground(Color.white);
		setTitle("Nuevo Paciente");
		Container contentPane = getContentPane();

		//---- button1 ----
		button1.setText("Guardar Paciente");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clickGuardarPaciente(e);
			}
		});

		//---- label1 ----
		label1.setText("CARGAR NUEVO PACIENTE");
		label1.setFont(new Font("Calibri", Font.BOLD, 14));

		//---- label2 ----
		label2.setText("Nombre Completo:");

		//---- label3 ----
		label3.setText("Apellido:");

		//---- label4 ----
		label4.setText("Fecha de Nacimiento (DD/MM/YYYY):");

		//---- mensajeRespuesta ----
		mensajeRespuesta.setFont(new Font("Calibri", Font.BOLD, 14));
		mensajeRespuesta.setText("_");
		mensajeRespuesta.setHorizontalAlignment(SwingConstants.CENTER);

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(169, 169, 169)
					.addComponent(button1)
					.addContainerGap(205, Short.MAX_VALUE))
				.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
					.addGap(0, 92, Short.MAX_VALUE)
					.addComponent(mensajeRespuesta, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(134, Short.MAX_VALUE))
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(44, 44, 44)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addComponent(label3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(label4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(label2, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
					.addGap(26, 26, 26)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addComponent(fechaDeNacimiento)
						.addComponent(apellido)
						.addComponent(nombre, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(71, Short.MAX_VALUE))
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(157, 157, 157)
					.addComponent(label1, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(163, Short.MAX_VALUE))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(label1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addGap(26, 26, 26)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(nombre)
						.addComponent(label2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label3, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(apellido, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(fechaDeNacimiento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label4, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(37, 37, 37)
					.addComponent(mensajeRespuesta)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(button1)
					.addGap(64, 64, 64))
		);
		pack();
		setLocationRelativeTo(getOwner());
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - 
	private JTextField nombre;
	private JTextField apellido;
	private JTextField fechaDeNacimiento;
	private JButton button1;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel mensajeRespuesta;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
