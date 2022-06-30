package BusinessLayer.Trials;

public abstract class Trials{
    private final String trialName;
    private final String typeOfTrial;
    private int rewardIP;
    private int penalizationIP;

    /**
     * Constructor for Trials
     * @param trialName Name of the trial, which can’t be empty and must be unique across all trials
     * @param typeOfTrial the type of the trial     */
    public Trials(String trialName, String typeOfTrial) {
        this.trialName = trialName;
        this.typeOfTrial = typeOfTrial;
    }

    /**
     * Get the name of the trial
     * @return the name of the trial
     */
    public String getTrialName(){
        return trialName;
    }

    /**
     * Get the type of the trial
     * @return the type of the trial
     */
    public String getTypeOfTrial(){
        return typeOfTrial;
    }

    /**
     * Get the reward IP of the trial
     * @return the reward IP of the trial
     */
    public int getRewardIP(){
        return rewardIP;
    }

    /**
     * Get the penalization IP of the trial
     * @return the penalization IP of the trial
     */
    public int getPenalizationIP(){
        return penalizationIP;
    }

    /**
     * Set the reward IP of the trial
     * @param rewardIP the reward IP of the trial
     */
    public void setRewardIP(int rewardIP){
        this.rewardIP = rewardIP;
    }

    /**
     * Set the penalization IP of the trial
     * @param penalizationIP the penalization IP of the trial
     */
    public void setPenalizationIP(int penalizationIP){
        this.penalizationIP = penalizationIP;
    }

    /**
     * Display the trial information
     * @return the trial information
     */
    public String displayTrialInfo(){
        return "\nTrial: " + getTrialName() + " (" + getTypeOfTrial() + ")\n" + getTrialInfo();
    }

    /**
     * Get the trial information
     * @return the trial information
     */
    public abstract String getTrialInfo();

    /**
     * Get the data to write to the file
     * @return the data to write to the file
     */
    public abstract String[] getDataToWrite();

    /**
     * Calculate if the trial has been won
     * @return true if the trial has been won, false otherwise
     */
    public abstract int hasWonTrial();
}
