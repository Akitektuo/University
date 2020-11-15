package view;

import container.Dictionary;
import container.DictionaryInterface;
import view.command.BaseCommand;

import java.util.Scanner;

public class TextMenuView {
    private final DictionaryInterface<String, BaseCommand> commands = new Dictionary<>();
    private final Scanner input = new Scanner(System.in);

    public void addCommand(BaseCommand command) {
        commands.set(command.getKey(), command);
    }

    public void show() {
        while (true) {
            printMenu();
            var command = commands.get(readOption());

            if (command == null) {
                System.out.println("Invalid option");
                continue;
            }

            command.execute();
        }
    }

    private void printMenu() {
        System.out.println("Choose an option from below:");
        commands.getValues().forEach(command -> System.out.printf("\n%s. %s\n", command.getKey(), command.getDescription()));
    }

    private String readOption() {
        System.out.print("> ");
        return input.nextLine();
    }
}
