package BusinessLayer.Trials;

import PersistenceLayer.TrialsFileManager;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrialManager {
    private ArrayList<Trials> trials;
    private TrialsFileManager trialsFileManager;

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
        switch(trialInfo[1]) {
            case "1" -> trials.add(new PaperSubmission(trialInfo[0], trialInfo[2], trialInfo[3], Integer.parseInt(trialInfo[4]), Integer.parseInt(trialInfo[5]), Integer.parseInt(trialInfo[6])));
        }
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
        return !trialType.equals("");
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
     * Chrck if the probabilites is over 100.
     * @param limitProbabilities probabilty inputed by user
     * @return true if the probabilites is over 100, false otherwise
     */
    public boolean checkLimitProbabilities(int limitProbabilities) {
        return limitProbabilities > 100;
    }

    /**
     * Write the trials to the file
     * @throws IOException if the file cannot be written
     */
    public void writeTrials() throws IOException {
        trialsFileManager.writeTrials(trials);
    }

    /**
     * Read the trials from the file
     * @throws IOException if the file cannot be read
     * @throws CsvException if the file is not in the correct format
     */
    public void readTrials() throws IOException, CsvException {
        List<String[]> allTrials = trialsFileManager. readTrials();
        for(String[] trial : allTrials){
            switch(trial[1]){
                case "Paper publication" -> trial[1] = "1";
            }
            addTrial(trial);
        }
    }
}
