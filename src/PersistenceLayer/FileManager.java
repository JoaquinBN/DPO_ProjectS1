package PersistenceLayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileManager {
    public FileManager() {
    }

    public boolean checkIfDirectoryExists() {
        File file = new File("files");
        return file.isDirectory();
    }

    public void createExecutionFileIfNecessary() throws IOException {
        File file = new File("files/Execution.csv");
        if(!file.exists())
            Files.createFile(file.toPath());
    }

    public void createTrialsFileIfNecessary() throws IOException {
        File file = new File("files/Trials.csv");
        if(!file.exists())
            Files.createFile(file.toPath());
    }

    public void createEditionsFileIfNecessary() throws IOException {
        File file = new File("files/Editions.csv");
        if(!file.exists())
            Files.createFile(file.toPath());
    }
}
