
import GDP.iMarker;
import GLT.algo.iAlgorithm;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class alg implements iAlgorithm {

    int numNodes;
    iMarker board;
    JTable table;
    DefaultTableModel model;
    Double[][] matrix;

    @Override
    public void init(Double[][] adjMatrix, ArrayList<ArrayList<Double>> adjList, JTable table, iMarker marcador) {
        numNodes = adjMatrix.length;
        board = marcador;
        this.table = table;
        matrix = adjMatrix;
    }

    @Override
    public String getAlgoName() {
        return "testAlgo";
    }

    @Override
    public void run() {
        synchronized (table) {
            table.setModel(new DefaultTableModel());
            ((DefaultTableModel) table.getModel()).setColumnCount(numNodes);
            String ids[] = new String[numNodes];
            for (int i = 0; i < numNodes; i++) {
                ids[i] = "n" + i;
            }
            ((DefaultTableModel) table.getModel()).addRow(new String[]{"Matriz de Adyacencia:"});
            for (Double[] a : matrix) {
                ((DefaultTableModel) table.getModel()).addRow(a);
            }

            ((DefaultTableModel) table.getModel()).setColumnIdentifiers(ids);
        }
        for (int i = 0; i < numNodes; i++) {
            board.markNode(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
        board.notify("End of Algorithm");
    }

    @Override
    public int nextStep() {
        return 0;
    }

    @Override
    public void previousStep() {
    }

    @Override
    public int getSupportedOperation() {
        return 0;
    }

    @Override
    public int getAllowedGraph() {
        return 0;
    }

}
