import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BagSorter {
    public static HashMap<Bag, List<Bag>> bagRules = new HashMap<>();
    public static Set<Bag> exceptions = new HashSet<>();
    public static Set<Bag> validBags;
//    private static int uniqueBags = 0;

    public static void main(String[] args) throws FileNotFoundException {
        //dull fuchsia bags contain 2 bright indigo bags, 3 plaid cyan bags, 1 light gold bag.
        File file = new File("data/test.txt");
        Scanner fileContents = new Scanner(file);
        var lines = new ArrayList<String>();
        int uniqueBags = 0;

        while (fileContents.hasNext()) {
            lines.add(fileContents.nextLine());
        }

        for (var thisLine : lines) {
            var words = thisLine.split("contain");
            var container = words[0].split(" ");
            ArrayList<Bag> bagsWithin = null;
            boolean canContain = true;

            var modifier = Modifier.valueOf(container[0]);
            var colour = Colour.valueOf(container[1]);
            if (thisLine.contains("no other")) {
                canContain = false;
                bagRules.put(new Bag(modifier, colour, canContain), bagsWithin);
                continue;
            }

            String[] containees;
            Modifier innerModifier;
            Colour innerColour;
            int counter;
            if (words[1].contains(",")) {
                var temp = words[1].trim().split(",");
                bagsWithin = new ArrayList<>();
                for (var thisBag : temp) {
                    containees = thisBag.trim().split(" ");

                    counter = Integer.parseInt(containees[0]);
                    innerModifier = Modifier.valueOf(containees[1]);
                    innerColour = Colour.valueOf(containees[2]);

                    bagsWithin.add(new Bag(counter, innerModifier, innerColour));
                }
            } else {
                bagsWithin = new ArrayList<>();
                containees = words[1].trim().split(" ");

                counter = Integer.parseInt(containees[0]);
                innerModifier = Modifier.valueOf(containees[1]);
                innerColour = Colour.valueOf(containees[2]);

                bagsWithin.add(new Bag(counter, innerModifier, innerColour));
            }
            bagRules.put(new Bag(modifier, colour, canContain), bagsWithin);
        }

        var goldenBoi = new Bag(Modifier.dark, Colour.blue);
//        validBags = new HashSet<>(searchForContainer(goldenBoi));

        for (var thisBag : bagRules.entrySet()) {
            if (searchForGold(thisBag)) uniqueBags++;
        }
        System.out.println("uniqueBags = " + uniqueBags);

        int totalInner = 0;
        var goldenEntry = findTheRightEntry(goldenBoi);
        for (var thisBag : goldenEntry.getValue()) {
            totalInner += thisBag.count * searchForInnerBags(findTheRightEntry(thisBag));
        }

        System.out.println("totalInner = " + totalInner);

//        var otherBags = secondarySearch(Set.copyOf(validBags));
////        var customSearch = new Bag(Modifier.bright, Colour.red);
////        if (validBags.contains(customSearch)) System.out.println("truth");
//
//        int totalBags = validBags.size() + otherBags.size();
//
//        System.out.println("Bags = " + totalBags);
    }

    static int searchForInnerBags(final Map.Entry<Bag, List<Bag>> bagRule) {
        if (bagRule.getValue() == null) return 0;
        for (var thisBag : bagRule.getValue()) {
            return thisBag.count + (thisBag.count * searchForInnerBags(findTheRightEntry(thisBag)));
        }
        return 0;
    }

    static boolean searchForGold(final Map.Entry<Bag, List<Bag>> bagRule) {
        if (bagRule.getValue() == null) return false;
        for (var thisBag : bagRule.getValue()) {
            if (thisBag.modifier == Modifier.shiny && thisBag.colour == Colour.gold) return true;
            else if (searchForGold(findTheRightEntry(thisBag))) return true;
        }
        return false;
//        for (var thisBag : bagRules.entrySet()) {
//            if (thisBag.getValue().contains(bagRule)) return true;
//            else return searchForGold();
//        }
    }

    static Map.Entry<Bag, List<Bag>> findTheRightEntry(final Bag bag) {
        for (var thisBagRule : bagRules.entrySet()) {
            if (thisBagRule.getKey().equals(bag)) return thisBagRule;
        }
        return null;
    }

    static Set<Bag> secondarySearch(final Set<Bag> bags) {
        var intermediaryBags = new HashSet<Bag>();//HashSet<Bag>();
        for (var thisBag : bags) {
            var temp = searchForContainer(thisBag);

            temp.forEach(System.out::println);
            if (temp.isEmpty()) { //leaf
                validBags.add(thisBag);
            }
            else intermediaryBags.addAll(temp);
        }
        if (intermediaryBags.isEmpty()) return intermediaryBags;
        return secondarySearch(Set.copyOf(intermediaryBags));
    }

    static Set<Bag> searchForContainer(final Bag searchTerm) {
        Set<Bag> results = new HashSet<>();
        for (var thisRule : bagRules.entrySet()) {
            if (!thisRule.getKey().canContain) exceptions.add(thisRule.getKey());
            else if (thisRule.getValue().contains(searchTerm)) results.add(thisRule.getKey());
        }
        return results;
    }

    static class Bag {
        private int count;
        private final Modifier modifier;
        private final Colour colour;
        private Boolean canContain;

        public Bag(int count, Modifier modifier, Colour colour) {
            this.count = count;
            this.modifier = modifier;
            this.colour = colour;
        }

        public Bag(Modifier modifier, Colour colour, Boolean canContain) {
            this.modifier = modifier;
            this.colour = colour;
            this.canContain = canContain;
        }

        public Bag(Modifier modifier, Colour colour) {
            this.modifier = modifier;
            this.colour = colour;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Bag) {
                var one = (Bag) obj;
                return one.modifier == this.modifier && one.colour == this.colour;
            }
            return false;
        }

        @Override
        public String toString() {
            return "Bag{" +
                    "count=" + count +
                    ", modifier=" + modifier +
                    ", colour=" + colour +
                    ", canContain=" + canContain +
                    '}';
        }
    }
}

enum Modifier { light, dark, posh, faded, muted, wavy, striped, dull, dotted, vibrant, dim, pale, shiny, clear, drab, mirrored, plaid, bright }
enum Colour { plum, blue, green, red, orange, purple, pink, chartreuse, black, white, turquoise, aqua, tan, olive, beige, brown, indigo,
    fuchsia, maroon, silver, salmon, tomato, lavender, lime, gold, gray, teal, coral, yellow, bronze, cyan, magenta, violet, crimson }

