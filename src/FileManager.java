import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    public static void appendToDataFile(Message message, boolean errorDetected) {
        File file = new File("Data.csv");
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write("\"" + "Transmitted message: " + message.getSequenceString() + "\",");
            fileWriter.write("\"" + "Error occurred: "      + message.hasError()          + "\",");
            fileWriter.write("\"" + "Error detected: "      + errorDetected               + "\"\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
