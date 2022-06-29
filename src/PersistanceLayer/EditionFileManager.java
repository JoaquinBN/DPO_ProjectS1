package PersistanceLayer;

import BusinessLayer.Edition.Edition;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class EditionFileManager {
    public EditionFileManager() {
    }

    private void writeEditions(ArrayList<Edition> Editions) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter("csv/Editions.csv", true),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, "\n");
        for (Edition edition : Editions) {
            String[] data = {String.valueOf(edition.getYear()), String.valueOf(edition.getNumberOfPlayers()), Arrays.toString(edition.getTrials())};
            writer.writeNext(data);
        }
        writer.close();
    }

    private void readEditions(){
        
    }


}
