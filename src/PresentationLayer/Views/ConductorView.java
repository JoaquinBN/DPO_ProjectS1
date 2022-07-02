package PresentationLayer.Views;

import java.util.Scanner;

/**
 * This class displays the menus and messages for the conductor.
 */
public class ConductorView {
    private final Scanner sc;

    /**
     * Constructor for the ConductorView class.
     */
    public ConductorView() {
        sc = new Scanner(System.in);
    }

    /**
     * Show error message.
     * @param error The error message.
     */
    public void showError(String error) {
        System.out.println(error);
    }

    /**
     * Show message
     * @param message The message.
     */
    public void showMessage(String message) {
        System.out.print(message);
    }

    /**
     * Ask for player name.
     * @param index The index of the player.
     * @param totalPlayers The total number of players.
     * @return The name of the player.
     */
    public String askForPlayerName(int index, int totalPlayers) {
        System.out.print("Enter the player's name (" + index + "/" + totalPlayers + "): ");
        return sc.nextLine();
    }

    /**
     * Show continue message
     * @return The answer of the user.
     */
    public boolean showContinueMessage(){
        String answer;
        while(true){
            System.out.print("\n\nContinue the execution? [yes/no]: ");
            answer = sc.nextLine();
            if(answer.equals("yes")){
                return true;
            }else if(answer.equals("no")){
                return false;
            }else{
                System.out.print("\nInvalid answer. Please try again:");
            }
        }
    }

    public void displayIPCount(int investigationPoints) {
        System.out.print(" PI count: " + investigationPoints);
        if(investigationPoints == 0){
            System.out.print(" - Disqualified!");
        }
    }
}
