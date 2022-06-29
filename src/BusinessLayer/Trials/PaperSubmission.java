package BusinessLayer.Trials;

public class PaperSubmission extends Trials {
    private final String publicationName;
    private final String quartile;
    private final int acceptProbability;
    private final int revisionProbability;
    private final int rejectProbability;

    //Name of the trial, which can’t be empty and must be unique across all trials
    public PaperSubmission(String trialName, String paperName, String quartile, int acceptProbability, int revisionProbability, int rejectProbability) {
        super(trialName, "PaperSubmission");
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
}
