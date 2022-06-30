package PresentationLayer.Views;

import java.util.Scanner;

public class ConductorView {
    private Scanner sc;

    public ConductorView() {
        sc = new Scanner(System.in);
    }

    public void showError(String error) {
        System.out.println(error);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public String askForPlayerName(int index, int totalPlayers) {
        System.out.print("Enter the player's name (" + index + "/" + totalPlayers + "): ");
        return sc.nextLine();
    }

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
