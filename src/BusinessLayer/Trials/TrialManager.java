package BusinessLayer.Trials;

import java.util.ArrayList;

public class TrialManager {
    private ArrayList<Trials> trials;

    public TrialManager() {
        trials = new ArrayList<Trials>();
    }

    public void addTrial(String trialName, String paperName, String quartile, int acceptProbability, int revisionProbability, int rejectProbability) {
        Trials trial = new PaperSubmission(trialName, paperName, quartile, acceptProbability, revisionProbability, rejectProbability);
        trials.add(trial);
    }

    public void removeTrial(int index) {
        trials.remove(index);
    }

    public Trials getTrial(String trialName) {
        for (Trials trial : trials) {
            if (trial.getTrialName().equals(trialName)) {
                return trial;
            }
        }
        return null;
    }

    public int getNumberOfTrials() {
        return trials.size();
    }

    public Trials getTrial(int index) {
        return trials.get(index);
    }

}
