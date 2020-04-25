
import GDP.iMarker;
import GLT.algo.iAlgorithm;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Dikjstra implements iAlgorithm {

    int numNodes;
    iMarker board;
    JTable table;
    DefaultTableModel model;
    Double[][] matrix;
    PriorityQueue<Point2D.Double> Q;
    Comparator<Point2D.Double> C;
    double dist[];
    boolean marked[];
    Integer father[];

    @Override
    public void init(Double[][] adjMatrix, ArrayList<ArrayList<Double>> adjList, JTable table, iMarker marcador) {
        numNodes = adjMatrix.length;
        board = marcador;
        this.table = table;
        matrix = adjMatrix;
    }

    @Override
    public String getAlgoName() {
        return "Dikjstra";
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
            ((DefaultTableModel) table.getModel()).addRow(new String[]{"Arreglo de predecesores:"});
            ((DefaultTableModel) table.getModel()).setColumnIdentifiers(ids);
        }
        double INF = Double.MAX_VALUE;
        dist = new double[numNodes];
        for (int i = 0; i < numNodes; i++) {
            dist[i] = INF;
        }
        marked = new boolean[numNodes];
        father = new Integer[numNodes];
        for (int i = 0; i < numNodes; i++) {
            father[i] = new Integer(-1);
        }

        C = new Comparator<Point2D.Double>() {
            @Override
            public int compare(Point2D.Double arg0, Point2D.Double arg1) {
                if (arg0.y < arg1.y) {
                    return -1;
                }
                return 1;
            }
        };

        Q = new PriorityQueue<Point2D.Double>(10000, C);
        int ini = 0;
        dist[ini] = 0;
        Q.add(new Point2D.Double(0.0, 0.0));
        while (!Q.isEmpty()) {
            int current = (int) Q.peek().x;
            Q.poll();
            synchronized (table) {
                ((DefaultTableModel) table.getModel()).addRow(new String[]{"Próxima iteración->"});
                ((DefaultTableModel) table.getModel()).addRow(father);
                table.updateUI();

            }
            if (marked[current]) {
                continue;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }

            marked[current] = true;
            board.markNode(current);
            System.out.println("marcado " + current);
            //a neccesary pause
            for (int i = 0; i < numNodes; i++) {
                if (matrix[current][i] != Double.NaN) {
                    if (dist[current] + matrix[current][i].doubleValue() < dist[i]) {
                        dist[i] = dist[current] + matrix[current][i].doubleValue();
                        Q.add(new Point2D.Double(i, dist[i]));
                        father[i] = current;
                    }
                }
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
