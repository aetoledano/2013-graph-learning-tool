package GDP.GUIs;

import GDP.Connector;
import GDP.Node;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

/**
 *
 * @author rsegui
 */
public class MultiLinkCreationUI extends javax.swing.JDialog {

    LinkedList<Node> nodes;
    LinkedList<Connector> connectors;

    public MultiLinkCreationUI(boolean modal, LinkedList<Node> nodes, LinkedList<Connector> connectors) {
        super();
        this.nodes = nodes;
        this.connectors = connectors;
        setModal(modal);
        initComponents();
        to.addKeyListener(common);
        done.addKeyListener(common);
        add.addKeyListener(common);
        valor.addKeyListener(common);
        GraphicsEnvironment ge
                = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        if (gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT)) {
            setOpacity(0.7F);
        }/*
         to.setToolTipText(config.getText("UI.TM.MultiLinkUI.read"));
         write.setToolTipText(config.getText("UI.TM.MultiLinkUI.write"));
         action.setToolTipText(config.getText("UI.TM.MultiLinkUI.action"));
         state.setToolTipText(config.getText("UI.TM.MultiLinkUI.st"));
         done.setToolTipText(config.getText("UI.done"));
         add.setToolTipText(config.getText("UI.add.lnk"));*/

    }

    int count = -1;
    int from;

    public void showUp(Point point, int from) {
        message.setText("Enlazar n" + from + " con...");
        if (point != null) {
            point.x -= getWidth() / 2;
            point.y -= getHeight() / 2;
            setLocation(point);
        } else {
            setLocationRelativeTo(null);
        }
        reload();
        pack();
        setVisible(true);
    }

    private void reload() {
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fondo = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        to = new javax.swing.JComboBox();
        dos_ptos = new javax.swing.JLabel();
        valor = new javax.swing.JTextField();
        add = new javax.swing.JButton();
        message = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        done = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);

        fondo.setBackground(new java.awt.Color(255, 255, 255));
        fondo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(89, 129, 189), 2, true));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.add(to);

        dos_ptos.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        dos_ptos.setText(":");
        jPanel1.add(dos_ptos);
        jPanel1.add(valor);

        add.setBackground(new java.awt.Color(255, 255, 255));
        add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GDP/Resources/add7.png"))); // NOI18N
        add.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        add.setFocusable(false);
        add.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });
        jPanel1.add(add);

        message.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        message.setText("Enlazar X con...");

        jLabel1.setText("Nodo:");

        jLabel2.setText("Valor del enlace:");

        done.setBackground(new java.awt.Color(255, 255, 255));
        done.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        done.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GDP/Resources/Done.png"))); // NOI18N
        done.setText("Listo");
        done.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        done.setFocusable(false);
        done.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        done.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        done.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        done.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout fondoLayout = new javax.swing.GroupLayout(fondo);
        fondo.setLayout(fondoLayout);
        fondoLayout.setHorizontalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(message)
                            .addGroup(fondoLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)))
                        .addGap(0, 39, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fondoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(done)))
                .addContainerGap())
        );
        fondoLayout.setVerticalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(message)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(done)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        //
    }//GEN-LAST:event_addActionPerformed

    private void doneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneActionPerformed
        this.dispose();
    }//GEN-LAST:event_doneActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton done;
    private javax.swing.JLabel dos_ptos;
    private javax.swing.JPanel fondo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel message;
    private javax.swing.JComboBox to;
    private javax.swing.JTextField valor;
    // End of variables declaration//GEN-END:variables
    KeyAdapter common = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                doneActionPerformed(null);
            }
            if (KeyEvent.VK_ESCAPE == e.getKeyChar()) {
                doneActionPerformed(null);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            if ( (Character.isDigit(e.getKeyChar()) || e.getKeyChar() == '.') && valor.getText().length() < 12) {
                super.keyTyped(e);
            } else {
                e.consume();
            }
        }

    };

}
