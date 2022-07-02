package PresentationLayer.Controllers;

import BusinessLayer.ConductorManager;
import BusinessLayer.Entities.Player;
import BusinessLayer.PlayerManager;
import PresentationLayer.Views.ConductorView;

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
        boolean shutDown = false;
        conductorView.showMessage("\nEntering execution mode...\n");
        if(printExceptionMessage(conductorManager.loadDataForTrials(), true))
            shutDown = true;
        if(conductorManager.fileIsEmpty() == 1){
            if(conductorManager.loadDataForCurrentEdition()){
                conductorView.showMessage("\n---The Trials 2022---\n\n");
                for(int i = 0; i < conductorManager.getTotalPlayer(); i++){
                    playerManager.addPlayer(conductorView.askForPlayerName(i+1, conductorManager.getTotalPlayer()));
                }
                startIndex = 0;
                executeEdition();
            }else
                conductorView.showError("\nNo edition is defined for the current year (2022).\n");
        }else if (conductorManager.fileIsEmpty() == 0){
            conductorView.showMessage("\n---The Trials 2022---\n");
            if(printExceptionMessage(conductorManager.loadDataForExecution(), true))
                shutDown = true;
            startIndex = conductorManager.getStartIndex();
            if(printExceptionMessage(playerManager.loadPlayersData(), false))
                shutDown = true;
            conductorManager.initializeEditionData(playerManager.getTotalPlayers());
            if(!shutDown)
                executeEdition();
        }else
            printExceptionMessage(true, true);

        conductorView.showMessage("Shutting down...\n");
    }

    private boolean printExceptionMessage(boolean isException, boolean isConductorManager){
        if(!isException){
            conductorView.showError("\n" + (isConductorManager?conductorManager.getErrorMessage():playerManager.getErrorMessage()) + "\n");
            return true;
        }
        return false;
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

        if (playerManager.allPlayersareDead()) {
            conductorView.showMessage("\n\nTHE TRIALS 2022 HAVE ENDED - PLAYERS LOST \n\n");
            printExceptionMessage(conductorManager.eraseInformationExecutionFile(),true);
        } else if (i == conductorManager.getNumTrials()){
            conductorView.showMessage("\n\nTHE TRIALS 2022 HAVE ENDED - PLAYERS WON \n\n");
            printExceptionMessage(conductorManager.eraseInformationExecutionFile(),true);
        }else {
            conductorView.showMessage("\nSaving & ");
            printExceptionMessage(conductorManager.saveData(i, startIndex),true);
            printExceptionMessage(playerManager.saveData(),false);
        }

    }


}
