package BusinessLayer;

import BusinessLayer.Entities.PaperSubmission;
import BusinessLayer.Entities.Trials;
import PersistenceLayer.TrialsFileManager;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TrialManager is a class in charge of the logic behind the trials information.
 */
public class TrialManager {
    private final ArrayList<Trials> trials;
    private final TrialsFileManager trialsFileManager;
    private String errorMessage;

    /**
     * Constructor for TrialManager
     * @param trialsFileManager the trials file manager
     */
    public TrialManager(TrialsFileManager trialsFileManager) {
        this.trials = new ArrayList<>();
        this.trialsFileManager = trialsFileManager;
    }

    /**
     * Add a new trial to the list of trials
     * @param trialInfo the trial info
     */
    public void addTrial(String[] trialInfo){
        trials.add(new PaperSubmission(trialInfo[0], trialInfo[2], trialInfo[3], Integer.parseInt(trialInfo[4]), Integer.parseInt(trialInfo[5]), Integer.parseInt(trialInfo[6])));
    }

    /**
     * Remove a trial from the list of trials
     * @param index the index of the trial
     */
    public void removeTrial(int index) {
        trials.remove(index);
    }

    /**
     * Get the numbers of trials in the system
     * @return the amount of trials in the system
     */
    public int getNumberOfTrials() {
        return trials.size();
    }

    /**
     * Get the trial at the given index
     * @param index the index of the trial
     * @return the trial at the given index
     */
    public Trials getTrial(int index) {
        return trials.get(index);
    }

    /**
     * Get the trial by the name of the trial
     * @param name the name of the trial
     * @return the trial with the given name
     */
    public Trials getTrialByName(String name){
        for(Trials trial: trials){
            if(trial.getTrialName().equals(name)){
                return trial;
            }
        }
        return null;
    }
    public String getTrialNameByIndex(int index){
        return trials.get(index).getTrialName();
    }

    /**
     * Get the trial type by the name of the trial
     * @param name the name of the trial
     * @return the trial type with the given name
     */
    public String getTrialTypeByName(String name){
        for(Trials trial: trials){
            if(trial.getTrialName().equals(name)){
                return trial.getTypeOfTrial();
            }
        }
        return null;
    }

    /**
     * Check if the name is unique in the system
     * @param trialName the name of the trial
     * @return true if the name is unique, false otherwise
     */
    public boolean checkUniqueName(String trialName) {
        for (Trials trial : trials) {
            if (trial.getTrialName().equals(trialName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if string is empty
     * @param trialType the type of the trial
     * @return true if the string is empty, false otherwise
     */
    public boolean checkEmptyString(String trialType) {
        return trialType.equals("");
    }

    /**
     * Check if quartile is valid
     * @param quartile the quartile of the trial
     * @return true if the quartile is valid, false otherwise
     */
    public boolean checkQuartile(String quartile) {
        return quartile.equals("Q1") || quartile.equals("Q2") || quartile.equals("Q3") || quartile.equals("Q4");
    }

    /**
     * Check if the probability is valid
     * @param probability the probability of the trial
     * @return true if the probability is valid, false otherwise
     */
    public boolean checkProbability(int probability) {
        return probability >= 0 && probability <= 100;
    }

    /**
     * Check if the sum of the probabilities is 100
     * @param sumProbabilities the sum of the probabilities
     * @return true if the sum of the probabilities is 100, false otherwise
     */
    public boolean checkSumProbabilities(int sumProbabilities) {
        return sumProbabilities == 100;
    }

    /**
     * Check if the probabilities is over 100.
     * @param limitProbabilities probability inputted by user
     * @return true if the sum of probabilities is over 100, false otherwise
     */
    public boolean checkLimitProbabilities(int limitProbabilities) {
        return limitProbabilities > 100;
    }

    /**
     * Write the trials to the file
     */
    public boolean writeTrials(){
        try {
            trialsFileManager.writeTrials(trials);
            return true;
        } catch (IOException e) {
            errorMessage = "Error writing trials to file";
            return false;
        }
    }

    /**
     * Read the trials from the file
     */
    public boolean readTrials(){
        List<String[]> allTrials;
        try {
            allTrials = trialsFileManager. readTrials();
            for(String[] trial : allTrials){
                addTrial(trial);
            }
            return true;
        } catch (IOException | CsvException e) {
            errorMessage = "Error reading trials from file";
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
}
