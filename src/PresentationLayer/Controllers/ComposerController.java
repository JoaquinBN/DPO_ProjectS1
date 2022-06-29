package PresentationLayer.Controllers;

import BusinessLayer.Edition.EditionManager;
import BusinessLayer.Trials.PaperSubmission;
import BusinessLayer.Trials.TrialManager;
import BusinessLayer.Trials.Trials;
import PresentationLayer.Views.ComposerView;

public class ComposerController {
    private final EditionManager editionManager;
    private final TrialManager trialManager;
    private final ComposerView composerView;


    public ComposerController(EditionManager editionManager, TrialManager trialManager, ComposerView composerView) {
        this.editionManager = editionManager;
        this.trialManager = trialManager;
        this.composerView = composerView;
    }

    public void managementMode(){
        String option;
        option = composerView.managementMenu();
        switch (option) {
            case "1" -> this.manageTrials();
            case "2" -> this.manageEditions();
            case "3" -> this.exitProgram();
            default -> {
                composerView.showError("Wrong option. Please try again.");
                managementMode();
            }
        }
    }

    private void manageTrials(){
        String option;
        option = composerView.manageTrialsMenu();
        switch (option) {
            case "a" -> this.createTrial();
            case "b" -> this.listTrials();
            case "c" -> this.deleteTrial();
            case "d" -> this.managementMode();
            default -> {
                composerView.showError("Wrong option. Please try again.");
                manageTrials();
            }
        }
    }

    private void createTrial(){
        String trialName, paperName, quartile;
        int acceptProbability, revisionProbability, rejectProbability, error;
        error = 0;
        do {
            if(error == 1)
                composerView.showError("This");
            error = 1;
            trialName = composerView.readTrialName();
        }while(!trialManager.checkTrialName(trialName));

        error = 0;
        do {
            if(error == 1)
                composerView.showError("The name of the publication cannot be empty. Please try again.");
            error = 1;
            paperName = composerView.readPaperName();
        }while(!trialManager.checkTrialType(paperName));

        error = 0;
        do {
            if(error == 1)
                composerView.showError("The quartile of the publication must be one of the following values: Q1, Q2, Q3, Q4. Please try again.");
            error = 1;
            quartile = composerView.readQuartile();
        }while(!trialManager.checkQuartile(quartile));

        error = 0;
        do {
            if(error == 1)
                composerView.showError("The probability of the paper publication must add up to 100. Please try again.");
            error = 1;
            do {
                if (error == 2)
                    composerView.showError("The sum of the acceptance and revision probabilities cannot be over 100. Please try again.");
                error = 2;
                do {
                    if (error == 3)
                        composerView.showError("The acceptance probability must be between 0 and 100. Please try again.");
                    error = 3;
                    acceptProbability = composerView.readAccept();
                } while (!trialManager.checkProbability(acceptProbability));

                error = 2;
                do {
                    if (error == 3)
                        composerView.showError("The revision probability must be between 0 and 100. Please try again.");
                    error = 3;
                    revisionProbability = composerView.readRevision();
                } while (!trialManager.checkProbability(revisionProbability));
                error = 2;
            } while (!trialManager.checkLimitProbabilities(acceptProbability + revisionProbability));

            error = 1;
            do {
                if(error == 2)
                    composerView.showError("The rejection probability must be between 0 and 100. Please try again.");
                error = 2;
                rejectProbability = composerView.readReject();
            } while (!trialManager.checkProbability(rejectProbability));
            error = 1;
        } while (!trialManager.checkSumProbabilities(acceptProbability + revisionProbability + rejectProbability));
        trialManager.addTrial(trialName, paperName, quartile, acceptProbability, revisionProbability, rejectProbability);
        composerView.createTrialSuccess();
        manageTrials();
    }

