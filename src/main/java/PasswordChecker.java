import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PasswordChecker {
    public static void main(String[] args) throws FileNotFoundException {
        //3-6 h: jkhnhwhx
        File file = new File("data/passwords.txt");
        Scanner fileContents = new Scanner(file);
        var passwords = new ArrayList<Password>();
        int valid = 0;

        while (fileContents.hasNext()) {
            String[] line = fileContents.nextLine().split(":");
            String[] bounds = line[0].split("-");
            String[] madness = bounds[1].split(" ");
            passwords.add(new Password(Integer.parseInt(bounds[0]), Integer.parseInt(madness[0]), madness[1].charAt(0), line[1]));
        }

        for (var thisPass : passwords) {
            if (thisPass.getPassword().charAt(thisPass.getMin()) == thisPass.getLetter() ^ thisPass.getPassword().charAt(thisPass.getMax()) == thisPass.getLetter()) valid++;
//            var charCount = thisPass.getPassword().chars().filter(ch -> ch == thisPass.getLetter()).count();
//            if (charCount >= thisPass.getMin() && charCount <= thisPass.getMax()) valid++;
        }

        System.out.println("valid = " + valid);
    }
}

class Password {
    private int min;
    private int max;
    private char letter;
    private String password;

    public Password(int min, int max, char letter, String password) {
        this.min = min;
        this.max = max;
        this.letter = letter;
        this.password = password;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}