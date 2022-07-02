package PersistenceLayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * FileManager is a class that check if the needed directory and file exist and create if it is needed.
 */
public class FileManager {
    public FileManager() {
    }

    /**
     * Check if the directory exists and creates if it doesn't.
     */
    public boolean checkIfDirectoryExists() {
        File file = new File("files");
        return file.isDirectory();
    }

    /**
     * Check if the execution file exists and creates it it doesn't.
     * @throws IOException
     */

    public void createExecutionFileIfNecessary() throws IOException {
        File file = new File("files/Execution.csv");
        if(!file.exists())
            Files.createFile(file.toPath());
    }

    /**
     * Check if the trial file exists and creates it it doesn't.
     * @throws IOException
     */
    public void createTrialsFileIfNecessary() throws IOException {
        File file = new File("files/Trials.csv");
        if(!file.exists())
            Files.createFile(file.toPath());
    }

    /**
     * Check if the edition file exists and creates it it doesn't.
     * @throws IOException
     */
    public void createEditionsFileIfNecessary() throws IOException {
        File file = new File("files/Editions.csv");
        if(!file.exists())
            Files.createFile(file.toPath());
    }
}
