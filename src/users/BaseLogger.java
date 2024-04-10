package users;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseLogger {
    private static final String INFO_LOG_FILE = "application_info.txt";
    private static final String ERROR_LOG_FILE = "application_error.txt";

    private String logLevel;
    private String logFile;

    private BaseLogger(String logLevel, String logFile) {
        this.logLevel = logLevel;
        this.logFile = logFile;
    }

    public static BaseLogger info() {
        return new BaseLogger("[INFO]", INFO_LOG_FILE);
    }

    public static BaseLogger error() {
        return new BaseLogger("[ERROR]", ERROR_LOG_FILE);
    }
    
    public void log(String message) {
        try {
            String formattedLog = generateLogEntry(message);
            FileWriter fileWriter = new FileWriter(logFile, true);
            fileWriter.write(formattedLog);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing log message: " + e.getMessage());
        }
    }

    private String generateLogEntry(String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        String timestamp = dateFormat.format(new Date());
        return "[" + timestamp + "]" + logLevel + " " + message;
    }

}
