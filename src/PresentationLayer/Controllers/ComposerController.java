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
                composerView.showError("Wrong option");
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
                composerView.showError("Wrong option");
                manageTrials();
            }
        }
    }

    private void createTrial(){
        String trialName, paperName, quartile;
        int acceptProbability, revisionProbability, rejectProbability;
        trialName = composerView.readTrialName();
        paperName = composerView.readPaperName();
        quartile = composerView.readQuartile();
        acceptProbability = composerView.readAccept();
        revisionProbability = composerView.readRevision();
        rejectProbability = composerView.readReject();
        trialManager.addTrial(trialName, paperName, quartile, acceptProbability, revisionProbability, rejectProbability);
        composerView.createTrialSuccess();
        manageTrials();
    }

    private void listTrials(){
        int trialIndex;
        trialIndex = showAllTrials();
        if(trialIndex != trialManager.getNumberOfTrials()){
            if(trialManager.getTrial(trialIndex) instanceof PaperSubmission){
                String trialName = trialManager.getTrial(trialIndex).getTrialName();
                String trialType = "Paper Submission";
                String paperName = ((PaperSubmission)trialManager.getTrial(trialIndex)).getPublicationName();
                String quartile = ((PaperSubmission)trialManager.getTrial(trialIndex)).getQuartile();
                int acceptProbability = ((PaperSubmission)trialManager.getTrial(trialIndex)).getAcceptProbability();
                int revisionProbability = ((PaperSubmission)trialManager.getTrial(trialIndex)).getRevisionProbability();
                int rejectProbability = ((PaperSubmission)trialManager.getTrial(trialIndex)).getRejectProbability();
                composerView.listPaperSubmission(trialName, trialType, paperName, quartile, acceptProbability, revisionProbability, rejectProbability);
            }
        }
        manageTrials();
    }

    private void deleteTrial(){
        int trialIndex;
        trialIndex = showAllTrials();
        if(trialIndex != trialManager.getNumberOfTrials()){
            trialManager.removeTrial(trialIndex);
            composerView.deleteTrialSuccess();
        }
        manageTrials();
    }

    private int showAllTrials(){
        for(int i = 0; i < trialManager.getNumberOfTrials(); i++){
            composerView.listTrials(i + 1, trialManager.getTrial(i).getTrialName());
        }
        return composerView.showBackAndOption(trialManager.getNumberOfTrials() + 1);
    }

    private void manageEditions(){
        String option;
        option = composerView.manageEditionsMenu();
        switch (option) {
            case "a" -> this.createEdition();
            case "b" -> this.listEditions();
            case "c" -> this.deleteEdition();
            case "d" -> this.duplicateEdition();
            case "e" -> this.managementMode();
            default -> {
                composerView.showError("Wrong option");
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
        for(int i = 0; i < trialManager.getNumberOfTrials(); i++){
            composerView.listTrials(i + 1, trialManager.getTrial(i).getTrialName());
        }
        int trialIndex;
        for(int j = 0; j < numberOfTrials; j++){
            trialIndex = composerView.pickTrial(numberOfTrials, j + 1) - 1;
            editionManager.addTrialToEdition(trialManager.getTrial(trialIndex), j);
        }
        composerView.createEditionSuccess();
        manageEditions();
    }

    private void listEditions(){
        int editionIndex;
        editionIndex = showAllEditions();
        if(editionIndex != editionManager.getNumberOfEditions()){
            int year = editionManager.getEditionByIndex(editionIndex).getYear();
            int numberOfPlayers = editionManager.getEditionByIndex(editionIndex).getNumberOfPlayers();
            composerView.showEdition(year, numberOfPlayers);
            int k = 1;
            for(Trials trial : editionManager.getEditionByIndex(editionIndex).getTrials()){
                composerView.listEditionTrials(k, trial.getTrialName(), trial.getTypeOfTrial());
                k++;
            }
        }
        manageEditions();
    }

    private void duplicateEdition(){
        int editionIndex, year, numberOfPlayers;
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
