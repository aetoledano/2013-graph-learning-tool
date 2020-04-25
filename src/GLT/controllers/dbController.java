/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GLT.controllers;

import GLT.algo.iAlgorithm;
import java.awt.List;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author clau
 */
public class dbController {

    static final String controlador = "org.sqlite.JDBC";
    static final String url_bd = "jdbc:sqlite:dat/dat";
    Connection conexion = null;
    String consulta = null;
    private ResultSet resultados = null;//maneja los resultados
    File file = null;
    DefaultListModel<String> model;

    public void OpenConection() throws ClassNotFoundException, SQLException {
        Class.forName(controlador);
        conexion = DriverManager.getConnection(url_bd);
        model = new DefaultListModel<>();
    }

    public void CloseConection() throws SQLException {
        conexion.close();
    }

    public byte[] ConvertArrayByte(File file) throws FileNotFoundException {
        long longitud = file.length();
        byte[] arraybyte = new byte[(int) longitud];
        FileInputStream doc = new FileInputStream(file);
        try {
            int aux = doc.read(arraybyte);
        } catch (IOException ex) {
            Logger.getLogger(dbController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arraybyte;
    }

    public File ConvertFile(byte[] b, String className) throws FileNotFoundException, IOException {
        File tempfile = new File(className + ".class");
        FileOutputStream os = new FileOutputStream(tempfile);
        os.write(b);
        os.close();
        return tempfile;
    }

    public Object[] getData(boolean folder) {
        file = fileController.Get_File(folder);

        if (file != null) {
            String algorithmName = null;
            byte bytes[] = null;
            int tipo = 0;
            String className = file.getName().substring(0, file.getName().lastIndexOf("."));
            iAlgorithm ia;
            try {
                URL[] url = {file.getParentFile().toURI().toURL()};
                URLClassLoader loader = new URLClassLoader(url);
                System.out.println("Comprobar que implementa iAlgoritmo");
                ia = (iAlgorithm) loader.loadClass(className).newInstance();
                System.out.println("Comprobado");
                System.out.println("Obtener el nombre del algoritmo");
                algorithmName = ia.getAlgoName();
                System.out.println("Nombre obtenido");
                bytes = ConvertArrayByte(file);
                System.out.println("Obteneter tipo de grefo");
                tipo=ia.getAllowedGraph();
            } catch (NoClassDefFoundError | ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException ex) {
                JOptionPane.showMessageDialog(null, "Archivo no valido.");
                return null;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(dbController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Object[] array = {algorithmName, className, bytes,tipo};
            Statement st;
            try {
                st = conexion.createStatement();
                consulta = "select algorithm_name from Algoritmo where algorithm_name=\"" + algorithmName + "\"";
                resultados = st.executeQuery(consulta);
                if (resultados.next()) {
                    JOptionPane.showMessageDialog(null, "El algoritmo ya se encuentra en la base de datos");
                    st.close();
                    return null;
                }
                st.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error de acceso a datos!");
                System.err.println(ex.getMessage());
                return null;
            }
            return array;
        }
        return null;
    }

    public void addAlgorithm(boolean folder) throws FileNotFoundException, SQLException, MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        Object[] array = getData(folder);
        if (array != null) {
            PreparedStatement ps;
            consulta = "insert into Algoritmo(id,algorithm_name,class_name,active,file,tipo) values(?,?,?,?,?,?);";
            ps = conexion.prepareStatement(consulta);
            ps.setString(2, (String) array[0]);
            ps.setString(3, (String) array[1]);
            ps.setInt(4, 0);
            ps.setBytes(5, (byte[]) array[2]);
            ps.setInt(6, (int) array[3]);
            if (ps.executeUpdate() == 1) {
                model.addElement((String) array[0]);
            }
            ps.close();
        }
    }

    public void DeleteAlgorithm(String name) throws SQLException {
        Statement stat = (Statement) conexion.createStatement();
        consulta = "delete from  Algoritmo where algorithm_name =\"" + name + "\"";
        stat.executeUpdate(consulta);
        model.removeElement(name);
        stat.close();

    }

    public ArrayList<String> ActiveAlgoritmIndex(int num) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        Statement stat = (Statement) conexion.createStatement();
        if(num==-1){
            consulta = "select algorithm_name from Algoritmo where active=1;";
        }
        else{
            consulta = "select algorithm_name from Algoritmo where active=1 and tipo=\""+num+"\"";
        }
        
        resultados = stat.executeQuery(consulta);
        while (resultados.next()) {
            list.add(resultados.getString(1));
        }
        resultados.close();
        stat.close();
        return list;
    }

    public List AlgoIndexNoActive() throws SQLException {
        List lista = new List();
        Statement stat = (Statement) conexion.createStatement();
        consulta = "select algorithm_name from Algoritmo where activo=0;";
        resultados = stat.executeQuery(consulta);
        while (resultados.next()) {
            lista.add(resultados.getString("algorithm_name"));
        }
        resultados.close();
        stat.close();
        return lista;
    }

    public boolean isActive(String name) throws SQLException {
        boolean activo;
        try (Statement stat = (Statement) conexion.createStatement()) {
            consulta = "select *  from Algoritmo where algorithm_name =\"" + name + "\"";
            resultados = stat.executeQuery(consulta);
            activo = resultados.getBoolean(4);
        }
        return activo;
    }

    public DefaultListModel<String> getModel() {
        return model;
    }

    public void updateAlgoModel() throws SQLException {
        model.clear();
        Statement stat = (Statement) conexion.createStatement();
        consulta = "select algorithm_name from  Algoritmo;";
        resultados = stat.executeQuery(consulta);
        while (resultados.next()) {
            model.addElement(resultados.getString("algorithm_name"));
        }
        resultados.close();
        stat.close();
    }

    public void ModifyState(String name, boolean activo) {
        try {
            int result;
            try (Statement stat = (Statement) conexion.createStatement()) {
                consulta = "update Algoritmo set active=" + ((activo) ? 1 : 0) + " where  algorithm_name =\"" + name + "\" ";
                System.out.println(consulta);
                result = stat.executeUpdate(consulta);
                if (result == 0) {
                    JOptionPane.showMessageDialog(null, "Error en la activacion del algoritmo");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(dbController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public iAlgorithm getAlgorithmInstance(String name) {
        try {
            iAlgorithm ia;
            Statement stat = (Statement) conexion.createStatement();
            System.out.println(">" + name + "<");
            consulta = "select * from Algoritmo where algorithm_name=\"" + name + "\"";
            System.out.println(">" + consulta + "<");
            resultados = stat.executeQuery(consulta);
            byte[] bytes = resultados.getBytes(5);
            String className = resultados.getString(3);
            File tmpFile = ConvertFile(bytes, className);
            URL[] url = {(new File(System.getProperty("user.dir"))).toURI().toURL()};
            URLClassLoader loader = new URLClassLoader(url);
            ia = (iAlgorithm) loader.loadClass(className).newInstance();
            tmpFile.deleteOnExit();
            return ia;
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(dbController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
