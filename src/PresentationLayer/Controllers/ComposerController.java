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
        boolean errorInput = false;

        if(trialManager.getNumberOfTrials() == 0){
            composerView.showError("\nThere are no trials to display.");
            composerView.showMessage("\nRedirecting to previous menu...\n");
        }else {
            showAllTrials();
            composerView.showBack(trialManager.getNumberOfTrials() + 1);

            trialIndex = composerView.getIndexInput();
            if (trialIndex < 0 || trialIndex > trialManager.getNumberOfTrials()) {
                composerView.showError("\nThe index entered must be an integer between 1 and " + trialManager.getNumberOfTrials() + ".");
                errorInput = true;
            }

            if (trialIndex != trialManager.getNumberOfTrials() && !errorInput)
                composerView.showMessage(trialManager.getTrial(trialIndex).displayTrialInfo());
        }
        manageTrials();
    }

    private void deleteTrial(){
        int trialIndex;
        boolean errorInput = false;

        if(trialManager.getNumberOfTrials() == 0) {
            composerView.showError("\nThere are no trials to delete.");
            composerView.showMessage("\nRedirecting to previous menu...\n");
        }else {
            showAllTrials();
            composerView.showBack(trialManager.getNumberOfTrials() + 1);

            trialIndex = composerView.getIndexInput();
            if (trialIndex < 0 || trialIndex > trialManager.getNumberOfTrials()) {
                composerView.showError("\nThe index entered must be between 1 and " + trialManager.getNumberOfTrials() + ".");
                errorInput = true;
            }

            if (trialIndex != trialManager.getNumberOfTrials() && !errorInput) {
                trialManager.removeTrial(trialIndex);
                composerView.deleteTrialSuccess();
            }
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

    private void createEdition(){
        int year, numberOfPlayers = -1, numberOfTrials = 1;
        boolean errorDisplay = false;

        year = composerView.readEditionYear();
        if (!editionManager.checkUniqueYear(year)){
            composerView.showError("\nThis edition already exists.");
            errorDisplay = true;
        }else if (!editionManager.checkValidYear(year)){
            composerView.showError("\nThe year of the edition must equal or greater than the current year (2022).");
            errorDisplay = true;
        }

        if(!errorDisplay) {
            numberOfPlayers = composerView.readEditionPlayer();
            if(!editionManager.checkPlayersRange(numberOfPlayers)){
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
            composerView.showMessage("\n\n");
            int trialIndex;
            for(int j = 0; j < numberOfTrials; j++){
                trialIndex = composerView.pickTrial(trialManager.getNumberOfTrials(), j + 1, numberOfTrials) - 1;
                editionManager.addTrialToEdition(trialManager.getTrial(trialIndex).getTrialName(), j);
            }
            composerView.createEditionSuccess();
        } else {
            composerView.showMessage("\nRedirecting to previous menu...\n");
        }

        manageEditions();
    }

    private void listEditions(){
        if (editionManager.getNumberOfEditions() == 0) {
            composerView.showError("\nThere are no editions to display.");
            composerView.showMessage("\nRedirecting to previous menu...\n");
        } else {
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
        }
        manageEditions();
    }

    private void duplicateEdition(){
        if (editionManager.getNumberOfEditions() == 0) {
            composerView.showError("\nThere are no editions to duplicate.");
            composerView.showMessage("\nRedirecting to previous menu...\n");
        } else {
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
        }
        manageEditions();
    }

    private void deleteEdition(){
        if (editionManager.getNumberOfEditions() == 0) {
            composerView.showError("\nThere are no editions to delete.");
            composerView.showMessage("\nRedirecting to previous menu...\n");
        } else {
            int editionIndex;
            composerView.showMessage("\nWhich edition do you want to delete?\n\n");
            editionIndex = showAllEditions();
            if(editionIndex != editionManager.getNumberOfEditions()) {
                editionManager.removeEdition(editionIndex);
                composerView.deleteEditionSuccess();
            }
        }
        manageEditions();
    }

    private int showAllEditions(){
        for(int i = 0; i < editionManager.getNumberOfEditions(); i++){
            composerView.listEditions(i + 1, editionManager.getEditionByIndex(i).getYear());
        }
        composerView.showBack(trialManager.getNumberOfTrials() + 1);
        return composerView.getIndexInput();
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
