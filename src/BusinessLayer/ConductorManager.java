package BusinessLayer;

import BusinessLayer.Edition.Edition;
import BusinessLayer.Edition.EditionManager;
import BusinessLayer.Players.PlayerManager;
import BusinessLayer.Trials.TrialManager;
import BusinessLayer.Trials.Trials;

import java.util.ArrayList;
import java.util.List;

public class ConductorManager {
    private Edition currentEdition;
    private Trials[] trials;
    private final TrialManager trialManager;
    private final EditionManager editionManager;

    public ConductorManager(PlayerManager playerManager, TrialManager trialManager, EditionManager editionManager) {
        this.trialManager = trialManager;
        this.editionManager = editionManager;
    }

    public int incrementInvestigationPoints(int indexTrial){
        int investigationPoints = 0;
        switch(trials[indexTrial].hasWonTrial()) {
            case 0 -> investigationPoints = trials[indexTrial].getPenalizationIP();
            case 1 -> investigationPoints =  trials[indexTrial].getRewardIP();
            case 2 -> investigationPoints = -1;
        }
        return investigationPoints;
    }

    public void loadData(List<String[]> executionData){
        for(String[] executionInfo: executionData) {
            currentEdition = new Edition(Integer.parseInt(executionInfo[0]), Integer.parseInt(executionInfo[1]), Integer.parseInt(executionInfo[2]));
        }
    }

    public Edition getCurrentEdition(){
        return currentEdition;
    }

    public int getNumTrials(){
        return currentEdition.getNumberOfTrials();
    }


}
