package GDP;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Iterator;

public class Node {

    public boolean isSelected;
    private ArrayList<Point> jploc;
    int np;
    public Point loc;
    public nodeBounds bounds;
    public volatile int draggedAtX, draggedAtY;
    protected boolean isPressed, isMouseInside;
    public int number, type;

    // <editor-fold defaultstate="collapsed" desc="Builder">
    public Node(Point location) {
        isSelected = false;
        loc = location;
        np = 8;
        bounds = new nodeBounds(loc.x, loc.y, Tools.nodeSize.width, Tools.nodeSize.height);
        loadJPointLocs(np, bounds.r - 4);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Visual Needs">
    public void setLocation(Point new_loc) {
        loc = new_loc;
        bounds.x = loc.x;
        bounds.y = loc.y;
        loadJPointLocs(np, bounds.r - 4);
    }

    public void setLocation(int x, int y) {
        loc.x = x;
        loc.y = y;
        bounds.x = loc.x;
        bounds.y = loc.y;
        loadJPointLocs(np, bounds.r - 4);
    }

    public boolean isPointInside(Point p) {
        return bounds.isInsideCircleBounds(p);
    }

    @Override
    public String toString() {
        return "n" + Integer.toString(number);
    }

    private void loadJPointLocs(int N, double R) {
        jploc = new ArrayList<>();
        double angle = ((2 * Math.PI) / (double) N);
        int x = bounds.x + 21;
        int y = bounds.y + 21;
        for (int i = 0; i < N; i++) {
            double a = angle * i;
            int xp = x + (int) (R * Math.cos(a));
            int yp = y + (int) (R * Math.sin(a));
            jploc.add(new Point(xp, yp));
        }
    }

    void mouseIN() {
        isMouseInside = true;
    }

    void mouseOUT() {
        isMouseInside = false;
    }

    public void paint(Graphics2D g, ImageObserver view_port) {
        if (isSelected) {
            g.drawImage(Tools.nodeSelectedImg, loc.x, loc.y, view_port);
        } else {
            g.drawImage(Tools.nodeImg, loc.x, loc.y, view_port);
        }
        g.setFont(new Font("Dialog", Font.BOLD, 15));
        switch (toString().length()) {
            case 2:
                g.drawString(toString(), loc.x + bounds.width / 4 + 3, loc.y + bounds.height / 2 + 5);
                break;
            case 3:
                g.drawString(toString(), loc.x + bounds.width / 5, loc.y + bounds.height / 2 + 5);
                break;
            case 4:
                g.setFont(new Font("Dialog", Font.BOLD, 10));
                g.drawString(toString(), loc.x + bounds.width / 5, loc.y + bounds.height / 2 + 5);
                break;
            default:
                g.setFont(new Font("Dialog", Font.BOLD, 10));
                g.drawString(toString(), loc.x + bounds.width / 6, loc.y + bounds.height / 2 + 5);
                break;
        }
        if (isMouseInside) {
            Iterator<Point> it = jploc.iterator();
            Point p;
            while (it.hasNext()) {
                p = it.next();
                g.drawImage(Tools.dotImg, p.x, p.y, view_port);
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Bounds Handler">
    /**
     *
     */
    public class nodeBounds {

        /**
         *
         */
        public int x,
                /**
                 *
                 */
                y,
                /**
                 *
                 */
                width,
                /**
                 *
                 */
                height,
                /**
                 *
                 */
                r,
                /**
                 *
                 */
                ir,
                /**
                 *
                 */
                iw,
                /**
                 *
                 */
                ih;
        private Polygon extendedBounds;

        /**
         *
         * @param x
         * @param y
         * @param width
         * @param height
         * @param iw
         * @param ih
         */
        public nodeBounds(int x, int y, int width, int height, int iw, int ih) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.iw = iw;
            this.ih = ih;
            r = width / 2;
            ir = r - 8;
            extendedBounds = null;
        }

        /**
         *
         * @param x
         * @param y
         * @param width
         * @param height
         */
        public nodeBounds(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            r = width / 2;
            ir = r - 8;
            extendedBounds = null;
        }

        /**
         *
         * @return
         */
        public int getCenterX() {
            return x + (width / 2);
        }

        /**
         *
         * @return
         */
        public int getCenterY() {
            return y + (height / 2);
        }

        /**
         *
         * @param p
         * @return
         */
        public boolean isInsideRectangleBounds(Point p) {
            return ((x < p.x && p.x < (x + width)) && (y < p.y && p.y < (y + height)));
        }

        /**
         *
         * @param p
         * @return
         */
        public boolean isInsideCircleBounds(Point p) {
            return Math.sqrt(Math.pow(Math.abs(getCenterX() - p.x), 2) + Math.pow(Math.abs(getCenterY() - p.y), 2)) < r;
        }

        /**
         *
         * @param p
         * @return
         */
        public boolean isInsideConnectingRingBounds(Point p) {
            int d;
            d = (int) Math.sqrt(Math.pow(Math.abs(getCenterX() - p.x), 2) + Math.pow(Math.abs(getCenterY() - p.y), 2));
            return ir < d && d < r;
        }

        /**
         *
         * @return
         */
        public Rectangle getRectBounds() {
            return new Rectangle(x, y, width, height);
        }

        /**
         *
         * @return
         */
        public Polygon getRealBounds() {
            extendedBounds = new Polygon();
            int my = getCenterY();
            extendedBounds.addPoint(x, y);
            extendedBounds.addPoint(x + width, y);
            extendedBounds.addPoint(x + width, y + height);
            extendedBounds.addPoint(x, y + height);
            return extendedBounds;
        }

        /**
         *
         * @return
         */
        public int MinimalX() {
            return x;
        }

        /**
         *
         * @return
         */
        public int MinimalY() {
            return y;
        }

        /**
         *
         * @return
         */
        public int MaximalX() {
            return x + width;
        }

        /**
         *
         * @return
         */
        public int MaximalY() {
            return y + height;
        }
    }// </editor-fold>

}
