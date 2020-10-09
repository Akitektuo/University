package view;

import controller.Controller;
import model.Bicycle;
import model.Car;
import model.Motorcycle;
import model.VehicleInterface;
import repository.RepositoryInterface;

import java.util.Scanner;

public class MainView {

    private final Controller controller;

    public MainView(RepositoryInterface repository) {
        controller = new Controller(repository);
    }

    public void start() {
        var scanner = new Scanner(System.in);

        readVehicles(scanner);

        var color = readFilterColor(scanner);

        printResult(color);

        System.out.println("\nClosing application...");
    }

    private void readVehicles(Scanner scanner) {
        while (true) {
            var typeToAdd = readType(scanner);

            var brand = readBrand(scanner);

            var color = readColor(scanner);

            VehicleInterface vehicle = buildVehicle(typeToAdd, brand, color);

            try {
                controller.addVehicle(vehicle);
            } catch (Exception exception) {
                System.out.println("No more space in repository!");
                break;
            }

            System.out.print("\nDo you want to add more vehicles? (y/n): ");
            var answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("n")) {
                break;
            }
        }
    }

    private String readType(Scanner scanner) {
        var typeToAdd = "";

        while (!(typeToAdd.equals("car") || typeToAdd.equals("motorcycle") || typeToAdd.equals("bicycle"))) {
            System.out.print("What do you want to add? (car/motorcycle/bicycle): ");
            typeToAdd = scanner.nextLine();
        }

        return typeToAdd;
    }

    private String readBrand(Scanner scanner) {
        System.out.print("Specify the brand: ");

        return scanner.nextLine();
    }

    private String readColor(Scanner scanner) {
        System.out.print("Specify the color: ");
        return scanner.nextLine();
    }

    private VehicleInterface buildVehicle(String type, String brand, String color) {
        return switch (type) {
            case "car" -> new Car(color, brand);
            case "motorcycle" -> new Motorcycle(color, brand);
            case "bicycle" -> new Bicycle(color, brand);
            default -> null;
        };
    }

    private String readFilterColor(Scanner scanner) {
        var color = "";

        while (color.isBlank()) {
            System.out.println("In which color should be the vehicles displayed?");
            color = scanner.nextLine();
        }

        return color;
    }

    private void printResult(String color) {
        var vehicles = color.equals("all") ? controller.getAllVehicles() : controller.filterVehiclesByColor(color);
        var count = 0;

        System.out.println();

        for (var vehicle : vehicles) {
            if (vehicle == null) {
                break;
            }
            count++;
            System.out.println(vehicle.toString());
        }

        if (count == 0) {
            System.out.println("No vehicles stored in repository.");
        }
    }
}
