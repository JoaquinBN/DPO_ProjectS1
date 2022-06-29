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

        CSVWriter writer = new CSVWriter(new FileWriter("Files/Editions.csv", true),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, "\n");



        for (Edition edition : Editions) {
            String[] data = {String.valueOf(edition.getYear()), String.valueOf(edition.getNumberOfPlayers()), String.valueOf(edition.getNumberOfTrials())};
            writer.writeNext(data);
        }
        writer.close();
    }




}
