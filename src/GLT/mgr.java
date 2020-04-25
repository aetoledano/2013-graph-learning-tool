/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GLT;

import GLT.controllers.dbController;
import GUI.pluginGUI;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mgr {

    static dbController db;
    static pluginGUI pluginGUI;

    public static void Init() {
        try {
            db = new dbController();
            pluginGUI = new pluginGUI();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(mgr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static dbController getDb() {
        return db;
    }

    public static pluginGUI getPluginGUI() {
        return pluginGUI;
    }
}
