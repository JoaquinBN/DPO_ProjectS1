package PresentationLayer.Controllers;

import BusinessLayer.Edition.EditionManager;
import BusinessLayer.Trials.TrialManager;
import PresentationLayer.Views.ComposerView;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class ComposerController {
    private final EditionManager editionManager;
    private final TrialManager trialManager;
    private final ComposerView composerView;

    /**
     * Constructor for the ComposerController.
     * @param editionManager - the edition manager
     * @param trialManager - the trial manager
     * @param composerView - the composer view
     */
    public ComposerController(EditionManager editionManager, TrialManager trialManager, ComposerView composerView) {
        this.editionManager = editionManager;
        this.trialManager = trialManager;
        this.composerView = composerView;
    }

    /**
     * Starts the composer view.
     */
    public void start(){
        try {
            editionManager.readEditions();
            trialManager.readTrials();
        } catch (IOException | CsvException e) {
            composerView.showError("Error reading trials file");
        }
        managementMode();
    }

    /**
     * Starts the management mode of the composer view.
     */
    private void managementMode(){
        String option;
        composerView.showMessage("\nEntering management mode...\n");
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

    /**
     * Starts the trials management mode of the composer view.
     */
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

    /**
     * Get the trials attributes depending on the type.
     *
     * @param attributeType the attribute type to be used in the edition management.
     * @return the attribute type.
     */
    private String getTrialAttribute(int attributeType){
        String probability, attribute;

        switch(attributeType){
            case 4 -> probability = "acceptance";
            case 5 -> probability = "revision";
            case 6 -> probability = "rejection";
            default -> probability = "";
        }

        switch (attributeType) {
            case 0 -> {
                attribute = composerView.getTrialTypeInput();
                if (!attribute.equals("1") && !attribute.equals("-1")) {
                    composerView.showError("\nThe trial has to be an existing type.");
                    attribute = "";
                }
            }
            case 1 -> {
                attribute = composerView.readTrialName();
                if (!trialManager.checkUniqueName(attribute)) {
                    composerView.showError("\nTrial name already exists.");
                    attribute = "";
                } else if (!trialManager.checkEmptyString(attribute)) {
                    composerView.showError("\nTrial name cannot be empty.");
                    attribute = "";
                }
            }
            case 2 -> {
                attribute = composerView.readPaperName();
                if (!trialManager.checkEmptyString(attribute)) {
                    composerView.showError("\nName of the publication cannot be empty.");
                    attribute = "";
                }
            }
            case 3 -> {
                attribute = composerView.readQuartile();
                if (!trialManager.checkQuartile(attribute)) {
                    composerView.showError("\nWrong quartile.");
                    attribute = "";
                }
            }
            case 4, 5, 6 -> {
                attribute = composerView.readProbability(probability);
                if (!trialManager.checkProbability(Integer.parseInt(attribute)) && !attribute.equals("-1")) {
                    composerView.showError("\nThe " + probability + " probability must be between 0 and 100.");
                    attribute = "";
                }
            }
            default -> attribute = "";
        }
        return attribute;
    }

    /**
     * Creates a trial.
     */
    private void createTrial(){
        String[] attributes = new String[7];
        boolean errorInput = false;
        int i;
        composerView.showTrialTypes();
        i = 0;
        while(!errorInput && i < 7) {
            attributes[i] = getTrialAttribute(i);
            if (attributes[i].equals("") || attributes[i].equals("-1"))
                errorInput = true;
            else if (i == 5 && trialManager.checkLimitProbabilities(Integer.parseInt(attributes[4]) + Integer.parseInt(attributes[5]))){
                composerView.showError("\nThe acceptance and revision probabilities sum cannot be greater than 100.");
                errorInput = true;
            }else if(i == 6 && !trialManager.checkSumProbabilities(Integer.parseInt(attributes[4])  + Integer.parseInt(attributes[5])  + Integer.parseInt(attributes[6]))) {
                composerView.showError("\nThe acceptance, revision and rejection probabilities sum cannot be greater than 100.");
                errorInput = true;
            }
            i++;
        }
        if(!errorInput) {
            Collections.swap(Arrays.asList(attributes), 0, 1);
            trialManager.addTrial(attributes);
            composerView.createSuccess("Trial");
        }
        composerView.showMessage("\nRedirecting to previous menu...\n");
        manageTrials();
    }

    /**
     * Lists the trials.
     */
    private void listTrials(){
        int trialIndex;
        boolean errorInput = false;

        if(trialManager.getNumberOfTrials() == 0){
            composerView.showError("\nThere are no trials to display.");
        }else {
            composerView.showList("trials");
            showAllTrials();
            composerView.showBack(trialManager.getNumberOfTrials() + 1);

            trialIndex = composerView.getIndexInput();
            if (trialIndex < 0 || trialIndex > trialManager.getNumberOfTrials()) {
                composerView.showError("\nThe index entered must be an integer between 1 and " + (trialManager.getNumberOfTrials() + 1) + ".");
                errorInput = true;
            }

            if (trialIndex != trialManager.getNumberOfTrials() && !errorInput)
                composerView.showMessage(trialManager.getTrial(trialIndex).displayTrialInfo());
        }
        composerView.showMessage("\nRedirecting to previous menu...\n");
        manageTrials();
    }

    /**
     * Deletes a trial.
     */
    private void deleteTrial(){
        int trialIndex;
        boolean errorInput = false;
        String deletionConfirmation;

        if(trialManager.getNumberOfTrials() == 0) {
            composerView.showError("\nThere are no trials to delete.");
        }else {
            composerView.showDelete("trial");
            showAllTrials();
            composerView.showBack(trialManager.getNumberOfTrials() + 1);

            trialIndex = composerView.getIndexInput();
            if (trialIndex < 0 || trialIndex > trialManager.getNumberOfTrials()) {
                composerView.showError("\nThe index entered must be between 1 and " + trialManager.getNumberOfTrials() + ".");
                errorInput = true;
            }

            if (trialIndex != trialManager.getNumberOfTrials() && !errorInput) {
                if(!editionManager.trialIsUsed(trialManager.getTrial(trialIndex).getTrialName())) {
                    deletionConfirmation = composerView.showDeletionConfirmation("trial's name");
                    if(trialManager.getTrial(trialIndex).getTrialName().equals(deletionConfirmation)) {
                        trialManager.removeTrial(trialIndex);
                        composerView.deleteSuccess("trial");
                    }else if(deletionConfirmation.equals("cancel"))
                        composerView.showMessage("\nOperation cancelled.\n");
                    else
                        composerView.showError("\nThe edition's year did not match the one you want to delete.");
                }else {
                    composerView.showError("\nThe trial cannot be deleted because it is used in an edition.");
                }
            }
        }
        composerView.showMessage("\nRedirecting to previous menu...\n");
        manageTrials();
    }

    /**
     * Shows all trials.
     */
    private void showAllTrials(){
        for(int i = 0; i < trialManager.getNumberOfTrials(); i++){
            composerView.listTrials(i + 1, trialManager.getTrial(i).getTrialName());
        }
        composerView.showMessage("\n");
    }

    /**
     * Starts the editions management mode of the composer view.
     */
    private void manageEditions(){
        if (trialManager.getNumberOfTrials() == 0) {
            composerView.showError("\nThere are no trials available.");
            managementMode();
        } else {
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
    }

    /**
     * Creates an edition.
     */
    private void createEdition(){
        int year, numberOfPlayers = -1, numberOfTrials = 1;
        boolean errorDisplay = false;

        year = composerView.readEditionYear();
        if (editionManager.checkUniqueYear(year)){
            composerView.showError("\nThis edition already exists.");
            errorDisplay = true;
        }else if (editionManager.checkValidYear(year)){
            composerView.showError("\nThe year of the edition must equal or greater than the current year (2022).");
            errorDisplay = true;
        }

        if(!errorDisplay) {
            numberOfPlayers = composerView.readEditionPlayer();
            if(editionManager.checkPlayersRange(numberOfPlayers)){
                composerView.showError("\nThe number of players must be between 1 and 5.");
                errorDisplay = true;
            }
        }

        if (!errorDisplay) {
            numberOfTrials = composerView.readEditionTrials();
            if(!editionManager.checkTrialsRange(numberOfTrials)){
                composerView.showError("\nThe number of trials must be between 3 and 12.");
                errorDisplay = true;
            }
        }

        if(!errorDisplay) {
            editionManager.addEdition(year, numberOfPlayers, numberOfTrials);
            composerView.showMessage("\n\t--- Trials ---\n");
            showAllTrials();
            composerView.showMessage("\n");
            int trialIndex;
            for (int j = 0; j < numberOfTrials; j++) {
                trialIndex = composerView.pickTrial(trialManager.getNumberOfTrials(), j + 1, numberOfTrials) - 1;
                editionManager.addTrialToEdition(trialManager.getTrial(trialIndex).getTrialName(), j);
            }
            composerView.createSuccess("edition");
        }

        composerView.showMessage("\nRedirecting to previous menu...\n");
        manageEditions();
    }

    /**
     * Lists the editions.
     */
    private void listEditions(){
        boolean errorDisplay = false;
        int editionIndex, k;

        if (editionManager.getNumberOfEditions() == 0) {
            composerView.showError("\nThere are no editions to display.");
        } else {
            composerView.showList("editions");
            editionIndex = showAllEditions();
            if(editionIndex < 0 || editionIndex > editionManager.getNumberOfEditions()){
                composerView.showError("\nThe edition index selected must be between 1 and " + (editionManager.getNumberOfEditions() + 1) + ".");
                errorDisplay = true;
            }
            if(editionIndex != editionManager.getNumberOfEditions() && !errorDisplay) {
                composerView.showEdition(editionManager.getEditionByIndex(editionIndex).getYear(), editionManager.getEditionByIndex(editionIndex).getNumberOfPlayers());
                k = 1;
                for(String trialName : editionManager.getEditionByIndex(editionIndex).getTrials()){
                    composerView.listEditionTrials(k, trialName, trialManager.getTrialTypeByName(trialName));
                    k++;
                }
            }
        }

        composerView.showMessage("\nRedirecting to previous menu...\n");
        manageEditions();
    }

    /**
     * Duplicates an edition by copyings its trials into a new edition.
     */
    private void duplicateEdition(){
        int editionIndex = -1, year = -1, numberOfPlayers = -1;
        boolean errorDisplay = false;
        if (editionManager.getNumberOfEditions() == 0) {
            composerView.showError("\nThere are no editions to duplicate.");
        } else {
            composerView.showMessage("\nWhich edition do you want to clone?\n\n");

            editionIndex = showAllEditions();
            if(editionIndex < 0 || editionIndex > editionManager.getNumberOfEditions()){
                composerView.showError("\nThe edition index selected must be between 1 and " + (editionManager.getNumberOfEditions() + 1) + ".");
                errorDisplay = true;
            }
            if(editionIndex != editionManager.getNumberOfEditions() && !errorDisplay) {
                composerView.showMessage("\n");
                year = composerView.readNewEditionYear();
                if (editionManager.checkUniqueYear(year)){
                    composerView.showError("\nThis edition already exists.");
                    errorDisplay = true;
                }else if (editionManager.checkValidYear(year)){
                    composerView.showError("\nThe year of the edition must equal or greater than the current year (2022).");
                    errorDisplay = true;
                }

                if(!errorDisplay) {
                    numberOfPlayers = composerView.readNewEditionPlayer();
                    if(editionManager.checkPlayersRange(numberOfPlayers)){
                        composerView.showError("\nThe number of players must be between 1 and 5.");
                        errorDisplay = true;
                    }
                }
            }
        }
        if(!errorDisplay){
            editionManager.duplicateEditions(year, numberOfPlayers, editionIndex);
            composerView.duplicateEditionSuccess();
        }
        composerView.showMessage("\nRedirecting to previous menu...\n");
        manageEditions();
    }

    /**
     * Deletes an edition.
     */
    private void deleteEdition(){
        boolean errorDisplay = false;
        String deletionConfirmation;
        int editionIndex;

        if (editionManager.getNumberOfEditions() == 0) {
            composerView.showError("\nThere are no editions to delete.");
        } else {
            composerView.showDelete("edition");
            editionIndex = showAllEditions();
            if(editionIndex < 0 || editionIndex > editionManager.getNumberOfEditions() ){
                composerView.showError("\nThe edition index selected must be between 1 and " + (editionManager.getNumberOfEditions() + 1) + ".");
                errorDisplay = true;
            }
            if(editionIndex != editionManager.getNumberOfEditions() && !errorDisplay) {
                deletionConfirmation = composerView.showDeletionConfirmation("edition's year");
                if(String.valueOf(editionManager.getEditionByIndex(editionIndex).getYear()).equals(deletionConfirmation)) {
                    editionManager.removeEdition(editionIndex);
                    try {
                        editionManager.deleteStoredState(editionIndex == 2022);
                    } catch (IOException e) {
                        composerView.showError("\nError deleting the stored state of the selected edition.\n");
                    }
                    composerView.deleteSuccess("edition");
                }else if(deletionConfirmation.equals("cancel"))
                    composerView.showMessage("\nOperation cancelled.\n");
                else
                    composerView.showError("\nThe edition's year did not match the one you want to delete.");
            }
        }
        composerView.showMessage("\nRedirecting to previous menu...\n");
        manageEditions();
    }

    /**
     * Shows the edition's menu.
     * @return Index of the edition selected.
     */
    private int showAllEditions(){
        for(int i = 0; i < editionManager.getNumberOfEditions(); i++){
            composerView.listEditions(i + 1, editionManager.getEditionByIndex(i).getYear());
        }
        composerView.showBack(editionManager.getNumberOfEditions() + 1);
        return composerView.getIndexInput();
    }

    /**
     * Shows the main menu.
     */
    private void exitProgram(){
        try {
            trialManager.writeTrials();
            editionManager.writeEditions();
        }catch(IOException e){
            composerView.showError("\nError writing data to file.");
        }
        composerView.exitProgram();
    }
}
