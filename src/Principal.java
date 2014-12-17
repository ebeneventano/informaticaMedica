import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import org.opencv.core.Core;

@SuppressWarnings("serial")
public class Principal extends JFrame {
    
	private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JButton jButtonSave;
    private JButton jButtonCrearHistograma;
    private JButton jButtonCompararHistograma;
    private JButton jButtonCrearPaciente;
    
    ProcesamientoImagen ObjProcesamiento=new ProcesamientoImagen();

    public Principal() {
        initComponents();
    }

    private void initComponents() {

        jButton3 = new JButton();
        jButton2 = new JButton();
        jButton1 = new JButton();
        jButtonSave = new JButton();
        jButtonCrearHistograma = new JButton();
        jButtonCompararHistograma = new JButton();
        jButtonCrearPaciente = new JButton();
        jScrollPane1 = new JScrollPane();
        jLabel1 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Imagenes Java");
        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jButton3.setText("Cerrar");
        jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setText("Cargar imagen");
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Aplicar filtro convolución");
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

            	if ( jLabel1.getIcon()!=null ){
            		jButton1ActionPerformed(evt);
            	}
            }
        });
        
        jButtonSave.setText("Guardar Estudio");
        jButtonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	jButtonSaveActionPerformed(evt);
            }
        });

        jButtonCrearHistograma.setText("Panel de Histograma");
        jButtonCrearHistograma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	jButtonDibujarHistograma(evt);
            }
        });
        
        jButtonCompararHistograma.setText("Comparar Histogramas");
        jButtonCompararHistograma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	new Comparador();
            }
        });
        
        jButtonCrearPaciente.setText("Crear Paciente");
        jButtonCrearPaciente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	new FormularioPaciente();
            }
        });
        
        jLabel1.setText("Label que contendra la imagen");
        jScrollPane1.setViewportView(jLabel1);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                    	.addComponent(jButtonCrearPaciente)
                    	.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSave)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonCrearHistograma)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonCompararHistograma)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    	.addComponent(jButtonCrearPaciente)
                        .addComponent(jButton1)
                        .addComponent(jButtonSave)
                        .addComponent(jButton2))
                        .addComponent(jButtonCrearHistograma)
                        .addComponent(jButtonCompararHistograma)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        jLabel1.setIcon(new ImageIcon(ObjProcesamiento.abrirImagen()));

    }
    
    private void jButton1ActionPerformed(ActionEvent evt) {
        jLabel1.setIcon(new ImageIcon(ObjProcesamiento.aplicarFiltroConvolucion()));
    }
    
    private void jButtonSaveActionPerformed(ActionEvent evt) {
    	
    	if ( jLabel1.getIcon()!=null ){
    		
    		new FormularioGuardarEstudio(ObjProcesamiento.getImagenActual());
    	}
    }
    
    private void jButtonDibujarHistograma(ActionEvent evt){
    	
    	ObjProcesamiento.dibujarHistograma();
    }
    
    private void jButton3ActionPerformed(ActionEvent evt) {
        System.exit(1);
    }
    
    private void formWindowOpened(WindowEvent evt) {
        jLabel1.setText("");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

    	try {
    		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    	EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

}
