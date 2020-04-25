package GDP;

import GDP.GMS.ViewPort;
import GDP.GUIs.ConfirmLinkUI;
import GDP.GUIs.MultiLinkCreationUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

/**
 *
 * @author rsegui
 */
public class Container {

    ViewPort view_port;
    LinkedList<Node> nodes;
    LinkedList<Connector> connectors;
    Iterator<Node> itn;
    Iterator<Connector> itc;
    private final Color ccolor;
    private final Color ncolor;
    boolean isAdding, dirigido;

    //interfaces
    ConfirmLinkUI clui;

    public VisualController parent;

    public Container(VisualController parent) {
        nodes = new LinkedList<>();
        connectors = new LinkedList<>();
        isAdding = false;
        this.parent = parent;
        ccolor = new Color(79, 129, 189);
        ncolor = Color.black;
        //inicializar ventanas modales y forms
        initGUIs();
        askGraphKind();
    }

    public void setParent(VisualController parent) {
        this.parent = parent;
    }

    // <editor-fold defaultstate="collapsed" desc="Graphics Management">
    /**
     *
     * @param g
     */
    public void paint(Graphics2D g) {
        g.setColor(ccolor);
        itc = connectors.iterator();
        while (itc.hasNext()) {
            itc.next().paint(g, view_port);
        }
        g.setColor(ncolor);
        itn = nodes.iterator();
        Node n;
        int i = 0;
        while (itn.hasNext()) {
            n = itn.next();
            n.number = i;
            n.paint(g, view_port);
            i++;
        }
    }

    /**
     *
     */
    public void UpdateView() {
        view_port.paint(view_port.getGraphics());
    }

    /**
     *
     */
    public void UpdateUI() {
        view_port.updateUI();
    }
    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="Event Management">
    boolean keyPressed(char keyChar) {
        boolean working = true;
        if (keyChar == KeyEvent.VK_DELETE) {
            working = false;
            removeSelection();
        }
        return !working;
    }

    boolean mousePressed(MouseEvent e) {
        itn = nodes.iterator();
        Node i;
        boolean working = true;
        while (itn.hasNext() && working) {
            if ((i = itn.next()).isPointInside(e.getPoint())) {
                if (i.bounds.isInsideConnectingRingBounds(e.getPoint())) {
                    connectors.add(new Connector(i, e.getPoint(), dirigido));
                    isAdding = true;
                    working = false;
                } else {
                    i.isPressed = true;
                    i.draggedAtX = e.getX() - i.loc.x;
                    i.draggedAtY = e.getY() - i.loc.y;
                    working = false;
                }
            }
        }
        return !working;
    }

    boolean mouseReleased(MouseEvent e) {
        if (isAdding) {
            itc = connectors.iterator();
            Connector c;
            Node n;
            while (itc.hasNext() && isAdding) {
                if ((c = itc.next()).isCreating) {
                    itn = nodes.iterator();
                    while (itn.hasNext() && isAdding) {
                        if ((n = itn.next()).isPointInside(e.getPoint())) {
                            isAdding = false;
                            if (!exists(c.start.number, n.number)) {
                                c.setEndingNode(n);
                                clui.showUP(e.getLocationOnScreen(), null);
                                c.updateTxt(clui.getPeso());
                            } else {
                                this.parent.nc.errReport("La arista ya existe!");
                                itc.remove();
                            }
                        }
                    }
                    if (isAdding) {
                        isAdding = false;
                        itc.remove();
                    }
                }
            }
            UpdateView();
            return !isAdding;
        } else {
            itn = nodes.iterator();
            Node i = null;
            boolean working = true;
            while (itn.hasNext() && working) {
                if ((i = itn.next()).isPressed) {
                    i.isPressed = false;
                    working = false;
                }
            }
            if (i != null) {
                if (parent.getSize().width < i.loc.x || parent.getSize().height < i.loc.y) {
                    parent.setPreferredSize(
                            new Dimension(
                                    Math.max(parent.getSize().width, e.getX() + 150),
                                    Math.max(parent.getSize().height, e.getY() + 150)));
                    view_port.updateUI();
                }
            }
            return !working;
        }
    }

    boolean mouseDragged(MouseEvent e) {
        boolean working = true;
        if (isAdding) {
            itc = connectors.iterator();
            Connector c;
            while (itc.hasNext() && working) {
                if ((c = itc.next()).isCreating) {
                    c.updateEndPoint(e.getPoint());
                    working = false;
                }
            }
        }
        itn = nodes.iterator();
        Node i;
        while (itn.hasNext() && working) {
            if ((i = itn.next()).isPressed) {
                if (e.getX() - i.draggedAtX > 0 && e.getY() - i.draggedAtY > 0) {
                    i.setLocation(e.getX() - i.draggedAtX, e.getY() - i.draggedAtY);
                }
                working = false;
            }
        }
        UpdateView();
        return !working;
    }

