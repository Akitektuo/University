package view;

import controller.Controller;
import repository.RepositoryInterface;

import java.util.Scanner;

import static utils.PredefinedProgramStates.*;

public class MainView {
    private final Controller controller;
    private final Scanner input = new Scanner(System.in);

    public MainView(RepositoryInterface repository) {
        controller = new Controller(repository, true);
    }

    public void start() {
        System.out.println("\nWelcome to TOY LANGUAGE INTERPRETER! (version 132.202010262100-beta)");

        while (true) {
            printMenu();
            var option = readOption();

            try {
                controller.addProgramState(switch (option) {
                    case 1 -> PROGRAM_1;
                    case 2 -> PROGRAM_2;
                    case 3 -> PROGRAM_3;
                    case 4 -> PROGRAM_4;
                    default -> throw new IllegalStateException("Unexpected value: " + option);
                });
            } catch (Exception ignore) {
                break;
            }

            try {
                System.out.println("Final output: " + controller.executeAllSteps());
            } catch (Exception exception) {
                System.out.printf("Error: %s%n", exception.getMessage());
            }

            System.out.print("Press enter to continue...");
            input.nextLine();
            input.nextLine();
            System.out.println();
        }

        System.out.println("Closing program...");
    }

    private void printMenu() {
        System.out.println("Choose an option from below:");
        System.out.println("0. Exit");

        System.out.println("\n1. Execute:");
        System.out.println(VISUAL_PROGRAM_1);

        System.out.println("\n2. Execute:");
        System.out.println(VISUAL_PROGRAM_2);

        System.out.println("\n3. Execute:");
        System.out.println(VISUAL_PROGRAM_3);

        System.out.println("\n4. Execute:");
        System.out.println(VISUAL_PROGRAM_4);
    }

    private int readOption() {
        System.out.print("> ");
        return input.nextInt();
    }
}
