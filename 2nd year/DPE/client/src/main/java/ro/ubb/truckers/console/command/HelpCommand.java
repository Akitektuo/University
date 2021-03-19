package ro.ubb.truckers.console.command;

import ro.ubb.truckers.domain.TruckersException;

import java.util.List;
import java.util.function.Predicate;

public class HelpCommand implements BaseCommand {
    private final List<BaseCommand> commands;

    public HelpCommand(List<BaseCommand> commands) {
        this.commands = commands;
    }

    @Override
    public String getKeyword() {
        return "help";
    }

    @Override
    public List<String> getPatterns() {
        return List.of("help", "help <command keyword>");
    }

    @Override
    public String getDescription() {
        return "Describes all commands or the specified command.";
    }

    @Override
    public boolean run(List<String> arguments) {
        switch (arguments.size()) {
            case 0 -> handleHelp();
            case 1 -> handleHelpForCommand(arguments.get(0));
            default -> throw new TruckersException("Invalid number of arguments for command help");
        }

        return true;
    }

    private void handleHelp() {
        commands.forEach(this::printCommandDetails);
    }

    private void handleHelpForCommand(String forCommand) {
        Predicate<BaseCommand> filterCommands = command -> command.getKeyword().equalsIgnoreCase(forCommand);

        if (commands.stream().noneMatch(filterCommands)) {
            throw new TruckersException("There is no command '%s' defined", forCommand);
        }

        commands.stream()
                .filter(filterCommands)
                .forEach(this::printCommandDetails);
    }

    private void printCommandDetails(BaseCommand command) {
        System.out.printf("Command '%s' - %s%n", command.getKeyword(), command.getDescription());
        command.getPatterns().forEach(pattern -> System.out.printf("\t%s%n", pattern));
    }
}
