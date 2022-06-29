import BusinessLayer.Edition.EditionManager;
import BusinessLayer.Trials.TrialManager;
import PersistenceLayer.EditionFileManager;
import PresentationLayer.Controllers.ComposerController;
import PresentationLayer.Views.ComposerView;

public class Main {
    public static void main(String[] args) {
        EditionFileManager editionFileManager = new EditionFileManager();
        TrialManager trialManager = new TrialManager();
        EditionManager editionManager = new EditionManager();
        ComposerView composerView = new ComposerView();
        ComposerController composerController = new ComposerController(editionManager, trialManager, composerView, editionFileManager);
        composerController.managementMode();
    }
}
