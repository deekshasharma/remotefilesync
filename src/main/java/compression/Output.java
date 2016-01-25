package compression;

public class Output {

    private int distance;
    private int length;
    private char unmatchedSymbol;

    public Output(int distance, int length, char unmatchedSymbol) {
        this.distance = distance;
        this.length = length;
        this.unmatchedSymbol = unmatchedSymbol;
    }

    public int getDistance() {
        return distance;
    }

    public int getLength() {
        return length;
    }

    public char getUnmatchedSymbol() {
        return unmatchedSymbol;
    }
}
