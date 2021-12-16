import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        String mode = "enc";
        String data = "";
        int key = 0;
        String fileIn = "";
        String fileOut = "";
        String alg = "";
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("-mode")) {
                mode = args[i + 1];
            }
            if (args[i].equals("-data")) {
                data = args[i + 1];
            }
            if (args[i].equals("-key")) {
                key = Integer.parseInt(args[i + 1]);
            }
            if (args[i].equals("-in")) {
                fileIn = args[i + 1];
            }
            if (args[i].equals("-out")) {
                fileOut = args[i + 1];
            }
            if (args[i].equals("-alg")) {
                alg = args[i + 1];
            }
        }

        if (!fileIn.equals("")) {
            try {
                data = readFileAsString(fileIn);
            } catch (IOException e) {
                System.out.println("Cannot read file: " + e.getMessage());
            }
        }
        if (fileOut.equals("")) {
            System.out.println(EncryptAlgorithm.chooseAlgo(alg));
        } else {
            writeToFile(fileOut, Objects.requireNonNull(EncryptAlgorithm.chooseAlgo(alg)).encrypt(mode, data, key));
        }
    }
    public static void writeToFile(String path, String data) {
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(data);
        } catch (IOException e){
            System.out.printf("An exception occurs %s", e.getMessage());
        }
    }
    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}