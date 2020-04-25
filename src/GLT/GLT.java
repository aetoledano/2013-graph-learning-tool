package GLT;

import GLT.algo.iAlgorithm;
import GUI.mainGUI;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GLT {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        setLookAndFeel();
        mgr.Init();
        GUI.mainGUI obj = new mainGUI();
        obj.setVisible(true);
        obj.setLocationRelativeTo(null);
    }

    public static void Run(iAlgorithm algo) throws SQLException, ClassNotFoundException {
        setLookAndFeel();
        mgr.Init();
        JOptionPane.showMessageDialog(null, "Cargado Algortimo: " + algo.getAlgoName());
        GUI.mainGUI obj = new mainGUI();
        obj.setVisible(true);
        obj.setLocationRelativeTo(null);
    }

    public static void setLookAndFeel() {
        try {
            String className = UIManager.getSystemLookAndFeelClassName();
            System.out.println("Now setting system L&F:" + className);
            UIManager.setLookAndFeel(className);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GLT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
