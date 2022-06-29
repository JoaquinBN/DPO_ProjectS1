package PresentationLayer.Controllers;

import PresentationLayer.Views.MainMenuView;

public class MainMenuController {
    private MainMenuView mainMenuView;
    private ComposerController composerController;
    private ConductorController conductorController;

    public MainMenuController(MainMenuView mainMenuView, ComposerController composerController, ConductorController conductorController) {
        this.mainMenuView = new MainMenuView();
        this.composerController = composerController;
        this.conductorController = conductorController;
    }

    public void mainMenuDisplay() {
        switch (mainMenuView.mainMenuDisplay()) {
            case 'A':
                composerController.managementMode();
                break;
            case 'B':
                //conductorController.conductorMenu();
                break;
            default:
                mainMenuView.showError("\nInvalid role. Choose A or B.\n");
                mainMenuDisplay();
                break;
        }


    }


}
