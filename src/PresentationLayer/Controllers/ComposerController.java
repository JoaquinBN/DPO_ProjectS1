package PresentationLayer.Controllers;

import BusinessLayer.Edition.EditionManager;
import BusinessLayer.Trials.PaperSubmission;
import BusinessLayer.Trials.TrialManager;
import BusinessLayer.Trials.Trials;
import PersistenceLayer.EditionFileManager;
import PersistenceLayer.TrialsFileManager;
import PresentationLayer.Views.ComposerView;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class ComposerController {
    private final EditionManager editionManager;
    private final TrialManager trialManager;
    private final ComposerView composerView;
    private boolean readFiles;

    public ComposerController(EditionManager editionManager, TrialManager trialManager, ComposerView composerView) {
        this.editionManager = editionManager;
        this.trialManager = trialManager;
        this.composerView = composerView;
        readFiles = true;
    }

    public void managementMode(){
        String option;
        if(readFiles){
            try {
                editionManager.readEditions();
                trialManager.readTrials();
                readFiles = false;
            } catch (IOException | CsvException e) {
                composerView.showError("Error reading trials file");
            }
        }

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

    private String getTrialAttribute(int attributeType){
        String probability, attribute;

        switch(attributeType){
            case 4 -> probability = "acceptance";
            case 5 -> probability = "revision";
            case 6 -> probability = "rejection";
            default -> probability = "";
        }

        switch(attributeType){
            case 0:
                attribute = composerView.getTrialTypeInput();
                if(!attribute.equals("1") && !attribute.equals("-1")) {
                    composerView.showError("\nThe trial has to be an existing type.");
                    attribute = "";
                }
                break;
            case 1:
                attribute = composerView.readTrialName();
                if(!trialManager.checkUniqueName(attribute)){
                    composerView.showError("\nTrial name already exists.");
                    attribute = "";
                }else if(!trialManager.checkEmptyString(attribute)){
                    composerView.showError("\nTrial name cannot be empty.");
                    attribute = "";
                }
                break;
            case 2:
                attribute = composerView.readPaperName();
                if(!trialManager.checkEmptyString(attribute)){
                    composerView.showError("\nName of the publication cannot be empty.");
                    attribute = "";
                }
                break;
            case 3:
                attribute = composerView.readQuartile();
                if(!trialManager.checkQuartile(attribute)){
                    composerView.showError("\nWrong quartile.");
                    attribute = "";
                }
                break;
            case 4, 5, 6:
                attribute = composerView.readProbability(probability);
                if(!trialManager.checkProbability(Integer.parseInt(attribute)) && !attribute.equals("-1")){
                    composerView.showError("\nThe " + probability + " probability must be between 0 and 100.");
                    attribute = "";
                }
                break;
            default:
                attribute = "";
                break;
        }
        return attribute;
    }

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
            composerView.createTrialSuccess();
        }
        composerView.showMessage("\nRedirecting to previous menu...\n");
        manageTrials();
    }

    private void listTrials(){
        int trialIndex;
        boolean errorDisplay;
        showAllTrials();
        composerView.showBack(trialManager.getNumberOfTrials() + 1);
        errorDisplay = false;
        do {
            if(errorDisplay){
                composerView.showError("\nThe index entered must be between 1 and " + trialManager.getNumberOfTrials() + ". Please try again:");
            }
            trialIndex = composerView.getIndexInput(trialManager.getNumberOfTrials() + 1);
            errorDisplay = true;
        } while (trialIndex < 0 || trialIndex > trialManager.getNumberOfTrials());

        if(trialIndex != trialManager.getNumberOfTrials()){
            if(trialManager.getTrial(trialIndex) instanceof PaperSubmission){
                composerView.showMessage(trialManager.getTrial(trialIndex).displayTrialInfo());
            }
        }
        manageTrials();
    }

    private void deleteTrial(){
        int trialIndex;
        boolean errorDisplay;
        showAllTrials();
        composerView.showBack(trialManager.getNumberOfTrials() + 1);

        errorDisplay = false;
        do {
            if(errorDisplay){
                composerView.showError("\nThe index entered must be between 1 and " + trialManager.getNumberOfTrials() + ". Please try again:");
            }
            trialIndex = composerView.getIndexInput(trialManager.getNumberOfTrials() + 1);
            errorDisplay = true;
        } while (trialIndex < 0 || trialIndex > trialManager.getNumberOfTrials());

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
        composerView.showMessage("\n");
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
            editionManager.addTrialToEdition(trialManager.getTrial(trialIndex).getTrialName(), j);
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
            for(String trialName : editionManager.getEditionByIndex(editionIndex).getTrials()){
                composerView.listEditionTrials(k, trialName, trialManager.getTrialTypeByName(trialName));
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
        composerView.showBack(trialManager.getNumberOfTrials() + 1);
        return composerView.getIndexInput(trialManager.getNumberOfTrials() + 1);
    }

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
