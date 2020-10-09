package repository;

import exception.RepositoryException;
import model.VehicleInterface;

public interface RepositoryInterface {
    void add(VehicleInterface vehicle) throws RepositoryException;

    VehicleInterface[] getAll();

    VehicleInterface[] getAllByColor(String color);
}
