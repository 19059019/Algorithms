import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void processCase(Reader reader, PrintStream out, int caseNum) throws IOException {
        boolean coms = true;
        za.ac.sun.cs.Cream.Spoon.hit(10, "Main.java");
        double sum = 0;
        za.ac.sun.cs.Cream.Spoon.hit(11, "Main.java");
        double count = 0;
        za.ac.sun.cs.Cream.Spoon.hit(12, "Main.java");
        int k = reader.nextInt();
        za.ac.sun.cs.Cream.Spoon.hit(13, "Main.java");
        int m = reader.nextInt();
        za.ac.sun.cs.Cream.Spoon.hit(14, "Main.java");
        Vertex[] vertices = new Vertex[k];
        za.ac.sun.cs.Cream.Spoon.hit(16, "Main.java");
        Edge[] edges = new Edge[m];
        za.ac.sun.cs.Cream.Spoon.hit(18, "Main.java");
        // Expect Vertices
        for (int i = 0; i < k; i++) {
            vertices[i] = new Vertex(reader.nextInt());
            za.ac.sun.cs.Cream.Spoon.hit(22, "Main.java");
        }
        // Expect Edges
        for (int i = 0; i < m; i++) {
            int from = reader.nextInt() - 1;
            za.ac.sun.cs.Cream.Spoon.hit(26, "Main.java");
            int to = reader.nextInt() - 1;
            za.ac.sun.cs.Cream.Spoon.hit(27, "Main.java");
            vertices[from].addNeighbour(to);
            za.ac.sun.cs.Cream.Spoon.hit(29, "Main.java");
            vertices[to].addNeighbour(from);
            za.ac.sun.cs.Cream.Spoon.hit(30, "Main.java");
            edges[i] = new Edge(from, to);
            za.ac.sun.cs.Cream.Spoon.hit(32, "Main.java");
        }
        while (coms) {
            int command = reader.nextInt();
            za.ac.sun.cs.Cream.Spoon.hit(36, "Main.java");
            switch(command) {
                // Terminate test case
                case 3:
                    coms = false;
                    za.ac.sun.cs.Cream.Spoon.hit(40, "Main.java");
                    break;
                // Delete X-th edge
                case 0:
                    int x = reader.nextInt() - 1;
                    za.ac.sun.cs.Cream.Spoon.hit(44, "Main.java");
                    // for linkedlist
                    vertices[edges[x].getTo()].removeNeighbour(edges[x].getFromObj());
                    za.ac.sun.cs.Cream.Spoon.hit(46, "Main.java");
                    vertices[edges[x].getFrom()].removeNeighbour(edges[x].getToObj());
                    za.ac.sun.cs.Cream.Spoon.hit(47, "Main.java");
                    break;
                // Change Weight of vertex X to Y
                case 1:
                    int vertex = reader.nextInt();
                    za.ac.sun.cs.Cream.Spoon.hit(52, "Main.java");
                    int weight = reader.nextInt();
                    za.ac.sun.cs.Cream.Spoon.hit(53, "Main.java");
                    vertices[vertex - 1].setWeight(weight);
                    za.ac.sun.cs.Cream.Spoon.hit(55, "Main.java");
                    break;
                // Fetch Weight of Y-th Largest vertex connected to X (illegal = 0)
                case 2:
                    int vertex1 = reader.nextInt() - 1;
                    za.ac.sun.cs.Cream.Spoon.hit(59, "Main.java");
                    int index = reader.nextInt();
                    za.ac.sun.cs.Cream.Spoon.hit(60, "Main.java");
                    if (index <= vertices[vertex1].getNeighbours().size() + 1) {
                        int[] connected = vertices[vertex1].query(vertices);
                        sum += connected[connected.length - index];
                    }
                    za.ac.sun.cs.Cream.Spoon.hit(62, "Main.java");
                    count++;
                    za.ac.sun.cs.Cream.Spoon.hit(67, "Main.java");
                    break;
            }
        }
        out.print("" + caseNum + ": ");
        za.ac.sun.cs.Cream.Spoon.hit(71, "Main.java");
        out.printf(Locale.US, "%.6f\n", sum / count);
        za.ac.sun.cs.Cream.Spoon.hit(72, "Main.java");
    }

    public static void main(String[] argv) throws IOException {
        Reader in = new Reader();
        za.ac.sun.cs.Cream.Spoon.hit(75, "Main.java");
        try {
            int N = in.nextInt();
            za.ac.sun.cs.Cream.Spoon.hit(78, "Main.java");
            for (int i = 1; i <= N; i++) {
                processCase(in, System.out, i);
                za.ac.sun.cs.Cream.Spoon.hit(80, "Main.java");
            }
        } catch (IOException e) {
            System.err.println("IOException");
            za.ac.sun.cs.Cream.Spoon.hit(83, "Main.java");
        }
        in.close();
        za.ac.sun.cs.Cream.Spoon.hit(86, "Main.java");
    }
}

class Edge {

    private Integer toObj;

    private Integer fromObj;

    public Edge(int from, int to) {
        this.toObj = Integer.valueOf(to);
        za.ac.sun.cs.Cream.Spoon.hit(96, "Main.java");
        this.fromObj = Integer.valueOf(from);
        za.ac.sun.cs.Cream.Spoon.hit(97, "Main.java");
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

    public Vertex(int weight) {
        this.weight = weight;
        za.ac.sun.cs.Cream.Spoon.hit(122, "Main.java");
        neighbours = new ArrayList<>();
        za.ac.sun.cs.Cream.Spoon.hit(123, "Main.java");
    }

    public int getWeight() {
        return this.weight;
    }

    public ArrayList<Integer> getNeighbours() {
        return this.neighbours;
    }

    public void setWeight(int weight) {
        this.weight = weight;
        za.ac.sun.cs.Cream.Spoon.hit(135, "Main.java");
    }

    public void addNeighbour(int index) {
        this.neighbours.add(index);
        za.ac.sun.cs.Cream.Spoon.hit(139, "Main.java");
    }

    public void removeNeighbour(Integer index) {
        this.neighbours.remove(index);
        za.ac.sun.cs.Cream.Spoon.hit(143, "Main.java");
    }

    public int[] query(Vertex[] vertices) {
        int[] connected = new int[neighbours.size() + 1];
        za.ac.sun.cs.Cream.Spoon.hit(147, "Main.java");
        int count = 0;
        za.ac.sun.cs.Cream.Spoon.hit(148, "Main.java");
        connected[count++] = weight;
        za.ac.sun.cs.Cream.Spoon.hit(149, "Main.java");
        for (int i : neighbours) {
            connected[count++] = vertices[i].getWeight();
            za.ac.sun.cs.Cream.Spoon.hit(151, "Main.java");
        }
        Arrays.sort(connected);
        za.ac.sun.cs.Cream.Spoon.hit(153, "Main.java");
        return connected;
    }
}
