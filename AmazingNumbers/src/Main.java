import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        menu();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a request:");
            String input = scanner.nextLine();
            String[] arr = input.split(" ");
            if (input.isEmpty() || arr.length == 0) {
                menu();
                continue;
            }
            if (arr.length == 1) {
                if (isReal(arr[0])) {
                    long a = Long.parseLong(arr[0]);
                    if (a == 0) {
                        System.out.println("Goodbye!");
                        break;
                    }
                    print(a);
                }
            } else if (arr.length == 2) {
                if (isReal(arr[0], arr[1])) {
                    long a = Long.parseLong(arr[0]);
                    long b = Long.parseLong(arr[1]);
                    print(a, b);
                }
            } else {
                if (isReal(arr[0], arr[1])) {
                    long a = Long.parseLong(arr[0]);
                    long b = Long.parseLong(arr[1]);
                    String[] propertyArray = new String[arr.length - 2];
                    for (int i = 2; i < arr.length; i++) {
                        propertyArray[i - 2] = arr[i].toUpperCase();
                    }
                    print(a, b, propertyArray);
                }
            }
        }
    }

    public static void menu() {
        System.out.println("Welcome to Amazing Numbers!");
        System.out.println("""
                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                \t* the first parameter represents a starting number;
                \t* the second parameters show how many consecutive numbers are to be processed;
                - two natural numbers and properties to search for
                  (EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD)
                  for example: "1 100 even sad";
                - a property preceded by minus must not be present in numbers;
                - separate the parameters with one space;
                - enter 0 to exit.
                - press the 'Enter' key to print the menu""");
    }

    public static boolean isReal(String... num) {
        boolean flag = true;
        try {
            for (String number : num) {
                long a = Long.parseLong(number);
                if (a < 0) {
                    if (flag) System.out.println("The first parameter should be a natural number or zero.");
                    else System.out.println("The second parameter should be a natural number.");
                    return false;
                }
                flag = false;
            }
        } catch (NumberFormatException e) {
            if (flag) System.out.println("The first parameter should be a natural number or zero.");
            else System.out.println("The second parameter should be a natural number.");
            return false;
        }
        return true;
    }

    public static boolean isValid(String[] properties) {
        ArrayList<String> validProperties = new ArrayList<>();
        ArrayList<String> wrongProperties = new ArrayList<>();
        for (String property : properties) {
            try {
                if (property.charAt(0) == '-') {
                    Property.valueOf(property.substring(1));
                } else {
                    Property.valueOf(property);
                }
                validProperties.add(property);
            } catch (IllegalArgumentException e) {
                wrongProperties.add(property);
            }
        }
        if (!wrongProperties.isEmpty()) {
            if (wrongProperties.size() == 1) {
                System.out.println("The property " + wrongProperties + " is wrong.\n" + "Available properties:\n" + Arrays.toString(Property.values()));
                return false;
            }
            System.out.println("The properties " + wrongProperties + " are wrong.\n" + "Available properties:\n" + Arrays.toString(Property.values()));
            return false;
        }
        if (hasExclusiveProperties("EVEN", "ODD", validProperties) || hasExclusiveProperties("DUCK", "SPY", validProperties) || hasExclusiveProperties("SUNNY", "SQUARE", validProperties) || hasExclusiveProperties("SAD", "HAPPY", validProperties)) {
            return false;
        }
        if (hasExclusiveProperties("-EVEN", "-ODD", validProperties) || hasExclusiveProperties("-SAD", "-HAPPY", validProperties)) {
            return false;
        }
        for (String a : validProperties) {
            if (hasExclusiveProperties(a, "-" + a, validProperties)) return false;
        }
        return true;
    }

    public static boolean hasExclusiveProperties(String a, String b, ArrayList<String> arr) {
        if (arr.contains(a) && arr.contains(b)) {
            System.out.println("The request contains mutually exclusive properties: [" + a + ", " + b + "]\nThere are no numbers with these properties.");
            return true;
        }
        return false;
    }

    public static boolean hasProperty(long a, String property) {
        switch (property) {
            case "EVEN":
                if (isEven(a)) {
                    return true;
                }
                break;
            case "ODD":
                if (!isEven(a)) {
                    return true;
                }
                break;
            case "BUZZ":
                if (isBuzz(a)) {
                    return true;
                }
                break;
            case "DUCK":
                if (isDuck(a)) {
                    return true;
                }
                break;
            case "PALINDROMIC":
                if (isPalindromic(a)) {
                    return true;
                }
                break;
            case "GAPFUL":
                if (isGapful(a)) {
                    return true;
                }
                break;
            case "SPY":
                if (isSpy(a)) {
                    return true;
                }
                break;
            case "SUNNY":
                if (isSunny(a)) {
                    return true;
                }
                break;
            case "SQUARE":
                if (isSquare(a)) {
                    return true;
                }
                break;
            case "JUMPING":
                if (isJumping(a)) {
                    return true;
                }
                break;
            case "HAPPY":
                if (isHappy(a)) {
                    return true;
                }
                break;
            case "SAD":
                if (!isHappy(a)) {
                    return true;
                }
                break;
        }
        return false;
    }

    public static void print(long a) {
        System.out.println("Properties of " + a);
        System.out.println(isEven(a) ? "\teven: true\n\todd: false" : "\teven: false\n\todd: true");
        System.out.println(isBuzz(a) ? "\tbuzz: true" : "\tbuzz: false");
        System.out.println(isDuck(a) ? "\tduck: true" : "\tduck: false");
        System.out.println(isPalindromic(a) ? "\tpalindromic: true" : "\tpalindromic: false");
        System.out.println(isGapful(a) ? "\tgapful: true" : "\tgapful: false");
        System.out.println(isSpy(a) ? "\tspy: true" : "\tspy: false");
        System.out.println(isSunny(a) ? "\tsunny: true" : "\tsunny: false");
        System.out.println(isSquare(a) ? "\tsquare: true" : "\tsquare: false");
        System.out.println(isJumping(a) ? "\tjumping: true" : "\tjumping: false");
        System.out.println(isHappy(a) ? "\thappy: true" : "\thappy: false");
        System.out.println(!isHappy(a) ? "\tsad: true" : "\tsad: false");
    }

    public static void print(long a, long b) {
        for (long i = a; i < a + b; i++) {
            printProperty(i);
        }
    }

    public static void print(long a, long b, String... properties) {
        if (isValid(properties)) {
            int counter = 0;
            while (counter < b) {
                boolean flag = true;
                for (String property : properties) {
                    if (property.charAt(0) != '-') {
                        if (!hasProperty(a, property)) {
                            flag = false;
                            break;
                        }
                    } else {
                        if (hasProperty(a, property.substring(1))) {
                            flag = false;
                            break;
                        }
                    }
                }
                if (flag) {
                    counter++;
                    printProperty(a);
                }
                a++;
            }
        }
    }

    public static void printProperty(long a) {
        System.out.print(a + " is ");
        if (isEven(a)) System.out.print("even");
        else System.out.print("odd");
        if (isBuzz(a)) System.out.print(", buzz");
        if (isDuck(a)) System.out.print(", duck");
        if (isPalindromic(a)) System.out.print(", palindromic");
        if (isGapful(a)) System.out.print(", gapful");
        if (isSpy(a)) System.out.print(", spy");
        if (isSunny(a)) System.out.print(", sunny");
        if (isSquare(a)) System.out.print(", square");
        if (isJumping(a)) System.out.print(", jumping");
        if (isHappy(a)) System.out.print(", happy");
        if (!isHappy(a)) System.out.print(", sad");
        System.out.println();
    }

    public static boolean isEven(long a) {
        return a % 2 == 0;
    }

    public static boolean isBuzz(long a) {
        boolean lastDigit = a % 10 == 7;
        boolean div7 = a % 7 == 0;
        return lastDigit || div7;
    }

    public static boolean isDuck(long a) {
        String isDuck = "" + a;
        return isDuck.contains("0") && isDuck.charAt(0) != '0';
    }

    public static boolean isPalindromic(long a) {
        String isPalindrome = "" + a;
        for (int i = 0; i < isPalindrome.length() / 2; i++) {
            if (isPalindrome.charAt(i) != isPalindrome.charAt(isPalindrome.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isGapful(long a) {
        String number = String.valueOf(a);
        String gap = "" + number.charAt(0) + number.charAt(number.length() - 1);
        return number.length() >= 3 && a % Long.parseLong(gap) == 0;
    }

    public static boolean isSpy(long a) {
        String number = String.valueOf(a);
        long sum = 0;
        long product = 1;
        for (int i = 0; i < number.length(); i++) {
            sum += Character.getNumericValue(number.charAt(i));
            product *= Character.getNumericValue(number.charAt(i));
        }
        return sum == product;
    }

    public static boolean isSunny(long a) {
        return isSquare(a + 1);
    }

    public static boolean isSquare(long a) {
        long root = (long) Math.sqrt(a);
        return root * root == a;
    }

    public static boolean isJumping(long a) {
        String number = String.valueOf(a);
        for (int i = 0; i < number.length() - 1; i++) {
            int current = Character.getNumericValue(number.charAt(i));
            int next = Character.getNumericValue(number.charAt(i + 1));
            if (current != next - 1 && current != next + 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isHappy(long a) {
        String number = String.valueOf(a);
        a = 0;
        while (true) {
            for (int i = 0; i < number.length(); i++) {
                long digit = Character.getNumericValue(number.charAt(i));
                a += digit * digit;
            }
            if (a == 1) return true;
            if (a == 4) return false;
            number = String.valueOf(a);
            a = 0;
        }
    }

    public enum Property {
        EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD
    }
}