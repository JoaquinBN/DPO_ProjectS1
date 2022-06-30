package PresentationLayer.Controllers;

import BusinessLayer.ConductorManager;
import BusinessLayer.Players.PlayerManager;
import PresentationLayer.Views.ConductorView;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;

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


    public void start() throws IOException, CsvException {
        try{
            conductorManager.loadDataForTrials();
            if(conductorManager.fileIsEmpty()){
                conductorManager.loadDataForCurrentEdition();
                for(int i = 0; i < conductorManager.getTotalPlayer(); i++){
                    playerManager.addPlayer(conductorView.askForPlayerName(i+1, conductorManager.getCurrentEdition().getNumberOfPlayers()));
                }
            }else{
                conductorManager.loadDataForExecution();
                playerManager.loadPlayersData();
                conductorManager.initializeEditionData(playerManager.getTotalPlayers());
            }
        } catch (IOException | CsvException e) {
            conductorView.showError("\nError reading files.\n");
        }
        executeEdition();
    }

    public void executeEdition() throws IOException, CsvException {
        int i, k, result;
        for(i = 0; i < conductorManager.getNumTrials(); i++){
            conductorView.showMessage("\nTrial #" + (i + 1) + " - " + conductorManager.getCurrentEdition().getTrials()[i]);
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
            else if(i != conductorManager.getNumTrials() - 1 && !conductorView.showContinueMessage())
                i++;
                break;
        }

        if(playerManager.allPlayersareDead())
            conductorView.showMessage("\n\nTHE TRIALS " + conductorManager.getCurrentEdition().getYear() + " HAVE ENDED - PLAYERS LOST \n");
        else if(i == conductorManager.getNumTrials())
            conductorView.showMessage("\n\nTHE TRIALS " + conductorManager.getCurrentEdition().getYear() + " HAVE ENDED - PLAYERS WON \n");
        else {
            conductorView.showMessage("\nSaving & ");
            conductorManager.saveData(i);
            playerManager.saveData();
        }

        conductorView.showMessage("Shutting down...");
    }


}
