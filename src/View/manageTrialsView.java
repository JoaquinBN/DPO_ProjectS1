package View;

import java.util.Scanner;

public class manageTrialsView {
    
    private Scanner input;

    private void createTrialView () {
        input = new Scanner(System.in);
        String name, description, date, location, time, duration, composer, conductor;
        int edition;

        System.out.println("Enter the name of the trial:");
        name = input.nextLine();
        System.out.println("Enter the description of the trial:");
        description = input.nextLine();
        System.out.println("Enter the date of the trial:");
        date = input.nextLine();
        System.out.println("Enter the location of the trial:");
        location = input.nextLine();
        System.out.println("Enter the time of the trial:");
        time = input.nextLine();
        System.out.println("Enter the duration of the trial:");
        duration = input.nextLine();
        System.out.println("Enter the composer of the trial:");
        composer = input.nextLine();
        System.out.println("Enter the conductor of the trial:");
        conductor = input.nextLine();
        System.out.println("Enter the edition of the trial:");
        edition = input.nextInt();

        System.out.println("\nTrial created successfully!\n");
    }

}
