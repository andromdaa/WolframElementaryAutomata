import java.util.Arrays;

public class Generation {
    //represents the states of a row at a fixed time
    private final boolean[] cellStates;

    public Generation(boolean... cellStates) {
        if (cellStates == null || cellStates.length == 0) {
            cellStates = new boolean[]{false};
        }

        this.cellStates = Arrays.copyOf(cellStates, cellStates.length);
    }

    public Generation(String states, char trueSymbol) {
        if (states == null || states.isEmpty()) {
            cellStates = new boolean[]{false};
            return;
        }

        //create new boolean array the size of the char array
        cellStates = new boolean[states.length()];

        //loop over the char array and assign its correct corresponding value
        for (int i = 0; i < states.length(); i++) {
            if (states.charAt(i) == trueSymbol) {
                cellStates[i] = true;
            } else {
                cellStates[i] = false;
            }
        }
    }

    public boolean getState(int idx) {
        return cellStates.clone()[idx];
    }

    public boolean[] getStates() {
        return cellStates.clone();
    }


    public String getStates(char falseSymbol, char trueSymbol) {
        StringBuilder stringBuilder = new StringBuilder();

        for (boolean s : getStates()) {
            if (s) {
                stringBuilder.append(trueSymbol);
            } else {
                stringBuilder.append(falseSymbol);
            }
        }

        return stringBuilder.toString();
    }

    public int size() {
        return cellStates.clone().length;
    }
}
