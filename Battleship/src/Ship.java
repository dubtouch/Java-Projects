public class Ship {
    private final ShipName name;
    private int size;

    public Ship(ShipName name, int size) {
        this.name = name;
        this.size = size;
    }

    public ShipName getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public void decreaseSize() {
        this.size--;
    }
}