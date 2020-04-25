package GDP.GUIs;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.plaf.basic.BasicComboPopup;

public class ConfirmLinkUI extends javax.swing.JDialog {

    public boolean cancel;

    KeyAdapter common = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
            if (KeyEvent.VK_ESCAPE == e.getKeyChar()) {
                cancel = true;
                jButton1ActionPerformed(null);
            }
            if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                cancel = false;
                jButton1ActionPerformed(null);
            }
        }
    };

    KeyAdapter numbers = new KeyAdapter() {
        //aki permitir solo numeros
    };

    public ConfirmLinkUI(boolean modal) {
        super();
        setModal(modal);
        initComponents();
        pack();
        setResizable(false);
        jButton1.addKeyListener(common);
        jTextField1.addKeyListener(common);
        jTextField1.addKeyListener(numbers);
        GraphicsEnvironment ge
                = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        if (gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT)) {
            setOpacity(0.7F);
        }
    }

    public String getPeso() {
        if (jTextField1.getText().isEmpty()) {
            return "0.0";
        }
        Double val = Double.parseDouble(jTextField1.getText());
        return val.toString();
    }

    public void showUP(Point point, String previous_value) {
        if (point != null) {
            point.x -= getWidth() / 2;
            point.y -= getHeight() / 2;
            setLocation(point);
        } else {
            setLocationRelativeTo(null);
        }
        if (previous_value == null) {
            jTextField1.setText("0");
        } else {
            jTextField1.setText(previous_value);
        }
        pack();
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jTextField1 = new javax.swing.JTextField();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(89, 129, 189), 2, true));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Peso", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("DialogInput", 1, 12))); // NOI18N
        jTextField1.setMinimumSize(new java.awt.Dimension(200, 37));
        jTextField1.setPreferredSize(new java.awt.Dimension(200, 37));
        jToolBar1.add(jTextField1);
        jToolBar1.add(filler1);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GDP/Resources/Done.png"))); // NOI18N
        jButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setOpaque(false);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jPanel1.add(jToolBar1);

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
