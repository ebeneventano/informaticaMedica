import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class Comparador extends JFrame {

	private File archivo1;
	private String nombreImagen1;
	private BufferedImage imagen1;
	private File archivo2;
	private String nombreImagen2;
	private BufferedImage imagen2;

	public Comparador() {
		setVisible(true);
		initComponents();
		ocultarMediciones();
	}

	private void clickBotonComparar(ActionEvent e) {

		if (label2.getIcon()!=null && label3.getIcon()!=null){
			
			this.porcentaje.setVisible(true);
			this.porcentajeLabel.setVisible(true);
			double porcentajeSimilitud = ProcesamientoImagen.compararHistogramas(imagen1, imagen2)*100;
			this.porcentaje.setText(String.valueOf(porcentajeSimilitud)+ "%");
		} else {
			
			ocultarMediciones();
		}
	}

	private void ocultarMediciones() {
		this.porcentaje.setVisible(false);
		this.porcentajeLabel.setVisible(false);
	}

	public BufferedImage abrirImagen1() {

		BufferedImage bmp = null;
		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle("Seleccione una imagen");
		FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter(
				"JPG & GIF & BMP", "jpg", "gif", "bmp");
		selector.setFileFilter(filtroImagen);
		int flag = selector.showOpenDialog(null);
		if (flag == JFileChooser.APPROVE_OPTION) {
			try {
				archivo1 = selector.getSelectedFile();
				bmp = ImageIO.read(archivo1);
			} catch (Exception e) {
			}

		}
		imagen1 = bmp;

		nombreImagen1 = archivo1.getName().split("\\.")[0];
		
		return bmp;
	}
	
	public BufferedImage abrirImagen2() {

		BufferedImage bmp = null;
		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle("Seleccione una imagen");
		FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter(
				"JPG & GIF & BMP", "jpg", "gif", "bmp");
		selector.setFileFilter(filtroImagen);
		int flag = selector.showOpenDialog(null);
		if (flag == JFileChooser.APPROVE_OPTION) {
			try {
				archivo2 = selector.getSelectedFile();
				bmp = ImageIO.read(archivo2);
			} catch (Exception e) {
			}

		}
		imagen2 = bmp;

		nombreImagen2 = archivo2.getName().split("\\.")[0];
		return bmp;
	}

	private void clickCargarImagen1(ActionEvent e) {
		label2.setIcon(new ImageIcon(abrirImagen1()));
	}

	private void clickCargarImagen2(ActionEvent e) {
		label3.setIcon(new ImageIcon(abrirImagen2()));
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - 
		button1 = new JButton();
		button2 = new JButton();
		scrollPane1 = new JScrollPane();
		label2 = new JLabel();
		scrollPane2 = new JScrollPane();
		label3 = new JLabel();
		button3 = new JButton();
		porcentajeLabel = new JLabel();
		porcentaje = new JLabel();

		//======== this ========
		setTitle("Comparador de Im\u00e1genes");
		Container contentPane = getContentPane();

		//---- button1 ----
		button1.setText("Cargar Imagen 1");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clickCargarImagen1(e);
			}
		});

		//---- button2 ----
		button2.setText("Cargar Imagen 2");
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clickCargarImagen2(e);
			}
		});

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(label2);
		}

		//======== scrollPane2 ========
		{
			scrollPane2.setViewportView(label3);
		}

		//---- button3 ----
		button3.setText("Comparar");
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clickBotonComparar(e);
			}
		});

		//---- porcentajeLabel ----
		porcentajeLabel.setText("Porcentaje de similitud: ");

		//---- porcentaje ----
		porcentaje.setText("0%");
		porcentaje.setFont(new Font("Tahoma", Font.BOLD, 11));

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
					.addContainerGap(92, Short.MAX_VALUE)
					.addComponent(button1)
					.addGap(53, 53, 53)
					.addComponent(button3)
					.addGap(55, 55, 55)
					.addComponent(button2)
					.addGap(79, 79, 79))
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE)
						.addComponent(porcentajeLabel, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE))
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addComponent(porcentaje, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
							.addGap(205, 205, 205))
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
							.addContainerGap())))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
						.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(porcentaje)
						.addComponent(porcentajeLabel, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(button1)
						.addComponent(button2)
						.addComponent(button3))
					.addContainerGap())
		);
		pack();
		setLocationRelativeTo(getOwner());
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - 
	private JButton button1;
	private JButton button2;
	private JScrollPane scrollPane1;
	private JLabel label2;
	private JScrollPane scrollPane2;
	private JLabel label3;
	private JButton button3;
	private JLabel porcentajeLabel;
	private JLabel porcentaje;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
