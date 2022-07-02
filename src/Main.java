import BusinessLayer.ConductorManager;
import BusinessLayer.EditionManager;
import BusinessLayer.PlayerManager;
import BusinessLayer.TrialManager;
import PersistenceLayer.EditionFileManager;
import PersistenceLayer.ExecutionFileManager;
import PersistenceLayer.FileManager;
import PersistenceLayer.TrialsFileManager;
import PresentationLayer.Controllers.ComposerController;
import PresentationLayer.Controllers.ConductorController;
import PresentationLayer.Controllers.MainMenuController;
import PresentationLayer.Views.ComposerView;
import PresentationLayer.Views.ConductorView;
import PresentationLayer.Views.MainMenuView;

import java.io.IOException;

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
        FileManager fileManager = new FileManager();
        if(!fileManager.checkIfDirectoryExists())
            mainMenuView.showError("\nError: The directory 'files' does not exist. Please create it within the project's directories.");
        else {
            try {
                fileManager.createTrialsFileIfNecessary();
                fileManager.createEditionsFileIfNecessary();
                fileManager.createExecutionFileIfNecessary();
                mainMenuController.mainMenuDisplay();
            } catch (IOException e) {
                mainMenuView.showError("\nError: The files could not be created. Please check the permissions of the project's directories.");
            }
        }
    }
}
