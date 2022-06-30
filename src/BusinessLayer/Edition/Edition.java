package BusinessLayer.Edition;

import BusinessLayer.Trials.Trials;

public class Edition {
    private int year;
    private int numberOfPlayers;
    private String[] trials;

    public Edition(int year, int numberOfPlayers, int numberOfTrials) {
        this.year = year;
        this.numberOfPlayers = numberOfPlayers;
        this.trials = new String[numberOfTrials];
    }

    //Getters
    public int getYear() {
        return year;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getNumberOfTrials() {
        return trials.length;
    }

    public String[] getTrials() {
        return trials;
    }

    //Setters
    public void setYear(int year) {
        this.year = year;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void addTrial(String trialName, int position) {
        this.trials[position] = trialName;
    }
}
