package GDP;

import GDP.GMS.ViewPort;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;

public class VisualController extends JComponent {
    
    NotifyCenter nc;
    Container container;
    int fx, fy, tx, ty, tta_type;
    Dimension dim;
    Color rect_color;
    boolean peforming_multiple_selection, tryingToAdd;

    // <editor-fold defaultstate="collapsed" desc="Builder">
    /**
     *
     */
    public VisualController() {
        container = new Container(this);
        commonInit();
    }
    
    VisualController(Container loaded) {
        container = loaded;
        container.setParent(this);
        commonInit();
    }
    
    private void commonInit() {
        rect_color = new Color(79, 129, 189, 75);
        setRequestFocusEnabled(true);
        dim = getSize();
        setBorder(new EmptyBorder(0, 0, 0, 0));
        AddEvents();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Overriden Paint">
    /**
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        container.paint((Graphics2D) g);
        if (peforming_multiple_selection) {
            g.setColor(rect_color);
            //  g.drawRect(Math.min(fx, tx), Math.min(fy, ty), Math.abs(tx - fx), Math.abs(ty - fy));
            g.drawRoundRect(Math.min(fx, tx), Math.min(fy, ty), Math.abs(tx - fx), Math.abs(ty - fy), 10, 10);
        } else {
            nc.Paint((Graphics2D) g);
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Event Management">
    private void AddEvents() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!container.keyPressed(e.getKeyChar())) {
                    //
                }
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension s = getSize();
                if (!dim.equals(s)) {
                    Rectangle b = getBounds();
                    Rectangle c = container.getExportImageRectangle();
                    if (!b.contains(c)) {
                        dim = new Dimension((c.x + c.width + 50), (c.y + c.height + 50));
                        setPreferredSize(dim);
                        container.UpdateUI();
                    }
                }
            }
        });
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocus();
                if (!container.mousePressed(e)) {
                    fx = e.getX();
                    fy = e.getY();
                    tx = fx;
                    ty = fy;
                    peforming_multiple_selection = true;
                }
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocus();
                if (!container.mouseClicked(e)) {
                    if (e.getButton() == 2 || e.getButton() == 3) {
                        showPopup(e.getPoint());
                    } else {
                        if (tryingToAdd) {
                            tryingToAdd = false;
                            Point loc = e.getPoint();
                            loc.x -= 25;
                            loc.y -= 25;
                            if (tta_type == -1) {
                                
                            } else {
                                
                            }
                        } else {
                            container.DeselectAll();
                        }
                    }
                    
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                requestFocus();
                if (!container.mouseReleased(e)) {
                    tx = e.getX();
                    ty = e.getY();
                    peforming_multiple_selection = false;
                    container.performMultipleSelection(new Rectangle(Math.min(fx, tx), Math.min(fy, ty), Math.abs(tx - fx), Math.abs(ty - fy)));
                }
            }
        });
        
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                
                if (!container.mouseDragged(e)) {
                    tx = e.getX();
                    ty = e.getY();
                }
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                
                container.mouseMoved(e);
                
                nc.updateMousePosition(e.getPoint());
            }
        });
    }

    private void showPopup(Point p) {
        Tools.showPopupContextMenu(p, this);
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="setContainerViewPort and init NotifyCenter">
    /**
     *
     * @param vp
     */
    public void setContainerViewPort(ViewPort vp) {
        container.view_port = vp;
        nc = new NotifyCenter(container.view_port);
    }// </editor-fold>

    void TryToAdd(int type) {
        tryingToAdd = true;
        tta_type = type;
    }
    
    public void SelectAll() {
        container.performMultipleSelection(new Rectangle(0, 0, getWidth(), getHeight()));
    }
    
    Container getContainer() {
        return container;
    }
    
    void setContainer(Container newContainer) {
        this.container = newContainer;
        updateUI();
    }

    void markNode(int nodeNumber) {
       container.markNode(nodeNumber);
    }

    void markEdge(int nodeFrom, int nodeDest) {
        container.markEdge(nodeFrom, nodeDest);
    }
}
