package BusinessLayer.Trials;

import java.util.Random;

public class PaperSubmission extends Trials {
    private final String publicationName;
    private final String quartile;
    private final int acceptProbability;
    private final int revisionProbability;
    private final int rejectProbability;

    //Name of the trial, which can’t be empty and must be unique across all trials
    public PaperSubmission(String trialName, String paperName, String quartile, int acceptProbability, int revisionProbability, int rejectProbability) {
        super(trialName, "Paper publication");
        this.publicationName = paperName;
        this.quartile = quartile;
        this.setRewardIP(calculateRewardIP());
        this.setPenalizationIP(calculatePenalizationIP());
        this.acceptProbability = acceptProbability;
        this.revisionProbability = revisionProbability;
        this.rejectProbability = rejectProbability;
    }


    //Getters
    public String getPublicationName() {
        return publicationName;
    }

    public String getQuartile() {
        return quartile;
    }

    public int getAcceptProbability() {
        return acceptProbability;
    }

    public int getRejectProbability() {
        return rejectProbability;
    }

    public int getRevisionProbability() {
        return revisionProbability;
    }

    private int calculateRewardIP() {
        return switch (quartile) {
            case "Q1" -> 4;
            case "Q2" -> 3;
            case "Q3" -> 2;
            case "Q4" -> 1;
            default -> 0;
        };

    }

    private int calculatePenalizationIP() {
        return switch (quartile) {
            case "Q1" -> -5;
            case "Q2" -> -4;
            case "Q3" -> -3;
            case "Q4" -> -2;
            default -> 0;
        };
    }

    @Override
    public String getTrialInfo() {
        return  "Journal: " + publicationName + " (" + quartile + ")\n" +
                "Chances: " + acceptProbability + "% acceptance, " + revisionProbability + "% revision, " + rejectProbability + "% rejection\n\n";
    }

    @Override
    public String[] getDataToWrite() {
        String[] dataToWrite = new String[7];
        dataToWrite[0] = getTrialName();
        dataToWrite[1] = getTypeOfTrial();
        dataToWrite[2] = publicationName;
        dataToWrite[3] = quartile;
        dataToWrite[4] = Integer.toString(acceptProbability);
        dataToWrite[5] = Integer.toString(revisionProbability);
        dataToWrite[6] = Integer.toString(rejectProbability);
        return dataToWrite;
    }

    @Override
    public int hasWonTrial() {
        Random random = new Random();
        int randomNumber = random.nextInt(100);
        if (randomNumber <= acceptProbability) {
            return 1;
        } else if (randomNumber <= acceptProbability + revisionProbability) {
            return 2;
        } else {
            return 0;
        }
    }
}
