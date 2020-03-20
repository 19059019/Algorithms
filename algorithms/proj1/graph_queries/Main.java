import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void processCase(Reader reader, PrintStream out, int caseNum) throws IOException {
        boolean coms = true;
        double sum = 0;
        double count = 0;
        int k = reader.nextInt();
        int m = reader.nextInt();

        Vertex[] vertices = new Vertex[k];

        Edge[] edges = new Edge[m];

        // Expect Vertices
        for (int i = 0; i < k; i++) {
            vertices[i] = new Vertex(reader.nextInt());
        }
        // Expect Edges
        for (int i = 0; i < m; i++) {
            int from = reader.nextInt() - 1;
            int to = reader.nextInt() - 1;

            vertices[from].addNeighbour(to);
            vertices[to].addNeighbour(from);

            edges[i] = new Edge(from, to);
        }

        while (coms) {
            int command = reader.nextInt();
            switch (command) {
            // Terminate test case
            case 3:
                coms = false;
                break;
            // Delete X-th edge
            case 0:
                int x = reader.nextInt()- 1;
                // for linkedlist
                vertices[edges[x].getTo()].removeNeighbour(edges[x].getFromObj());
                vertices[edges[x].getFrom()].removeNeighbour(edges[x].getToObj());

                break;
            // Change Weight of vertex X to Y
            case 1:
                int vertex = reader.nextInt();
                int weight = reader.nextInt();

                vertices[vertex - 1].setWeight(weight);
                break;
            // Fetch Weight of Y-th Largest vertex connected to X (illegal = 0)
            case 2:
                int vertex1 = reader.nextInt() - 1;
                int index = reader.nextInt();

                if (index <= vertices[vertex1].getNeighbours().size() + 1) {
                    int[] connected = vertices[vertex1].query(vertices);
                    sum += connected[connected.length - index];
                }

                count++;
                break;
            }
        }
        out.print("" + caseNum + ": ");
        out.printf(Locale.US, "%.6f\n", sum / count);
    }
    public static void main(String[] argv) throws IOException {
        Reader in = new Reader();

        try{
            int N = in.nextInt();
            for (int i = 1; i <= N; i++) {
                processCase(in, System.out, i);
            }
        } catch(IOException e) {
            System.err.println("IOException");
        }

		in.close();
    }

}

class Edge {
    private Integer toObj;
    private Integer fromObj;

    public Edge(int from, int to) {
        this.toObj = Integer.valueOf(to);
        this.fromObj = Integer.valueOf(from);
    }

    public int getFrom() {
        return this.fromObj;
    }

    public int getTo() {
        return this.toObj;
    }

    public Integer getFromObj() {
        return this.fromObj;
    }

    public Integer getToObj() {
        return this.toObj;
    }
}

class Vertex {
    private int weight;
    private ArrayList<Integer> neighbours;
    
    public Vertex(int weight){
        this.weight = weight;
        neighbours = new ArrayList<>();
    }

    public int getWeight() {
        return this.weight;
    }

    public ArrayList<Integer> getNeighbours(){
        return this.neighbours;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void addNeighbour(int index){
        this.neighbours.add(index);
    }

    public void removeNeighbour(Integer index){
        this.neighbours.remove(index);
    }

    public int[] query(Vertex[] vertices){
        int[] connected = new int[neighbours.size() + 1];
        int count = 0;
        connected[count++] = weight;
        for (int i : neighbours) {
            connected[count++] =  vertices[i].getWeight();
        }
        Arrays.sort(connected);
        return connected;
    }
}
