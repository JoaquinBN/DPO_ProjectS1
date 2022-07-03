package PresentationLayer.Views;

import java.util.Scanner;

/**
 * This class displays the menus and messages for the main menu.
 */
public class MainMenuView {
    private final Scanner sc;

    /**
     * Constructor for the MainMenuView class.
     */
    public MainMenuView() {
        sc = new Scanner(System.in);
    }

    /**
     * Display main menu
     * @return The menu choice.
     */
    public String mainMenuDisplay(){
        String option;
        System.out.println("""
                 _____ _            _____      _       _
                /__   \\ |__   ___  /__   \\_ __(_) __ _| |___
                  / /\\/ '_ \\ / _ \\   / /\\/ '__| |/ _` | / __|
                 / /  | | | |  __/  / /  | |  | | (_| | \\__ \\
                 \\/   |_| |_|\\___|  \\/   |_|  |_|\\__,_|_|___/
                
                Welcome to The Trials. Who are you?
                
                    A) The Composer
                    B) This year’s Conductor
                """);
        System.out.print("Enter a role: ");
        option = sc.next();

        return option;
    }

    /**
     * Display error message.
     * @param error The error message.
     */
    public void showError(String error) {
        System.out.println(error);
    }
}
