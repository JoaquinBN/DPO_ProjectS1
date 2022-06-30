package PersistenceLayer;

import BusinessLayer.Edition.Edition;
import BusinessLayer.Trials.Trials;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class EditionFileManager {
    public EditionFileManager() {
    }

    /**
     * Writes the editions to the csv file.
     *
     * @param Editions the editions to write.
     * @throws IOException if the file could not be written.
     */
    public void writeEditions(ArrayList<Edition> Editions) throws IOException {

        CSVWriter writer = new CSVWriter(new FileWriter("files/Editions.csv", false),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, "\n");

        for (Edition edition : Editions) {
            String[] line = new String[edition.getNumberOfTrials()];
            int i = 0;
            for(String trialName : edition.getTrials()) {
                line[i] = trialName;
                i++;
            }
            String[] data = {String.valueOf(edition.getYear()), String.valueOf(edition.getNumberOfPlayers())};
            String[] all = new String[data.length + line.length];
            System.arraycopy(data, 0, all, 0, data.length);
            System.arraycopy(line, 0, all, data.length, line.length);
            writer.writeNext(all);
        }
        writer.close();
    }

    /**
     * Reads the editions from the csv file.
     *
     * @return the editions read.
     * @throws IOException if the file could not be read.
     * @throws CsvException if the file is not in the correct format.
     */
    public List<String[]> readEditions() throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader("files/Editions.csv"));
        List<String[]> editions = reader.readAll();
        reader.close();
        return editions;
    }
}
