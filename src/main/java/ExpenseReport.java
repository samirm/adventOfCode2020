import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ExpenseReport {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("data/expenseReport.txt");
        Scanner fileContents = new Scanner(file);
        var expenses = new ArrayList<Integer>();
        int found1 = -9000;
        int found2 = -9000;
        int found3 = -9000;

        while (fileContents.hasNextInt()) {
            expenses.add(fileContents.nextInt());
        }
        Collections.sort(expenses);
        System.out.println("expenses = " + expenses);
        int product = 0;

        for (int i = 0; i < expenses.size(); i++) {
            for (int j = 1; j < expenses.size() - 1; j++) {
                for (int k = 2; k < expenses.size() - 2; k++) {
                    if (expenses.get(i) + expenses.get(j) + expenses.get(k) == 2020) {
                        found1 = expenses.get(i);
                        found2 = expenses.get(j);
                        found3 = expenses.get(k);
                        product = found1 * found2 * found3;
                        break;
                    }
                }
            }
        }

        System.out.println("found1 = " + found1 + ", found2 = " + found2 + ", found3 = " + found3);
        System.out.println("product = " + product);
    }
}
