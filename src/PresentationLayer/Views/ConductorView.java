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

    public String showPlayersInput(int index, int totalPlayers) {
        System.out.print("Enter the player's name (" + index + "/" + totalPlayers + "): ");
        return sc.nextLine();
    }

    public boolean showContinueMessage(){
        String answer;
        while(true){
            System.out.print("\nContinue the execution? [yes/no]: ");
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


}
