package PresentationLayer.Controllers;

import BusinessLayer.ConductorManager;
import BusinessLayer.Edition.EditionManager;
import BusinessLayer.Players.PlayerManager;
import BusinessLayer.Trials.TrialManager;
import PresentationLayer.Views.ComposerView;
import PresentationLayer.Views.ConductorView;

public class ConductorController {
    private final ConductorManager conductorManager;
    private final ConductorView conductorView;
    private final PlayerManager playerManager;
    private int startIndex;

    public ConductorController(ConductorManager conductorManager, ConductorView conductorView, PlayerManager playerManager) {
        this.conductorManager = conductorManager;
        this.conductorView = conductorView;
        this.playerManager = playerManager;
    }


    public void saveInitialPlayers(){
        for(int i = 0; i < playerManager.getTotalPlayers(); i++){
            playerManager.addPlayer(conductorView.askForPlayerName(i+1, conductorManager.getCurrentEdition().getNumberOfPlayers()));
        }
    }

    public void trialDisplay(int trialIndex){

    }

    public void executeEdition(){
        int i, k, result;
        for(i = startIndex; i < conductorManager.getNumTrials(); i++){
            conductorView.showMessage("\nTrial #" + (i + 1) + " - " + conductorManager.getCurrentEdition().getTrials()[i] + "\n");
            for(int j = 0; j < playerManager.getTotalPlayers(); j++){
                if(!playerManager.playerIsDead(j)) {
                    k = -1;
                    do {
                        result = conductorManager.incrementInvestigationPoints(i);
                        k++;
                    } while (result == -1);
                    playerManager.getPlayerByIndex(j).addInvestigationPoints(result);
                    conductorView.displayPlayerCondition(playerManager.getPlayerByIndex(j).getName(), k, result, playerManager.getPlayerByIndex(j).getInvestigationPoints());
                }
            }
            if(playerManager.allPlayersareDead())
                break;
            else if(!conductorView.showContinueMessage())
                break;
        }

        if(playerManager.allPlayersareDead())
            conductorView.showMessage("\nTHE TRIALS " + conductorManager.getCurrentEdition().getYear() + " HAVE ENDED - PLAYERS LOST \n\n");
        else if(i == conductorManager.getNumTrials())
            conductorView.showMessage("\nTHE TRIALS " + conductorManager.getCurrentEdition().getYear() + " HAVE ENDED - PLAYERS WON \n\n");
        else
            conductorView.showMessage("\n\nSaving & ");

        conductorView.showMessage("Shutting down...");
    }


}
