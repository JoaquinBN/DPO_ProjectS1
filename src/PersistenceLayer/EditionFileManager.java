package PersistenceLayer;

import BusinessLayer.Edition.Edition;
import BusinessLayer.Trials.Trials;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class EditionFileManager {
    public EditionFileManager() {
    }

    public void writeEditions(ArrayList<Edition> Editions) throws IOException {

        CSVWriter writer = new CSVWriter(new FileWriter("files/Editions.csv", false),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, "\n");

        for (Edition edition : Editions) {
            String[] line = new String[edition.getNumberOfTrials()];
            int i = 0;
            for(Trials trial : edition.getTrials()) {
                line[i] = trial.getTrialName();
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


}