    private void listTrials(){
        int trialIndex;
        showAllTrials();
        trialIndex = composerView.showBackAndOption(trialManager.getNumberOfTrials() + 1);
        if(trialIndex != trialManager.getNumberOfTrials()){
            if(trialManager.getTrial(trialIndex) instanceof PaperSubmission){
                composerView.showMessage(trialManager.getTrial(trialIndex).displayTrialInfo());
            }
        }
        manageTrials();
    }

    private void deleteTrial(){
        int trialIndex;
        showAllTrials();
        trialIndex = composerView.showBackAndOption(trialManager.getNumberOfTrials() + 1);
        if(trialIndex != trialManager.getNumberOfTrials()){
            trialManager.removeTrial(trialIndex);
            composerView.deleteTrialSuccess();
        }
        manageTrials();
    }

    private void showAllTrials(){
        for(int i = 0; i < trialManager.getNumberOfTrials(); i++){
            composerView.listTrials(i + 1, trialManager.getTrial(i).getTrialName());
        }
    }

    private void manageEditions(){
        String option;
        option = composerView.manageEditionsMenu();
        switch (option) {
            case "a" -> this.createEdition();
            case "b" -> this.listEditions();
            case "c" -> this.duplicateEdition();
            case "d" -> this.deleteEdition();
            case "e" -> this.managementMode();
            default -> {
                composerView.showError("Wrong option. Please try again.");
                manageEditions();
            }
        }
    }

    private void createEdition(){
        int year, numberOfPlayers, numberOfTrials;
        year = composerView.readEditionYear();
        numberOfPlayers = composerView.readEditionPlayer();
        numberOfTrials = composerView.readEditionTrials();
        editionManager.addEdition(year, numberOfPlayers, numberOfTrials);
        composerView.showMessage("\n\t--- Trials ---\n");
        showAllTrials();
        composerView.showMessage("\n\n");
        int trialIndex;
        for(int j = 0; j < numberOfTrials; j++){
            trialIndex = composerView.pickTrial(numberOfTrials, j + 1) - 1;
            editionManager.addTrialToEdition(trialManager.getTrial(trialIndex), j);
        }
        composerView.createEditionSuccess();
        manageEditions();
    }

    private void listEditions(){
        int editionIndex, k;
        composerView.showMessage("\nHere are the current editions, do you want to see more details or go back?\n\n");
        editionIndex = showAllEditions();
        if(editionIndex != editionManager.getNumberOfEditions()){
            composerView.showEdition(editionManager.getEditionByIndex(editionIndex).getYear(), editionManager.getEditionByIndex(editionIndex).getNumberOfPlayers());
            k = 1;
            for(Trials trial : editionManager.getEditionByIndex(editionIndex).getTrials()){
                composerView.listEditionTrials(k, trial.getTrialName(), trial.getTypeOfTrial());
                k++;
            }
        }
        manageEditions();
    }

    private void duplicateEdition(){
        int editionIndex, year, numberOfPlayers;
        composerView.showMessage("\nWhich edition do you want to clone?\n\n");
        editionIndex = showAllEditions();
        if(editionIndex != editionManager.getNumberOfEditions()) {
            year = composerView.readNewEditionYear();
            numberOfPlayers = composerView.readNewEditionPlayer();
            editionManager.duplicateEditions(year, numberOfPlayers, editionIndex);
            composerView.duplicateEditionSuccess();
        }
        manageEditions();
    }

    private void deleteEdition(){
        int editionIndex;
        composerView.showMessage("\nWhich edition do you want to delete?\n\n");
        editionIndex = showAllEditions();
        if(editionIndex != editionManager.getNumberOfEditions()) {
            editionManager.removeEdition(editionIndex);
            composerView.deleteEditionSuccess();
        }
        manageEditions();
    }

    private int showAllEditions(){
        for(int i = 0; i < editionManager.getNumberOfEditions(); i++){
            composerView.listEditions(i + 1, editionManager.getEditionByIndex(i).getYear());
        }
        return composerView.showBackAndOption(editionManager.getNumberOfEditions() + 1);
    }

    private void exitProgram(){
        composerView.exitProgram();
    }
}
