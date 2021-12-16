import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter two numbers in format: {source base} {target base} (To quit type 'exit') ");
            String a = scanner.next();
            if (a.equals("exit")) break;
            String b = scanner.next();
            convert(Integer.parseInt(a), Integer.parseInt(b));
        }
    }

    public static void convert(int sourceBase, int targetBase) {
        System.out.println("Enter whole/decimal number in base " + sourceBase + " to convert to base " + targetBase + " (To go back type 'back') ");
        String[] number = scanner.next().toUpperCase().split("\\.");
        while (!number[0].equals("BACK")) {
            if (number.length == 1) {
                System.out.println("Conversion result:" + new BigInteger(number[0], sourceBase).toString(targetBase).toUpperCase());
                System.out.println("Enter number in base " + sourceBase + " to convert to base " + targetBase + " (To go back type 'back') ");
                number = scanner.next().toUpperCase().split("\\.");
            } else {
                BigDecimal decimal = BigDecimal.ZERO;
                for (int i = 0; i < number[1].length(); i++) {
                    int digit = Character.isDigit(number[1].charAt(i)) ? number[1].charAt(i) - '0' : number[1].charAt(i) - 55;
                    decimal = BigDecimal.valueOf(digit).divide(BigDecimal.valueOf(Math.pow(sourceBase, i + 1)), 5, RoundingMode.UP).add(decimal);
                }
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < 5; i++) {
                    decimal = decimal.multiply(BigDecimal.valueOf(targetBase));
                    if (!decimal.toString().contains(".")) {
                        result.append(decimal);
                        break;
                    }
                    String[] temp = decimal.toString().split("\\.");
                    if (!temp[0].equals("0")) {
                        decimal = decimal.subtract(new BigDecimal(temp[0]));
                    }
                    int a = Integer.parseInt(temp[0]);
                    if (a < 10) result.append(a);
                    else {
                        result.append((char) (a + 87));
                    }
                }
                if (result.length() > 5) result = new StringBuilder(result.charAt(0) + result.substring(result.length() - 4));
                result.insert(0, new BigInteger(number[0], sourceBase).toString(targetBase) + ".");
                System.out.println("Conversion result:" + result.toString().toUpperCase());
                System.out.println("Enter number in base " + sourceBase + " to convert to base " + targetBase + " (To go back type 'back') ");
                number = scanner.next().toUpperCase().split("\\.");
            }
        }
    }
}
