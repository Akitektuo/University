package ro.ubb.truckers.console.command;

import ro.ubb.truckers.controller.Controller;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.util.ServiceProvider;
import ro.ubb.truckers.util.StringValidator;

import java.util.List;

public class FilterCommand implements BaseCommand {
    private final Controller controller = ServiceProvider.inject(Controller.class);

    /**
     * Returns the keyword of the command.
     *
     * @return a {@code String} - the keyword that identifies the command
     */
    @Override
    public String getKeyword() {
        return "filter";
    }

    /**
     * @return a {@code List} - consists of all the patterns that the command has
     */
    @Override
    public List<String> getPatterns() {
        return List.of(
                "filter companies <name>",
                "filter deliveries <origin> <destination>",
                "filter garages <availableCapacityAtLeast>",
                "filter trucks <model>",
                "filter users <yearOfBirth>");
    }

    /**
     * Returns a short description of the command.
     *
     * @return a {@code String} - a short description of the command
     */
    @Override
    public String getDescription() {
        return "Displays the filtered entities by the specified keywords.";
    }

    /**
     * Executes the command.
     *
     * @param arguments must not be null.
     * @return a {@code boolean} - represents if the program should continue or not
     */
    @Override
    public boolean run(List<String> arguments) {
        StringValidator.requireArguments(arguments);

        switch (arguments.get(0)) {
            case "companies" -> handleFilterCompanies(arguments);
            case "deliveries" -> handleFilterDeliveries(arguments);
            case "garages" -> handleFilterGarages(arguments);
            case "trucks" -> handleFilterTrucks(arguments);
            case "users" -> handleFilterUsers(arguments);
            default -> throw new TruckersException("There is no entity with the name of '%s'", arguments.get(0));
        }

        return true;
    }

    private void handleFilterCompanies(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 2);

        var name = arguments.get(1);

        var output = controller.getCompaniesByName(name);

        displayList(output);
    }

    private void handleFilterDeliveries(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 3);

        var origin = arguments.get(1);
        var destination = arguments.get(2);

        var output = controller.getDeliveriesByOriginAndDestination(origin, destination);

        displayList(output);
    }

    private void handleFilterGarages(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 2);

        var availableCapacityAtLeast = StringValidator.convertToInt(arguments.get(1));

        var output = controller.getGaragesByAvailableCapacityAscending(availableCapacityAtLeast);

        displayList(output);
    }

    private void handleFilterTrucks(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 2);

        var model = arguments.get(1);

        var output = controller.getTrucksByModel(model);

        displayList(output);
    }

    private void handleFilterUsers(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 2);

        var yearOfBirth = StringValidator.convertToInt(arguments.get(1));

        var output = controller.getUsersByYearOfBirth(yearOfBirth);

        displayList(output);
    }

    private void displayList(List<?> listToDisplay) {
        listToDisplay.forEach(System.out::println);
    }
}
