public class Cell {
    private char symbol;
    private boolean isMine;

    public Cell(char symbol, boolean isMine) {
        this.symbol = symbol;
        this.isMine = isMine;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }
}