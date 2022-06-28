package PresentationLayer.Controllers;

import BusinessLayer.Edition.EditionManager;
import BusinessLayer.Trials.TrialManager;
import PresentationLayer.Views.ComposerView;
import PresentationLayer.Views.ConductorView;

public class ConductorController {
    private EditionManager editionManager;
    private TrialManager trialManager;
    private ConductorView conductorView;


    public ConductorController(EditionManager editionManager, TrialManager trialManager, ConductorView conductorView) {
        this.editionManager = editionManager;
        this.trialManager = trialManager;
        this.conductorView = conductorView;
    }


}
