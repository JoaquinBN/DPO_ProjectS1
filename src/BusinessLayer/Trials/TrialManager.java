package BusinessLayer.Trials;

import java.util.ArrayList;

public class TrialManager {
    private ArrayList<Trials> trials;

    public TrialManager() {
        trials = new ArrayList<Trials>();
    }

    public void addTrial(String trialName, String paperName, String quartile, int acceptProbability, int revisionProbability, int rejectProbability) {
        trials.add(new PaperSubmission(trialName, paperName, quartile, acceptProbability, revisionProbability, rejectProbability));
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

    public boolean checkTrialName(String trialName) {
        for (Trials trial : trials) {
            if (trial.getTrialName().equals(trialName)) {
                return false;
            }
        }
        return !trialName.equals("");
    }

    public boolean checkTrialType(String trialType) {
        return !trialType.equals("");
    }

    public boolean checkQuartile(String quartile) {
        return quartile.equals("Q1") || quartile.equals("Q2") || quartile.equals("Q3") || quartile.equals("Q4");
    }

    public boolean checkProbability(int probability) {
        return probability >= 0 && probability <= 100;
    }

    public boolean checkSumProbabilities(int sumProbabilities) {
        return sumProbabilities == 100;
    }

    public boolean checkLimitProbabilities(int limitProbabilities) {
        return limitProbabilities > 100;
    }
}
