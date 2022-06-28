package View;

import java.util.*;

public class mainMenuView {
    private Scanner input;

    public static void main(String[] args){
        new mainMenuView();
    }

    private mainMenuView() {
        mainMenuDisplay();
    }

    private void mainMenuDisplay(){
       input = new Scanner(System.in);
       char option, c2, c3;
       int c1;

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
        option = input.next().charAt(0);
        if (option == 1) {
            composerMenu();
        } else if (option == 2) {
            //conductorMenu();
        } else {
            System.out.println("Invalid option");
        }
    }

   private int composerMenu() {
       input = new Scanner(System.in);
       int option;
       int mt1, mt2;

       System.out.println("\nEntering management mode...\n" +
               "\n\t1) Manage Trials\n" +
               "\t2) Manage Editions\n" +
               "\n\t3) Exit\n");

       System.out.print("Enter an option: ");
       option = input.nextInt();

       switch(option) {
           case 1:
               mt1 = manageTrialsMenu();

               switch (mt1) {
                   case 'a':
                       manageTrialsViews.createTrial();
                       break;
                   case 'b':
                       manageTrialsViews.listTrial();
                       break;
                   case 'c':
                       manageTrialsViews.trialDeletion();
                       break;
                   case 'd':
                       break;
                   default:
                       break;
               }


               break;
           case 2:
               manageEditionsMenu();
               break;
           case 3:
               break;
           default:
               System.out.println("Invalid option");
               break;
       }
       return option;
   }

   private char manageTrialsMenu() {
       input = new Scanner(System.in);
       char option;

       System.out.println("\n\ta) Create Trial\n" +
               "\tb) List Trials\n" +
               "\tc) Delete Trial\n" +
               "\n\td) Back\n");

       System.out.print("Enter an option: ");
       option = input.next().charAt(0);
       return option;
   }

   private char manageEditionsMenu() {
       input = new Scanner(System.in);
       char option;

       System.out.println("\n\ta) Create Edition\n" +
               "\tb) List Editions\n" +
               "\tc) Duplicate Edition\n" +
               "\td) Delete Edition\n" +
               "\n\te) Back\n");

       System.out.print("Enter an option: ");
       option = input.next().charAt(0);

       return option;
   }


   }
