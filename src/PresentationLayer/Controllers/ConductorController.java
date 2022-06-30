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

    /**
     * Constructor for the ConductorController.
     * @param conductorManager
     * @param conductorView
     * @param playerManager
     */
    public ConductorController(ConductorManager conductorManager, ConductorView conductorView, PlayerManager playerManager) {
        this.conductorManager = conductorManager;
        this.conductorView = conductorView;
        this.playerManager = playerManager;
    }


    /**
     * Starts the conductor.
     */
    public void start() {
        try{
            conductorView.showMessage("\nEntering execution mode...\n");
            conductorManager.loadDataForTrials();
            if(conductorManager.fileIsEmpty()){
                if(conductorManager.loadDataForCurrentEdition()){
                    conductorView.showMessage("\n---The Trials 2022---\n\n");
                    for(int i = 0; i < conductorManager.getTotalPlayer(); i++){
                        playerManager.addPlayer(conductorView.askForPlayerName(i+1, conductorManager.getCurrentEdition().getNumberOfPlayers()));
                    }
                    startIndex = 0;
                    executeEdition();
                }else
                    conductorView.showError("\nNo edition is defined for the current year (2022).\n");
            }else{
                conductorView.showMessage("\n---The Trials 2022---\n");
                startIndex = conductorManager.loadDataForExecution();
                playerManager.loadPlayersData();
                conductorManager.initializeEditionData(playerManager.getTotalPlayers());
                executeEdition();
            }
        } catch (IOException | CsvException e) {
            conductorView.showError("\nError loading data.\n");
        }

        conductorView.showMessage("Shutting down...\n");
    }

    /**
     * Executes the current edition.
     */
    private void executeEdition() {
        int i, k, result;
        for (i = 0; i < conductorManager.getNumTrials(); i++) {
            conductorView.showMessage("\nTrial #" + (startIndex + 1) + " - " + conductorManager.getCurrentEdition().getTrials()[i] + "\n");
            for (int j = 0; j < playerManager.getTotalPlayers(); j++) {
                if (!playerManager.playerIsDead(j)) {
                    k = -1;
                    do {
                        result = conductorManager.incrementInvestigationPoints(i);
                        k++;
                    } while (result == -1);
                    playerManager.getPlayerByIndex(j).addInvestigationPoints(result);
                    conductorView.displayPlayerCondition(playerManager.getPlayerByIndex(j).getName(), k, result, playerManager.getPlayerByIndex(j).getInvestigationPoints());
                }
            }
            startIndex++;
            if (playerManager.allPlayersareDead())
                break;
            else if (i != conductorManager.getNumTrials() - 1 && !conductorView.showContinueMessage()) {
                i++;
                break;
            }
        }

        if (playerManager.allPlayersareDead()) {
            conductorView.showMessage("\n\nTHE TRIALS " + conductorManager.getCurrentEdition().getYear() + " HAVE ENDED - PLAYERS LOST \n\n");
            try{
                conductorManager.eraseInformationExecutionFile();
            } catch (IOException e) {
                conductorView.showError("\nError erasing data.\n");
            }
        } else if (i == conductorManager.getNumTrials()){
            conductorView.showMessage("\n\nTHE TRIALS " + conductorManager.getCurrentEdition().getYear() + " HAVE ENDED - PLAYERS WON \n\n");
            try{
                conductorManager.eraseInformationExecutionFile();
            } catch (IOException e) {
                conductorView.showError("\nError erasing data.\n");
            }
        }else {
            conductorView.showMessage("\nSaving & ");
            try{
                conductorManager.saveData(i, startIndex);
                playerManager.saveData();
            } catch (IOException | CsvException e) {
                conductorView.showError("\nError saving files.\n");
            }
        }

    }


}
