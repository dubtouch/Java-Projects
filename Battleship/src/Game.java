import java.io.IOException;
import java.util.Scanner;

public class Game {
    Cell[][] playerOneField ;
    Cell[][] playerTwoField;
    Ship[] playerOneShips;
    Ship[] playerTwoShips;
    boolean playerOneTurn = true;
    Scanner scanner = new Scanner(System.in);

    public Game() {
        System.out.println("Player 1, place your ships on the game field");
        playerOneField = newField();
        playerOneShips = newShipList();
        for (Ship ship : playerOneShips) {
            setShip(ship, playerOneField);
            printFieldUncovered(playerOneField);
        }
        System.out.println("Player 2, place your ships on the game field");
        playerTwoField = newField();
        playerTwoShips = newShipList();
        promptEnterKey();
        for (Ship ship : playerTwoShips) {
            setShip(ship, playerTwoField);
            printFieldUncovered(playerTwoField);
        }
        promptEnterKey();
    }

    public Cell[][] newField() {
        Cell[][] field = new Cell[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = new Cell();
            }
        }
        printField(field);
        return field;
    }

    public Ship[] newShipList() {
        return new Ship[]{
                new Ship(ShipName.AIRCRAFTCARRIER, 5),
                new Ship(ShipName.BATTLESHIP, 4),
                new Ship(ShipName.SUBMARINE, 3),
                new Ship(ShipName.CRUISER, 3),
                new Ship(ShipName.DESTROYER, 2)
        };
    }

    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        while(isGameOn(playerOneShips) && isGameOn(playerTwoShips)) {
            if (playerOneTurn) {
                System.out.println("Player 1, it's your turn:");
                printField(playerTwoField);
                System.out.println("---------------------");
                printFieldUncovered(playerOneField);
                String input = scanner.next();
                if (input.charAt(0) < 65 || input.charAt(0) > 74 || Integer.parseInt(input.substring(1)) < 1 || Integer.parseInt(input.substring(1))  > 10) {
                    System.out.println("Error! You entered the wrong coordinates! Try again:");
                    continue;
                }
                checkCell(input, playerTwoField);
                playerOneTurn = false;
            } else {
                System.out.println("Player 2, it's your turn:");
                printField(playerOneField);
                System.out.println("---------------------");
                printFieldUncovered(playerTwoField);
                String input = scanner.next();
                if (input.charAt(0) < 65 || input.charAt(0) > 74 || Integer.parseInt(input.substring(1)) < 1 || Integer.parseInt(input.substring(1))  > 10) {
                    System.out.println("Error! You entered the wrong coordinates! Try again:");
                    continue;
                }
                checkCell(input, playerOneField);
                playerOneTurn = true;
            }
            promptEnterKey();
        }
    }

    public void checkCell(String input, Cell[][] field) {
        int y = Character.getNumericValue(input.charAt(0) - 17);
        int x = Integer.parseInt(input.substring(1)) - 1;
        field[y][x].setExplored(true);
        if (field[y][x].isShip()) {
            field[y][x].getShipType().decreaseSize();
            if (!isGameOn(playerOneShips) || !isGameOn(playerTwoShips)) System.out.println("You sank the last ship. You won. Congratulations!");
            if (field[y][x].getShipType().getSize() == 0) System.out.println("You sank a ship!");
            else System.out.println("You hit a ship!");

        }
        else System.out.println("You missed!");
    }

    public boolean isGameOn(Ship[] ships) {
        for (Ship ship : ships) {
            if (ship.getSize() > 0) {
                return true;
            }
        }
        return false;
    }

    public void printField(Cell[][] field) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < 10; i++) {
            System.out.print((char) (i + 65));
            for (int j = 0; j < 10; j++) {
                System.out.print(" " + field[i][j].getSymbol());
            }
            System.out.println();
        }
    }

    public void printFieldUncovered(Cell[][] field) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < 10; i++) {
            System.out.print((char) (i + 65));
            for (int j = 0; j < 10; j++) {
                if (field[i][j].isShip() && !field[i][j].isExplored()) System.out.print(" O");
                else System.out.print(" " + field[i][j].getSymbol());
            }
            System.out.println();
        }
    }

    public void setShip(Ship ship, Cell[][] field) {
        System.out.println("Enter the coordinates of the " + ship.getName().getName() + " (" + ship.getSize() + " cells):");
        while (true) {
            String a = scanner.next().toUpperCase();
            String b = scanner.next().toUpperCase();
            if (!checkInput(ship, field, a, b)) continue;
            int size = ship.getSize();
            if (a.charAt(0) == b.charAt(0)) {
                int y = Character.getNumericValue(a.charAt(0) - 17);
                int x = Math.min(Integer.parseInt(a.substring(1)), Integer.parseInt(b.substring(1))) - 1;
                while (size > 0) {
                    field[y][x].setShip(true);
                    field[y][x].setShipType(ship);
                    x++;
                    size--;
                }
            } else {
                int y = Character.getNumericValue( (char) (Math.min(a.charAt(0), b.charAt(0)) - 17));
                int x = Integer.parseInt(a.substring(1)) - 1;
                while (size > 0) {
                    field[y][x].setShip(true);
                    field[y][x].setShipType(ship);
                    y++;
                    size--;
                }
            }
            break;
        }
    }

    public boolean checkInput(Ship ship, Cell[][] field, String a, String b) {
        if (a.charAt(0) != b.charAt(0) && a.charAt(1) != b.charAt(1)) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }
        int size;
        if (a.charAt(0) == b.charAt(0)) size = Math.abs(Integer.parseInt(a.substring(1)) - Integer.parseInt(b.substring(1))) + 1;
        else size = Math.abs(b.charAt(0) - a.charAt(0)) + 1;
        if (size != ship.getSize()) {
            System.out.printf("Error! Wrong length of the %s! Try again:\n", ship.getName().name());
            return false;
        }
        if (a.charAt(0) == b.charAt(0)) {
            int y = Character.getNumericValue(a.charAt(0) - 17);
            int x = Math.min(Integer.parseInt(a.substring(1)), Integer.parseInt(b.substring(1))) - 1;
            try {
                for (int i = y-1; i < y+2; i++) {
                    for (int j = x-1; j < x + size + 1; j++) {
                        if (field[i][j].isShip() && !field[i][j].getShipType().equals(ship)) {
                            System.out.println("Error! You placed it too close to another one. Try again:");
                            return false;
                        }
                    }
                }
            } catch (IndexOutOfBoundsException ignored) {

            }
        } else {
            int y = Character.getNumericValue( (char) (Math.min(a.charAt(0), b.charAt(0)) - 17));
            int x = Integer.parseInt(a.substring(1)) - 1;
            try {
                for (int i = y - 1; i < y + size + 1; i++) {
                    for (int j = x-1; j < x + 2; j++) {
                        if (field[i][j].isShip() && !field[i][j].getShipType().equals(ship)) {
                            System.out.println("Error! You placed it too close to another one. Try again:");
                            return false;
                        }
                    }
                }
            } catch (IndexOutOfBoundsException ignored) {

            }
        }
        return true;
    }
}

enum ShipName {
    AIRCRAFTCARRIER("Aircraft Carrier"),
    BATTLESHIP("Battleship"),
    SUBMARINE("Submarine"),
    CRUISER("Cruiser"),
    DESTROYER("Destroyer");

    private final String name;

    ShipName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}