package BusinessLayer;

import BusinessLayer.Entities.Edition;
import BusinessLayer.Entities.Trials;
import PersistenceLayer.EditionFileManager;
import PersistenceLayer.ExecutionFileManager;
import PersistenceLayer.TrialsFileManager;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.List;

/**
 * ConductorManager is a class that runs the trials of the current edition.
 */
public class ConductorManager {
    private Edition currentEdition;
    private Trials[] trials;
    private final TrialManager trialManager;
    private final EditionFileManager editionFileManager;
    private final TrialsFileManager trialsFileManager;
    private final ExecutionFileManager executionFileManager;
    private String errorMessage;
    private int startIndex; //the index of the first trial in the current edition

    /**
     * Constructor for ConductorManager
     * @param trialManager the trial manager
     * @param editionFileManager the edition file manager
     * @param trialsFileManager the trials file manager
     * @param executionFileManager the execution file manager
     */
    public ConductorManager(TrialManager trialManager, EditionFileManager editionFileManager, TrialsFileManager trialsFileManager, ExecutionFileManager executionFileManager) {
        this.trialManager = trialManager;
        this.editionFileManager = editionFileManager;
        this.trialsFileManager = trialsFileManager;
        this.executionFileManager = executionFileManager;
    }

    /**
     * Increment the investigation points of a player
     * @param indexTrial the index of the trial
     * @return the amount of investigation points to add to the player
     */
    public int incrementInvestigationPoints(int indexTrial){
        if(trials[indexTrial].getPassed())
            return trials[indexTrial].getRewardIP();
        else
            return trials[indexTrial].getPenalizationIP();
    }

    /**
     * Get the index's corresponding trial type
     * @param index the index of the trial
     * @return the specified trial type
     */
    public String getTrialName(int index){
        return trials[index].getTrialName();
    }

    /**
     * Get the number of trials in the current edition
     * @return the number of trials in the current edition
     */
    public int getNumTrials(){
        return currentEdition.getNumberOfTrials();
    }

    /**
     * Get the number of players in the current edition
     * @return the number of players in the current edition
     */
    public int getTotalPlayer(){
        return currentEdition.getNumberOfPlayers();
    }

    /**
     * Get the trial needed to input
     * @return the trial needed to input
     */
    public String getTrialPrintOutput(int index, String playerName){
        return this.trials[index].printTrialOutput(playerName);
    }

    /**
     * Load data for the trials
     */
    public boolean loadDataForTrials(){
        List<String[]> allTrials;
        try {
            allTrials = trialsFileManager.readTrials();
            for(String[] trial : allTrials){
                trialManager.addTrial(trial);
            }
            return true;
        } catch (IOException | CsvException e) {
            errorMessage = "Error loading data trials";
            return false;
        }
    }

    /**
     * Load data for the edition
     * @return true if the edition exists, false otherwise
     */
    public boolean loadDataForCurrentEdition(){
        boolean currentEditionExists = false;
        List<String[]> editionsString;
        try {
            editionsString = editionFileManager.readEditions();
            for(String[] executionInfo: editionsString) {
                if(executionInfo[0].equals("2022")) {
                    currentEdition = new Edition(Integer.parseInt(executionInfo[0]), Integer.parseInt(executionInfo[1]), executionInfo.length-2);
                    trials = new Trials[currentEdition.getNumberOfTrials()];
                    for(int i = 2; i < executionInfo.length; i++) {
                        currentEdition.addTrial(executionInfo[i], i-2);
                        trials[i-2] = trialManager.getTrialByName(executionInfo[i]);
                    }
                    currentEditionExists = true;
                }
            }
        } catch (IOException | CsvException e) {
            errorMessage = "Error loading data for current edition";
        }
        return currentEditionExists;
    }



    /**
     * Initialize the current edition data
     * @param numberOfPlayers the number of players
     */
    public void initializeEditionData(int numberOfPlayers) {
        currentEdition = new Edition(2022, numberOfPlayers, trials.length);
        for(int i = 0; i < trials.length; i++) {
            currentEdition.addTrial(trials[i].getTrialName(), i);
        }
    }

    /**
     * Load data for the execution
     * @return Trials length
     */
    public boolean loadDataForExecution(){
        String[] allTrials;
        try {
            allTrials = executionFileManager.readTrials();
            trials = new Trials[allTrials.length - 1];
            for(int i = 0; i < allTrials.length - 1; i++) {
                trials[i] = trialManager.getTrialByName(allTrials[i]);
            }
            startIndex = Integer.parseInt(allTrials[allTrials.length - 1]);
            return true;
        } catch (IOException | CsvException e) {
            errorMessage = "Error loading data from the execution file";
            return false;
        }
    }

    /**
     * Check if the file is empty
     * @return true if the file is empty, false otherwise
     */
    public int fileIsEmpty(){
        try {
            return executionFileManager.fileIsEmpty()? 1: 0;
        } catch (IOException | CsvValidationException e) {
            errorMessage = "Error checking if the file is empty";
            return 2;
        }
    }

    /**
     * Save the current edition data
     * @param trialIndex the index of the trial
     * @param startIndex the index of the start player
     */
    public boolean saveData(int trialIndex, int startIndex){
        String[] allTrialNames = new String[trials.length - trialIndex + 1];
        for(int i = trialIndex; i < trials.length; i++) {
            allTrialNames[i-trialIndex] = trials[i].getTrialName();
        }
        allTrialNames[allTrialNames.length-1] = String.valueOf(startIndex);
        try {
            executionFileManager.writeTrials(allTrialNames);
            return true;
        } catch (IOException e) {
            errorMessage = "Error saving data for trials";
            return false;
        }
    }

    /**
     * Delete information for the execution files
     */
    public boolean eraseInformationExecutionFile() {
        try {
            executionFileManager.eraseFile();
            return true;
        } catch (IOException e) {
            errorMessage = "Error erasing information from the execution file";
            return false;
        }
    }

    /**
     * Get the error message
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Get the start index
     * @return the start index
     */
    public int getStartIndex() {
        return startIndex;
    }
}
