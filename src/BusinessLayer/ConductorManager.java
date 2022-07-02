package BusinessLayer;

import BusinessLayer.Entities.Edition;
import BusinessLayer.Entities.Trials;
import PersistenceLayer.EditionFileManager;
import PersistenceLayer.ExecutionFileManager;
import PersistenceLayer.TrialsFileManager;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.List;

public class ConductorManager {
    private Edition currentEdition;
    private Trials[] trials;
    private final TrialManager trialManager;
    private final EditionFileManager editionFileManager;
    private final TrialsFileManager trialsFileManager;
    private final ExecutionFileManager executionFileManager;

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

    public String getTrialPrintOutput(int index, String playerName){
        return this.trials[index].printTrialOutput(playerName);
    }

    /**
     * Load data for the trials
     */
    public void loadDataForTrials(){
        List<String[]> allTrials = trialsFileManager. readTrials();
        for(String[] trial : allTrials){
            trialManager.addTrial(trial);
        }
    }

    /**
     * Load data for the edition
     * @return true if the edition exists, false otherwise
     */
    public boolean loadDataForCurrentEdition(){
        boolean currentEditionExists = false;
        List<String[]> editionsString = editionFileManager.readEditions();
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
        return currentEditionExists;
    }

    /**
     * Load data for the execution
     * @return Trials length
     */
    public int loadDataForExecution(){
        String[] allTrials = executionFileManager.readTrials();
        trials = new Trials[allTrials.length - 1];
        for(int i = 0; i < allTrials.length - 1; i++) {
            trials[i] = trialManager.getTrialByName(allTrials[i]);
        }
        return Integer.parseInt(allTrials[allTrials.length - 1]);
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
     * Check if the file is empty
     * @return true if the file is empty, false otherwise
     */
    public boolean fileIsEmpty(){
        return executionFileManager.fileIsEmpty();
    }

    /**
     * Save the current edition data
     * @param trialIndex the index of the trial
     * @param startIndex the index of the start player
     * @throws IOException if the file is not found
     * @throws CsvException if the file is not valid
     */
    public void saveData(int trialIndex, int startIndex) throws IOException, CsvException {
        String[] allTrialNames = new String[trials.length - trialIndex + 1];
        for(int i = trialIndex; i < trials.length; i++) {
            allTrialNames[i-trialIndex] = trials[i].getTrialName();
        }
        allTrialNames[allTrialNames.length-1] = String.valueOf(startIndex);
        executionFileManager.writeTrials(allTrialNames);
    }

    /**
     * Delete information for the execution files
     * @throws IOException if the file is not found
     */
    public void eraseInformationExecutionFile() throws IOException {
        executionFileManager.deleteFile();
    }
}
