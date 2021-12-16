import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    Cell[][] field = new Cell[9][9];
    int hiddenCells = 81;

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many mines do you want on the field?");
        int mines = scanner.nextInt();
        setField(mines);
        while (mines > 0) {
            printField();
            System.out.println("Set/unset mines marks or claim a cell as free by typing x and y coordinates " +
                    "\nand 'mine' to mark as mine or 'free' to free a cell");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            String action = scanner.next();
            switch (action) {
                case "mine" -> {
                    if (field[y - 1][x - 1].getSymbol() == '.' && field[y - 1][x - 1].isMine()) {
                        field[y - 1][x - 1].setSymbol('*');
                        mines--;
                    } else if (field[y - 1][x - 1].getSymbol() == '.') {
                        field[y - 1][x - 1].setSymbol('*');
                        mines++;
                    } else if (field[y - 1][x - 1].getSymbol() == '*' && field[y - 1][x - 1].isMine()) {
                        mines++;
                        field[y - 1][x - 1].setSymbol('.');
                    } else if (field[y - 1][x - 1].getSymbol() == '*') {
                        mines--;
                        field[y - 1][x - 1].setSymbol('.');
                    }
                }
                case "free" -> {
                    if (field[y - 1][x - 1].isMine()) {
                        System.out.println("You stepped on a mine and failed!");
                        printFieldWithMines();
                        return;
                    } else if (field[y - 1][x - 1].getSymbol() == '.' || field[y - 1][x - 1].getSymbol() == '*') {
                        checkCell(y - 1, x - 1);
                    }
                }
            }
            if (hiddenCells == mines) break;
        }
        printField();
        System.out.println("Congratulations! You found all mines!");
    }

    public void setField(int mines) {
        Random rand = new Random();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                field[i][j] = new Cell('.', false);
            }
        }
        while (mines > 0) {
            int h = rand.nextInt(9);
            int w = rand.nextInt(9);
            if (!field[h][w].isMine()) {
                field[h][w].setMine(true);
                mines--;
            }
        }
    }

    public void printField() {
        System.out.print(" |123456789|\n-|---------|\n");
        for (int i = 0; i < field.length; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < field[0].length; j++) {
                System.out.print(field[i][j].getSymbol());
            }
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }

    public void printFieldWithMines() {
        System.out.print(" |123456789|\n-|---------|\n");
        for (int i = 0; i < field.length; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j].isMine()) System.out.print('X');
                else System.out.print(field[i][j].getSymbol());
            }
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }

    public void checkCell(int i, int j) {
        int counter = 0;
        hiddenCells--;
        for (int a = i - 1; a < i + 2; a++) {
            for (int b = j - 1; b < j + 2; b++) {
                try {
                    if (field[a][b].isMine()) counter++;
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        }
        if (counter == 0) {
            field[i][j].setSymbol('/');
            for (int a = i - 1; a < i + 2; a++) {
                for (int b = j - 1; b < j + 2; b++) {
                    try {
                        if (!field[a][b].isMine() && field[a][b].getSymbol() == '.' || !field[a][b].isMine() && field[a][b].getSymbol() == '*') {
                            checkCell(a, b);
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
            }
        } else field[i][j].setSymbol((char) (counter + '0'));
    }
}