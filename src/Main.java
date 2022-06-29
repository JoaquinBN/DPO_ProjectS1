import BusinessLayer.Edition.EditionManager;
import BusinessLayer.Trials.TrialManager;
import PersistenceLayer.EditionFileManager;
import PersistenceLayer.TrialsFileManager;
import PresentationLayer.Controllers.ComposerController;
import PresentationLayer.Views.ComposerView;

public class Main {
    public static void main(String[] args) {
        EditionFileManager editionFileManager = new EditionFileManager();
        TrialsFileManager trialsFileManager = new TrialsFileManager();
        TrialManager trialManager = new TrialManager();
        EditionManager editionManager = new EditionManager();
        ComposerView composerView = new ComposerView();
        ComposerController composerController = new ComposerController(editionManager, trialManager, composerView, editionFileManager, trialsFileManager);
        composerController.managementMode();
    }
}
