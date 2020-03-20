import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.StringTokenizer;

import java.io.IOException;

public class Main {
    public static HashMap<String, Integer> calcedTriangles = new HashMap<String, Integer>();

    public static void processCase(Reader in, StringBuilder out, int iteration) throws IOException {
        String output = "";
        int m = in.nextInt();
        int p = in.nextInt();
        Node[] vertices = new Node[m];
        boolean isBalanced = false;

        // Create Nodes
        for (int i = 0; i < m; i++) {
            vertices[i] = new Node(i + 1, m);
        }
        // Set positive edges
        for (int i = 0; i < p; i++) {
            int from = in.nextInt();
            int to = in.nextInt();
            vertices[from - 1].setPositiveEdge(to);
            vertices[to - 1].setPositiveEdge(from);
        }

        if (m <= 2) {
            out.append("" + iteration + ": " + m + "\n");
            return;
        }

        if (m == 3) {
            if (vertices[0].getEdge(2) * vertices[1].getEdge(3) * vertices[2].getEdge(1) == 1) {
                m = 3;
            } else {
                m = 2;
            }
            out.append("" + iteration + ": " + m + "\n");
            return;
        }

        if (m == 4) {
            if (checkTriangles(vertices, 4)) {
                out.append("" + iteration + ": " + 4 + "\n");
                return;
            } else if (checkTriangles(vertices, 3)) {
                out.append("" + iteration + ": " + 3 + "\n");
                return;
            } else {
                out.append("" + iteration + ": " + 2 + "\n");
                return;
            }
        }
        
        // Check if balanced for each size from m downwards from m being at least 5
        int nodes = m;
        m = (nodes / 2) ;
        if (checkTriangles(vertices, m)) {
            while (m < nodes - 1) {
                m+=2;
                if (!checkTriangles(vertices, m)) {
                    out.append("" + iteration + ": " + (m - 1) + "\n");
                    return;
                }
            }
            if (m == nodes) {
                if (checkTriangles(vertices, m)) {
                    out.append("" + iteration + ": " + nodes + "\n");
                    return;
                } else {
                    m --;
                }
            } else {
                m = nodes;
            }
        } else {
            m--;
        }

        while (m > 2 && isBalanced == false) {
            isBalanced = checkTriangles(vertices, m);
            m--;
        }
        if (isBalanced == false) {
            output = "" + 2;
        } else {
            output = "" + (m + 1);
        }

        out.append("" + iteration + ": " + output + "\n");
    }

    /**
     * @param vertices All vertices
     * @param n        Number of allowed vertices
     * @return Whether it is possible to have a balanced reaction with n nodes
     */
    public static boolean checkTriangles(Node[] vertices, int n) {
        // Generate list of nodes to be left out
        int tabooSize = vertices.length - n;
        int[] verticesIndices = new int[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            verticesIndices[i] = i + 1;
        }
        int[] data = new int[tabooSize];
        return genTaboo(vertices, verticesIndices, tabooSize, 0, data, 0);
    }

    /**
     * @param vertices
     * @param arr
     * @param r
     * @param index
     * @param data
     * @param i
     * @return True if it is possible to create a balanced reaction with r missing
     *         nodes, False otherwise
     */
    static boolean genTaboo(Node[] vertices, int arr[], int r, int index, int data[], int i) {
        int n = arr.length;
        if (index == r) {
            HashSet<Integer> temp = new HashSet<>();
            for (int j = 0; j < r; j++) {
                temp.add(data[j]);
            }
            if (!temp.contains(1)) {
                return triangesWithoutNodes(vertices, temp);
            }
            return false;
        }
        if (i >= n) {
            return false;
        }
        data[index] = arr[i];
        if (genTaboo(vertices, arr, r, index + 1, data, i + 1) == true) {
            return true;
        } else if (genTaboo(vertices, arr, r, index, data, i + 1) == true) {
            return true;
        } else
            return false;
    }

    /**
     * @param vertices
     * @param tabooNodes
     * 
     * @return false if there is a negative triangle in a reaction of the vertices
     *         without the tabooNodes
     */
    public static boolean triangesWithoutNodes(Node[] vertices, HashSet<Integer> tabooNodes) {
        int balance;
        String hash;
        for (int i = 0; i < vertices.length; i++) {
            if (tabooNodes.contains(i + 1)) {
                continue;
            }
            for (int j = i + 1; j < vertices.length; j++) {
                if (tabooNodes.contains(j + 1)) {
                    continue;
                }
                for (int k = j + 1; k < vertices.length; k++) {
                    if (tabooNodes.contains(k + 1)) {
                        continue;
                    }
                    balance = vertices[i].getEdge(j + 1) * vertices[j].getEdge(k + 1) * vertices[k].getEdge(i + 1);
                    // if triangle is negative, return false
                    if (balance == -1) {
                        return false;
                    }
                }
            }
        }
        // No negative triangle was detected
        return true;
    }

    public static void process(Reader in, StringBuilder out) throws IOException {
        int N = in.nextInt();
        for (int i = 1; i <= N; i++) {
            processCase(in, out, i);
        }
    }

    public static void main(String[] argv) throws IOException {
        StringBuilder s = new StringBuilder();
        process(new Reader(), s);
        System.out.print(s.toString());
    }
}

class Node {
    private int index;
    private HashMap<Integer, Integer> edges;

    public Node(int i, int s) {
        index = i;
        edges = new HashMap<>();
        for (int f = 1; f <= s; f++) {
            edges.put(f, -1);
        }
        edges.put(index, 1);
    }

    public void setPositiveEdge(int i) {
        edges.put(i, 1);
    }

    public HashMap getEdges() {
        return edges;
    }

    public int getEdge(int e) {
        return edges.get(e);
    }

    public int getIndex() {
        return index;
    }
}