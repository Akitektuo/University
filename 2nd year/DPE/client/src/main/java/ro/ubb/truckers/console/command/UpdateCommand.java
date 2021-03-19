package ro.ubb.truckers.console.command;

import ro.ubb.truckers.controller.Controller;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.util.ServiceProvider;
import ro.ubb.truckers.util.StringValidator;

import java.util.List;

public class UpdateCommand implements BaseCommand {
    private final Controller controller = ServiceProvider.inject(Controller.class);

    /**
     * Returns the keyword of the command.
     *
     * @return a {@code String} - the keyword that identifies the command
     */
    @Override
    public String getKeyword() {
        return "update";
    }

    /**
     * @return a {@code List} - consists of all the patterns that the command has
     */
    @Override
    public List<String> getPatterns() {
        return List.of(
                "update company <existingId> <name>",
                "update delivery <existingId> <origin> <destination> <distance> <delivered> <load> <truckId> <companyId>",
                "update garage <existingId> <location> <capacity> <companyId>",
                "update truck <existingId> <model> <licensePlate> <mileage> <driverId> <garageId>",
                "update user <existingId> <firstName> <lastName> <email> <password> <dateOfBirth> <truckId>");
    }

    /**
     * Returns a short description of the command.
     *
     * @return a {@code String} - a short description of the command
     */
    @Override
    public String getDescription() {
        return "Updates the entity by the specified id with the new given properties.";
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
            case "company" -> handleUpdateCompany(arguments);
            case "delivery" -> handleUpdateDelivery(arguments);
            case "garage" -> handleUpdateGarage(arguments);
            case "truck" -> handleUpdateTruck(arguments);
            case "user" -> handleUpdateUser(arguments);
            default -> throw new TruckersException("There is no entity with the name of '%s'", arguments.get(0));
        }

        return true;
    }

    private void handleUpdateCompany(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 3);

        var existingId = StringValidator.convertToInt(arguments.get(1));
        var newName = arguments.get(2);

        controller.updateCompany(existingId, newName);

        printOperationSuccessful();
    }

    private void handleUpdateDelivery(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 9);

        var existingId = StringValidator.convertToInt(arguments.get(1));
        var newOrigin = arguments.get(2);
        var newDestination = arguments.get(3);
        var newDistance = StringValidator.convertToInt(arguments.get(4));
        var newDeliveryStatus = StringValidator.convertToBoolean(arguments.get(5));
        var newLoad = arguments.get(6);
        var newTruckId = StringValidator.convertToInt(arguments.get(7));
        var newCompanyId = StringValidator.convertToInt(arguments.get(8));

        controller.updateDelivery(existingId, newOrigin, newDestination, newDistance, newDeliveryStatus,
                newLoad, newTruckId, newCompanyId);

        printOperationSuccessful();
    }

    private void handleUpdateGarage(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 5);

        var existingId = StringValidator.convertToInt(arguments.get(1));
        var newLocation = arguments.get(2);
        var newCapacity = StringValidator.convertToInt(arguments.get(3));
        var newCompanyId = StringValidator.convertToInt(arguments.get(4));

        controller.updateGarage(existingId, newLocation, newCapacity, newCompanyId);

        printOperationSuccessful();
    }

    private void handleUpdateTruck(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 7);

        var existingId = StringValidator.convertToInt(arguments.get(1));
        var newModel = arguments.get(2);
        var newLicensePlate = arguments.get(3);
        var newMileage = StringValidator.convertToInt(arguments.get(4));
        var newDriverId = StringValidator.convertToInt(arguments.get(5));
        var newGarageId = StringValidator.convertToInt(arguments.get(6));

        controller.updateTruck(existingId, newModel, newLicensePlate, newMileage, newDriverId, newGarageId);

        printOperationSuccessful();
    }

    private void handleUpdateUser(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 8);

        var existingId = StringValidator.convertToInt(arguments.get(1));
        var newFirstName = arguments.get(2);
        var newLastName = arguments.get(3);
        var newEmail = arguments.get(4);
        var newPassword = arguments.get(5);
        var newDateOfBirth = StringValidator.convertToDate(arguments.get(6));
        var newTruckId = StringValidator.convertToInt(arguments.get(7));

        controller.updateUser(existingId, newFirstName, newLastName, newEmail, newPassword, newDateOfBirth, newTruckId);

        printOperationSuccessful();
    }

    private void printOperationSuccessful() {
        System.out.println("Updated entity successfully!");
    }
}