package GDP;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author rsegui
 */
public class Tools {

    static public void Init() {
        if (!init) {
            init = true;
            baseAddr = "/GDP/Resources/";
            //cargar imagenes
            LoadAppImages();
            //cargar los menus
            InitPopupContextMenu();
            InitPopupNode();
        }
    }

    private static boolean init;
    public static Image nodeImg, nodeSelectedImg, dotImg, lazoImg, lazo2Img;
    public static ImageIcon menuNodeIcon, selectionIcon, trashIcon, severalLinksIcon;
    public static Dimension nodeSize, dotSize, lazoSize, lazo2Size;
    public static String baseAddr;
    static private Point where;
    static private VisualController vc;

    public static ImageIcon loadImageIcon(String name) {
        return new ImageIcon(Tools.class.getResource(baseAddr + name));
    }

    private static void LoadAppImages() {
        ImageIcon icon;
        //carga de las imagenes del paquete
        icon = loadImageIcon("node.png");
        nodeSize = new Dimension(icon.getIconWidth(), icon.getIconHeight());
        nodeImg = icon.getImage();
        icon = loadImageIcon("node-selected.png");
        nodeSelectedImg = icon.getImage();
        icon = loadImageIcon("dot.png");
        dotSize = new Dimension(icon.getIconWidth(), icon.getIconHeight());
        dotImg = icon.getImage();
        icon = loadImageIcon("lazo.png");
        lazoSize = new Dimension(icon.getIconWidth(), icon.getIconHeight());
        lazoImg = icon.getImage();
        icon = loadImageIcon("lazo2.png");
        lazo2Size = new Dimension(icon.getIconWidth(), icon.getIconHeight());
        lazo2Img = icon.getImage();

        //iconos de los menus
        menuNodeIcon = loadImageIcon("ad.png");
        selectionIcon = loadImageIcon("sel.png");
        trashIcon = loadImageIcon("trash2.png");
        severalLinksIcon = loadImageIcon("ml.png");
    }

    private static JPopupMenu node;

    static private void InitPopupNode() {
        JMenuItem delNode, severalsLinks;
        delNode = new JMenuItem("Eliminar Nodo", trashIcon);
        severalsLinks = new JMenuItem("Enlazar con...", severalLinksIcon);
        delNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vc.getContainer().DelNode(number_of_node);
            }
        });
        severalsLinks.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                vc.getContainer().showMultiLinkCreationDialog(number_of_node, where);
            }
        });

        node = new JPopupMenu();
        node.add(delNode);
        node.add(severalsLinks);
    }

    static int number_of_node;

    static void showPopupNode(Point coordinates, int number, Point location_on_screen, VisualController parent) {
        if (init) {
            vc = parent;
            where = location_on_screen;
            number_of_node = number;
            node.show(vc, coordinates.x, coordinates.y);
        }
    }

    private static JPopupMenu link;

    private static void InitPopupLink() {
        link = new JPopupMenu();
    }

    static void showPopupLink(Point coordinates, int from, int to, VisualController parent) {
        if (init) {
            vc = parent;
        }
    }

    public static JPopupMenu contextMenu;

    static private void InitPopupContextMenu() {
        JMenuItem selall, delsel, delall, addNewNode;

        selall = new JMenuItem("Seleccionar todos", selectionIcon);
        delsel = new JMenuItem("Eliminar Seleccionados", trashIcon);
        delall = new JMenuItem("Eliminar todos", trashIcon);
        addNewNode = new JMenuItem("Nuevo nodo", menuNodeIcon);

        addNewNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vc.container.AddNode(where);
            }
        });
        selall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vc.SelectAll();
            }
        });

        delsel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vc.getContainer().removeSelection();
            }
        });
        delall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vc.getContainer().removeAll();
            }
        });
        contextMenu = new JPopupMenu();
        contextMenu.add(addNewNode);
        contextMenu.add(selall);
        contextMenu.add(delsel);
        contextMenu.add(delall);
    }

    static void showPopupContextMenu(Point coordinates, VisualController parent) {
        if (init) {
            vc = parent;
            where = coordinates;
            Component comps[] = contextMenu.getComponents();
            if (vc.getContainer().getNodeCount() == 0) {
                for (int i = 1; i < comps.length; i++) {
                    comps[i].setEnabled(false);
                }
            } else {
                for (Component c : comps) {
                    c.setEnabled(true);
                }
            }
            contextMenu.show(vc, coordinates.x, coordinates.y);
        }
    }

}
