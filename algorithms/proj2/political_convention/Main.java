import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.HashSet;
import java.io.IOException;

public class Main {
    private static char SAME = '~';
    private static char DIFF = '*';

    public static void processCase(Reader in, PrintStream out) throws IOException {
        StringBuilder sb = new StringBuilder();
        HashMap<Integer, Vertex> delegates = new HashMap<>();
        HashSet<Integer> visited;
        in.nextInt();
        int m = in.nextInt();
        int opp, x, y, tempOut;
        Vertex vertX, vertY;

        for (int i = 0; i < m; i++) {
            opp = in.nextInt();
            x = in.nextInt();
            y = in.nextInt();
            tempOut = -2;
            if (!delegates.containsKey(x)) {
                delegates.put(x, new Vertex(x));
                delegates.get(x).addConnection(delegates.get(x), SAME);
            }
            if (!delegates.containsKey(y)) {
                delegates.put(y, new Vertex(y));
                delegates.get(y).addConnection(delegates.get(y), SAME);
            }

            vertX = delegates.get(x);
            vertY = delegates.get(y);

            switch (opp) {
            case 1:
                visited = new HashSet<Integer>();
                // sameParty statement
                tempOut = isSame(visited, vertX, y);
                if (tempOut == -1) {
                    vertX.addConnection(vertY, SAME);
                    vertY.addConnection(vertX, SAME);
                    tempOut = -2;
                } else if (tempOut == 1) {
                    tempOut = -2;
                } else if (tempOut == 0) {
                    tempOut = -1;
                }
                break;
            case 2:
                visited = new HashSet<Integer>();
                // diffParty statement
                tempOut = isDiff(visited, vertX, vertY, x, y);
                if (tempOut == -1) {
                    vertX.addConnection(vertY, DIFF);
                    vertY.addConnection(vertX, DIFF);
                    tempOut = -2;
                } else if (tempOut == 1) {
                    tempOut = -2;
                } else if (tempOut == 0) {
                    tempOut = -1;
                }
                break;
            case 3:
                visited = new HashSet<Integer>();
                // isSame question
                tempOut = isSame(visited, vertX, y);
                break;
            case 4:
                visited = new HashSet<Integer>();
                // isDiff
                tempOut = isDiff(visited, vertX, vertY, x, y);
                break;
            }
            if (tempOut != -2) {
                sb.append(tempOut).append("\n");
            }

        }
        System.out.print(sb);
    }

    public static int isSame(HashSet<Integer> visited, Vertex vertX, int y) {
        int tempOut = vertX.dfs1(y, visited, SAME);
        return tempOut;
    }

    public static int isDiff(HashSet<Integer> visited, Vertex vertX, Vertex vertY, int x, int y) {
        int tempOut = -1;
        int tempOutX = vertX.dfs1(y, visited, SAME);
        if (tempOutX == 1) {
            tempOut = 0;
        } else if (tempOutX == 0) {
            tempOut = 1;
        }
        return tempOut;
    }

    public static void process(Reader in, PrintStream out) throws IOException {
        int N = in.nextInt();
        for (int i = 1; i <= N; i++) {
            out.println(i + ":");
            processCase(in, out);
        }
    }

    public static void main(String[] argv) throws IOException {
        process(new Reader(), System.out);
    }

}

class Vertex {
    private char SAME = '~';
    private char DIFF = '*';
    private HashMap<Integer, Edge> connections;
    private int index;

    public Vertex(int index) {
        this.index = index;
        connections = new HashMap<Integer, Edge>();
    }

    /**
     * 
     * @param v   Vertex to connect to
     * @param rel Relationship ~=>[same]/*=>[diff]
     */
    public void addConnection(Vertex v, char rel) {
        connections.put(v.getIndex(), new Edge(v, rel));
    }

    // Returns 1 for same, 0 for different and -1 for unknown
    // Start with rel == SAME
    public int dfs1(int v, HashSet<Integer> visited, char rel) {
        Vertex vertex;
        char tempRel;
        int temp;
        // If this is the one were looking for
        if (this.getIndex() == v) {
            if (rel == SAME) {
                return 1;
            } else if (rel == DIFF) {
                return 0;
            } else {
                return 1;
            }
        }
        visited.add(this.getIndex());
        tempRel = rel;
        // If this is not the one we were looking for
        for (Edge e : this.getConnections().values()) {
            rel = tempRel;
            vertex = e.getVertex();
            // Skip all visited
            if (visited.contains(vertex.getIndex())) {
                continue;
            } else {
                // DFS
                if (rel == DIFF && e.getRel() == DIFF) {
                    rel = SAME;
                } else if (rel == DIFF && e.getRel() == SAME) {
                    rel = DIFF;
                } else if (rel == SAME && e.getRel() == SAME) {
                    rel = SAME;
                } else if (rel == SAME && e.getRel() == DIFF) {
                    rel = DIFF;
                }
                temp = vertex.dfs1(v, visited, rel);
                if (temp == -1) {
                    continue;
                } else {
                    return temp;
                }
            }
        }
        return -1;
    }

    public HashMap<Integer, Edge> getConnections() {
        return this.connections;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        String out = index + ": ";
        for (Edge e : connections.values()) {
            out += e.getRel() + " " + e.getVertex().getIndex() + ", ";
        }
        return out;
    }
}

class Edge {
    private Vertex vertex;
    private char rel;

    public Edge(Vertex v, char rel) {
        this.vertex = v;
        this.rel = rel;
    }

    public Vertex getVertex() {
        return this.vertex;
    }

    public char getRel() {
        return this.rel;
    }

}
