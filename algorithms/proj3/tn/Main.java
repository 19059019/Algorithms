import java.io.PrintStream;
import java.util.HashMap;
import java.awt.font.NumericShaper.Range;
import java.io.IOException;

public class Main {
    // Stores catalan numbers
    private static final int[] CAT_NUMS = getCats();
    private static final int[] SUM_CATS = new int[] { 1, 2, 4, 9, 23, 65, 197, 626, 2056, 6918, 23714, 82500, 290512,
            1033412, 3707852, 13402697, 48760367, 178405157, 656043857 };

    private static HashMap<Integer, String[]> memoise = new HashMap<>();
    private static HashMap<Integer, String> dynamicMemoise = new HashMap<>();

    public static void processCase(Reader in, PrintStream out, int iteration) throws IOException {
        String output = "";
        int n = in.nextInt();
        output = getTree((int) n);
        out.println("" + iteration + ": " + output);
    }

    public static String getTree(int n) {
        if (dynamicMemoise.containsKey( n)) {
            return dynamicMemoise.get( n);
        }
        int position = 0;
        int nodes = 0;
        String out = "";
        for (int i = 0; i < SUM_CATS.length; i++) {
            if (n < sumCats(0, i)) {
                position = n - sumCats(0, i -1);
                break;
            }
            nodes++;
        }

        if (memoise.get(nodes) == null) {
            int current = 0;
            int leftTree = 0;
            int leftTreePos = 0;
            int rightTreePos = 0;
            boolean stop = false;
            String left = "";
            String right = "";
            // Go through every node set until we reach the one that we want
            for (int rightTree = nodes - 1; rightTree >= 0; rightTree--) {
                leftTree = nodes - rightTree - 1;
                long iterations = CAT_NUMS[rightTree] * CAT_NUMS[leftTree];
                if (position >= current + iterations) {
                    current += iterations;
                    continue;
                } else {
                    // Start right tree at the start of the previous node set
                    rightTreePos = sumCats(0, rightTree - 1);
                    leftTreePos = sumCats(0, leftTree - 1);
                    for (int i = 0; i < iterations; i++) {
                        if (current == position) {
                            left = getTree(leftTreePos);
                            right = getTree(rightTreePos);
                            stop = true;
                            break;
                        }
                        current++;
                        rightTreePos++;
                        if (rightTreePos >= sumCats(0, rightTree)) {
                            rightTreePos = sumCats(0, rightTree - 1);
                            leftTreePos++;
                        }
                    }
                    if (stop == true) {
                        break;
                    }
                }

            }
            if (rightTreePos == 0 && leftTreePos == 0) {
                out = "X";
            } else if (rightTreePos == 0) {
                out = "("+left+")X";
            } else if (leftTreePos == 0) {
                out = "X("+right+")";
            } else {
                out = "(" + left + ")X(" + right + ")";
            }
        } else {
            // fetch from memoised HashMap
            out = memoise.get((nodes))[position];
        }
        dynamicMemoise.put(n, out);
        return out;
    }

    /**
     * Returns the size of the set of trees up to annd including n nodes
     */
    public static int sumCats(int start, int end) {
        if (end >= 0) {
            return SUM_CATS[end];
        } else {
            return 0;
        }
    }

    public static void main(String[] argv) throws IOException {
        process(new Reader(), System.out);
    }

    public static void process(Reader in, PrintStream out) throws IOException {
        int N = in.nextInt();
        getStartStates();
        for (int i = 1; i <= N; i++) {
            processCase(in, out, i);
        }
    }

    public static int[] getCats() {

        int[] cats = new int[] { 1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862, 16796, 58786, 208012, 742900, 2674440,
                9694845, 35357670, 129644790, 477638700, 1767263190 };
        return cats;
    }

