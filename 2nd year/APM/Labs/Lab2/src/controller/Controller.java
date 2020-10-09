package controller;

import exception.RepositoryException;
import model.VehicleInterface;
import repository.RepositoryInterface;

public class Controller {
    private final RepositoryInterface repository;

    public Controller(RepositoryInterface repository) {
        this.repository = repository;
    }

    public void addVehicle(VehicleInterface vehicle) throws RepositoryException {
        repository.add(vehicle);
    }

    public VehicleInterface[] getAllVehicles() {
        return repository.getAll();
    }

    public VehicleInterface[] filterVehiclesByColor(String color) {
        return repository.getAllByColor(color);
    }
}
