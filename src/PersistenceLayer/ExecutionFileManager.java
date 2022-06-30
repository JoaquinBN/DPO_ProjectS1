package PersistenceLayer;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.List;

public class ExecutionFileManager {
    
    public ExecutionFileManager(){
        
    }
    
    public static void writePlayersData(List<String[]> playersData) {
    }

    public void writeTrials(String[] allTrials) {
    }

    public static List<String[]> readPlayersData() {
        return null;
    }

    public String[] readTrials() throws IOException, CsvException {
        return null;
    }


    public boolean fileIsEmpty() {
        return false;
    }
}
