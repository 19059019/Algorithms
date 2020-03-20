import java.io.PrintStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import java.io.IOException;

@SuppressWarnings("unchecked")
public class Main {

    public static void processCase(Reader in, PrintStream out, int iteration) throws IOException {
        String output = "";
        HashSet<Character> characters = new HashSet<>();
        HashMap<Integer, Character> indexToChar = new HashMap<>();
        HashMap<Character, Integer> charToIndex = new HashMap<>();
        int wordCount = Integer.parseInt(in.readLine());
        LinkedList<Integer>[] nodes = new LinkedList[15];
        if(wordCount == 0){
            System.out.println("" + iteration + ": " + "0");
            return;
        }
        int charCount = 0;
        String firstWord = in.readLine();
        String secondWord;
        if (wordCount == 1) {
            int answer = factorial(firstWord.length());
            System.out.println("" + iteration + ": " + answer);
            return;
        }
        

        for (int i = 0; i < wordCount - 1; i++) {
            boolean adj = false;
            secondWord = in.readLine();
            int length = Math.max(firstWord.length(), secondWord.length());
            for (int j = 0; j < length; j++) {
                // Add new characters to library
                if (j < firstWord.length()) {
                    char currentChar = firstWord.charAt(j);
                    if (!characters.contains(currentChar)) {
                        nodes[charCount] = new LinkedList<>();
                        characters.add(currentChar);
                        charToIndex.put(currentChar, charCount);
                        indexToChar.put(charCount++, currentChar);
                    }

                }
                if (j < secondWord.length()) {
                    char newCurrentChar = secondWord.charAt(j);
                    if (!characters.contains(newCurrentChar)) {
                        nodes[charCount] = new LinkedList<>();
                        characters.add(newCurrentChar);
                        charToIndex.put(newCurrentChar, charCount);
                        indexToChar.put(charCount++, newCurrentChar);
                    }
                }

                // TODO: Compare first word and second word
                if (adj == false && j < firstWord.length()) {
                    char first = firstWord.charAt(j);
                    char second;
                    if (j < secondWord.length()) {
                        second = secondWord.charAt(j);
                        if (second != first) {
                            nodes[charToIndex.get(first)].add(charToIndex.get(second));
                            adj = true;
                        }
                    }
                }

            }
            firstWord = secondWord;
        }

        // Create a graph with all of the collected edges
        Graph graph = new Graph(charCount);
        for (int i = 0; i < charCount; i++) {
            for(int j : nodes[i]){
                graph.addEdge(i, j);
            }
        }

        // Find all topological sorts
        LinkedList<Integer> temp = graph.allTopologicalSorts();
        // If there are more than one topological sorts, the count is added to the
        // returned list
        if (temp.size() > charCount) {
            output = temp.getLast() + "";
        } else {
            for (int a : temp) {
                output += (indexToChar.get(a));
            }
        }

        out.println("" + iteration + ": " + output);
    }

    public static void process(Reader in, PrintStream out) throws IOException {
        int N = Integer.parseInt(in.readLine());
        for (int i = 1; i <= N; i++) {
            processCase(in, out, i);
        }
    }

    public static int factorial(int n) {
        if (n == 0)
            return 1;
        else
            return (n * factorial(n - 1));
    }

    public static void main(String[] argv) throws IOException {
        process(new Reader(), System.out);
    }
}