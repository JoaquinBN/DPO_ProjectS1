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

    public String readAccept() {
        return checkInputMismatchException("Enter the acceptance probability: ", "\nThe acceptance probability must be a number between 0 and 100. Please try again:\n");
    }

    public String readRevision() {
        return checkInputMismatchException("Enter the revision probability: ", "\nThe revision probability must be a number between 0 and 100. Please try again:\n");
    }

    public String readReject() {
        return checkInputMismatchException("Enter the rejection probability: ", "\nThe rejection probability must be a number between 0 and 100. Please try again:\n");
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

    public int showBackAndOption(int index) {
        System.out.print("\n\t" + index + ") Back\n");
        System.out.print("\nEnter an option: ");
        return Integer.parseInt(sc.nextLine()) - 1;
    }

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
        return checkFormatException("Enter the edition's year: ", "\nThe edition's year must be a number. Please try again:\n");
    }

    public int readEditionPlayer() {
        return checkFormatException("Enter the initial number of players: ", "\nThe number of players must be an integer. Please try again:\n");
    }

    public int readEditionTrials() {
        return checkFormatException("\nEnter the number of trials: ", "\nThe number of trials must be an integer. Please try again:\n");
    }

    public int pickTrial(int totalTrials, int index){
        return checkFormatException("Pick a trial (" + index + "/" + totalTrials + "): ","\nThe number of the trial must be an integer. Please try again:\n");
    }

    public int checkFormatException(String message, String errorMessage) {
        while(true){
            try{
                System.out.print(message);
                return sc.nextInt();
            } catch (NumberFormatException exception) {
                System.out.print(errorMessage);
            }finally {
                sc.nextLine();
            }
        }
    }

    public String checkInputMismatchException(String message, String errorMessage){
        while(true){
            try{
                System.out.print(message);
                return String.valueOf(sc.nextInt());
            } catch (InputMismatchException exception) {
                System.out.print(errorMessage);
            }finally {
                sc.nextLine();
            }
        }
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
        return checkFormatException("Enter the new edition's year: ", "\nThe edition's year must be an integer. Please try again:\n");
    }

    public int readNewEditionPlayer() {
        return checkFormatException("Enter the new edition's initial number of players: ", "\nThe number of players must be an integer. Please try again:\n");
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

    public int showTrialType() {
        System.out.println("\n\t--- Trial types ---");
        System.out.println("\n\t1) Paper publication");
        return checkFormatException("\nEnter the trial's type: ", "\nThe trial's type must be an integer. Please try again:\n");
    }
}
