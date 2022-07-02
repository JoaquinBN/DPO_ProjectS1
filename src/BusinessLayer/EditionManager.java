package BusinessLayer;

import BusinessLayer.Entities.Edition;
import PersistenceLayer.EditionFileManager;
import PersistenceLayer.ExecutionFileManager;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * EditionManager is a class in charge of the logic behind the editions information.
 */
public class EditionManager {
    private final ArrayList<Edition> editions;
    private final EditionFileManager editionFileManager;
    private final ExecutionFileManager executionFileManager;
    private String errorMessage;

    /**
     * Constructor for EditionManager
     * @param editionFileManager the edition file manager
     * @param executionFileManager the execution file manager
     */
    public EditionManager(EditionFileManager editionFileManager, ExecutionFileManager executionFileManager) {
        editions = new ArrayList<>();
        this.editionFileManager = editionFileManager;
        this.executionFileManager = executionFileManager;
    }

    /**
     * Add a new edition to the list of editions
     * @param year the year of the edition
     * @param numberOfPlayers the number of players in the edition
     * @param numberOfTrials the number of trials in the edition
     */
    public void addEdition(int year, int numberOfPlayers, int numberOfTrials) {
        editions.add(new Edition(year, numberOfPlayers, numberOfTrials));
    }

    /**
     * Remove edition from the list of editions
     * @param indexEdition the index of the edition
     */
    public void removeEdition(int indexEdition) {
        editions.remove(indexEdition);
    }

    /**
     * Get the number of editions in the system
     * @return the amount of editions in the system
     */
    public int getNumberOfEditions() {
        return editions.size();
    }

    /**
     * Get the edition at the specified index
     * @param index the index of the edition
     * @return the year of the edition
     */
    public int getEditionYear(int index){
        return editions.get(index).getYear();
    }

    /**
     * Get the number of players in the edition at the specified index
     * @param index the index of the edition
     * @return the number of players in the edition
     */
    public int getEditionNumberOfPlayers(int index){
        return editions.get(index).getNumberOfPlayers();
    }

    /**
     * Get the trials in the edition at the specified index
     * @param index the index of the edition
     * @return the trials in the edition
     */
    public String[] getEditionTrials(int index){
        return editions.get(index).getTrials();
    }
    /**
     * Duplicate an edition by copying its trials
     * @param year the year of the edition to duplicate
     * @param numberOfPlayers the number of players in the edition to duplicate
     * @param indexEdition the index of the edition to duplicate
     */
    public void duplicateEditions(int year, int numberOfPlayers, int indexEdition) {
        int i = 0;
        editions.add(new Edition(year, numberOfPlayers, editions.get(indexEdition).getNumberOfTrials()));
        for(String trialName: editions.get(indexEdition).getTrials()) {
            editions.get(editions.size() - 1).addTrial(trialName, i);
            i++;
        }
    }

    /**
     * Add a trial to an edition
     * @param trialName the trial name
     * @param indexTrial the index of the trial in the system
     */
    public void addTrialToEdition(String trialName, int indexTrial) {
        editions.get(editions.size() - 1).addTrial(trialName, indexTrial);
    }

    /**
     * Check if year is already in the system
     * @param year the year to check
     * @return true if year is already in the system, false otherwise
     */
    public boolean checkUniqueYear(int year) {
        for (Edition edition : editions) {
            if (edition.getYear() == year) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if trial has already been done
     * @param trialName the trial name to check
     * @return true if trial has already been done, false otherwise
     */
    public boolean trialIsUsed(String trialName){
        for(Edition edition : editions){
            if(Arrays.asList(edition.getTrials()).contains(trialName)){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if year is valid
     * @param year the year to check
     * @return true if year is valid, false otherwise
     */
    public boolean checkValidYear(int year) {
        return year < 2022;
    }

    /**
     * Check if number of players is valid
     * @param numberOfPlayers the number of players to check
     * @return true if number of players is valid, false otherwise
     */
    public boolean checkPlayersRange(int numberOfPlayers) {
        return numberOfPlayers < 1 || numberOfPlayers > 5;
    }

    /**
     * Check if number of trials is valid
     * @param numberOfTrials the number of trials to check
     * @return true if number of trials is valid, false otherwise
     */
    public boolean checkTrialsRange(int numberOfTrials) {
        return numberOfTrials >= 3 && numberOfTrials <= 12;
    }

    /**
     * Write new editions to the file
     * @return true if the editions were successfully written to the file, false otherwise
     */
    public boolean writeEditions(){
        try {
            editionFileManager.writeEditions(editions);
            return true;
        } catch (IOException e) {
            errorMessage = "Error writing editions to file";
            return false;
        }
    }

    /**
     * Read editions from the file
     * @return true if editions were read, false otherwise
     */
    public boolean readEditions(){
        List<String[]> editionsString;
        try {
            editionsString = editionFileManager.readEditions();
            for (String[] edition : editionsString) {
                editions.add(new Edition(Integer.parseInt(edition[0]), Integer.parseInt(edition[1]), edition.length-2));
                for(int i = 2; i < edition.length; i++) {
                    editions.get(editions.size() - 1).addTrial(edition[i], i-2);
                }
            }
            return true;
        } catch (IOException | CsvException e) {
            errorMessage = "Error reading editions' file";
            return false;
        }
    }

    /**
     * Delete already in progress executions
     * @param isCurrentYear true if the current year is being deleted, false otherwise
     * @return true if the execution file's information was deleted, false otherwise
     */
    public boolean deleteStoredState(boolean isCurrentYear){
        if(isCurrentYear) {
            try {
                executionFileManager.eraseFile();
                return true;
            } catch (IOException e) {
                errorMessage = "Error erasing execution file's data for the current edition";
                return false;
            }
        }
        return true;
    }

    /**
     * Get the error message
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

}
