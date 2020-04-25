package GDP.GUIs;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

/**
 *
 * @author rsegui
 */
public class ModifyLinkUI extends javax.swing.JDialog {
/*
    public DefaultComboBoxModel readModel;

    public DefaultComboBoxModel writeModel;
  public DefaultComboBoxModel actionModel;

    public DefaultListModel listModel;
    private DefaultComboBoxModel emptyModel;
    public boolean cancel;

    private void myInit(char[] allowedAlphabet, char[] alphabet) {
        emptyModel = new DefaultComboBoxModel();
        emptyModel.addElement("{ }");
        Character[] array = new Character[allowedAlphabet.length];
        for (int i = 0; i < allowedAlphabet.length; i++) {
            array[i] = new Character(allowedAlphabet[i]);
        }
        readModel = new DefaultComboBoxModel(array);
        if (readModel.getSize() == 0) {
            read.setModel(emptyModel);
        } else {
            read.setModel(readModel);
        }
        array = new Character[alphabet.length];
        for (int i = 0; i < alphabet.length; i++) {
            array[i] = new Character(alphabet[i]);
        }
        writeModel = new DefaultComboBoxModel(array);
        write.setModel(writeModel);
        Character[] actions = new Character[2];
        actions[0] = 'R';
        actions[1] = 'L';
        actionModel = new DefaultComboBoxModel(actions);
        action.setModel(actionModel);
    }
    KeyAdapter common = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (KeyEvent.VK_ENTER == e.getKeyChar()) {
                cancel = false;
                doneActionPerformed(null);
            }
            if (KeyEvent.VK_ESCAPE == e.getKeyChar()) {
                cancel = true;
                doneActionPerformed(null);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    };

    public ModifyLinkUI(boolean modal, char[] allowedAlphabet, char[] alphabet, Link e, Point point) {
        super();
        setModal(modal);
        initComponents();
        setLocationRelativeTo(null);
        myInit(allowedAlphabet, alphabet);
        setTitle(config.getText("add.lnk.dialog.title"));
        pack();
        setResizable(false);
        read.addKeyListener(common);
        write.addKeyListener(common);
        action.addKeyListener(common);
        remove.addKeyListener(common);
        done.addKeyListener(common);
        add.addKeyListener(common);
        preview.addKeyListener(common);
        listModel = new DefaultListModel();
        for (int i = 0; i < e.transitions.size(); i++) {
            listModel.addElement(e.transitions.get(i));
        }
        preview.setModel(listModel);
        if (point != null) {
            point.x -= getWidth() / 2;
            point.y -= getHeight() / 2;
            setLocation(point);
        } else {
            setLocationRelativeTo(null);
        }
        GraphicsEnvironment ge
                = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        if (gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT)) {
            setOpacity(0.7F);
        }
        read.setToolTipText(config.getText("UI.TM.MultiLinkUI.read"));
        write.setToolTipText(config.getText("UI.TM.MultiLinkUI.write"));
        action.setToolTipText(config.getText("UI.TM.MultiLinkUI.action"));
        add.setToolTipText(config.getText("UI.TM.ModifyLinkUI.add"));
        remove.setToolTipText(config.getText("UI.TM.ModifyLinkUI.del"));
        done.setToolTipText(config.getText("UI.done"));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fondo = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        preview = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        read = new javax.swing.JComboBox();
        dos_ptos = new javax.swing.JLabel();
        write = new javax.swing.JComboBox();
        coma = new javax.swing.JLabel();
        action = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        add = new javax.swing.JButton();
        remove = new javax.swing.JButton();
        done = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);

        fondo.setBackground(new java.awt.Color(255, 255, 255));
        fondo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(89, 129, 189), 3, true));

        preview.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        preview.setForeground(new java.awt.Color(89, 129, 189));
        preview.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        preview.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                previewMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(preview);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        read.setOpaque(false);
        jPanel1.add(read);

        dos_ptos.setBackground(new java.awt.Color(255, 255, 255));
        dos_ptos.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        dos_ptos.setText(":");
        dos_ptos.setOpaque(true);
        jPanel1.add(dos_ptos);

        write.setOpaque(false);
        jPanel1.add(write);

        coma.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        coma.setText(",");
        jPanel1.add(coma);

        action.setOpaque(false);
        jPanel1.add(action);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jToolBar2.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jToolBar2.setFloatable(false);
        jToolBar2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar2.setRollover(true);

        add.setBackground(new java.awt.Color(255, 255, 255));
        add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMGS/add7.png"))); // NOI18N
        add.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        add.setFocusable(false);
        add.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add.setOpaque(false);
        add.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });
        jToolBar2.add(add);

        remove.setBackground(new java.awt.Color(255, 255, 255));
        remove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMGS/cancel.png"))); // NOI18N
        remove.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        remove.setFocusable(false);
        remove.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        remove.setOpaque(false);
        remove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeActionPerformed(evt);
            }
        });
        jToolBar2.add(remove);

        done.setBackground(new java.awt.Color(255, 255, 255));
        done.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMGS/Done.png"))); // NOI18N
        done.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        done.setFocusable(false);
        done.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        done.setOpaque(false);
        done.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        done.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneActionPerformed(evt);
            }
        });
        jToolBar2.add(done);

        jPanel3.add(jToolBar2);

        javax.swing.GroupLayout fondoLayout = new javax.swing.GroupLayout(fondo);
        fondo.setLayout(fondoLayout);
        fondoLayout.setHorizontalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                .addContainerGap())
        );
        fondoLayout.setVerticalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeActionPerformed
        if (listModel.size() > 1 && !preview.isSelectionEmpty()) {
            if (readModel.getSize() == 0) {
                read.setModel(readModel);
            }
            int s = preview.getSelectedIndex();
            Transition t = (Transition) listModel.get(s);
            listModel.remove(s);
            readModel.addElement(t.letter);
        }
    }//GEN-LAST:event_removeActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        if (readModel.getSize() > 0) {
            char r, w, a;
            r = (Character) readModel.getSelectedItem();
            w = (Character) writeModel.getSelectedItem();
            a = (Character) actionModel.getSelectedItem();
            readModel.removeElement(r);
            if (readModel.getSize() == 0) {
                read.setModel(emptyModel);
            }
            Transition t;
            t = new Transition(r, w, a);
            listModel.addElement(t);
        }
    }//GEN-LAST:event_addActionPerformed

    private void doneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneActionPerformed
        this.dispose();
    }//GEN-LAST:event_doneActionPerformed

    private void previewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_previewMouseClicked
        if (evt.getClickCount() == 2 || (evt.getButton() == 2 || evt.getButton() == 3)) {
            if (!preview.isSelectionEmpty()) {
                int selection = preview.getSelectedIndex();
                char[] read_array, write_array;
                read_array = new char[readModel.getSize()];
                write_array = new char[writeModel.getSize()];
                for (int i = 0, n = read_array.length; i < n; i++) {
                    read_array[i] = (Character) readModel.getElementAt(i);
                }
                for (int i = 0, n = write_array.length; i < n; i++) {
                    write_array[i] = (Character) writeModel.getElementAt(i);
                }
                ConfirmLinkUI ctd = new ConfirmLinkUI(true, read_array, write_array, evt.getLocationOnScreen());
                ctd.setVisible(true);
                if (!ctd.cancel) {
                    char r, w, a;
                    r = (Character) ctd.readModel.getSelectedItem();
                    w = (Character) ctd.writeModel.getSelectedItem();
                    a = (Character) ctd.actionModel.getSelectedItem();
                    Transition t = new Transition(r, w, a);
                    readModel.addElement(
                            ((Transition) listModel.getElementAt(selection)).letter);
                    readModel.removeElement((Character) r);
                    listModel.set(selection, t);
                } else {
                    System.out.println("cancelado");
                }
            }
        }
    }//GEN-LAST:event_previewMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox action;
    private javax.swing.JButton add;
    private javax.swing.JLabel coma;
    private javax.swing.JButton done;
    private javax.swing.JLabel dos_ptos;
    private javax.swing.JPanel fondo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JList preview;
    private javax.swing.JComboBox read;
    private javax.swing.JButton remove;
    private javax.swing.JComboBox write;
    // End of variables declaration//GEN-END:variables
*/
    }
