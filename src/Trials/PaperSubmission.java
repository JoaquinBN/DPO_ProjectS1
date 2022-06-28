package Trials;

public class PaperSubmission extends Trials{
    private String paperName;
    private String quartile;
    private int acceptProbability;
    private int rejectProbability;
    private int revisionProbability;

    //Name of the trial, which can’t be empty and must be unique across all trials
    public PaperSubmission(String paperName){
        this.paperName = paperName;
    }

    public String listPaperName(){
        return paperName;
    }

    public void showPaper(){

    }
}
