package GLT.algo;

import GDP.iMarker;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JTable;

public interface iAlgorithm extends Serializable, Runnable {

    public void init(Double[][] adjMatrix, ArrayList<ArrayList<Double>> adjList, JTable table, iMarker marcador);

    public String getAlgoName();

    @Override
    public void run();

    public int nextStep();

    public void previousStep();

    //0 - Metodo run implementado
    //1 - solo modo manual implementado
    //2 - Ambos modos disponibles
    public int getSupportedOperation();

    public int getAllowedGraph();
}
