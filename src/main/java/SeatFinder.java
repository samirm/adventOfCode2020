import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class SeatFinder {
    static int colMin = 1;
    static int colMax = 8;
    static int rowMin = 1;
    static int rowMax = 128;

    public static void main(String[] args) throws FileNotFoundException {
        //FBFBBFFRLR
        File file = new File("data/seatingArrangement.txt");
        Scanner fileContents = new Scanner(file);
        var lines = new ArrayList<String>();
        int maxId = 0;
        var col = 0;
        var row = 0;
        var seats = new HashMap<Integer, Integer>();
        var answers = new ArrayList<Integer>();

        while (fileContents.hasNext()) {
            lines.add(fileContents.nextLine());
        }

        for (var thisLine : lines) {
//            var rows = thisLine.substring(0, 6);
//            var cols = thisLine.substring(7);
            //RLR - 6, LRL - (1,4), (3,4), (3)
            //FBFBBFF - 44

            for (var thisChar : thisLine.toCharArray()) {
                switch (thisChar) {
                    case 'F':
                        if (rowMax - rowMin == 1 || rowMax == rowMin) {
                            row = rowMin;
                            resetRows();
                            break;
                        }
                        rowMax = rowMax - (rowMax-rowMin+1)/2;
                        break;
                    case 'B':
                        if (rowMax - rowMin == 1) {
                            row = rowMax;
                            resetRows();
                            break;
                        }
                        if (rowMin == 1) rowMin = ((rowMax-rowMin +1)/2) + 1;
                        else rowMin = rowMin + ((rowMax-rowMin +1)/2);
                        break;
                    case 'L':
                        if (colMax - colMin == 1) {
                            col = colMin;
                            resetCols();
                            break;
                        }
                        colMax = colMax - (colMax-colMin+1)/2;
                        break;
                    case 'R':
                        if (colMax - colMin == 1) {
                            col = colMax;
                            resetCols();
                            break;
                        }
                        if (colMin == 1) colMin = ((colMax-colMin +1)/2) + 1;
                        else colMin = colMin + ((colMax-colMin +1)/2);
                        break;
                    default:
                        System.out.println("Bad string");
                }
            }
            var thisId = (row-1) * 8 + (col-1);
            if (thisId > maxId) maxId = thisId;
            answers.add(thisId);

//            var seat = new HashMap<Integer, Integer>();
            seats.put(row-1, col-1);
//            seats.add(seat);
        }

        var rows = seats.keySet().toArray();
        Arrays.sort(rows);
        System.out.println("rows = " + Arrays.toString(rows));

        var cols = seats.values().toArray();
        Arrays.sort(cols);
        System.out.println("cols = " + Arrays.toString(cols));

        Collections.sort(answers);
        answers.remove(0);
        answers.remove(0);
        for (int i = 1; i < answers.size(); i++) {
            System.out.print("answers = " + answers.get(i-1));
            if (i % 10 == 0) System.out.println();
        }
        System.out.println("answers = " + answers);

        System.out.println("\nmaxId = " + maxId);
    }

    static void resetCols() {
        colMax = 8;
        colMin = 1;
    }

    static void resetRows() {
        rowMax = 128;
        rowMin = 1;
    }
}
