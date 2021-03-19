package ro.ubb.truckers.console.command;

import ro.ubb.truckers.controller.Controller;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.util.ServiceProvider;
import ro.ubb.truckers.util.StringValidator;

import java.util.List;
import java.util.Scanner;

public class DeleteCommand implements BaseCommand {
    private final Controller controller = ServiceProvider.inject(Controller.class);
    private final Scanner scanner;

    public DeleteCommand(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Returns the keyword of the command.
     *
     * @return a {@code String} - the keyword that identifies the command
     */
    @Override
    public String getKeyword() {
        return "delete";
    }

    /**
     * @return a {@code List} - consists of all the patterns that the command has
     */
    @Override
    public List<String> getPatterns() {
        return List.of(
                "delete company <byId>",
                "delete delivery <byId>",
                "delete garage <byId>",
                "delete truck <byId>",
                "delete user <byId>");
    }

    /**
     * Returns a short description of the command.
     *
     * @return a {@code String} - a short description of the command
     */
    @Override
    public String getDescription() {
        return "Deletes the specified entity by the given id.";
    }

    /**
     * Executes the command.
     *
     * @param arguments must not be null.
     * @return a {@code boolean} - represents if the program should continue or not
     */
    @Override
    public boolean run(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 2);

        switch (arguments.get(0)) {
            case "company" -> handleDeleteCompany(arguments);
            case "delivery" -> handleDeleteDelivery(arguments);
            case "garage" -> handleDeleteGarage(arguments);
            case "truck" -> handleDeleteTruck(arguments);
            case "user" -> handleDeleteUser(arguments);
            default -> throw new TruckersException("There is no entity with the name of '%s'", arguments.get(0));
        }

        return true;
    }

    private void handleDeleteCompany(List<String> arguments) {
        var byId = StringValidator.convertToInt(arguments.get(1));

        if (shouldStop("company", byId)) {
            printOperationStopped();
            return;
        }

        controller.deleteCompany(byId);

        printOperationSuccessful();
    }

    private void handleDeleteDelivery(List<String> arguments) {
        var byId = StringValidator.convertToInt(arguments.get(1));

        if (shouldStop("delivery", byId)) {
            printOperationStopped();
            return;
        }

        controller.deleteDelivery(byId);

        printOperationSuccessful();
    }

    private void handleDeleteGarage(List<String> arguments) {
        var byId = StringValidator.convertToInt(arguments.get(1));

        if (shouldStop("garage", byId)) {
            printOperationStopped();
            return;
        }

        controller.deleteGarage(byId);

        printOperationSuccessful();
    }

    private void handleDeleteTruck(List<String> arguments) {
        var byId = StringValidator.convertToInt(arguments.get(1));

        if (shouldStop("truck", byId)) {
            printOperationStopped();
            return;
        }

        controller.deleteTruck(byId);

        printOperationSuccessful();
    }

    private void handleDeleteUser(List<String> arguments) {
        var byId = StringValidator.convertToInt(arguments.get(1));

        if (shouldStop("user", byId)) {
            printOperationStopped();
            return;
        }

        controller.deleteUser(byId);

        printOperationSuccessful();
    }

    private boolean shouldStop(String entityName, int id) {
        System.out.printf(
                "Are you sure you want to delete %s with id %s? All the connected entities will be deleted as well (y/N): ",
                entityName, id);
        var input = scanner.nextLine();

        return !input.equalsIgnoreCase("y");
    }

    private void printOperationSuccessful() {
        System.out.println("Deleted entity successfully!");
    }

    private void printOperationStopped() {
        System.out.println("No entity was deleted!");
    }
}
