package BusinessLayer.Edition;

import PersistenceLayer.EditionFileManager;
import PersistenceLayer.ExecutionFileManager;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditionManager {
    private final ArrayList<Edition> editions;
    private final EditionFileManager editionFileManager;
    private final ExecutionFileManager executionFileManager;

    public EditionManager(EditionFileManager editionFileManager, ExecutionFileManager executionFileManager) {
        editions = new ArrayList<>();
        this.editionFileManager = editionFileManager;
        this.executionFileManager = executionFileManager;
    }

    public void addEdition(int year, int numberOfPlayers, int numberOfTrials) {
        editions.add(new Edition(year, numberOfPlayers, numberOfTrials));
    }

    public void removeEdition(int indexEdition) {
        editions.remove(indexEdition);
    }

    public Edition getEditionByIndex(int index) {
        return editions.get(index);
    }

    public int getNumberOfEditions() {
        return editions.size();
    }

    public void duplicateEditions(int year, int numberOfPlayers, int indexEdition) {
        int i = 0;
        editions.add(new Edition(year, numberOfPlayers, editions.get(indexEdition).getNumberOfTrials()));
        for(String trialName: editions.get(indexEdition).getTrials()) {
            editions.get(editions.size() - 1).addTrial(trialName, i);
            i++;
        }
    }

    public void addTrialToEdition(String trialName, int indexTrial) {
        editions.get(editions.size() - 1).addTrial(trialName, indexTrial);
    }

    public boolean checkUniqueYear(int year) {
        for (Edition edition : editions) {
            if (edition.getYear() == year) {
                return true;
            }
        }
        return false;
    }

    public boolean trialIsUsed(String trialName){
        for(Edition edition : editions){
            if(Arrays.asList(edition.getTrials()).contains(trialName)){
                return true;
            }
        }
        return false;
    }
    public boolean checkValidYear(int year) {
        return year < 2022;
    }

    public boolean checkPlayersRange(int numberOfPlayers) {
        return numberOfPlayers < 1 || numberOfPlayers > 5;
    }

    public boolean checkTrialsRange(int numberOfTrials) {
        return numberOfTrials >= 3 && numberOfTrials <= 12;
    }

    public void writeEditions() throws IOException {
        editionFileManager.writeEditions(editions);
    }

    public void readEditions() throws IOException, CsvException {
        List<String[]> editionsString = editionFileManager.readEditions();
        for (String[] edition : editionsString) {
            editions.add(new Edition(Integer.parseInt(edition[0]), Integer.parseInt(edition[1]), edition.length-2));
            for(int i = 2; i < edition.length; i++) {
                editions.get(editions.size() - 1).addTrial(edition[i], i-2);
            }
        }
    }

    public void deleteStoredState(boolean isCurrentYear) throws IOException {
        if(isCurrentYear)
            executionFileManager.deleteFile();
    }

}
