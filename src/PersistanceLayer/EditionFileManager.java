package PersistanceLayer;

import BusinessLayer.Edition.Edition;
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

    public void writeEditions(ArrayList<Edition> Editions) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter("csv/Editions.csv", true),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, "\n");
        for (Edition edition : Editions) {
            String[] data = {String.valueOf(edition.getYear()), String.valueOf(edition.getNumberOfPlayers()), Arrays.toString(edition.getTrials())};
            writer.writeNext(data);
        }
        writer.close();
    }

    public void readEditions() throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader("csv/Editions.csv"));
        List<String[]> editions = reader.readAll();
        for (String[] edition : editions) {
            Edition e = new Edition(Integer.parseInt(edition[0]), Integer.parseInt(edition[1]), Arrays.asList(edition[2].split(",")));
            Edition.Editions.add(e);
        }
    }


}
