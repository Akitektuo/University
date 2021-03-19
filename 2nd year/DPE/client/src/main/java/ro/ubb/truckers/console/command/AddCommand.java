package ro.ubb.truckers.console.command;

import ro.ubb.truckers.controller.Controller;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.util.ServiceProvider;
import ro.ubb.truckers.util.StringValidator;

import java.util.List;

public class AddCommand implements BaseCommand {
    private final Controller controller = ServiceProvider.inject(Controller.class);

    @Override
    public String getKeyword() {
        return "add";
    }

    @Override
    public List<String> getPatterns() {
        return List.of(
                "add company <id> <name>",
                "add garage <id> <location> <capacity> <companyId>",
                "add truck <id> <model> <licensePlate> <mileage> <driverId> <garageId>",
                "add delivery <id> <origin> <destination> <distance> <delivered> <load> <truckId> <companyId>",
                "add user <id> <firstName> <lastName> <email> <password> <dateOfBirth> <truckId>",
                "add userCompany <userId> <companyId> <isManager>");
    }

    @Override
    public String getDescription() {
        return "Adds the specified entity into the program with the given properties.";
    }

    @Override
    public boolean run(List<String> arguments) {
        StringValidator.requireArguments(arguments);

        switch (arguments.get(0)) {
            case "company" -> handleAddCompany(arguments);
            case "delivery" -> handleAddDelivery(arguments);
            case "garage" -> handleAddGarage(arguments);
            case "truck" -> handleAddTruck(arguments);
            case "user" -> handleAddUser(arguments);
            case "userCompany" -> handleAddUserCompany(arguments);
            default -> throw new TruckersException("There is no entity with the name of '%s'", arguments.get(0));
        }

        return true;
    }

    private void handleAddCompany(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 3);

        var id = StringValidator.convertToInt(arguments.get(1));
        var name = arguments.get(2);

        controller.addCompany(id, name);

        printOperationSuccessful();
    }

    private void handleAddDelivery(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 9);

        var id = StringValidator.convertToInt(arguments.get(1));
        var origin = arguments.get(2);
        var destination = arguments.get(3);
        var distance = StringValidator.convertToInt(arguments.get(4));
        var delivered = StringValidator.convertToBoolean(arguments.get(5));
        var load = arguments.get(6);
        var truckId = StringValidator.convertToInt(arguments.get(7));
        var companyId = StringValidator.convertToInt(arguments.get(8));

        controller.addDelivery(id, origin, destination, distance, delivered, load,
                truckId, companyId);

        printOperationSuccessful();
    }

    private void handleAddGarage(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 5);

        var id = StringValidator.convertToInt(arguments.get(1));
        var location = arguments.get(2);
        var capacity = StringValidator.convertToInt(arguments.get(3));
        var companyId = StringValidator.convertToInt(arguments.get(4));

        controller.addGarage(id, location, capacity, companyId);

        printOperationSuccessful();
    }

    private void handleAddTruck(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 7);

        var id = StringValidator.convertToInt(arguments.get(1));
        var model = arguments.get(2);
        var licensePlate = arguments.get(3);
        var mileage = StringValidator.convertToInt(arguments.get(4));
        var driverId = StringValidator.convertToInt(arguments.get(5));
        var garageId = StringValidator.convertToInt(arguments.get(6));

        controller.addTruck(id, model, licensePlate, mileage, driverId, garageId);

        printOperationSuccessful();
    }

    private void handleAddUser(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 8);

        var id = StringValidator.convertToInt(arguments.get(1));
        var firstName = arguments.get(2);
        var lastName = arguments.get(3);
        var email = arguments.get(4);
        var password = arguments.get(5);
        var dateOfBirth = StringValidator.convertToDate(arguments.get(6));
        var truckId = StringValidator.convertToInt(arguments.get(7));

        controller.addUser(id, firstName, lastName, email, password, dateOfBirth, truckId);

        printOperationSuccessful();
    }

    private void handleAddUserCompany(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 4);

        var userId = StringValidator.convertToInt(arguments.get(1));
        var companyId = StringValidator.convertToInt(arguments.get(2));
        var manager = StringValidator.convertToBoolean(arguments.get(3));

        controller.addUserCompany(userId, companyId, manager);

        printOperationSuccessful();
    }


    private void printOperationSuccessful() {
        System.out.println("Added entity successfully!");
    }
}
