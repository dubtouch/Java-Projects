public class UnicodeAlgo extends EncryptAlgorithm{


    public String encrypt(String action, String a, int key) {
        StringBuilder sb = new StringBuilder();
        if (action.equals("dec")) key = -key;
        for (char b : a.toCharArray()) {
            sb.append((char) (b + key));
        }
        return sb.toString();
    }
}