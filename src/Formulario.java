import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class Formulario extends JFrame {

	private JPanel JPanel_Gris;
    private JButton jButton1;
    private JLabel jLabel1_Imagen;
    private JPanel jPanel1;
    private JPanel jPanel_alfa;
    private JPanel jPanel_azul;
    private JPanel jPanel_rojo;
    private JPanel jPanel_verde;
    private JScrollPane jScrollPane1;
    private static BufferedImage imagen;
    
    @SuppressWarnings("static-access")
	public Formulario(BufferedImage imagen) {
    	this.imagen = imagen;
        initComponents();
        this.setVisible(true);
    }

    @SuppressWarnings("static-access")
	private void initComponents() {

        jPanel1 = new JPanel();
        jScrollPane1 = new JScrollPane();
        jLabel1_Imagen = new JLabel();
        jPanel_rojo = new JPanel();
        jPanel_verde = new JPanel();
        jPanel_alfa = new JPanel();
        jPanel_azul = new JPanel();
        jButton1 = new JButton();
        JPanel_Gris = new JPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1_Imagen.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1_Imagen.setIcon(new ImageIcon(this.imagen)); // NOI18N
        jLabel1_Imagen.setText("JLabel_Imagen");
        jLabel1_Imagen.setToolTipText("");
        jScrollPane1.setViewportView(jLabel1_Imagen);

        jPanel_rojo.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        GroupLayout jPanel_rojoLayout = new GroupLayout(jPanel_rojo);
        jPanel_rojo.setLayout(jPanel_rojoLayout);
        jPanel_rojoLayout.setHorizontalGroup(
            jPanel_rojoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel_rojoLayout.setVerticalGroup(
            jPanel_rojoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 203, Short.MAX_VALUE)
        );

        jPanel_verde.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        GroupLayout jPanel_verdeLayout = new GroupLayout(jPanel_verde);
        jPanel_verde.setLayout(jPanel_verdeLayout);
        jPanel_verdeLayout.setHorizontalGroup(
            jPanel_verdeLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );
        jPanel_verdeLayout.setVerticalGroup(
            jPanel_verdeLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 203, Short.MAX_VALUE)
        );

        jPanel_alfa.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        GroupLayout jPanel_alfaLayout = new GroupLayout(jPanel_alfa);
        jPanel_alfa.setLayout(jPanel_alfaLayout);
        jPanel_alfaLayout.setHorizontalGroup(
            jPanel_alfaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel_alfaLayout.setVerticalGroup(
            jPanel_alfaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 207, Short.MAX_VALUE)
        );

        jPanel_azul.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        GroupLayout jPanel_azulLayout = new GroupLayout(jPanel_azul);
        jPanel_azul.setLayout(jPanel_azulLayout);
        jPanel_azulLayout.setHorizontalGroup(
            jPanel_azulLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 275, Short.MAX_VALUE)
        );
        jPanel_azulLayout.setVerticalGroup(
            jPanel_azulLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 208, Short.MAX_VALUE)
        );

        jButton1.setText("Dibujar histogramas");
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                dibujarHistograma(evt);
            }
        });

        JPanel_Gris.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        GroupLayout JPanel_GrisLayout = new GroupLayout(JPanel_Gris);
        JPanel_Gris.setLayout(JPanel_GrisLayout);
        JPanel_GrisLayout.setHorizontalGroup(
            JPanel_GrisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        JPanel_GrisLayout.setVerticalGroup(
            JPanel_GrisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 452, GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel_rojo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(9, 9, 9))
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel_azul, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(JPanel_Gris, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_verde, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_alfa, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel_verde, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_alfa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(JPanel_Gris, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel_rojo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_azul, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }
    
    @SuppressWarnings("static-access")
	private void dibujarHistograma(ActionEvent evt) {
        try {
            Histograma ObjHistograma=new Histograma();
            int[][] histograma=ObjHistograma.completarHistograma(this.imagen);
            //DIBUJAMOS EL HISTOGRAMA
            DibujarGrafico ObjDibujaHisto=new DibujarGrafico();
            for (int i = 0; i < 5; i++) {
                //extraemos un canal del histograma 
                int[] histogramaCanal=new int[256];
                System.arraycopy(histograma[i], 0, histogramaCanal, 0, histograma[i].length);
                //Dibujamos en el panel
                switch(i){
                    case 0:
                        ObjDibujaHisto.crearHistograma(histogramaCanal, jPanel_rojo, Color.red);
                        break;
                    case 1:
                        ObjDibujaHisto.crearHistograma(histogramaCanal, jPanel_verde, Color.green);
                        break;
                    case 2:
                        ObjDibujaHisto.crearHistograma(histogramaCanal, jPanel_azul, Color.blue);
                        break;
                    case 3:
                        ObjDibujaHisto.crearHistograma(histogramaCanal, jPanel_alfa, Color.black);
                        break;
                    case 4:
                        ObjDibujaHisto.crearHistograma(histogramaCanal, JPanel_Gris, Color.gray);
                        break;
                }
            }
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
