abstract class EncryptAlgorithm {

    public static EncryptAlgorithm chooseAlgo(String alg) {
        if (alg.equals("shift")) {
            return new ShiftAlgo();
        } else if (alg.equals("unicode")) {
            return new UnicodeAlgo();
        }
        return null;
    }
    abstract String encrypt(String action, String input, int key);
}