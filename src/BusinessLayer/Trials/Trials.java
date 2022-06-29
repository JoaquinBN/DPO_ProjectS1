package BusinessLayer.Trials;

public abstract class Trials{
    private final String trialName;
    private final String typeOfTrial;
    private int rewardIP;
    private int penalizationIP;

    public Trials(String trialName, String typeOfTrial) {
        this.trialName = trialName;
        this.typeOfTrial = typeOfTrial;
    }

    public String getTrialName(){
        return trialName;
    }

    //Getters
    public String getTypeOfTrial(){
        return typeOfTrial;
    }

    public int getRewardIP(){
        return rewardIP;
    }

    public int getPenalizationIP(){
        return penalizationIP;
    }

    //Setters
    public void setRewardIP(int rewardIP){
        this.rewardIP = rewardIP;
    }

    public void setPenalizationIP(int penalizationIP){
        this.penalizationIP = penalizationIP;
    }

    public String displayTrialInfo(){
        return "\nTrial: " + getTrialName() + " (" + getTypeOfTrial() + ")\n" + getTrialInfo();
    }

    public abstract String getTrialInfo();

}
