import java.util.Scanner;

public class Cinema {
    static Scanner scanner = new Scanner(System.in);
    static int purchasedTickets = 0;
    static int currentIncome = 0;
    static int rows;
    static int seats;
    static char[][] cinemaSeats;

    public static void main(String[] args) {

        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();
        cinemaSeats = createCinemaSeats();
        boolean flag = true;
        while (flag) {
            menu();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> printSeats();
                case 2 -> buyTicket();
                case 3 -> printStatistics();
                case 0 -> flag = false;
            }
        }
    }

    public static void menu() {
        System.out.println("""
                \n1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit""");
    }

    public static void printStatistics() {
        System.out.println("\nNumber of purchased tickets:" + purchasedTickets);
        System.out.printf("Percentage: %.2f%%" + "", (double) (purchasedTickets * 100) / (rows * seats));
        System.out.println("\nCurrent income: $" + currentIncome);
        System.out.print("Total income: $");
        totalIncome();
    }

    public static void buyTicket() {
        while(true) {
            System.out.println("Enter a row number:");
            int row = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seat = scanner.nextInt();
            if (row < 1 || row > rows || seat < 1 || seat > seats) {
                System.out.println("Wrong input!");
                continue;
            }
            if (cinemaSeats[row - 1][seat - 1] == 'B') {
                System.out.println("That ticket has already been purchased!");
            } else {
                System.out.println("Ticket price: $" + seatPrice(row));
                cinemaSeats[row - 1][seat - 1] = 'B';
                currentIncome += seatPrice(row);
                purchasedTickets++;
                break;
            }
        }
    }

    public static char[][] createCinemaSeats() {
        char[][] cinemaSeats = new char[rows][seats];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                cinemaSeats[i][j] = 'S';
            }
        }
        return cinemaSeats;
    }

    public static void printSeats() {
        System.out.print("Cinema:\n  ");
        for (int i = 0; i < cinemaSeats[0].length; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();
        for (int i = 0; i < cinemaSeats.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < cinemaSeats[0].length; j++) {
                System.out.print(cinemaSeats[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void totalIncome() {
        System.out.println(rows * seats <= 60 ? rows * seats * 10 : (rows % 2 == 0) ? rows * seats * 9 : (rows / 2) * seats * 10 + (rows / 2 + 1) * seats * 8);
    }

    public static int seatPrice(int row) {
        return (rows * seats <= 60) ? 10 : (row <= rows / 2) ? 10 : 8;
    }
}