import BusinessLayer.ConductorManager;
import BusinessLayer.Edition.EditionManager;
import BusinessLayer.Players.PlayerManager;
import BusinessLayer.Trials.TrialManager;
import PersistenceLayer.EditionFileManager;
import PersistenceLayer.ExecutionFileManager;
import PersistenceLayer.TrialsFileManager;
import PresentationLayer.Controllers.ComposerController;
import PresentationLayer.Controllers.ConductorController;
import PresentationLayer.Controllers.MainMenuController;
import PresentationLayer.Views.ComposerView;
import PresentationLayer.Views.ConductorView;
import PresentationLayer.Views.MainMenuView;

public class Main {
    /**
     * Main function of the program
     * @param args command line arguments
     */
    public static void main(String[] args) {
        ExecutionFileManager executionFileManager = new ExecutionFileManager();
        EditionFileManager editionFileManager = new EditionFileManager();
        TrialsFileManager trialsFileManager = new TrialsFileManager();
        PlayerManager playerManager = new PlayerManager(executionFileManager);
        TrialManager trialManager = new TrialManager(trialsFileManager);
        EditionManager editionManager = new EditionManager(editionFileManager, executionFileManager);
        ConductorView conductorView = new ConductorView();
        ConductorManager conductorManager = new ConductorManager(trialManager, editionFileManager, trialsFileManager, executionFileManager);
        ConductorController conductorController = new ConductorController(conductorManager, conductorView, playerManager);
        ComposerView composerView = new ComposerView();
        ComposerController composerController = new ComposerController(editionManager, trialManager, composerView);
        MainMenuView mainMenuView = new MainMenuView();
        MainMenuController mainMenuController = new MainMenuController(mainMenuView, composerController, conductorController);
        mainMenuController.mainMenuDisplay();
    }
}
