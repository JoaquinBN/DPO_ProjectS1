package PersistenceLayer;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExecutionFileManager {
    
    public ExecutionFileManager(){ }

    /**
     * Write players data to the csv file.
     * @param playersData List of String arrays with players data.
     * @throws IOException if the file could not be written.
     */
    public void writePlayersData(List<String[]> playersData) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter("files/Execution.csv", true),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, "\n");
        for(String[] playerData : playersData){
            writer.writeNext(playerData);
        }
        writer.close();
    }

    /**
     * Reads the players data from the csv file.
     * @param allTrials the trials to write.
     * @throws IOException if the file could not be written.
     * @throws CsvException if the file is not in the correct format.
     */
    public void writeTrials(String[] allTrials) throws IOException, CsvException {
        CSVWriter writer = new CSVWriter(new FileWriter("files/Execution.csv", false),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, "\n");
        writer.writeNext(allTrials);
        writer.close();
    }

    /**
     * Reads the players data from the csv file.
     * @return playersData the data read.
     * @throws IOException if the file could not be read.
     * @throws CsvException if the file is not in the correct format.
     */
    public List<String[]> readPlayersData() throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader("files/Execution.csv"));
        List<String[]> playersData = reader.readAll();
        reader.close();
        return playersData;
    }

    /**
     * Reads the trials from the csv file.
     * @return the trials read.
     * @throws IOException if the file could not be read.
     * @throws CsvException if the file is not in the correct format.
     */
    public String[] readTrials() throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader("files/Execution.csv"));
        String[] allTrials = reader.readNext();
        reader.close();
        return allTrials;
    }

    /**
     * Checks if the file is empty.
     * @return if the file is empty or not.
     * @throws IOException if the file could not be written.
     */
    public boolean fileIsEmpty() throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader("files/Execution.csv"));
        String[] allTrials = reader.readNext();
        return allTrials == null;
    }

    /**
     * Clears the file.
     * @throws IOException if the file could not be cleared.
     */
    public void deleteFile() throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter("files/Execution.csv", false),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, "\n");
        writer.close();
    }
}
