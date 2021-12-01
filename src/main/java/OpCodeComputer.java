import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class OpCodeComputer {
    static int acc = 0;
//    static List<Instruction<String, Integer>> history = new ArrayList<>();
    static List<Integer> history = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
//        nop +0
//        acc +1
//        jmp +4
//        acc +3
//        jmp -3
//        acc -99
//        acc +1
//        jmp -4
//        acc +6
        File file = new File("data/instructions.txt");
        Scanner fileContents = new Scanner(file);
        var lines = new ArrayList<String>();
//        int uniqueBags = 0;


        while (fileContents.hasNext()) {
            lines.add(fileContents.nextLine());
        }

        var instructions = convertToInstructions(lines);
        boolean found = false;

        //try new input iteration
        for (int i = 0; i < instructions.size(); i++) {
            var thisIns = instructions.get(i);
            if (thisIns.operation.equals("nop")) {
                var from = "nop"; var to = "jmp";
                System.out.print("Iteration " + i + from + " to " + to);
//                System.out.println(String.format(" at position %d%n", i));
                var newList = replaceOperationAt(from, to, i, new ArrayList<>(instructions));
                if (newList != null && process(newList)) {
                    found = true;
                    System.out.println(" succeeded!\n");
                    break;
                }
                System.out.println(" failed!");
                acc = 0;
            }

            else if (thisIns.operation.equals("jmp")) {
                var from = "jmp"; var to = "nop";
                System.out.print("Iteration " + i + from + " to " + to);
//                System.out.println(String.format(" at position %d%n", i));
                var newList = replaceOperationAt(from, to, i, new ArrayList<>(instructions));
                if (newList != null && process(newList)) {
                    found = true;
                    System.out.println(" succeeded!\n");
                    break;
                }
                System.out.println(" failed!");
                acc = 0;
            }
//            System.out.println();
        }
//        for (var thisIns : List.copyOf(instructions)) {
//            if (thisIns.operation.equals("jmp")) {
//                thisIns.operation = "nop";
//                if (process(instructions)) {
//                    found = true;
//                    break;
//                }
//
//            }
//        }

//        process(lines);

        if (found) System.out.println("acc = " + acc);
        else System.out.println("looped");
    }

    static List<Instruction> replaceOperationAt(final String from, final String to, final int position, final List<Instruction> instructions) {
//        var newList = List.copyOf(instructions);
        var instruction = instructions.get(position);
        if (instruction.operation.equals(from)) {
            instructions.set(position, new Instruction(to, instruction.argument));
            return instructions;
        }
        return null;
    }

    private static List<Instruction> convertToInstructions(final ArrayList<String> lines) {
        var results = new ArrayList<Instruction>();
        lines.forEach(i -> {
            var instructions = i.split(" ");
            results.add(new Instruction(instructions[0], Integer.valueOf(instructions[1])));
        });
        return results;
    }

    static boolean process(List<Instruction> instructions) {
        int nopCounter = 0;
        int accCounter = 0;
        int jmpCounter = 0;

        for (int i = 0; i < instructions.size(); i++) {
//            var instructions = lines.get(i).split(" ");
            var instruction = instructions.get(i);
            switch (instruction.operation) {
                case "nop":
                    if (history.contains(i)) nopCounter++;
                    history.add(i);
                    break;
                case "acc":
                    if (history.contains(i)) accCounter++;
                    acc += instruction.argument;
                    history.add(i);
                    break;
                case "jmp":
                    if (history.contains(i)) jmpCounter++;
                    i += instruction.argument - 1;
                    history.add(i);
                    break;
                default:
                    System.out.println("bad instruction");
            }
            if (nopCounter > 300 || jmpCounter > 300 || accCounter > 300) return false;
        }
        return true;
    }

    static class Instruction {
        private String operation;
        private Integer argument;

        public Instruction(String operation, Integer argument) {
            this.operation = operation;
            this.argument = argument;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public Integer getArgument() {
            return argument;
        }

        public void setArgument(Integer argument) {
            this.argument = argument;
        }

        @Override
        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            Instruction<?, ?> that = (Instruction<?, ?>) o;
//            return Objects.equals(operation, that.operation) &&
//                    Objects.equals(argument, that.argument);
            return ((Instruction)o).argument == this.argument && ((Instruction)o).operation == this.operation;
        }

//        @Override
//        public int hashCode() {
//            return Objects.hash(operation, argument);
//        }
    }
}
