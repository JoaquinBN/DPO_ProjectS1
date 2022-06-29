package PresentationLayer.Views;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ComposerView {
    Scanner sc;
    public ComposerView() {
        sc = new Scanner(System.in);
    }

    public String managementMenu() {
        System.out.println("\n\t1) Manage Trial");
        System.out.println("\t2) Manage Editions");
        System.out.println("\n\t3) Exit");
        System.out.print("\nEnter an option: ");
        return sc.nextLine();
    }

    public String manageTrialsMenu() {
        System.out.println("\n\ta) Create Trial");
        System.out.println("\tb) List Trials");
        System.out.println("\tc) Delete Trial");
        System.out.println("\n\td) Back");
        System.out.print("\nEnter an option: ");
        return sc.nextLine();
    }

    public String readTrialName() {
        System.out.print("\nEnter the trial's name: ");
        return sc.nextLine();
    }

    public String readPaperName() {
        System.out.print("Enter the journal's name: ");
        return sc.nextLine();
    }

    public String readQuartile() {
        System.out.print("Enter the journal's quartile: ");
        return sc.nextLine();
    }

    public String readProbability(String probability) {
        return String.valueOf(checkForExceptions("Enter the "+ probability + " probability: ", "\nThe "+ probability + " probability must be an integer between 0 and 100. Please try again:\n"));
    }

    public void createTrialSuccess() {
        System.out.println("\nThe trial was created successfully!");
    }

    public void deleteTrialSuccess() {
        System.out.println("\nThe trial was deleted successfully!");
    }

    public void listTrials(int i, String trialName) {
        System.out.print("\n\t" + i + ") " + trialName);
    }

    public void showBack(int index) {
        System.out.print("\n\t" + index + ") Back\n");
    }

    public int getIndexInput(){ return checkForExceptions("\nEnter an option: ", "\nThe index must be an integer. \n") - 1;}

    public String manageEditionsMenu() {
        System.out.println("\n\ta) Create Edition");
        System.out.println("\tb) List Editions");
        System.out.println("\tc) Duplicate Edition");
        System.out.println("\td) Delete Edition");
        System.out.println("\n\te) Back");
        System.out.print("\nEnter an option: ");
        return sc.nextLine();
    }

    public int readEditionYear() {
        return checkForExceptions("Enter the edition's year: ", "\nThe edition's year must be a number.\n");
    }

    public int readEditionPlayer() {
        return checkForExceptions("Enter the initial number of players: ", "\nThe number of players must be an integer.\n");
    }

    public int readEditionTrials() {
        return checkForExceptions("Enter the number of trials: ", "\nThe number of trials must be an integer.\n");
    }

    public int pickTrial(int totalTrials, int index){
        int trialIndex;
        trialIndex = checkForExceptions("Pick a trial (" + index + "/" + totalTrials + "): ","\nThe number of the trial must be an integer.\n");
        if(trialIndex > 0 && trialIndex <= totalTrials){
            return trialIndex;
        }
        else{
            System.out.println("\nThe number of the trial must be between 1 and " + totalTrials + ".\n");
            return pickTrial(totalTrials, index);
        }
    }

    public int checkForExceptions(String message, String errorMessage) {
        try{
            System.out.print(message);
            return sc.nextInt();
        } catch (NumberFormatException | InputMismatchException  exception) {
            System.out.print(errorMessage);
        }finally {
            sc.nextLine();
        }
        return -1;
    }

    public void createEditionSuccess() {
        System.out.println("\nThe edition was created successfully!");
    }

    public void showEdition(int year, int numberOfPlayers) {
        System.out.println("\nYear: " + year);
        System.out.println("Players: " + numberOfPlayers);
        System.out.println("Trials:");
    }
    public void listEditionTrials(int k, String trialName, String typeOfTrial) {
        System.out.print("\t" + k + "- " + trialName + " (" + typeOfTrial + ")\n");
    }

    public int readNewEditionYear() {
        return checkForExceptions("Enter the new edition's year: ", "\nThe edition's year must be an integer. Please try again:\n");
    }

    public int readNewEditionPlayer() {
        return checkForExceptions("Enter the new edition's initial number of players: ", "\nThe number of players must be an integer. Please try again:\n");
    }

    public void duplicateEditionSuccess() {
        System.out.println("\nThe edition was cloned successfully!");
    }

    public void deleteEditionSuccess() {
        System.out.println("\nThe edition was deleted successfully!");
    }

    public void listEditions(int i, int year) {
        System.out.println("\t" + i + ") The Trials " + year);
    }

    public void exitProgram() {
        System.out.println("\n\nShutting down...\n");
    }

    public void showError(String error) {
        System.out.println(error);
    }

    public void showMessage(String message) {
        System.out.print(message);
    }

    public String getTrialTypeInput() {
        return String.valueOf(checkForExceptions("Enter the trial's type: ", "\nThe trial's type must be an integer. Please try again:\n"));
    }

    public void showTrialTypes() {
        System.out.println("\n\t--- Trial types ---");
        System.out.println("\n\t1) Paper publication\n");
    }
}
