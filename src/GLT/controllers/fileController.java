
package GLT.controllers;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author clau
 */
public class fileController {
    
    public static File Get_File(boolean folder) {
        JFileChooser ch = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Solo ficheros .class", "class");
        ch.setFileFilter(filtro);
        ch.setFileSystemView(ch.getFileSystemView());
        ch.setMultiSelectionEnabled(false);
        ch.setDialogTitle("Selecciona un fichero");
        if (folder) {
            ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            ch.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }
        ch.showOpenDialog(null);
        return ch.getSelectedFile();
    }
    
}
