package GDP;

import GDP.GMS.ViewPort;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 *
 * @author rsegui
 */
public class NotifyCenter {

    private int max, begin;

    /**
     *
     * @param where
     */
    public NotifyCenter(JComponent where) {
        where_to_show = where;
        dibujar = false;
        erase = true;
        tmp = where.getBounds();
        alpha = 150;
        red = 67;
        green = 137;
        blue = 211;
        msg_font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        begin = 20;
    }

    JComponent where_to_show;
    RoundRectangle2D bounds;
    Rectangle tmp;

    public boolean dibujar, erase;
    Color own;
    Font msg_font;
    FontMetrics fm;
    int red, green, blue, alpha, stry;

    /**
     *
     * @param g
     */
    public void Paint(Graphics2D g) {
        if (dibujar && (lastmsg != null || msg != null)) {
            updateBounds();
            own = new Color(red, green, blue, alpha);
            g.setColor(own);
            g.fill(bounds);
            g.setFont(msg_font);
            stry = (int) bounds.getY() + msg_font.getSize() + msg_font.getSize() / 2;
            if (reporting) {
                switch (kind) {
                    case 0://case for quick&extended
                        g.setColor(Color.WHITE);
                        g.drawString(msg, (int) (bounds.getX() + begin), stry);
                        break;
                    case 1://case for err
                        g.setColor(new Color(255, 70, 70, 255));
                        g.drawString(msg, (int) (bounds.getX() + begin), stry);
                        break;
                    case 3://case for warnings
                        g.setColor(new Color(255, 250, 81, 255));
                        g.drawString(msg, (int) (bounds.getX() + begin), stry);
                        break;
                    case 2://case for living reporter
                        if (live) {
                            if (regex == -1) {
                                g.setColor(Color.WHITE);
                                g.drawString(msg, (int) (bounds.getX() + begin), stry);
                            } else {
                                //mirar aki cuantos caracteres del
                                //analisis se van a mostrar
                                g.setColor(Color.WHITE);
                                int x = (int) (bounds.getX() + begin);
                                for (int i = 0; i < msg.length(); i++) {
                                    if (i == regex) {
                                        g.setColor(Color.BLUE);
                                    } else {
                                        g.setColor(Color.WHITE);
                                    }
                                    g.drawString(String.valueOf(msg.charAt(i)), x, stry);
                                    x += 20;
                                }
                            }
                        } else {
                            g.setColor(Color.WHITE);
                            g.drawString(msg, (int) (bounds.getX() + begin), stry);
                        }
                        break;
                }
            } else {
                g.setColor(Color.WHITE);
                g.drawString("Último: " + lastmsg, (int) (bounds.getX() + begin), stry);
            }
        }
    }

    //updates
    private void updateView() {
        updateBounds();
        where_to_show.paint(where_to_show.getGraphics());
    }

    ViewPort vp;

    private void updateBounds() {
        if (where_to_show instanceof ViewPort) {
            vp = (ViewPort) where_to_show;
            if (!tmp.equals(vp.getViewRect())) {
                tmp = vp.getViewRect();
                tmp.x = vp.getViewPosition().x;
                tmp.y = vp.getViewPosition().y;
                bounds = new RoundRectangle2D.Double(tmp.x + 10, tmp.y + (tmp.height - 60), (tmp.width - 30), 40, 30, 30);
                max = ((int) (bounds.getWidth()) / 20 - 1);
            }
        } else {
            if (!tmp.equals(where_to_show.getBounds())) {
                tmp = where_to_show.getBounds();
                bounds = new RoundRectangle2D.Double(tmp.x + 10, tmp.y + (tmp.height - 60), (tmp.width - 30), 40, 30, 30);
                max = ((int) (bounds.getWidth()) / 19);
            }
        }
    }

    /**
     *
     * @param point
     */
    public void updateMousePosition(Point point) {
        updateBounds();
        if (reporting) {
            dibujar = true;
        } else {
            dibujar = bounds.contains(point);
            if (dibujar) {
                updateView();
                erase = true;
            } else {
                if (erase) {
                    updateView();
                    erase = false;
                }
            }
        }
    }
    //reporting
    int time, delay = 1000, regex = -1;
    String msg, lastmsg;
    boolean reporting, live;
    int kind;//tipos de notificaciones 0 - quick&extended 1-err 2-live 3-warning
    Timer timer;

    private void StartVanish() {
        delay = 70;
        timer.setDelay(delay);
    }

    private void Disipating() {
        alpha -= 15;
        updateView();
    }

    private void resetVanish() {
        timer.stop();
        alpha = 150;
        delay = 1000;
        reporting = false;
        dibujar = false;
        lastmsg = msg;
        updateView();
    }
    //
    ActionListener reporter = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateView();
            time--;
            if (time == 0) {
                StartVanish();
            }
            if (time < 0) {
                Disipating();
            }
            if (alpha == 0) {
                resetVanish();
            }
        }
    };
    ActionListener live_suspend = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            alpha -= 5;
            if (alpha == 0) {
                resetVanish();
            }
            updateView();
        }
    };

    public void quickReport(String msg) {
        report(msg, 3, 0);
    }

    public void warningReport(String msg) {
        report(msg, 2, 3);
    }

    public void errReport(String msg) {
        report(msg, 3, 1);
    }

    public void extendedReport(String msg) {
        report(msg, 20, 0);
    }

    public int getMaxCharacterAllowed() {
        return max;
    }

    private void report(String msg, int t, int k) {
        if (timer != null && timer.isRunning()) {
            resetVanish();
        }
        timer = new Timer(delay, reporter);
        time = t;
        this.msg = msg;
        reporting = true;
        kind = k;
        dibujar = true;
        timer.start();
        updateView();
    }

    //this is for inverting the function of this system
    /**
     *
     * @param msg
     */
    public void startLiveReport(String msg) {
        if (timer != null) {
            resetVanish();
        }
        reporting = true;
        dibujar = true;
        this.msg = msg;
        kind = 2;
        live = false;
        regex = -1;
        updateView();
    }

    /**
     *
     * @param msg
     */
    public void updateLiveReport(String msg) {
        live = true;
        this.msg = msg;
    }

    /**
     *
     * @param str
     * @param regex
     */
    public void updateLiveReport(String str, int regex) {
        live = true;
        this.msg = str;
        this.regex = regex;
        updateView();
    }

    /**
     *
     * @param msg
     */
    public void stopLiveReport(String msg) {
        timer = new Timer(30, live_suspend);
        timer.start();
        if (msg != null) {
            this.msg = msg;
        }
    }

}
