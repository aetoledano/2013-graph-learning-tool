package GDP;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Connector {

    private Color normal, seleccionado;
    private boolean selected;
    private int lw, lh;
    public Node start, end;
    public Polygon bounds;
    private double r;
    private double angle;
    private int a1p, a2p, common_point;
    private int ratio;
    public boolean isCreating, isDirigido;
    private Point endPoint;
    private Point2D txtPoint;
    public String txt;
    private int txtSize;
    private Font font;
    private FontMetrics fm;

    public Connector(Node i, Node f, boolean isDirigido) {
        end = f;
        isCreating = false;
        commonBuilder(i, isDirigido);
    }

    public Connector(Node i, Point p, boolean isDirigido) {
        end = null;
        isCreating = true;
        endPoint = p;
        commonBuilder(i, isDirigido);
    }

    private void commonBuilder(Node i, boolean isDirigido) {
        this.isDirigido = isDirigido;
        if (isDirigido) {
            lw = (int) Tools.lazoSize.getWidth();
            lh = (int) Tools.lazoSize.getHeight();
        } else {
            lw = (int) Tools.lazo2Size.getWidth();
            lh = (int) Tools.lazo2Size.getHeight();
        }
        start = i;
        bounds = new Polygon();
        r = start.bounds.width / 2;
        ratio = 3;
        txt = null;
        font = new Font("Dialog", Font.BOLD, 13);
        fm = new FontMetrics(font) {
        };
        txtSize = 0;
        selected = false;
        seleccionado = new Color(246, 62, 59);
    }

    // <editor-fold defaultstate="collapsed" desc="Sets">
    public void setStartingNode(Node init) {
        this.start = init;
    }

    public void setEndingNode(Node end) {
        this.end = end;
        isCreating = false;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Gets">
    public boolean isSelected() {
        return selected;
    }

    public boolean isPointInside(Point p) {
        return bounds.contains(p);
    }

    public int MinimalY() {
        int y[] = bounds.ypoints;
        int a = y[0];
        for (int i = 1, n = bounds.npoints; i < n; i++) {
            if (a > y[i]) {
                a = y[i];
            }
        }
        return a;
    }

    public String getTxt() {
        return txt;
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Updates">
    void updateEndPoint(Point point) {
        endPoint = point;
    }

    void updateTxt(String txt) {
        this.txt = txt;
        txtSize = (int) fm.getStringBounds(this.txt, null).getWidth();
        System.out.println("Longitud de la cadena: " + txtSize);
    }

    private void updateAngle(double x1, double y1, double x2, double y2) {
        angle = Math.atan((y2 - y1) / (x2 - x1));
    }

    private void updateBounds() {
        bounds = new Polygon();
        if (isCreating) {
            bounds = reloadBounds(start.bounds.getCenterX(), start.bounds.getCenterY(),
                    endPoint.x, endPoint.y);
        } else {
            bounds = reloadBounds(start.bounds.getCenterX(), start.bounds.getCenterY(),
                    end.bounds.getCenterX(), end.bounds.getCenterY());
        }
    }

    private Polygon reloadBounds(double x1, double y1, double x2, double y2) {
        updateAngle(x1, y1, x2, y2);
        double dx = x2 - x1;
        double dy = y2 - y1;
        double dist = Math.sqrt(dx * dx + dy * dy);
        ArrayList<Line> lines = new ArrayList<>();
        int strlen;

        /*
         out         5----------------------------------------------\4                  IN
         out         |                                               \                  IN
         out         |                                                \                 IN
         out     ----------------------------------------------------------------       IN
         out         |                                                / 3               IN
         out         |                                               /                  IN
         out         1----------------------------------------------/2                  IN
         */
        //define la linea perpendicular a la del centro que esta en el nodo de salida
        lines.add(new Line(dx, dy, -(dx * x1 + dy * y1) - 25 * dist));//0
        if (isCreating) {
            //define la linea que perpendicular a la central que esta en la punta d la flecha
            lines.add(new Line(dx, dy, -(dx * x2 + dy * y2) - dist));//1
            //linea que define la parte atras de la cabeza d la flecha
            lines.add(new Line(dx, dy, -(dx * x2 + dy * y2) + 15 * dist));//2
        } else {
            //define la linea que perpendicular a la central que esta en el nodo de llegada
            lines.add(new Line(dx, dy, -(dx * x2 + dy * y2) + 25 * dist));//1
            //linea que define la parte atras de la cabeza d la flecha
            lines.add(new Line(dx, dy, -(dx * x2 + dy * y2) + 40 * dist));//2
        }
        //definne las lineas laterales
        // la de abajo
        lines.add(new Line(dy, -dx, -(dy * x1 + (-dx * y1)) - 4 * dist));//3
        //la de arriba
        lines.add(new Line(dy, -dx, -(dy * x1 + (-dx * y1)) + 4 * dist));//4
        //define la linea central
        lines.add(new Line(dy, -dx, -(dy * x1 + (-dx * y1))));//5
        if (txt != null) {
            double val = Math.abs((Math.abs(dx) > Math.abs(dy)) ? dx / 2 : dy / 2) - txtSize / 2;
            Line l1, l2;
            if (x1 < x2) {
                l1 = lines.get(3);
                l2 = new Line(dx, dy, -(dx * x1 + dy * y1) - (val * dist));
            } else {
                l1 = new Line(dy, -dx, -(dy * x1 + (-dx * y1)) - 14 * dist);
                l2 = new Line(dx, dy, -(dx * x1 + dy * y1) - ((val + txtSize) * dist));
            }
            txtPoint = GIP(l1, l2);
        }
        //definir las prioridades de las intersecciones de los puntos
        int priority[];
        if (isDirigido) {
            priority = new int[10];
            //0
            priority[0] = 0;
            priority[1] = 3;
            //1
            priority[2] = 3;
            priority[3] = 2;
            a1p = 1;
            //2
            priority[4] = 1;
            priority[5] = 5;
            common_point = 2;
            //3
            priority[6] = 4;
            priority[7] = 2;
            a2p = 3;
            //4
            priority[8] = 4;
            priority[9] = 0;

        } else {
            priority = new int[8];
            //0
            priority[0] = 0;
            priority[1] = 3;
            //1
            priority[2] = 3;
            priority[3] = 1;
            //2
            priority[4] = 1;
            priority[5] = 4;
            //3
            priority[6] = 4;
            priority[7] = 0;
        }
        Polygon p = new Polygon();
        Point2D pnt;
        for (int i = 1; i < priority.length; i += 2) {
            pnt = GIP(lines.get(priority[i - 1]), lines.get(priority[i]));
            p.addPoint((int) pnt.getX(), (int) pnt.getY());
        }
        return p;
    }

    protected class Line {

        double a, b, c;

        public Line(double a, double b, double c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    private Point2D GIP(Line l1, Line l2) {
        double x = (l1.b * l2.c - l2.b * l1.c) / (l1.a * l2.b - l2.a * l1.b);
        double y = (l2.a * l1.c - l1.a * l2.c) / (l1.a * l2.b - l2.a * l1.b);
        return new Point2D.Double(x, y);
    }

    private void lazoBoundsCalculate() {
        bounds = new Polygon();
        //punto superior izquierdo
        bounds.addPoint(start.bounds.getCenterX() - lw / 2, start.loc.y - lh);
        bounds.addPoint(start.bounds.getCenterX() - lw / 2, start.loc.y);
        bounds.addPoint(start.bounds.getCenterX() + lw / 2, start.loc.y);
        bounds.addPoint(start.bounds.getCenterX() + lw / 2, start.loc.y - lh);
        if (txt != null) {
            txtPoint = new Point2D.Double(start.bounds.getCenterX() - txtSize / 2, start.loc.y - lh);
        }
        angle = 0;
    }
    // </editor-fold>    

    // <editor-fold defaultstate="collapsed" desc="Paint">
    public void paint(Graphics2D g, ImageObserver view_port) {
        if (selected) {
            normal = g.getColor();
            g.setColor(seleccionado);
        }
        if ((start.equals(end) || (end == null && start.isPointInside(endPoint)))) {
            lazoBoundsCalculate();
            if (isDirigido) {
                g.drawImage(Tools.lazoImg, bounds.xpoints[0], bounds.ypoints[0], view_port);
            } else {
                g.drawImage(Tools.lazo2Img, bounds.xpoints[0], bounds.ypoints[0], view_port);
            }
        } else if (isCreating && !isDirigido) {
            g.drawLine(FrX(), FrY(), ToX(), ToY());
        } else {
            updateBounds();
            //g.drawPolygon(bounds);
            g.drawLine(FrX(), FrY(), ToX(), ToY());
            if (isDirigido) {
                g.drawLine(A1X(), A1Y(), ACX(), ACY());
                g.drawLine(A2X(), A2Y(), ACX(), ACY());
            }
        }
        if (txt != null && txtPoint != null) {
            AffineTransform at;
            g.setFont(font);
            at = g.getTransform();
            g.rotate(angle, txtPoint.getX(), txtPoint.getY());
            g.drawString(txt, (float) txtPoint.getX(), (float) txtPoint.getY());
            g.setTransform(at);
        }
        if (selected) {
            g.setColor(normal);
        }
    }

    public int FrX() {
        return start.bounds.getCenterX();
    }

    public int FrY() {
        return start.bounds.getCenterY();
    }

    public int ToX() {
        return (isCreating) ? endPoint.x : end.bounds.getCenterX();
    }

    public int ToY() {
        return (isCreating) ? endPoint.y : end.bounds.getCenterY();
    }

    public int A1X() {
        return bounds.xpoints[a1p];
    }

    public int A1Y() {
        return bounds.ypoints[a1p];
    }

    public int A2X() {
        return bounds.xpoints[a2p];
    }

    public int A2Y() {
        return bounds.ypoints[a2p];
    }

    public int ACX() {
        return bounds.xpoints[common_point];
    }

    public int ACY() {
        return bounds.ypoints[common_point];
    }// </editor-fold> 
}
