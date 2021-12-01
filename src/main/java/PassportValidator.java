import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PassportValidator {
    public static void main(String[] args) throws FileNotFoundException {
//        byr:1983 iyr:2017
//        pid:796082981 cid:129 eyr:2030
//        ecl:oth hgt:182cm
        File file = new File("data/passports.txt");
        Scanner fileContents = new Scanner(file);
        var passports = new ArrayList<Passport>();
        int valid = 0;
        var fields = new HashMap<String, String>();

        while (fileContents.hasNext()) {
            var line = fileContents.nextLine();
            if (!line.equals("")) {
                if (line.equals("-")) break;
                Arrays.stream(line.split(" ")).forEach(e -> {
                    var temp = e.split(":");
                    fields.put(temp[0], temp[1]);
                });
            } else {
                passports.add(new Passport(new HashMap<>(fields)));
                fields.clear();
            }
        }

        for (var thisPassport : passports) {
            if (thisPassport.validatePassport()) valid++;
        }

        System.out.println("valid = " + valid);
    }

//    byr (Birth Year) - four digits; at least 1920 and at most 2002.
//    iyr (Issue Year) - four digits; at least 2010 and at most 2020.
//    eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
//    hgt (Height) - a number followed by either cm or in:
//    If cm, the number must be at least 150 and at most 193.
//    If in, the number must be at least 59 and at most 76.
//    hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
//            ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
//            pid (Passport ID) - a nine-digit number, including leading zeroes.
//    cid (Country ID) - ignored, missing or not.

    static class Passport {
        private final HashMap<String, String> entries;

        public boolean validatePassport() {
            for (var thisEntry : entries.entrySet()) {
                switch (thisEntry.getKey()) {
                    case "byr":
                        var year = Integer.parseInt(thisEntry.getValue());
                        if (year > 2002 || year < 1920) return false;
                        break;
                    case "iyr":
                        year = Integer.parseInt(thisEntry.getValue());
                        if (year > 2020 || year < 2010) return false;
                        break;
                    case "eyr":
                        year = Integer.parseInt(thisEntry.getValue());
                        if (year > 2030 || year < 2020) return false;
                        break;
                    case "hgt":
                        var height = thisEntry.getValue();
                        if (!height.matches("\\d+(cm|in)")) return false;
                        else {
                            int hgt = Integer.parseInt(height.replaceAll("[^0-9]", ""));
                            if (height.contains("cm")) {
                                if (hgt > 193 || hgt < 150) return false;
                            } else if (height.contains("in")) {
                                if (hgt > 76 || hgt < 59) return false;
                            }
                        }
                        break;
                    case "hcl":
                        var hair = thisEntry.getValue();
                        if (hair.length() > 7) return false;
                        else if (!hair.startsWith("#")) return false;
                        else if (!hair.substring(1).matches("^[a-z0-9]{6}$")) return false;
                        break;
                    case "ecl":
                        try {
                            var eye = eyeColour.valueOf(thisEntry.getValue());
                        } catch (Exception e) {
                            return false;
                        }
                        break;
                    case "pid":
                        var spid = thisEntry.getValue();
                        if (spid.length() != 9) return false;
                        try {
                            var pid = Integer.parseInt(spid);
                        } catch (Exception e) {
                            return false;
                        }
                        break;
                    case "cid":
                        if (entries.size() != 8) return false;
                        break;
                    default:
                        System.out.println("def error");
                }
            }

            return entries.size() > 6;
        }

        public Passport(HashMap<String, String> entries) {
            this.entries = entries;
        }

        public void addEntry(String[] entry) {
            entries.put(entry[0], entry[1]);
        }

        public boolean checkIfCidMissing() {
            for (var thisKey : entries.keySet()) {
                if (thisKey.equals("cid")) return false;
            }
            return true;
        }
    }
    enum eyeColour { amb, blu, brn, gry, grn, hzl, oth }
}