    // Store some start states
    public static void getStartStates() {
        memoise.put(0, new String[] { "" });
        memoise.put(1, new String[] { "X" });
        memoise.put(2, new String[] { "X(X)", "(X)X" });
        memoise.put(3, new String[] { "X(X(X))", "X((X)X)", "(X)X(X)", "(X(X))X", "((X)X)X" });
        memoise.put(4,
                new String[] { "X(X(X(X)))", "X(X((X)X))", "X((X)X(X))", "X((X(X))X)", "X(((X)X)X)", "(X)X(X(X))",
                        "(X)X((X)X)", "(X(X))X(X)", "((X)X)X(X)", "(X(X(X)))X", "(X((X)X))X", "((X)X(X))X",
                        "((X(X))X)X", "(((X)X)X)X" });
        memoise.put(5, new String[] { "X(X(X(X(X))))", "X(X(X((X)X)))", "X(X((X)X(X)))", "X(X((X(X))X))",
                "X(X(((X)X)X))", "X((X)X(X(X)))", "X((X)X((X)X))", "X((X(X))X(X))", "X(((X)X)X(X))", "X((X(X(X)))X)",
                "X((X((X)X))X)", "X(((X)X(X))X)", "X(((X(X))X)X)", "X((((X)X)X)X)", "(X)X(X(X(X)))", "(X)X(X((X)X))",
                "(X)X((X)X(X))", "(X)X((X(X))X)", "(X)X(((X)X)X)", "(X(X))X(X(X))", "(X(X))X((X)X)", "((X)X)X(X(X))",
                "((X)X)X((X)X)", "(X(X(X)))X(X)", "(X((X)X))X(X)", "((X)X(X))X(X)", "((X(X))X)X(X)", "(((X)X)X)X(X)",
                "(X(X(X(X))))X", "(X(X((X)X)))X", "(X((X)X(X)))X", "(X((X(X))X))X", "(X(((X)X)X))X", "((X)X(X(X)))X",
                "((X)X((X)X))X", "((X(X))X(X))X", "(((X)X)X(X))X", "((X(X(X)))X)X", "((X((X)X))X)X", "(((X)X(X))X)X",
                "(((X(X))X)X)X", "((((X)X)X)X)X" });
        memoise.put(6, new String[] { "X(X(X(X(X(X)))))", "X(X(X(X((X)X))))", "X(X(X((X)X(X))))", "X(X(X((X(X))X)))",
                "X(X(X(((X)X)X)))", "X(X((X)X(X(X))))", "X(X((X)X((X)X)))", "X(X((X(X))X(X)))", "X(X(((X)X)X(X)))",
                "X(X((X(X(X)))X))", "X(X((X((X)X))X))", "X(X(((X)X(X))X))", "X(X(((X(X))X)X))", "X(X((((X)X)X)X))",
                "X((X)X(X(X(X))))", "X((X)X(X((X)X)))", "X((X)X((X)X(X)))", "X((X)X((X(X))X))", "X((X)X(((X)X)X))",
                "X((X(X))X(X(X)))", "X((X(X))X((X)X))", "X(((X)X)X(X(X)))", "X(((X)X)X((X)X))", "X((X(X(X)))X(X))",
                "X((X((X)X))X(X))", "X(((X)X(X))X(X))", "X(((X(X))X)X(X))", "X((((X)X)X)X(X))", "X((X(X(X(X))))X)",
                "X((X(X((X)X)))X)", "X((X((X)X(X)))X)", "X((X((X(X))X))X)", "X((X(((X)X)X))X)", "X(((X)X(X(X)))X)",
                "X(((X)X((X)X))X)", "X(((X(X))X(X))X)", "X((((X)X)X(X))X)", "X(((X(X(X)))X)X)", "X(((X((X)X))X)X)",
                "X((((X)X(X))X)X)", "X((((X(X))X)X)X)", "X(((((X)X)X)X)X)", "(X)X(X(X(X(X))))", "(X)X(X(X((X)X)))",
                "(X)X(X((X)X(X)))", "(X)X(X((X(X))X))", "(X)X(X(((X)X)X))", "(X)X((X)X(X(X)))", "(X)X((X)X((X)X))",
                "(X)X((X(X))X(X))", "(X)X(((X)X)X(X))", "(X)X((X(X(X)))X)", "(X)X((X((X)X))X)", "(X)X(((X)X(X))X)",
                "(X)X(((X(X))X)X)", "(X)X((((X)X)X)X)", "(X(X))X(X(X(X)))", "(X(X))X(X((X)X))", "(X(X))X((X)X(X))",
                "(X(X))X((X(X))X)", "(X(X))X(((X)X)X)", "((X)X)X(X(X(X)))", "((X)X)X(X((X)X))", "((X)X)X((X)X(X))",
                "((X)X)X((X(X))X)", "((X)X)X(((X)X)X)", "(X(X(X)))X(X(X))", "(X(X(X)))X((X)X)", "(X((X)X))X(X(X))",
                "(X((X)X))X((X)X)", "((X)X(X))X(X(X))", "((X)X(X))X((X)X)", "((X(X))X)X(X(X))", "((X(X))X)X((X)X)",
                "(((X)X)X)X(X(X))", "(((X)X)X)X((X)X)", "(X(X(X(X))))X(X)", "(X(X((X)X)))X(X)", "(X((X)X(X)))X(X)",
                "(X((X(X))X))X(X)", "(X(((X)X)X))X(X)", "((X)X(X(X)))X(X)", "((X)X((X)X))X(X)", "((X(X))X(X))X(X)",
                "(((X)X)X(X))X(X)", "((X(X(X)))X)X(X)", "((X((X)X))X)X(X)", "(((X)X(X))X)X(X)", "(((X(X))X)X)X(X)",
                "((((X)X)X)X)X(X)", "(X(X(X(X(X)))))X", "(X(X(X((X)X))))X", "(X(X((X)X(X))))X", "(X(X((X(X))X)))X",
                "(X(X(((X)X)X)))X", "(X((X)X(X(X))))X", "(X((X)X((X)X)))X", "(X((X(X))X(X)))X", "(X(((X)X)X(X)))X",
                "(X((X(X(X)))X))X", "(X((X((X)X))X))X", "(X(((X)X(X))X))X", "(X(((X(X))X)X))X", "(X((((X)X)X)X))X",
                "((X)X(X(X(X))))X", "((X)X(X((X)X)))X", "((X)X((X)X(X)))X", "((X)X((X(X))X))X", "((X)X(((X)X)X))X",
                "((X(X))X(X(X)))X", "((X(X))X((X)X))X", "(((X)X)X(X(X)))X", "(((X)X)X((X)X))X", "((X(X(X)))X(X))X",
                "((X((X)X))X(X))X", "(((X)X(X))X(X))X", "(((X(X))X)X(X))X", "((((X)X)X)X(X))X", "((X(X(X(X))))X)X",
                "((X(X((X)X)))X)X", "((X((X)X(X)))X)X", "((X((X(X))X))X)X", "((X(((X)X)X))X)X", "(((X)X(X(X)))X)X",
                "(((X)X((X)X))X)X", "(((X(X))X(X))X)X", "((((X)X)X(X))X)X", "(((X(X(X)))X)X)X", "(((X((X)X))X)X)X",
                "((((X)X(X))X)X)X", "((((X(X))X)X)X)X", "(((((X)X)X)X)X)X" });
    }
}