package GDP;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.EmptyBorder;

public class GMS extends JScrollPane implements iMarker{

    public VisualController vc;

    // <editor-fold defaultstate="collapsed" desc="Builder">
    public GMS() {
        build(false);
    }

    public GMS(boolean load) {
        build(load);
    }

    private Container Load() {
        //se cargara el archivo del container
        //y se correra un update UI
        return null;
    }

    private void build(boolean load) {
        if (load) {
            Container loaded = Load();
            vc = new VisualController(loaded);
            commonInit();
        } else {
            vc = new VisualController();
            commonInit();
        }
    }

    private void commonInit() {
        ViewPort viewPort = new ViewPort();
        Tools.Init();
        viewPort.setView(vc);
        setViewport(viewPort);
        vc.setContainerViewPort(viewPort);
        setAutoscrolls(true);
        setRequestFocusEnabled(true);
        setNextFocusableComponent(vc);
        setBorder(new EmptyBorder(0, 0, 0, 0));
    }
// </editor-fold>

    @Override
    public void markNode(int nodeNumber) {
        vc.markNode(nodeNumber);
    }

    @Override
    public void markEdge(int nodeFrom, int nodeDest) {
        vc.markEdge(nodeFrom, nodeDest);
    }

    @Override
    public void notify(String text) {
        vc.nc.quickReport(text);
    }

    // <editor-fold defaultstate="collapsed" desc="ViewPort with WHITE background - DoubleBuffered">
    /**
     *
     */
    public class ViewPort extends JViewport {

        Dimension oldSize;
        Image buffer;
        Graphics2D bg;

        /**
         *
         */
        public ViewPort() {
            setBackground(Color.WHITE);
        }

        /**
         *
         * @param g
         */
        @Override
        public void paint(Graphics g) {
            if (isShowing()) {
                if (oldSize == null || buffer == null || oldSize != getSize()) {
                    oldSize = getSize();
                    buffer = createImage((int) oldSize.getWidth(), (int) oldSize.getHeight());
                    bg = (Graphics2D) buffer.getGraphics();
                }
                bg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                bg.setStroke(new BasicStroke(2.6f));
                super.paint(bg);
                g.drawImage(buffer, 0, 0, this);
            }
        }
    }

    /**
     *
     * @return @throws HeadlessException
     */
    @Override
    public Point getMousePosition() throws HeadlessException {
        Point pos = viewport.getMousePosition();
        if (pos == null) {
            return pos;
        }
        pos.x += viewport.getViewPosition().x - 25;
        pos.y += viewport.getViewPosition().y - 25;
        return pos;
    }// </editor-fold>  

    //opciones que seran accesibles
    public void Save() {
        //solo se salvara el container
        vc.getContainer();
    }

    public void ExportAsPng_RGB() {
    }

    public void ExportAsPng_blackNwhite() {
    }

    public Double[][] getGraphAdjMatrix() {
        return vc.container.getAdjMatrix();
    }

    public ArrayList<ArrayList<Double>> getGraphAdjList() {
        return null;
    }

}
