import java.util.Scanner;

public class CoffeeMachine {
    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private int money;

    private final Coffee espresso = new Coffee(250, 0, 16, 4);
    private final Coffee latte = new Coffee(350, 75, 20, 7);
    private final Coffee cappuccino = new Coffee(200, 100, 12, 6);

    private final Scanner scanner = new Scanner(System.in);

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;
    }

    public void run() {
        boolean flag = true;
        System.out.println("Coffee Machine running!");
        while (flag) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String action = scanner.next();
            switch (action) {
                case "buy" -> {
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ");
                    String choice = scanner.next();
                    if (choice.equals("1") && checkAvailableIngredients(espresso)) makeCoffee(espresso);
                    else if (choice.equals("2") && checkAvailableIngredients(latte)) makeCoffee(latte);
                    else if (choice.equals("3") && checkAvailableIngredients(cappuccino)) makeCoffee(cappuccino);
                }
                case "fill" -> fill();
                case "take" -> take();
                case "remaining" -> System.out.println(this);
                case "exit" -> flag = false;
                default -> System.out.println("Wrong input");
            }
        }
    }

    public void makeCoffee(Coffee coffee){
        cups--;
        this.water -= coffee.getWater();
        this.milk -= coffee.getMilk();
        this.coffeeBeans -= coffee.getCoffeeBeans();
        this.money += coffee.getPrice();
    }

    public void fill() {
        System.out.println("Write how many ml of water you want to add: ");
        this.water += scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add: ");
        this.milk += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add: ");
        this.coffeeBeans += scanner.nextInt();
        System.out.println("Write how many disposable cups of coffee you want to add: ");
        this.cups += scanner.nextInt();
    }

    public void take() {
        System.out.println("I gave you $" + this.money);
        this.money = 0;
    }

    @Override
    public String toString() {
        return "The coffee machine has:\n" + this.water +
                " ml of water\n" + this.milk +
                " ml of milk\n" + this.coffeeBeans +
                " g of coffee beans\n" + this.cups +
                " disposable cups\n$" + this.money +
                " of money";
    }

    public boolean checkAvailableIngredients(Coffee coffee) {
        if (this.water < coffee.getWater() || this.milk < coffee.getMilk() || this.coffeeBeans < coffee.getCoffeeBeans()) {
            System.out.println("Sorry, not enough ingredients!");
            return false;
        }
        System.out.println("I have enough resources, making you a coffee!");
        return true;
    }
}
