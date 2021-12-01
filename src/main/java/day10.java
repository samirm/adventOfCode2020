import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class day10 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("data/test.txt");
        Scanner fileContents = new Scanner(file);
        var lines = new ArrayList<Integer>();
        int oneJolt = 0;
        int twoJolt = 0;
        int threeJolt = 0;
        int runningJolt = 0;

        while (fileContents.hasNext()) {
            lines.add(fileContents.nextInt());
        }
        Collections.sort(lines);

        for (var thisAdapter : lines) {
            if (thisAdapter - runningJolt == 1) {
                oneJolt++;
                runningJolt++;
            } else if (thisAdapter - runningJolt == 2) {
                twoJolt++;
                runningJolt += 2;
            } else if (thisAdapter - runningJolt == 3) {
                threeJolt++;
                runningJolt += 3;
            }
//            runningJolt += thisAdapter;
        }
        int combinations = 0;
        int running = 0;
        runningJolt = 0;
        for (var thisAdapter : lines) {
            if (thisAdapter - runningJolt == 1) {
//                oneJolt++;
                runningJolt++;
                combinations++;
            } else if (thisAdapter - runningJolt == 2) {
//                twoJolt++;
                runningJolt += 2;
                combinations++;
            } else if (thisAdapter - runningJolt == 3) {
//                threeJolt++;
                runningJolt += 3;
                combinations++;
            }
//            runningJolt += thisAdapter;
        }
//        for (int i = 0; i < lines.size(); i++) {
//            if (lines.contains(running+1)) combinations++;
//
////            if (lines.contains(running+3)) {
////                var maxIndex = lines.indexOf(running+3);
////                combinations += (maxIndex - i) + 1;
////                running+=3;
////            } else if (lines.contains(running+2)) {
////                var maxIndex = lines.indexOf(running+3);
//////                combinations += (maxIndex - i) +1;
////                running+=2;
////            } else {
//////                combinations++;
////                running++;
////            }
//        }

        System.out.println("oneJolt = " + oneJolt);
        System.out.println("threeJolt = " + (threeJolt +1));
        System.out.println("combinations = " + combinations);

//        System.out.println("badValue = " + badValue);
    }
}
