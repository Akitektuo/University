package com.akitektuo;

public class Vehicle {

    public static int totalVehicles = 0;

    public Vehicle() {
        totalVehicles++;
    }

    public void startEngine() throws Exception {
        throw new Exception("The vehicle has no engine");
    }
}
