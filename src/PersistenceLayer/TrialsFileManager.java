package PersistenceLayer;

import BusinessLayer.Trials.Trials;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrialsFileManager {


    public TrialsFileManager() {
    }

    /**
     * Writes the trials to the csv file.
     *
     * @param trials the trials to write.
     * @throws IOException if the file could not be written.
     */
    public void writeTrials(ArrayList<Trials> trials) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter("files/Trials.csv", false),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, "\n");

        for (Trials trial : trials){
            writer.writeNext(trial.getDataToWrite());
        }
        writer.close();
    }

    /**
     * Reads the trials from the csv file.
     *
     * @return the trials read.
     * @throws IOException if the file could not be read.
     * @throws CsvException if the file is not in the correct format.
     */
    public List<String[]> readTrials() throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader("files/Trials.csv"));
        List<String[]> trials = reader.readAll();
        reader.close();
        return trials;
    }
}
