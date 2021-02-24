import java.util.HashMap;


public class Rule {
    private final int ruleNum;
    private final boolean[] ruleStates = new boolean[8];
    private final HashMap<String, Boolean> binaryValues = new HashMap<>();
    private char[] charArray;

    /*
    On the first generation, the middle index is set to 1 (positive),
    for the next generation, we compare the states of its two neighbors (left and right) in order from left to right.
    example: to get the next generation of the middle index, we would get 010. We then compare that to the corresponding
    eight possible states and if it had any numbers divided into it. In the case of rule 145, it would be set to 0 the next
    generation.
     */

    public Rule(int ruleNum) {
        if (ruleNum > 255) {
            ruleNum = 255;
        } else if (ruleNum < 0) {
            ruleNum = 0;
        }

        this.ruleNum = ruleNum;

        padBinary();
        createValueArray();
        fillHashmap();

    }

    private void padBinary() {

        char[] binaryCharArray = Integer.toBinaryString(ruleNum).toCharArray();

        charArray = new char[8];

        int amountToPad = charArray.length - binaryCharArray.length;

        for (int i = 0; i < amountToPad; i++) {
            charArray[i] = '0';
        }

        int charIdx = 0;
        for (int i = amountToPad; i < binaryCharArray.length + amountToPad; i++) {
            charArray[i] = binaryCharArray[charIdx];
            charIdx++;
        }

    }

    private void createValueArray() {
        for (int i = 0; i < charArray.length; i++) {
            if (Integer.parseInt(String.valueOf(charArray[i])) == 1) {
                ruleStates[i] = true;
            } else {
                ruleStates[i] = false;
            }
        }
    }

    private void fillHashmap() {
        binaryValues.put("111", ruleStates[0]);
        binaryValues.put("110", ruleStates[1]);
        binaryValues.put("101", ruleStates[2]);
        binaryValues.put("100", ruleStates[3]);
        binaryValues.put("011", ruleStates[4]);
        binaryValues.put("010", ruleStates[5]);
        binaryValues.put("001", ruleStates[6]);
        binaryValues.put("000", ruleStates[7]);
    }



    public Generation evolve(Generation gen) {
        boolean[] generationBooleans = gen.getStates();

        boolean[] newGeneration = new boolean[generationBooleans.length];

        for (int i = 0; i < generationBooleans.length; i++) {
            newGeneration[i] = evolve(getNeighborhood(i, gen));
        }

        return new Generation(newGeneration);

    }

    public static boolean[] getNeighborhood(int idx, Generation gen) {
        boolean[] generationBooleans = gen.getStates();
        boolean[] neighbors = new boolean[3];

        if (idx == 0) {
            neighbors[0] = generationBooleans[generationBooleans.length - 1];
            neighbors[1] = generationBooleans[idx];
            neighbors[2] = generationBooleans[idx + 1];
        } else if (idx == generationBooleans.length - 1) {
            neighbors[0] = generationBooleans[idx - 1];
            neighbors[1] = generationBooleans[idx];
            neighbors[2] = generationBooleans[0];
        } else {
            neighbors[0] = generationBooleans[idx - 1];
            neighbors[1] = generationBooleans[idx];
            neighbors[2] = generationBooleans[idx + 1];
        }

        return neighbors;
    }

    public boolean evolve(boolean[] neighborhood) {
        StringBuilder intNeighborhood = new StringBuilder();

        for (boolean b : neighborhood) {
            if (b) {
                intNeighborhood.append(1);
            } else {
                intNeighborhood.append(0);
            }
        }

        return binaryValues.get(intNeighborhood.toString());
    }

    public int getRuleNum() {
        return ruleNum;
    }

}
