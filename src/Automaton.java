import java.io.*;
import java.util.ArrayList;


public class Automaton {
    private Rule rule;
    private ArrayList<Generation> generations;
    char falseSymbol;
    char trueSymbol;

    public Automaton(int ruleNum, Generation initial) {
        rule = new Rule(ruleNum);
        generations = new ArrayList<>();
        generations.add(initial);
        trueSymbol = '1';
        falseSymbol = '0';
    }

    public Automaton(String fileName) {
        String initialCellStates = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            rule = new Rule(Integer.parseInt(reader.readLine()));
            String valueSymbols = reader.readLine();
            falseSymbol = valueSymbols.split("")[0].trim().charAt(0);
            trueSymbol = valueSymbols.split("")[1].trim().charAt(0);
            initialCellStates = reader.readLine();
        } catch (IOException ignored) {
        }

        Generation initialGeneration = new Generation(initialCellStates, trueSymbol);
        generations.add(initialGeneration);

    }

    public int getRuleNum() {
        return rule.getRuleNum();
    }

    public void evolve(int numSteps) {
        if (numSteps <= 0) {
            return;
        }

        for (int i = 0; i < numSteps; i++) {
            Generation lastGeneration = generations.get(generations.size() - 1);
            generations.add(rule.evolve(lastGeneration));
        }

    }

    public Generation getGeneration(int stepNum) {
        if (stepNum > generations.size() - 1) {
            evolve(stepNum - getTotalSteps());
        }

        return generations.get(stepNum);
    }

    public int getTotalSteps() {
        return generations.size() - 1;
    }

    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;

        for (Generation generation : generations) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append(System.lineSeparator());
            }
            for (boolean b : generation.getStates()) {
                if (b) {
                    stringBuilder.append(trueSymbol);
                } else {
                    stringBuilder.append(falseSymbol);
                }
            }
        }


        return stringBuilder.toString();

    }

    public void saveEvolution(String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(toString());
            writer.close();

        } catch (IOException ignored) {
        }

    }


}
