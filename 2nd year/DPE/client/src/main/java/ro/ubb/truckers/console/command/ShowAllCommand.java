package ro.ubb.truckers.console.command;

import ro.ubb.truckers.controller.Controller;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.util.ServiceProvider;
import ro.ubb.truckers.util.StringValidator;

import java.util.List;

public class ShowAllCommand implements BaseCommand {
    private final Controller controller = ServiceProvider.inject(Controller.class);

    @Override
    public String getKeyword() {
        return "showAll";
    }

    @Override
    public List<String> getPatterns() {
        return List.of("showAll companies", "showAll deliveries", "showAll garages", "showAll trucks", "showAll users");
    }

    @Override
    public String getDescription() {
        return "Displays all the entities of the provided type.";
    }

    @Override
    public boolean run(List<String> arguments) {
        StringValidator.requireArguments(arguments);

        switch (arguments.get(0)) {
            case "companies" -> handleShowAllCompanies();
            case "deliveries" -> handleShowAllDeliveries();
            case "garages" -> handleShowAllGarages();
            case "trucks" -> handleShowAllTrucks();
            case "users" -> handleShowAllUsers();
            default -> throw new TruckersException("There is no entity with the name of '%s'", arguments.get(0));
        }

        return true;
    }

    private void handleShowAllDeliveries() {
        var output = controller.getAllDeliveries();

        displayList(output);
    }

    private void handleShowAllCompanies() {
        var output = controller.getAllCompanies();

        displayList(output);
    }

    private void handleShowAllGarages() {
        var output = controller.getAllGarages();

        displayList(output);
    }

    private void handleShowAllTrucks() {
        var output = controller.getAllTrucks();

        displayList(output);
    }

    private void handleShowAllUsers() {
        var output = controller.getAllUsers();

        displayList(output);
    }

    private void displayList(List<?> listToDisplay) {
        listToDisplay.forEach(System.out::println);
    }
}
