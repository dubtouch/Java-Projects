public class Coffee {

    private final int water;
    private final int coffeeBeans;
    private final int milk;
    private final int price;

    public Coffee(int water,int milk, int coffee, int price) {
        this.water = water;
        this.coffeeBeans = coffee;
        this.milk = milk;
        this.price = price;
    }

    public int getWater() {
        return water;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public int getMilk() {
        return milk;
    }

    public int getPrice() {
        return price;
    }
}