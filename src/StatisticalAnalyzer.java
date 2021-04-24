import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * A small class that represents a pretty simple  statistical analyzer
 * in our system. The class contains a main method and can be executed
 * independently from the rest system. Before execution, a data file
 * must have been created and data must be stored in it, in a very
 * specific format. For more info on the format of the data file,
 * please have a look at the way FileManager class writes data reports
 * to data files.
 * <p>
 * The class only serves for simple statistic measurements on the CRC
 * algorithm execution and has nothing to do with the actual CRC
 * algorithm. The main reason behind its development is the fact that
 * the originally gathered data was pretty big in size (20 Million
 * executions), so there were no easy way to calculate simple measurement
 * in Microsoft Excel or similar software.
 *
 * @author Vasileios Papastergios
 */
public class StatisticalAnalyzer {
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
