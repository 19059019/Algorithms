// code from https://www.geeksforgeeks.org/all-topological-sorts-of-a-directed-acyclic-graph/

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Iterator;

@SuppressWarnings({ "deprecation", "unchecked" })
class Graph {
    private int V; // No. of vertices
    private LinkedList<Integer> adj[]; // Adjacency List
    private LinkedList<Integer> outputList;
    private int outputCount;
    // Constructor
    public Graph(int v) {
        V = v;
        outputList = new LinkedList<>();
        outputCount = 0;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    // Function to add an edge into the graph
    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    private void allTopologicalSortsUtil(boolean[] visited, int[] indegree, ArrayList<Integer> stack) {
        // To indicate whether all topological are found
        // or not
        boolean flag = false;

        for (int i = 0; i < this.V; i++) {
            // If indegree is 0 and not yet visited then
            // only choose that vertex
            if (!visited[i] && indegree[i] == 0) {

                // including in result
                visited[i] = true;
                stack.add(i);
                for (int adjacent : this.adj[i]) {
                    indegree[adjacent]--;
                }
                allTopologicalSortsUtil(visited, indegree, stack);

                // resetting visited, res and indegree for
                // backtracking
                visited[i] = false;
                stack.remove(stack.size() - 1);
                for (int adjacent : this.adj[i]) {
                    indegree[adjacent]++;
                }

                flag = true;
            }
        }
        // We reach here if all vertices are visited.
        // So we print the solution here
        if (!flag) {
            outputCount++;
            if (outputCount == 1) {
                stack.forEach(i -> outputList.add(i));
            }
        }

    }

    // The function does all Topological Sort.
    // It uses recursive alltopologicalSortUtil()
    public LinkedList<Integer> allTopologicalSorts() {
        // Mark all the vertices as not visited
        boolean[] visited = new boolean[this.V];
        int[] indegree = new int[this.V];
        for (int i = 0; i < this.V; i++) {
            for (int var : this.adj[i]) {
                indegree[var]++;
            }
        }

        ArrayList<Integer> stack = new ArrayList<>();
        allTopologicalSortsUtil(visited, indegree, stack);
        // Append count to output if higher than 1
        if (outputCount == 1) {
            return outputList;
        } else {
            outputList.add(outputCount);
            return outputList;
        }
    }
}