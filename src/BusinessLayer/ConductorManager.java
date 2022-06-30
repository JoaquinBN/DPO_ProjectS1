package BusinessLayer;

import BusinessLayer.Edition.Edition;
import BusinessLayer.Trials.TrialManager;
import BusinessLayer.Trials.Trials;
import PersistenceLayer.EditionFileManager;
import PersistenceLayer.ExecutionFileManager;
import PersistenceLayer.TrialsFileManager;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.List;

public class ConductorManager {
    private Edition currentEdition;
    private Trials[] trials;
    private final TrialManager trialManager;
    private final EditionFileManager editionFileManager;
    private final TrialsFileManager trialsFileManager;
    private final ExecutionFileManager executionFileManager;

    public ConductorManager(TrialManager trialManager, EditionFileManager editionFileManager, TrialsFileManager trialsFileManager, ExecutionFileManager executionFileManager) {
        this.trialManager = trialManager;
        this.editionFileManager = editionFileManager;
        this.trialsFileManager = trialsFileManager;
        this.executionFileManager = executionFileManager;
    }

    public int incrementInvestigationPoints(int indexTrial){
        int investigationPoints = 0;
        switch(trials[indexTrial].hasWonTrial()) {
            case 0 -> investigationPoints = trials[indexTrial].getPenalizationIP();
            case 1 -> investigationPoints =  trials[indexTrial].getRewardIP();
            case 2 -> investigationPoints = -1;
        }
        return investigationPoints;
    }

    public Edition getCurrentEdition(){
        return currentEdition;
    }

    public int getNumTrials(){
        return currentEdition.getNumberOfTrials();
    }

    public int getTotalPlayer(){
        return currentEdition.getNumberOfPlayers();
    }

    public void loadDataForTrials() throws IOException, CsvException {
        List<String[]> allTrials = trialsFileManager. readTrials();
        for(String[] trial : allTrials){
            switch(trial[1]){
                case "Paper publication" -> trial[1] = "1";
            }
            trialManager.addTrial(trial);
        }
    }

    public boolean loadDataForCurrentEdition() throws IOException, CsvException{
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

    public int loadDataForExecution() throws IOException, CsvException{
        String[] allTrials = executionFileManager.readTrials();
        trials = new Trials[allTrials.length - 1];
        for(int i = 0; i < allTrials.length - 1; i++) {
            trials[i] = trialManager.getTrialByName(allTrials[i]);
        }
        return Integer.parseInt(allTrials[allTrials.length - 1]);
    }

    public void initializeEditionData(int numberOfPlayers) {
        currentEdition = new Edition(2022, numberOfPlayers, trials.length);
        for(int i = 0; i < trials.length; i++) {
            currentEdition.addTrial(trials[i].getTrialName(), i);
        }
    }

    public boolean fileIsEmpty() throws IOException, CsvValidationException {
        return executionFileManager.fileIsEmpty();
    }

    public void saveData(int trialIndex, int startIndex) throws IOException, CsvException {
        String[] allTrialNames = new String[trials.length - trialIndex + 1];
        for(int i = trialIndex; i < trials.length; i++) {
            allTrialNames[i-trialIndex] = trials[i].getTrialName();
        }
        allTrialNames[allTrialNames.length-1] = String.valueOf(startIndex);
        executionFileManager.writeTrials(allTrialNames);
    }

    public void eraseInformationExecutionFile() throws IOException {
        executionFileManager.deleteFile();
    }
}