    boolean mouseClicked(MouseEvent e) {
        //simple click
        if (e.getButton() == MouseEvent.BUTTON3) {
            boolean working = true;
            itn = nodes.iterator();
            Node i;
            while (itn.hasNext() && working) {
                if ((i = itn.next()).isPointInside(e.getPoint())) {
                    Tools.showPopupNode(e.getPoint(), i.number, e.getLocationOnScreen(), parent);
                    working = false;
                }
            }
            itc = connectors.iterator();
            Connector c;
            while (itc.hasNext() && working) {
                if ((c = itc.next()).isPointInside(e.getPoint())) {
                    Tools.showPopupLink(e.getPoint(), c.start.number, c.end.number, parent);
                    working = false;
                }
            }
            return !working;
        } else {
            boolean working = true;
            itn = nodes.iterator();
            Node i;
            while (itn.hasNext() && working) {
                if ((i = itn.next()).isPointInside(e.getPoint())) {
                    i.isSelected = !i.isSelected;
                    working = false;
                }
            }
            itc = connectors.iterator();
            Connector c;
            while (itc.hasNext()) {
                if ((c = itc.next()).isPointInside(e.getPoint())) {
                    c.setSelected(!c.isSelected());
                    working = false;
                }
            }
            UpdateView();
            return !working;
        }
    }

