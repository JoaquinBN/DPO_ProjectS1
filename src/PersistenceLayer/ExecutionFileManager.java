package PersistenceLayer;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExecutionFileManager {
    
    public ExecutionFileManager(){ }
    
    public static void writePlayersData(List<String[]> playersData) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter("files/Execution.csv", false),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, "\n");
        for(String[] playerData : playersData){
            writer.writeNext(playerData);
        }
        writer.close();
    }

    public void writeTrials(String[] allTrials) throws IOException, CsvException {
        CSVWriter writer = new CSVWriter(new FileWriter("files/Execution.csv", false),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, "\n");
        writer.writeNext(allTrials);
        writer.close();
    }

    public static List<String[]> readPlayersData() throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader("files/Execution.csv"));
        List<String[]> playersData = reader.readAll();
        reader.close();
        return playersData;
    }

    public String[] readTrials() throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader("files/Execution.csv"));
        String[] allTrials = reader.readNext();
        reader.close();
        return allTrials;
    }

    public boolean fileIsEmpty() throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader("files/Execution.csv"));
        String[] allTrials = reader.readNext();
        return allTrials == null;
    }
}
