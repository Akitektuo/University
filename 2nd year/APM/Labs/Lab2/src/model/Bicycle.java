package model;

public class Bicycle implements VehicleInterface {

    private final String color;
    private final String brand;

    public Bicycle(String color, String brand) {
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
        return "Bicycle{" +
                "color='" + color + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
