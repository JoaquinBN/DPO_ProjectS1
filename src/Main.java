import BusinessLayer.ConductorManager;
import BusinessLayer.Edition.EditionManager;
import BusinessLayer.Players.PlayerManager;
import BusinessLayer.Trials.TrialManager;
import PersistenceLayer.EditionFileManager;
import PersistenceLayer.TrialsFileManager;
import PresentationLayer.Controllers.ComposerController;
import PresentationLayer.Controllers.ConductorController;
import PresentationLayer.Controllers.MainMenuController;
import PresentationLayer.Views.ComposerView;
import PresentationLayer.Views.ConductorView;
import PresentationLayer.Views.MainMenuView;

public class Main {
    public static void main(String[] args) {
        EditionFileManager editionFileManager = new EditionFileManager();
        TrialsFileManager trialsFileManager = new TrialsFileManager();
        PlayerManager playerManager = new PlayerManager();
        TrialManager trialManager = new TrialManager(trialsFileManager);
        EditionManager editionManager = new EditionManager(editionFileManager);
        ConductorView conductorView = new ConductorView();
        ConductorManager conductorManager = new ConductorManager(playerManager, trialManager, editionManager);
        ConductorController conductorController = new ConductorController(conductorManager, conductorView);
        ComposerView composerView = new ComposerView();
        ComposerController composerController = new ComposerController(editionManager, trialManager, composerView);
        MainMenuView mainMenuView = new MainMenuView();
        MainMenuController mainMenuController = new MainMenuController(mainMenuView, composerController, conductorController);
        mainMenuController.mainMenuDisplay();
    }
}
