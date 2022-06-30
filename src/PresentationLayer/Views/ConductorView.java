package PresentationLayer.Views;

import java.util.Scanner;

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

    /**
     * Show message of the end of the execution with player status
     * @param name The name of the player.
     * @param k The index of the player.
     * @param result The result of the player.
     * @param investigationPoints The number of investigation points.
     */
    public void displayPlayerCondition(String name, int k, int result, int investigationPoints) {
        System.out.print("\n\t" + name + " is submitting... ");
        for(int i = 0; i < k; i++){
            System.out.print("Revisions... ");
        }
        if(result < 0){
            System.out.print("Rejected. PI count: ");
        }else{
            System.out.print("Accepted! PI count: ");
        }
        System.out.print(investigationPoints);
        if(investigationPoints == 0){
            System.out.print(" - Disqualified!");
        }
    }
}
