import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class StatisticsManager {
    private static int numberOfDistorted = 0;
    private static int numberOfDetected = 0;
    private static int numberOfNotDetected = 0;
    private static int numberOfBath = 0;

    public static void main(String[] args) {
        try {
            File file = new File("Data.csv");
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                ArrayList<StringBuilder> tokens = new ArrayList<>();
                String line = fileReader.nextLine();
                StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
                while (stringTokenizer.hasMoreTokens()) {
                    tokens.add(new StringBuilder(stringTokenizer.nextToken()));
                }
                for (StringBuilder token : tokens) {
                    token.delete(0, token.indexOf(": ") + 2);
                    token.deleteCharAt(token.length() - 1);
                }
                if (tokens.get(1).charAt(0) == 't') numberOfDistorted++;
                if (tokens.get(2).charAt(0) == 't') numberOfDetected++;
                if (tokens.get(1).charAt(0) == 't' && tokens.get(2).charAt(0) == 'f') numberOfNotDetected++;
                if (tokens.get(1).charAt(0) == 'f' && tokens.get(2).charAt(0) == 't') numberOfBath++;
            }
            fileReader.close();
            System.out.println("Number of distorted:    " + numberOfDistorted);
            System.out.println("Number of detected:     " + numberOfDetected);
            System.out.println("Number of not detected: " + numberOfNotDetected);
            System.out.println("Number of bath:         " + numberOfBath);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
