package GUI;

import java.awt.Component;
import javax.swing.JScrollPane;

public class AccesibleContentScroll extends JScrollPane {

    public Component table;

    @Override
    public void setViewportView(Component view) {
        super.setViewportView(view); //To change body of generated methods, choose Tools | Templates.
        table = view;
    }

}
