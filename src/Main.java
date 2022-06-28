import BusinessLayer.Edition.EditionManager;
import BusinessLayer.Trials.TrialManager;
import PresentationLayer.Controllers.ComposerController;
import PresentationLayer.Views.ComposerView;

public class Main {
    public static void main(String[] args) {
        TrialManager trialManager = new TrialManager();
        EditionManager editionManager = new EditionManager();
        ComposerView composerView = new ComposerView();
        ComposerController composerController = new ComposerController(editionManager, trialManager, composerView);
        composerController.managementMode();
    }
}
