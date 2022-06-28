package PresentationLayer.Views;

import java.util.Scanner;

public class ComposerView {
    Scanner sc;
    public ComposerView() {
        sc = new Scanner(System.in);
    }

    public String managementMenu() {
        System.out.println("\t1) Manage Trial");
        System.out.println("\t2) Manage Editions");
        System.out.println("\n\t3) Exit");
        System.out.print("\nEnter an option: ");
        return sc.nextLine();
    }

    public String manageTrialsMenu() {
        System.out.println("\ta) Create Trial");
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

    public int readAccept() {
        System.out.print("Enter the acceptance probability: ");
        return Integer.parseInt(sc.nextLine());
    }

    public int readRevision() {
        System.out.print("Enter the revision probability: ");
        return Integer.parseInt(sc.nextLine());
    }

    public int readReject() {
        System.out.print("Enter the rejection probability: ");
        return Integer.parseInt(sc.nextLine());
    }

    public void createTrialSuccess() {
        System.out.println("\nThe trial was created successfully!\n");
    }

    public void listPaperSubmission(String trialName, String trialType, String paperName, String quartile, int acceptProbability, int revisionProbability, int rejectProbability) {
        System.out.println("\nTrial: " + trialName + " (" + trialType + ")");
        System.out.println("Journal: " + paperName + " (" + quartile + ")");
        System.out.println("Chances: " + acceptProbability + "% acceptance, " + revisionProbability + "% revision, " + rejectProbability + "% rejection");
    }

    public void deleteTrialSuccess() {
        System.out.println("\nThe trial was deleted successfully!\n");
    }

    public void listTrials(int i, String trialName) {
        System.out.println("\t" + i + ") " + trialName);
    }

    public int showBackAndOption(int index) {
        System.out.println("\n\t" + index + ") Back\n");
        System.out.print("\nEnter an option: ");
        return Integer.parseInt(sc.nextLine()) - 1;
    }

    public String manageEditionsMenu() {
        System.out.println("\ta) Create Edition");
        System.out.println("\tb) List Editions");
        System.out.println("\tc) Duplicate Edition");
        System.out.println("\td) Delete Edition");
        System.out.println("\n\te) Back");
        System.out.print("\nEnter an option: ");
        return sc.nextLine();
    }

    public int readEditionYear() {
        System.out.print("\nEnter the edition's year: ");
        return Integer.parseInt(sc.nextLine());
    }

    public int readEditionPlayer() {
        System.out.print("Enter the initial number of players: ");
        return Integer.parseInt(sc.nextLine());
    }

    public int readEditionTrials() {
        System.out.print("Enter the number of trials: ");
        return Integer.parseInt(sc.nextLine());
    }

    public int pickTrial(int totalTrials, int index){
        System.out.print("Pick a trial (" + index + "/" + totalTrials + "): ");
        return Integer.parseInt(sc.nextLine());
    }

    public void createEditionSuccess() {
        System.out.println("\nThe edition was created successfully!\n");
    }

    public void showEdition(int year, int numberOfPlayers) {
        System.out.println("\nYear: " + year);
        System.out.println("Players: " + numberOfPlayers);
        System.out.println("Trials:");
    }
    public void listEditionTrials(int k, String trialName, String typeOfTrial) {
        System.out.println("\n\t" + k + "- " + trialName + " (" + typeOfTrial + ")");
    }

    public int readNewEditionYear() {
        System.out.print("\nEnter the new edition's year: ");
        return Integer.parseInt(sc.nextLine());
    }

    public int readNewEditionPlayer() {
        System.out.print("Enter the new edition's initial number of players: ");
        return Integer.parseInt(sc.nextLine());
    }

    public void duplicateEditionSuccess() {
        System.out.println("\nThe edition was cloned successfully!\n");
    }

    public void deleteEditionSuccess() {
        System.out.println("\nThe edition was deleted successfully!\n");
    }

    public void listEditions(int i, int year) {
        System.out.print("\n\t" + i + ") The Trials " + year);
    }

    public void exitProgram() {
        System.out.println("\n\nShutting down...\n");
    }

    public void showError(String error) {
        System.out.println("\n" + error + "\n");
    }
}
