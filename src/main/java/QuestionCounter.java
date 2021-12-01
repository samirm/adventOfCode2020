import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class QuestionCounter {
    public static void main(String[] args) throws FileNotFoundException {
//        abc
//
//        a
//        b
//        c
        File file = new File("data/questionnaire.txt");
        Scanner fileContents = new Scanner(file);
        var lines = new ArrayList<String>();
        int maxId = 0;
        var seats = new HashMap<Integer, Integer>();
        var answers = new ArrayList<Integer>();
        var buckets = new HashSet<Character>();
        var bucketList = new ArrayList<Set<Character>>();

        while (fileContents.hasNext()) {
            lines.add(fileContents.nextLine());
        }

        for (var thisLine : lines) {
            if (thisLine.equals("") || thisLine.equals("-")) {
//                answers.add(buckets.size());
//                buckets.clear();
                HashSet<Character> firstSet;
                if (bucketList.isEmpty()) firstSet = new HashSet<Character>();
                else {
                    firstSet = new HashSet<>(Set.copyOf(bucketList.remove(0)));
                    for (var thisBucket : bucketList) {
                        firstSet.retainAll(new HashSet<>(Set.copyOf(thisBucket)));
                    }
                }
                answers.add(firstSet.size());
                bucketList.clear();
            } else {
                for (var thisChar : thisLine.toCharArray()) buckets.add(thisChar);
                bucketList.add(Set.copyOf(buckets));
                buckets.clear();
            }
        }

        System.out.println("answers = " + answers.stream().reduce(0, Integer::sum));
    }
}
