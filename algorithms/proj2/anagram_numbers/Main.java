import java.io.PrintStream;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.StringTokenizer;

import java.io.IOException;

public class Main {

    public static void processCase(Reader in, PrintStream out, int iteration) throws IOException {
        HashMap<Character, Integer> letterMap = new HashMap<>();
        TreeSet<Character> letterSet = new TreeSet<>();
        long tempCount;
        long currentCount;
        char currentLetter;
        String output = "";
        StringTokenizer line = new StringTokenizer(in.readLine(), " ");
        String word = line.nextToken();
        long k = Long.parseLong(line.nextToken());
        line = null;
        // Find duplicate letters
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            letterSet.add(c);
            if (letterMap.containsKey(c)) {
                letterMap.put(c, letterMap.get(c) + 1);
            } else {
                letterMap.put(c, 1);
            }
        }
        /**
         * This while removes letters in alphabetical order, to check if k is in their
         * bound of anagrams. If it is, the letter is kept removed and added to the
         * output, if not, the letter is added back and the next letter is removed.
         */
        currentCount = 0;
        currentLetter = letterSet.first();
        while (output.length() < word.length()) {
            // Remove letter from map and set
            letterMap.put(currentLetter, letterMap.get(currentLetter) - 1);
            if (letterMap.get(currentLetter) == 0) {
                letterSet.remove(currentLetter);
                letterMap.remove(currentLetter);
            }
            // Find current bound
            tempCount = anagramCount(letterMap, letterSet);
            currentCount += tempCount;
            if (currentCount >= k) {
                // adjust currentCount
                currentCount -= tempCount;

                // add letter to output. keep letter removed.
                output += currentLetter;

                if (output.length() == word.length()) {
                    break;
                }
                // Go to the first letter in alphabetical order
                currentLetter = letterSet.first();
            } else {
                // Go to the next letter in alphabetical order, then add the letter back
                letterSet.add(currentLetter);
                if (letterMap.containsKey(currentLetter)) {
                    letterMap.put(currentLetter, letterMap.get(currentLetter) + 1);
                } else {
                    letterMap.put(currentLetter, 1);
                }
                currentLetter = letterSet.higher(currentLetter);
            }
        }
        out.println("" + iteration + ": " + output);
    }

    // Return number of permutations from a given word
    public static long anagramCount(HashMap<Character, Integer> letterMap, TreeSet<Character> letterSet) {
        long duplicates;
        long numerator = 0;
        long denominator = 1;
        for (char c : letterSet) {
            duplicates = letterMap.get(c);
            numerator += duplicates;
            denominator *= factorial(duplicates);
        }
        return factorial(numerator) / denominator;
    }

    public static void process(Reader in, PrintStream out) throws IOException {
        int N = in.nextInt();
        for (int i = 1; i <= N; i++) {
            processCase(in, out, i);
        }
    }

    public static long factorial(long n) {
        if (n == 0)
            return 1;
        else
            return (n * factorial(n - 1));
    }

    public static void main(String[] argv) throws IOException {
        process(new Reader(), System.out);
    }

}