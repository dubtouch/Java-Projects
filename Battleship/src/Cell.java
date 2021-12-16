public class Cell {

    private boolean isShip = false;
    private boolean isExplored = false;
    private Ship shipType;

    public char getSymbol() {
        if (isShip && isExplored) return 'X';
        if (isExplored) return 'M';
        return '~';
    }

    public void setShip(boolean ship) {
        isShip = ship;
    }

    public Ship getShipType() {
        return shipType;
    }

    public void setShipType(Ship shipType) {
        this.shipType = shipType;
    }

    public boolean isShip() {
        return isShip;
    }

    public boolean isExplored() {
        return isExplored;
    }

    public void setExplored(boolean explored) {
        isExplored = explored;
    }
}