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
    private final PlayerManager playerManager;
    private final TrialManager trialManager;
    private final EditionManager editionManager;

    public ConductorManager(PlayerManager playerManager, TrialManager trialManager, EditionManager editionManager) {
        this.playerManager = playerManager;
        this.trialManager = trialManager;
        this.editionManager = editionManager;
    }

    public int incrementInvestigationPoints(int indexPlayer, int indexTrial){
        switch(trials[indexTrial].hasWonTrial()) {
            case 0 -> playerManager.getPlayerByIndex(indexPlayer).addInvestigationPoints(trials[indexTrial].getPenalizationIP());
            case 1 -> playerManager.getPlayerByIndex(indexPlayer).addInvestigationPoints(trials[indexTrial].getRewardIP());
            case 2 -> { return -1; }
        }
        return playerManager.getPlayerByIndex(indexPlayer).getInvestigationPoints();
    }

    public void loadData(List<String[]> executionData){
        for(String[] executionInfo: executionData) {
            currentEdition = new Edition(Integer.parseInt(executionInfo[0]), Integer.parseInt(executionInfo[1]), Integer.parseInt(executionInfo[2]));
        }
    }

}
