package PresentationLayer.Controllers;

import BusinessLayer.ConductorManager;
import BusinessLayer.Entities.Player;
import BusinessLayer.PlayerManager;
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
     * @param conductorManager the conductor manager
     * @param conductorView the conductor view
     * @param playerManager the player manager
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
                        playerManager.addPlayer(conductorView.askForPlayerName(i+1, conductorManager.getTotalPlayer()));
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
        int i;
        for (i = 0; i < conductorManager.getNumTrials(); i++) {
            conductorView.showMessage("\nTrial #" + (startIndex + 1) + " - " + conductorManager.getTrialName(i) + "\n");
            for (Player player: playerManager.getPlayers()) {
                if (!player.isDead()) {
                    conductorView.showMessage(conductorManager.getTrialPrintOutput(i, player.getName()));
                    player.addInvestigationPoints(conductorManager.incrementInvestigationPoints(i));
                    conductorView.displayIPCount(player.getInvestigationPoints());
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

        //TODO - remove try catch
        if (playerManager.allPlayersareDead()) {
            conductorView.showMessage("\n\nTHE TRIALS 2022 HAVE ENDED - PLAYERS LOST \n\n");
            try{
                conductorManager.eraseInformationExecutionFile();
            } catch (IOException e) {
                conductorView.showError("\nError erasing data.\n");
            }
        } else if (i == conductorManager.getNumTrials()){
            conductorView.showMessage("\n\nTHE TRIALS 2022 HAVE ENDED - PLAYERS WON \n\n");
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
