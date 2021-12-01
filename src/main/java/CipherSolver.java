import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CipherSolver {
    static final int preamble = 25;
    static ArrayList<Double> lines;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("data/numbers.txt");
        Scanner fileContents = new Scanner(file);
        lines = new ArrayList<>();
        Double badValue = null;
        int position = 0;

        while (fileContents.hasNext()) {
            lines.add(fileContents.nextDouble());
        }

        for (int i = preamble; i < lines.size(); i++) {
            var thisValue = lines.get(i);
            if (checkForSum(i, thisValue)) {
                badValue = thisValue;
                position = i;
                break;
            }
        }

        System.out.println("badValue = " + badValue);

        var answer = findWeaknessPair(badValue, position-1);

        System.out.println("answer = " + (answer.left + answer.right));
    }

    static Pair<Double, Double> findWeaknessPair(final Double sum, final int position) {
        Double runningTotal = 0.0;
        var history = new ArrayList<Double>();
        int count = 0;
        for (int i = 0; i < position; i++) {
            if (runningTotal < sum) {
                var nextNumber = lines.get(i);
                runningTotal += nextNumber;
                history.add(nextNumber);
            } else if (runningTotal.equals(sum)) {
                Collections.sort(history);
                return new Pair<>(history.get(0), history.get(history.size()-1));
            } else if (runningTotal > sum) {
                history.clear();
                count++;
                i = count;
                runningTotal = 0.0;
            }
//            var pair = setFound(i);
//            if (pair != null) answer = new Pair<>(lines.get(s))
        }
        return null;
    }

//    static Pair setFound(int i) {
//
//    }

    static boolean checkForSum(final int position, final Double value) {
        for (int i = position-preamble; i < position; i++) {
            for (int j = i + 1; j < position; j++) {
                if (lines.get(i) + lines.get(j) == value) {
                    return false;
                }
            }
        }
        return true;
    }

    static class Pair<T, U> {
        private T left;
        private U right;

        public Pair(T left, U right) {
            this.left = left;
            this.right = right;
        }

        public T getLeft() {
            return left;
        }

        public void setLeft(T left) {
            this.left = left;
        }

        public U getRight() {
            return right;
        }

        public void setRight(U right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "left=" + left +
                    ", right=" + right +
                    '}';
        }
    }
}
