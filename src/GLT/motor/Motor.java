package GLT.motor;

import GDP.iMarker;
import GLT.algo.iAlgorithm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.Timer;

public final class Motor {

    iAlgorithm algo;
    int mode, algoMode;
    iMarker marcador;
    JTable tabla;
    JRadioButton array[];
    Thread automaticRunner;
    Timer monitor = new Timer(500, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (automaticRunner != null && !automaticRunner.isAlive()) {
                Stop();
            }
        }
    });

    public Motor(iAlgorithm algoritmo, int tipoGrafo, iMarker marcador, JTable table) throws Exception {
        if (tipoGrafo != algoritmo.getAllowedGraph()) {
            throw new Exception("Este algoritmo no se aplica al tipo de grafo encontrado!");
        }
        this.algo = algoritmo;
        this.marcador = marcador;
        this.tabla = table;
        mode = -1;
        try {
            algoMode = algo.getSupportedOperation();
        } catch (Exception ex) {
            marcador.notify("No fue posible determinar el modo soportado por el algoritmo");
            Thread.sleep(1000);
            marcador.notify("Asumiendo modo automático");
            algoMode = 0;
        }
        array = new JRadioButton[2];
        array[0] = new JRadioButton("Automático Completo");
        array[1] = new JRadioButton("Manual");
        ButtonGroup group = new ButtonGroup();
        for (JRadioButton b : array) {
            group.add(b);
        }
        array[0].setSelected(true);
        init = false;
    }

    private boolean init;

    public void InitData(Double[][] adjMatrix, ArrayList<ArrayList<Double>> adjList) {

        algo.init(adjMatrix, adjList, tabla, marcador);
        init = true;
    }

    public void Play_Pause() {
        if (init) {
            switch (mode) {
                case -1:
                    if (algoMode == 2) {
                        JOptionPane.showMessageDialog(null, array, "Seleccionar Modo", JOptionPane.QUESTION_MESSAGE);
                    } else {
                        array[0].setSelected(algoMode == 0);
                        array[1].setSelected(algoMode == 1);
                    }
                    if (array[0].isSelected()) {
                        mode = 0;
                        automaticRunner = new Thread(algo);
                        automaticRunner.setDaemon(true);
                        automaticRunner.start();
                        monitor.start();
                    } else if (array[1].isSelected()) {
                        mode = 1;
                        marcador.notify("Presione SIGUIENTE para la próxima iteración");
                    }
                    break;
                case 0:
                    mode = 2;
                    automaticRunner.suspend();
                    break;
                case 2:
                    mode = 0;
                    automaticRunner.resume();
                    break;
            }
        }
    }

    int out;

    public void NextStep() {
        if (mode == 1) {
            out = algo.nextStep();
            if (out != 0) {
                Stop();
            }
        }
    }

    public void PrevStep() {
        if (mode == 1) {
            algo.previousStep();
        }
    }

    public void Stop() {
        monitor.stop();
        System.out.println("Trying to stop!");
        mode = -1;
        if (automaticRunner != null && automaticRunner.isAlive()) {
            System.out.println("--------------------------------------------------------------------------");
            automaticRunner.stop();
        }
        automaticRunner = null;
        init = false;
        if (out == 0) {
            marcador.notify("Terminado OK");
        } else {
            marcador.notify("Terminado con problemas");
        }
    }

    public boolean isInit() {
        return init;
    }

    public boolean isRunning() {
        return mode == 0 || mode == 1 || mode == 2;
    }

    private double[][] matrixCopy(double[][] adjMatrix) {
        int rows = adjMatrix.length;
        int cols = adjMatrix[0].length;
        double newMatrix[][] = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            newMatrix[i] = Arrays.copyOf(adjMatrix[i], cols);
        }
        return newMatrix;
    }
}
