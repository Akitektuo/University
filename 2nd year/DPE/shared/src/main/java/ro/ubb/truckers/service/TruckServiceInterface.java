package ro.ubb.truckers.service;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Truck;

import java.util.List;
import java.util.concurrent.Future;

public interface TruckServiceInterface {
    String API_ADD_TRUCK = "addTruck";
    String API_GET_ALL_TRUCKS = "getAllTrucks";
    String API_UPDATE_TRUCK = "updateTruck";
    String API_DELETE_TRUCK = "deleteTruck";
    String API_GET_TRUCKS_BY_MODEL = "getTrucksByModel";

    /**
     * Adds a new truck into the repository.
     *
     * @param truck to add.
     * @throws TruckersException if there is already a truck with the same id.
     */
    void addTruck(Truck truck) throws TruckersException;

    /**
     * Returns all trucks.
     *
     * @return a {@code List} consisting of all trucks.
     */
    Future<List<Truck>> getAllTrucks();

    /**
     * Updates the truck based on the provided id.
     *
     * @param updatedTruck represents the new data for the truck with the specified id.
     * @throws TruckersException if there is no truck with given id.
     */
    void updateTruck(Truck updatedTruck);

    /**
     * Deletes a {@code Truck} entity based on the given id.
     *
     * @param byId the id by which the truck will be deleted.
     * @throws TruckersException if no truck was found with the specified id.
     */
    void deleteTruck(int byId);

    /**
     * Returns a filtered {@code List} representing the trucks with the given model.
     *
     * @param model minimum available capacity the garage should have.
     * @return a {@code List} consisting of filtered trucks.
     */
    Future<List<Truck>> getTrucksByModel(String model);
}