    void mouseMoved(MouseEvent e) {
        itn = nodes.iterator();
        Node i;
        boolean found = false, paint = false;
        int n = -1;
        while (itn.hasNext() && !found) {
            if ((i = itn.next()).isPointInside(e.getPoint())) {
                found = true;
                i.mouseIN();
                n = i.number;
            }
        }
        if (found) {
            itn = nodes.iterator();
            while (itn.hasNext()) {
                if ((i = itn.next()).number != n) {
                    i.mouseOUT();
                }
            }
            UpdateView();
        } else {
            itn = nodes.iterator();
            while (itn.hasNext()) {
                if ((i = itn.next()).isMouseInside) {
                    i.mouseOUT();
                    paint = true;
                }
            }
            if (paint) {
                UpdateView();
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Interaction">
    void performMultipleSelection(Rectangle selection) {
        itn = nodes.iterator();
        Node i;
        while (itn.hasNext()) {
            if (selection.contains((i = itn.next()).bounds.getRectBounds())) {
                if (!i.isSelected) {
                    i.isSelected = !i.isSelected;
                }
            }
        }
        UpdateView();
    }

    Integer[] getSelectedNumbers() {
        LinkedList<Integer> list = new LinkedList<>();
        itn = nodes.iterator();
        Node i;
        int count = 0;
        while (itn.hasNext()) {
            if ((i = itn.next()).isSelected) {
                list.add(i.number);
                count++;
            }
        }
        Integer[] selection = new Integer[count];
        return list.toArray(selection);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Controller Interface">
    /**
     *
     * @param p
     */
    public void AddNode(Point p) {
        nodes.add(new Node(p));
        UpdateView();
    }

    public void AddConnector(Node i, Point point) {
        connectors.add(new Connector(i, point, dirigido));
        UpdateView();
    }

    public void AddConnector(Node from, Node to) {
        connectors.add(new Connector(from, to, dirigido));
        UpdateView();
    }

    public void AddConnector(int from, int to, String txt) {
        Connector c = new Connector(nodes.get(from), nodes.get(to), dirigido);
        c.updateTxt(txt);
        connectors.add(c);
        UpdateView();
    }

    public void DelNode(int number) {
        itn = nodes.iterator();
        itc = connectors.iterator();
        Connector c;
        boolean working = true;
        while (itn.hasNext() && working) {
            if (itn.next().number == number) {
                itn.remove();
                while (itc.hasNext()) {
                    c = itc.next();
                    if (c.start.number == number || c.end.number == number) {
                        itc.remove();
                    }
                }
                working = false;
            }
        }
        UpdateView();
    }

    public void DelConnector(int from, int to) {
        itc = connectors.iterator();
        Connector c;
        boolean working = true;
        while (itc.hasNext() && working) {
            c = itc.next();
            if (c.start.number == from && c.end.number == to) {
                itc.remove();
                working = false;
            }
        }
        UpdateView();
    }

    public void removeSelection() {
        removeConnectorSelection();
        removeNodeSelection();
    }

    private void removeConnectorSelection() {
        Connector c;
        itc = connectors.iterator();
        while (itc.hasNext()) {
            c = itc.next();
            if (c.isSelected()) {
                itc.remove();
            }
        }
    }

    private void removeNodeSelection() {
        Integer[] array = getSelectedNumbers();
        if (array.length == nodes.size()) {
            removeAll();
        } else {
            removeNumbers(array);
        }
    }

    private void removeNumbers(Integer selection[]) {
        Connector c;
        for (int i = selection.length - 1; i >= 0; i--) {
            nodes.remove(selection[i].intValue());
            itc = connectors.iterator();
            while (itc.hasNext()) {
                c = itc.next();
                if (c.start.number == selection[i] || c.end.number == selection[i]) {
                    itc.remove();
                }
            }
        }
        UpdateView();
    }

    public void removeAll() {
        System.out.println("Remover todos!");
        connectors.clear();
        nodes.clear();
        UpdateView();
    }

    public Rectangle getExportImageRectangle() {
        int x = Integer.MAX_VALUE,
                y = Integer.MAX_VALUE,
                xm = Integer.MIN_VALUE,
                ym = Integer.MIN_VALUE;
        itn = nodes.iterator();
        itc = connectors.iterator();
        if (!itn.hasNext() && !itc.hasNext()) {
            return new Rectangle(0, 0);
        }
        Node e;
        while (itn.hasNext()) {
            e = itn.next();
            if (x > e.bounds.MinimalX()) {
                x = e.bounds.MinimalX();
            }
            if (y > e.bounds.MinimalY()) {
                y = e.bounds.MinimalY();
            }
            if (xm < e.bounds.MaximalX()) {
                xm = e.bounds.MaximalX();
            }
            if (ym < e.bounds.MaximalY()) {
                ym = e.bounds.MaximalY();
            }
        }
        Connector c;
        while (itc.hasNext()) {
            c = itc.next();
            if (y > c.MinimalY()) {
                y = c.MinimalY();
            }
        }
        Rectangle r = new Rectangle(x, y, xm - x, ym - y);
        return r;
    }

    public void PaintFor(Graphics2D g) {
        this.paint(g);
    }

    public void DeselectAll() {
        itn = nodes.iterator();
        Node i;
        while (itn.hasNext()) {
            i = itn.next();
            if (i.isSelected) {
                i.isSelected = !i.isSelected;
            }
        }
        itc = connectors.iterator();
        Connector c;
        while (itc.hasNext()) {
            c = itc.next();
            if (c.isSelected()) {
                c.setSelected(false);
            }
        }
        UpdateView();
    }

    public Point[] getLocations() {
        Point[] loc = new Point[nodes.size()];
        itn = nodes.iterator();
        int i = 0;
        while (itn.hasNext()) {
            loc[i] = itn.next().loc;
            i++;
        }
        return loc;
    }

    public int getNodeCount() {
        return nodes.size();
    }

    // </editor-fold>
    MultiLinkCreationUI mlcd;

    void showMultiLinkCreationDialog(int from, Point where) {
        mlcd.showUp(where, from);
    }

    private void initGUIs() {
        mlcd = new MultiLinkCreationUI(true, nodes, connectors);
        clui = new ConfirmLinkUI(true);
    }

    private void askGraphKind() {
        JRadioButton butt[] = new JRadioButton[2];
        butt[0] = new JRadioButton("GRAFO DIRIGIDO");
        butt[0].setSelected(true);
        butt[1] = new JRadioButton("GRAFO NO DIRIGIDO");
        ButtonGroup group = new ButtonGroup();
        for (JRadioButton j : butt) {
            group.add(j);
        }
        JOptionPane.showMessageDialog(parent, butt, "Seleccione tipo de grafo", JOptionPane.QUESTION_MESSAGE);
        dirigido = butt[0].isSelected();
    }

    void markNode(int nodeNumber) {
        System.out.println("Pintando nodo: #" + nodeNumber);
        itn = nodes.iterator();
        Node n;
        while (itn.hasNext()) {
            n = itn.next();
            n.isSelected = n.number == nodeNumber;
        }
        UpdateView();
    }

    void markEdge(int nodeFrom, int nodeDest) {
        itc = connectors.iterator();
        Connector c;
        while (itc.hasNext()) {
            c = itc.next();
            if (c.start.number == nodeFrom && c.end.number == nodeDest) {
                c.setSelected(true);
            } else {
                c.setSelected(false);
            }
        }
        UpdateView();
    }

    private boolean exists(int from, int to) {
        itc = connectors.iterator();
        Connector x;
        boolean found = false;
        while (itc.hasNext() && !found) {
            x = itc.next();
            if (dirigido) {
                if (!x.isCreating && x.start.number == from && x.end.number == to) {
                    found = true;
                }
            } else {
                if (!x.isCreating && ((x.start.number == from && x.end.number == to) || (x.start.number == to && x.end.number == from))) {
                    found = true;
                }
            }
        }
        return found;
    }

    Double[][] getAdjMatrix() {
        int cant = nodes.size();
        Double[][] matrix = new Double[cant][cant];
        for (Double[] row : matrix) {
            Arrays.fill(row, 0, row.length, Double.NaN);
        }
        itc = connectors.iterator();
        Connector c;
        while (itc.hasNext()) {
            c = itc.next();
            if (dirigido) {
                matrix[c.start.number][c.end.number] = Double.parseDouble(c.getTxt());
            } else {
                matrix[c.start.number][c.end.number] = Double.parseDouble(c.getTxt());
                matrix[c.end.number][c.start.number] = Double.parseDouble(c.getTxt());
            }
        }
        return matrix;
    }

    ArrayList<ArrayList<Integer>> getAdjList() {
        return null;
    }
}
