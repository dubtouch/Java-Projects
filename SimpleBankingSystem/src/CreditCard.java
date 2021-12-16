import java.util.Random;

public class CreditCard {
    private final String creditCardNumber;
    private final String pin;

    public CreditCard() {
        Random rand = new Random();
        StringBuilder cardNumber = new StringBuilder("400000");
        while (cardNumber.length() < 15) {
            cardNumber.append(rand.nextInt(9));
        }
        int sum = 0;
        for (int j = 0; j < cardNumber.length(); j++) {
            int digit = cardNumber.charAt(j) - '0';
            if (j % 2 == 0) digit *= 2;
            if (digit > 9) digit -= 9;
            sum += digit;
        }
        sum = sum % 10;
        cardNumber.append(sum == 0 ? 0 : 10 - sum);
        StringBuilder pin = new StringBuilder();
        while (pin.length() < 4) {
            pin.append(rand.nextInt(9));
        }
        this.creditCardNumber = cardNumber.toString();
        this.pin = pin.toString();
        System.out.println("Your card has been created\n" +
                "Your card number:");
        System.out.println(this.creditCardNumber);
        System.out.println("Your card PIN:");
        System.out.println(this.pin);

    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getPin() {
        return pin;
    }

    public boolean verifyNumberAndPin(String number, String pin) {
        return this.creditCardNumber.equals(number) && this.pin.equals(pin);
    }
}
