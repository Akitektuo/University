package repository;

import exception.RepositoryException;
import model.VehicleInterface;

public class MemoryRepository implements RepositoryInterface {
    private final VehicleInterface[] vehicles;
    private int size;

    public MemoryRepository(int capacity)
    {
        size = 0;
        vehicles = new VehicleInterface[capacity];
    }

    @Override
    public void add(VehicleInterface vehicle) throws RepositoryException {
        if (size == vehicles.length) {
            throw new RepositoryException("Size exceeded");
        }
        vehicles[size++] = vehicle;
    }

    @Override
    public VehicleInterface[] getAll() {
        return vehicles.clone();
    }

    @Override
    public VehicleInterface[] getAllByColor(String color) {
        var filteredVehicles = new VehicleInterface[size];
        var filteredCount = 0;

        for (var vehicle : vehicles) {
            if (vehicle != null && vehicle.getColor().equals(color)) {
                filteredVehicles[filteredCount++] = vehicle;
            }
        }

        return filteredVehicles;
    }
}
