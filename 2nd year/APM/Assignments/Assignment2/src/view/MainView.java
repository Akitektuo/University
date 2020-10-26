package view;

import controller.Controller;
import model.expression.ExpressionException;
import model.statement.StatementException;
import repository.Repository;
import utils.CodeFormatter;

import java.util.Scanner;

import static utils.PredefinedProgramStates.*;

public class MainView {
    private final Controller controller;
    private final Scanner input = new Scanner(System.in);

    public MainView(Repository repository) {
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
                    default -> throw new IllegalStateException("Unexpected value: " + option);
                });
            } catch (Exception ignore) {
                break;
            }

            try {
                System.out.println("Final output: " + controller.executeAllSteps());
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
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
    }

    private int readOption() {
        System.out.print("> ");
        return input.nextInt();
    }
}
