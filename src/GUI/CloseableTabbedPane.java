package GUI;

import GLT.motor.Motor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class CloseableTabbedPane extends JTabbedPane implements MouseListener {

    //lista de motores disponibles
    LinkedList<Motor> motors = new LinkedList<>();

    public Motor getMotor() {
        if (getTabCount() > 0) {
            return motors.get(getSelectedIndex());
        }
        return null;
    }

    public void addMotor(Motor newMotor) {
        motors.add(newMotor);
    }

    private static final long serialVersionUID = 1L;

    public CloseableTabbedPane() {
        addMouseListener(this);
    }

    public CloseableTabbedPane(int tabPlacement) {
        super(tabPlacement);
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    @Override
    public void addTab(String title, Component component) {
        this.addTab(title, component, null);
    }

    public void addTab(String title, Component component, Icon extraIcon) {
        super.addTab(title, new CloseTabIcon(extraIcon), component);
    }

    public void addT(String title, Icon c, Component component) {
        super.addTab(title, c, component);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());
        if (tabNumber < 0) {
            return;
        }
        Rectangle rect = ((CloseTabIcon) getIconAt(tabNumber)).getBounds();
        if (rect.contains(e.getX(), e.getY())) {
            this.motors.remove(tabNumber);
            this.removeTabAt(tabNumber);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}

class CloseTabIcon implements Icon {

    private int x_pos;
    private int y_pos;
    private int width;
    private int height;
    private Icon fileIcon;
    private Color col;

    public CloseTabIcon(Icon fileIcon) {
        this.fileIcon = fileIcon;
        width = 16;
        height = 16;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        this.x_pos = x;
        this.y_pos = y;

        col = g.getColor();

        g.setColor(Color.black);
        int y_p = y + 2;
        // g.drawLine(x+1, y_p, x+12, y_p);
        // g.drawLine(x+1, y_p+13, x+12, y_p+13);
        // g.drawLine(x, y_p+1, x, y_p+12);
        // g.drawLine(x+13, y_p+1, x+13, y_p+12);
        g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
        g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);
        g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
        g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);
        g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
        g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);
        g.setColor(col);
        if (fileIcon != null) {
            fileIcon.paintIcon(c, g, x + width, y_p);
        }
    }

    @Override
    public int getIconWidth() {
        return width + (fileIcon != null ? fileIcon.getIconWidth() : 0);
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x_pos, y_pos, width, height);
    }
}
