import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TreeMapSolver {
    public static void main(String[] args) throws FileNotFoundException {
//        .#..............##....#.#.####.
//        ##..........#.....##...........
//        .......#....##...........#.#...
//        .........#.#...#..........#....
//        .........#..#................##
//        ..#...#..#..#...........#......
//        ...................#...##..##..
//        ........#.....##...#.#.#...#...
//        #..#.##......#.#..#..........#.
//        ......#.#...#.#...#........##.#
//        .....#.####........#...........
//        ...###..#............#.........
//        .....#.......##......#...#.....
//        #......##......................
//        ......#..............#.........
//        ..##...#....###.##.............
//        #...#..........#.#.........#...
        File file = new File("data/treeMap.txt");
        Scanner fileContents = new Scanner(file);
        var lines = new ArrayList<String>();
        int trees = 0;
        int x = 0;

        while (fileContents.hasNext()) {
            lines.add(fileContents.nextLine());
        }

        for (int i = 2; i < lines.size(); i+=2) {
            x += 1;
            if (lines.get(i).charAt(x % 31) == '#') trees++;
        }

        System.out.println("trees = " + trees);
        //193, 57, 64, 55, 22
    }
}
