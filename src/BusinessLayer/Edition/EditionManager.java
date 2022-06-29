package BusinessLayer.Edition;

import BusinessLayer.Trials.Trials;

import java.util.ArrayList;

public class EditionManager {
    private ArrayList<Edition> editions;

    public EditionManager() {
        editions = new ArrayList<>();
    }

    public void addEdition(int year, int numberOfPlayers, int numberOfTrials) {
        editions.add(new Edition(year, numberOfPlayers, numberOfTrials));
    }

    public void removeEdition(int indexEdition) {
        editions.remove(indexEdition);
    }

    public Edition getEditionByIndex(int index) {
        return editions.get(index);
    }

    public int getNumberOfEditions() {
        return editions.size();
    }

    public void duplicateEditions(int year, int numberOfPlayers, int indexEdition) {
        int i = 0;
        editions.add(new Edition(year, numberOfPlayers, editions.get(indexEdition).getNumberOfTrials()));
        for(Trials trial: editions.get(indexEdition).getTrials()) {
            editions.get(editions.size() - 1).addTrial(trial, i);
            i++;
        }
    }

    public void addTrialToEdition(Trials trial, int indexTrial) {
        editions.get(editions.size() - 1).addTrial(trial, indexTrial);
    }

    public boolean checkUniqueYear(int year) {
        for (Edition edition : editions) {
            if (edition.getYear() == year) {
                return false;
            }
        }
        return true;
    }

    public boolean checkValidYear(int year) {
        return year >= 2022;
    }

    public boolean checkPlayersRange(int numberOfPlayers) {
        return numberOfPlayers >= 1 && numberOfPlayers <= 5;
    }

    public boolean checkTrialsRange(int numberOfTrials) {
        return numberOfTrials >= 3 && numberOfTrials <= 12;
    }

    public ArrayList<Edition> getEditions() {
        return editions;
    }

}
