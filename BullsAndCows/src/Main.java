import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int turns = 1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        String length = scanner.nextLine();
        if (!isValid(length)) return;
        System.out.println("Input the number of possible symbols in the code:");
        String characters = scanner.nextLine();
        if (!isValid(characters)) return;
        int a = Integer.parseInt(length);
        int b = Integer.parseInt(characters);
        if (a > b || a == 0) {
            System.out.println("Error: it's not possible to generate a code with a length of " + a + " with " + b + " unique symbols.");
            return;
        }
        if (b > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }
        printCharactersUsed(b, a);
        System.out.println("Okay, let's start a game!");
        String code = randomGenerator(a, b);
        while (true) {
            System.out.println("Turn " + turns + ":");
            System.out.println("*".repeat(code.length()));
            turns++;
            String guess = scanner.next();
            checkCowsBulls(code, guess);
            if (guess.equals(code)) {
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }
        }
    }

    public static boolean isValid(String a) {
        try {
            Integer.parseInt(a);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + a + "\" isn't a valid number.");
            return false;
        }
    }

    public static void printCharactersUsed(int characters, int length) {
        if (characters <= 10) System.out.println("The secret is prepared: " + "*".repeat(length) + " (0-" + (characters - 1) + ").");
        else {
            char a = (char) (characters + 86);
            System.out.println("The secret is prepared: " + "*".repeat(length) + " (0-9, a-" + a + ").");
        }
    }

    public static String randomGenerator(int length, int characters) {
        StringBuilder random = new StringBuilder();
        Random rand = new Random();
        while (random.length() < length) {
            char a = (char) (rand.nextInt(characters) + 48);
            if (a > 57) a += 39;
            if (!random.toString().contains(String.valueOf(a))) {
                random.append(a);
            }
        }
        return random.toString();
    }

    public static void checkCowsBulls(String code, String input) {
        int cows = 0;
        int bulls = 0;
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == input.charAt(i)) bulls++;
            else if (code.contains("" + input.charAt(i))) cows++;
        }
        StringBuilder sb = new StringBuilder("Grade: ");
        if (bulls != 0 && cows != 0) {
            sb.append(bulls).append(" bull(s) and ").append(cows).append(" cow(s)");
        } else if (bulls != 0) {
            sb.append(bulls).append(" bull(s). ");
        } else if (cows != 0) {
            sb.append(cows).append(" cow(s). ");
        } else {
            sb.append("None.");
        }
        System.out.println(sb);
    }
}