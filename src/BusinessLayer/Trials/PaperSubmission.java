package BusinessLayer.Trials;

import java.util.Random;

public class PaperSubmission extends Trials {
    private final String publicationName;
    private final String quartile;
    private final int acceptProbability;
    private final int revisionProbability;
    private final int rejectProbability;

    /**
     * Constructor for PaperSubmission
     * @param trialName Name of the trial, which can’t be empty and must be unique across all trials
     * @param paperName the name of the paper
     * @param quartile the quartile of the paper
     * @param acceptProbability the probability of acceptance
     * @param revisionProbability the probability of revision
     * @param rejectProbability the probability of rejection
     */
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

    /**
     * Get the name of the publication
     * @return the name of the publication
     */
    public String getPublicationName() {
        return publicationName;
    }

    /**
     * Get the quartile of the publication
     * @return the quartile of the publication
     */
    public String getQuartile() {
        return quartile;
    }

    /**
     * Get the probability of acceptance
     * @return the probability of acceptance
     */
    public int getAcceptProbability() {
        return acceptProbability;
    }

    /**
     * Get the probability of revision
     * @return the probability of revision
     */
    public int getRejectProbability() {
        return rejectProbability;
    }

    /**
     * Get the probability of rejection
     * @return the probability of rejection
     */
    public int getRevisionProbability() {
        return revisionProbability;
    }

    /**
     * Calculate the reward IP
     * @return the reward IP
     */
    private int calculateRewardIP() {
        return switch (quartile) {
            case "Q1" -> 4;
            case "Q2" -> 3;
            case "Q3" -> 2;
            case "Q4" -> 1;
            default -> 0;
        };

    }

    /**
     * Calculate the penalization IP
     * @return the penalization IP
     */
    private int calculatePenalizationIP() {
        return switch (quartile) {
            case "Q1" -> -5;
            case "Q2" -> -4;
            case "Q3" -> -3;
            case "Q4" -> -2;
            default -> 0;
        };
    }

    /**
     * Get trial information
     * @return the trial information
     */
    @Override
    public String getTrialInfo() {
        return  "Journal: " + publicationName + " (" + quartile + ")\n" +
                "Chances: " + acceptProbability + "% acceptance, " + revisionProbability + "% revision, " + rejectProbability + "% rejection\n\n";
    }

    /**
     * Get the data to write to the file
     * @return the data to write to the file
     */
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

    /**
     * Check if the trial is finished
     * @return true if the trial is finished, false otherwise
     */
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
