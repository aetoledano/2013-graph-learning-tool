package GDP;

public interface iMarker {

    public void markNode(int nodeNumber);

    public void markEdge(int nodeFrom, int nodeDest);

    public void notify(String text);
}
