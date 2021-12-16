import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static int id = 1;
    static boolean loggedIn = false;
    static boolean flag = true;
    static String loggedAccount = "";

    public static void main(String[] args) {
        String url = "jdbc:sqlite:card.s3db";
        System.out.println(url);
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement()) {
            createTable(statement);
            while (flag) {
                if (!loggedIn) {
                    createOrLogIntoAccount(statement);
                } else {
                    manageAccount(statement);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTable(Statement statement){
        try {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                    "id INTEGER," +
                    "number TEXT NOT NULL," +
                    "pin TEXT NOT NULL," +
                    "balance INTEGER)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createOrLogIntoAccount(Statement statement) {
        System.out.println("""
                1. Create an account
                2. Log into account
                0. Exit""");
        int action = scanner.nextInt();
        try {
            switch (action) {
                case 1 -> {
                    CreditCard card = new CreditCard();
                    statement.executeUpdate("INSERT INTO card VALUES (" +
                            id + ", '" + card.getCreditCardNumber() + "', '" +
                            card.getPin() + "', 0)");
                    id++;
                }
                case 2 -> {
                    System.out.println("Enter your card number:");
                    String number = scanner.next();
                    System.out.println("Enter your PIN:");
                    String pin = scanner.next();
                    ResultSet result = statement.executeQuery("SELECT pin FROM card WHERE number = " + number);
                    if (result.next()) {
                        if (!checkNumberAndPin(result.getString("pin"), pin, number))
                            System.out.println("Wrong card number or PIN!");
                    } else {
                        System.out.println("Wrong card number or PIN!");
                    }
                }
                case 0 -> flag = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkNumberAndPin(String pin, String inputPin, String number) {
        if (pin.equals(inputPin)) {
            loggedIn = true;
            System.out.println("You have successfully logged in!");
            loggedAccount = number;
            return true;
        }
        return false;
    }

    public static void manageAccount(Statement statement){
        System.out.println("""
                1. Balance
                2. Add income
                3. Do transfer
                4. Close account
                5. Log out
                0. Exit""");
        int action = scanner.nextInt();
        try {
            statement.executeQuery("SELECT * FROM card WHERE number = " + loggedAccount);
            ResultSet result;
            switch (action) {
                case 1 -> {
                    result = statement.executeQuery("SELECT * FROM card WHERE number = " + loggedAccount);
                    System.out.println("Balance: " + result.getInt("balance"));
                }
                case 2 -> {
                    System.out.println("Enter income:");
                    int income = scanner.nextInt();
                    statement.executeUpdate("UPDATE card SET balance = balance + " + income + " WHERE number = " + loggedAccount + ";");
                }
                case 3 -> {
                    result = statement.executeQuery("SELECT * FROM card WHERE number = " + loggedAccount);
                    int balance = result.getInt("balance");
                    System.out.println("Transfer\n" +
                            "Enter card number:");
                    String number = scanner.next();
                    if (!checkLuhn(number)) {
                        System.out.println("Probably you made a mistake in the card number. Please try again!");
                        break;
                    }
                    ResultSet result2 = statement.executeQuery("SELECT * FROM card WHERE number = " + number + ";");
                    if (!result2.next()) {
                        System.out.println("Such a card does not exist.");
                        break;
                    }
                    System.out.println("Enter how much money you want to transfer:");
                    int input = scanner.nextInt();
                    if (input > balance) {
                        System.out.println("Not enough money!");
                    } else {
                        statement.executeUpdate("UPDATE card SET balance = balance - " + input + " WHERE number = " + loggedAccount + ";");
                        statement.executeUpdate("UPDATE card SET balance = balance + " + input + " WHERE number = " + number + ";");
                    }
                }
                case 4 -> {
                    statement.executeUpdate("DELETE FROM card WHERE number = " + loggedAccount);
                    loggedIn = false;
                }
                case 5 -> loggedIn = false;
                case 0 -> flag = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkLuhn(String number) {
        int sum = 0;
        for (int j = 0; j < number.length() - 1; j++) {
            int digit = number.charAt(j) - '0';
            if (j % 2 == 0) digit *= 2;
            if (digit > 9) digit -= 9;
            sum += digit;
        }
        sum = sum % 10;
        char lastDigit = (char) (10 - sum + '0');
        return number.charAt(number.length() - 1) == lastDigit;
    }
}