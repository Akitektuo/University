package com.akitektuo;

public class Main {
    public static void main(String[] args) {
        try {
            new Vehicle().startEngine();
            System.out.println("No exception thrown!");
        } catch (Exception exception) {
            System.out.println("Exception thrown: " + exception.getMessage());
        }
        new Car().startEngine();
        new RacingCar().startEngine();
        System.out.println("Total vehicles created: " + Vehicle.totalVehicles);
    }
}
