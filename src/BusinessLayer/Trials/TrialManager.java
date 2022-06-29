package BusinessLayer.Trials;

import PersistenceLayer.TrialsFileManager;

import java.io.IOException;
import java.util.ArrayList;

public class TrialManager {
    private ArrayList<Trials> trials;
    private TrialsFileManager trialsFileManager;

    public TrialManager(TrialsFileManager trialsFileManager) {
        this.trials = new ArrayList<>();
        this.trialsFileManager = trialsFileManager;
    }

    public void addTrial(String[] trialInfo){
        switch(trialInfo[1]) {
            case "1" -> trials.add(new PaperSubmission(trialInfo[0], trialInfo[2], trialInfo[3], Integer.parseInt(trialInfo[4]), Integer.parseInt(trialInfo[5]), Integer.parseInt(trialInfo[6])));
        }
    }

    public void removeTrial(int index) {
        trials.remove(index);
    }

    public int getNumberOfTrials() {
        return trials.size();
    }

    public Trials getTrial(int index) {
        return trials.get(index);
    }

    public boolean checkUniqueName(String trialName) {
        for (Trials trial : trials) {
            if (trial.getTrialName().equals(trialName)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkEmptyString(String trialType) {
        return !trialType.equals("");
    }

    public boolean checkQuartile(String quartile) {
        return quartile.equals("Q1") || quartile.equals("Q2") || quartile.equals("Q3") || quartile.equals("Q4");
    }

    public boolean checkProbability(String probability) {
        return Integer.parseInt(probability) >= 0 && Integer.parseInt(probability) <= 100;
    }

    public boolean checkSumProbabilities(int sumProbabilities) {
        return sumProbabilities == 100;
    }

    public boolean checkLimitProbabilities(int limitProbabilities) {
        return limitProbabilities > 100;
    }

    public void writeTrials() throws IOException {
        trialsFileManager.writeTrials(trials);
    }

    public void setTrialsArrayList(ArrayList<Trials> trials){
        this.trials = trials;
    }
}
