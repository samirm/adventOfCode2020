import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SeatMapSolver {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("data/test.txt");
        Scanner fileContents = new Scanner(file);
        var lines = new ArrayList<Integer>();
        var seatMap = new char[97][91];
        int x = 0;
        int y = 0;

        while (fileContents.hasNext()) {
//            lines.add(fileContents.nextInt());
            seatMap[x][y] = (char) fileContents.nextByte();
        }
    }
}
