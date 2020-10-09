package model;

public class Motorcycle implements VehicleInterface {

    private final String color;
    private final String brand;

    public Motorcycle(String color, String brand) {
        this.color = color;
        this.brand = brand;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String toString() {
        return "Motorcycle{" +
                "color='" + color + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
