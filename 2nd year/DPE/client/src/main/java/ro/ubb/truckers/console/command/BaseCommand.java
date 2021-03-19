package ro.ubb.truckers.console.command;

import java.util.List;

public interface BaseCommand {
    /**
     * Returns the keyword of the command.
     *
     * @return a {@code String} - the keyword that identifies the command
     */
    String getKeyword();

    /**
     * @return a {@code List} - consists of all the patterns that the command has
     */
    List<String> getPatterns();

    /**
     * Returns a short description of the command.
     *
     * @return a {@code String} - a short description of the command
     */
    String getDescription();

    /**
     * Executes the command.
     *
     * @param arguments must not be null.
     *
     * @return a {@code boolean} - represents if the program should continue or not
     */
    boolean run(List<String> arguments);
}
