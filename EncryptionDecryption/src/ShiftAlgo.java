public class ShiftAlgo extends EncryptAlgorithm{

    @Override
    public String encrypt(String action, String a, int key) {
        StringBuilder sb = new StringBuilder();
        if (action.equals("dec")) key = -key;
        for (char b : a.toCharArray()) {
            if (b > 96 && b < 123) {
                int number = b + key - 97;
                if (number < 0) number = 26 + number;
                sb.append((char) (number % 26 + 97));
            } else if (b > 64 && b < 91) {
                int number = b + key - 65;
                if (number < 0) number = 26 + number;
                sb.append((char) (number % 26 + 65));
            } else {
                sb.append(b);
            }
        }
        return sb.toString();
    }
}