package ro.ubb.truckers.console.command;

import java.util.List;

public class ExitCommand implements BaseCommand {

    @Override
    public String getKeyword() {
        return "exit";
    }

    @Override
    public List<String> getPatterns() {
        return List.of("exit");
    }

    @Override
    public String getDescription() {
        return "Stops the execution of the program.";
    }

    @Override
    public boolean run(List<String> arguments) {
        return false;
    }
}
