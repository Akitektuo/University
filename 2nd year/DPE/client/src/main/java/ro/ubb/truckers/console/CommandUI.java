package ro.ubb.truckers.console;

import ro.ubb.truckers.console.command.*;
import ro.ubb.truckers.domain.TruckersException;

import java.util.*;

public class CommandUI {
    private final HashMap<String, BaseCommand> commandsWithKey = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public CommandUI() {
        setupCommands();
    }

    private void setupCommands() {
        List<BaseCommand> commands = new ArrayList<>();

        commands.add(new HelpCommand(commands));
        commands.add(new AddCommand());
        commands.add(new UpdateCommand());
        commands.add(new DeleteCommand(scanner));
        commands.add(new ShowAllCommand());
        commands.add(new FilterCommand());
        commands.add(new ReportCommand());
        commands.add(new ExitCommand());

        commands.forEach(command -> commandsWithKey.put(command.getKeyword(), command));
    }

    public void run() {
        System.out.println("For all commands type 'help' and press enter.");
        boolean keepRunning = true;

        while (keepRunning) {
            try {
                keepRunning = interpretCommand(readCommand());
            } catch (TruckersException exception) {
                System.out.printf("Error: %s!%n", exception.getMessage());
            }
        }

        System.exit(0);
    }

    private String readCommand() {
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    private boolean interpretCommand(String stringCommand) {
        var commandKey = commandsWithKey.keySet()
                .stream()
                .filter(stringCommand::startsWith)
                .findFirst()
                .orElseThrow(() -> new TruckersException("Invalid command"));

        var arguments = getArguments(stringCommand.substring(commandKey.length()).trim());

        return commandsWithKey.get(commandKey).run(arguments);
    }

    private List<String> getArguments(String restOfCommand) {
        if (restOfCommand.isBlank()) {
            return new ArrayList<>();
        }

        return Arrays.asList(restOfCommand.split(" "));
    }
}
