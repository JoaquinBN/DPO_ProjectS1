package BusinessLayer.Edition;

import BusinessLayer.Trials.Trials;

public class Edition {
    private int year;
    private int numberOfPlayers;
    private String[] trials;

    /**
     * Constructor for Edition
     * @param year the year of the edition
     * @param numberOfPlayers the number of players in the edition
     * @param numberOfTrials the number of trials in the edition
     */
    public Edition(int year, int numberOfPlayers, int numberOfTrials) {
        this.year = year;
        this.numberOfPlayers = numberOfPlayers;
        this.trials = new String[numberOfTrials];
    }

    /**
     * Get the year of the edition
     * @return the year of the edition
     */
    public int getYear() {
        return year;
    }

    /**
     * Get the number of players in the edition
     * @return the number of players in the edition
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Get the number of trials in the edition
     * @return the number of trials in the edition
     */
    public int getNumberOfTrials() {
        return trials.length;
    }

    /**
     * Get the trials in the edition
     * @return the trials in the edition
     */
    public String[] getTrials() {
        return trials;
    }

    /**
     * Set the year of the edition
     * @param year the year of the edition
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Set the number of players in the edition
     * @param numberOfPlayers the number of players in the edition
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Set the trials in the edition
     * @param trialName the trial name
     * @param position the trial number
     */
    public void addTrial(String trialName, int position) {
        this.trials[position] = trialName;
    }
}
