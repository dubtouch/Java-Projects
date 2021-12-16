import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static char[][] game = new char[3][3];
    static int counter = 0;

    public static void main(String[] args) {
        createGame();
        printGame();
        play();
    }

    public static void play() {
        boolean xTurn = true;
        while(gameOn()) {
            if (xTurn) {
                checkCell('X');
                counter++;
                xTurn = false;
                printGame();
            } else {
                checkCell('O');
                counter++;
                xTurn = true;
                printGame();
            }
        }
    }

    public static void checkCell(char ch) {
        while (true) {
            System.out.println("Enter the coordinates: ");
            int a = scanner.nextInt() - 1;
            int b = scanner.nextInt() - 1;
            if (a > 2 || a < 0 || b > 2 || b < 0) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            if (game[a][b] == ' ') {
                game[a][b] = ch;
                break;
            }
            else {
                System.out.println("This cell is occupied! Choose another one!");
            }
        }
    }

    public static void createGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                game[i][j] = ' ';
            }
        }
    }

    public static void printGame() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(game[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public static boolean gameOn() {
        if (checkWin('X')) {
            System.out.println("X wins");
            return false;
        }
        else if (checkWin('O')) {
            System.out.println("O wins");
            return false;
        }
        else if (counter == 9)  {
            System.out.println("Draw");
            return false;
        }
        return true;
    }

    public static boolean checkWin(char a) {
        return checkLine(a, game[0][0], game[0][1], game[0][2])
                || checkLine(a, game[1][0], game[1][1], game[1][2])
                || checkLine(a, game[2][0], game[2][1], game[2][2])
                || checkLine(a, game[0][0], game[1][0], game[2][0])
                || checkLine(a, game[0][1], game[1][1], game[2][1])
                || checkLine(a, game[0][2], game[1][2], game[2][2])
                || checkLine(a, game[0][0], game[1][1], game[2][2])
                || checkLine(a, game[0][2], game[1][1], game[2][0]);
    }

    public static boolean checkLine(char a, char b, char c, char d) {
        return a == b && !(a != c | a != d);
    }
}
