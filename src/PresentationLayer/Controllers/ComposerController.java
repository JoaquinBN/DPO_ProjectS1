package PresentationLayer.Controllers;

import BusinessLayer.Edition.EditionManager;
import BusinessLayer.Trials.PaperSubmission;
import BusinessLayer.Trials.TrialManager;
import BusinessLayer.Trials.Trials;
import PersistenceLayer.EditionFileManager;
import PersistenceLayer.TrialsFileManager;
import PresentationLayer.Views.ComposerView;

import java.io.IOException;

public class ComposerController {
    private final EditionManager editionManager;
    private final TrialManager trialManager;
    private final ComposerView composerView;
    private final EditionFileManager editionFileManager;
    private final TrialsFileManager trialsFileManager;


    public ComposerController(EditionManager editionManager, TrialManager trialManager, ComposerView composerView, EditionFileManager editionFileManager, TrialsFileManager trialsFileManager) {
        this.editionManager = editionManager;
        this.trialManager = trialManager;
        this.composerView = composerView;
        this.editionFileManager = editionFileManager;
        this.trialsFileManager = trialsFileManager;
    }

    public void managementMode(){
        String option;
        option = composerView.managementMenu();
        switch (option) {
            case "1" -> this.manageTrials();
            case "2" -> this.manageEditions();
            case "3" -> this.exitProgram();
            default -> {
                composerView.showError("\nWrong option. Please try again:");
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
                composerView.showError("\nWrong option. Please try again:");
                manageTrials();
            }
        }
    }

    private String getTrialAttribute(String attributeType){
        String attribute = "";
        boolean condition = true, condition2 = true;
        boolean wrongInput = false;
        do{
            if(wrongInput){
                if(attributeType.equals("name") && !condition)
                    composerView.showError("\nThis trial name already exists. Please try again:");
                else if(attributeType.equals("name") && !condition2)
                    composerView.showError("\nThe name of the trial cannot be empty. Please try again:");
                switch (attributeType) {
                    case "type" -> composerView.showError("\nThe type of the trial cannot be empty. Please try again:");
                    case "quartile" -> composerView.showError("\nThe quartile of the publication must be one of the following values: Q1, Q2, Q3, Q4. Please try again:");
                    case "accept" -> composerView.showError("\nThe acceptance probability must be between 0 and 100. Please try again:");
                    case "revision" -> composerView.showError("\nThe revision probability must be between 0 and 100. Please try again:");
                    case "reject" -> composerView.showError("\nThe rejection probability must be between 0 and 100. Please try again:");
                }

            }
            switch(attributeType){
                case "name" -> attribute = composerView.readTrialName();
                case "type" -> attribute = composerView.readPaperName();
                case "quartile" -> attribute = composerView.readQuartile();
                case "accept" -> attribute = composerView.readAccept();
                case "revision" -> attribute = composerView.readRevision();
                case "reject" -> attribute = composerView.readReject();
                default -> attribute = "";
            }
            wrongInput = true;
            switch(attributeType){
                case "name" -> condition = trialManager.checkUniqueName(attribute);
                case "type" -> condition = trialManager.checkEmptyString(attribute);
                case "quartile" -> condition = trialManager.checkQuartile(attribute);
                case "accept", "revision", "reject" -> condition = trialManager.checkProbability(attribute);
                default -> condition = false;
            }
            if(attributeType.equals("name"))
                condition2 = trialManager.checkEmptyString(attribute);
            else
                condition2 = true;
        }while(!condition || !condition2);
        return attribute;
    }

    private void createTrial(){
        String trialName, paperName, quartile;
        int acceptProbability, revisionProbability, rejectProbability, error, trialType;
        error = 0;
        composerView.showTrialTypes();
        do {
            if(error == 1)
                composerView.showError("\nThe trial has to be an existing type. Please try again:");
            trialType = composerView.getTrialTypeInput();
            error = 1;
        } while (trialType != 1);

        trialName = getTrialAttribute("name");
        paperName = getTrialAttribute("type");
        quartile = getTrialAttribute("quartile");

        error = 0;
        do {
            if(error == 1)
                composerView.showError("\nThe probability of the paper publication must add up to 100. Please try again:");

            do {
                if (error == 2)
                    composerView.showError("\nThe sum of the acceptance and revision probabilities cannot be over 100. Please try again:");
                acceptProbability = Integer.parseInt(getTrialAttribute("accept"));
                revisionProbability = Integer.parseInt(getTrialAttribute("revision"));
                error = 2;
            } while (trialManager.checkLimitProbabilities(acceptProbability + revisionProbability));

            rejectProbability = Integer.parseInt(getTrialAttribute("reject"));
            error = 1;
        } while (!trialManager.checkSumProbabilities(acceptProbability + revisionProbability + rejectProbability));
        trialManager.addTrial(trialType, trialName, paperName, quartile, acceptProbability, revisionProbability, rejectProbability);
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
                composerView.showError("\nWrong option. Please try again:");
                manageEditions();
            }
        }
    }

    private void createEdition(){
        int year = 0, numberOfPlayers, numberOfTrials;
        boolean errorDisplay = false;
        do {
            if(errorDisplay && !editionManager.checkUniqueYear(year))
                composerView.showError("\nThis edition already exists. Please try again:");
            else if(errorDisplay && !editionManager.checkValidYear(year))
                composerView.showError("\nThe year of the edition must equal or greater than the current year (2022). Please try again:");
            year = composerView.readEditionYear();
            errorDisplay = true;
        }while (!editionManager.checkUniqueYear(year) || !editionManager.checkValidYear(year));

        errorDisplay = false;
        do {
            if(errorDisplay)
                composerView.showError("\nThe number of players must be between 1 and 5. Please try again:");
            numberOfPlayers = composerView.readEditionPlayer();
            errorDisplay = true;
        }while(!editionManager.checkPlayersRange(numberOfPlayers));

        errorDisplay = false;
        do {
            if(errorDisplay)
                composerView.showError("\nThe number of trials must be between 3 and 12. Please try again:");
            numberOfTrials = composerView.readEditionTrials();
            errorDisplay = true;
        }while(!editionManager.checkTrialsRange(numberOfTrials));
        editionManager.addEdition(year, numberOfPlayers, numberOfTrials);
        composerView.showMessage("\n\t--- Trials ---\n");
        showAllTrials();
        composerView.showMessage("\n\n");
        int trialIndex;
        for(int j = 0; j < numberOfTrials; j++){
            do{
                trialIndex = composerView.pickTrial(numberOfTrials, j + 1) - 1;
            }while(trialIndex < 0 || trialIndex >= trialManager.getNumberOfTrials());
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
        try {
            editionFileManager.writeEditions(editionManager.getEditions());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            trialsFileManager.writeTrials(trialManager.getTrialsArrayList());
        }catch (IOException e) {
            e.printStackTrace();
        }
        composerView.exitProgram();
    }
}
