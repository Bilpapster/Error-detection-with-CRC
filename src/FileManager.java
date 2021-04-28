import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * A simple class that handles the file-writing process of the raw data.
 * Nothing special, just a neat and organized way of hosting the file
 * handling procedure.
 *
 * @author Vasileios Papastergios
 */
public class FileManager {
    public static final String dataFileName = "Data.csv";   // change the string literal for different data file

    /**
     * Executes the file-writing procedure. The information written for a single transmission
     * is the following:
     * - the transmitted bit series
     * - error occurrence during transmission (boolean - at least one error)
     * - error detection at the receiver end (boolean)
     * <p>
     * All data are formatted under a csv format: all data for a single transmission is written
     * in a single line of the data file and the values are separated from one another by comma.
     *
     * @param message       the message to write information from.
     * @param errorDetected a flag that informs whether have errors been detected at the receiver
     *                      end, during the transmission of the specific message.
     */
    public static void appendToDataFile(Message message, boolean errorDetected) {
        File dataFile = new File(dataFileName);
        try {
            FileWriter fileWriter = new FileWriter(dataFile, true);
            fileWriter.write("\"" + "Transmitted message: " + message.getSequenceString() + "\",");
            fileWriter.write("\"" + "Error occurred: " + message.hasError() + "\",");
            fileWriter.write("\"" + "Error detected: " + errorDetected + "\"\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
