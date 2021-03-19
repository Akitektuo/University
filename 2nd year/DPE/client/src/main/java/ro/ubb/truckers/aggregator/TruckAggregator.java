package ro.ubb.truckers.aggregator;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Truck;
import ro.ubb.truckers.network.ServerManager;
import ro.ubb.truckers.service.TruckServiceInterface;
import ro.ubb.truckers.util.Extensions;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static ro.ubb.truckers.util.network.Utils.async;
import static ro.ubb.truckers.util.network.Utils.await;

public class TruckAggregator implements TruckServiceInterface {
    /**
     * Adds a new truck into the repository.
     *
     * @param truck to add.
     * @throws TruckersException if there is already a truck with the same id.
     */
    @Override
    public void addTruck(Truck truck) throws TruckersException {
        ServerManager.sendRequest(API_ADD_TRUCK, truck.toJSON());
    }

    /**
     * Returns all trucks.
     *
     * @return a {@code List} consisting of all trucks.
     */
    @Override
    public Future<List<Truck>> getAllTrucks() {
        return async(() -> {
            var response = await(ServerManager.sendRequest(API_GET_ALL_TRUCKS));

            return Extensions.toJSONArrayStream(response)
                    .map(Truck::fromJSON)
                    .collect(Collectors.toList());
        });
    }

    /**
     * Updates the truck based on the provided id.
     *
     * @param updatedTruck represents the new data for the truck with the specified id.
     * @throws TruckersException if there is no truck with given id.
     */
    @Override
    public void updateTruck(Truck updatedTruck) {
        ServerManager.sendRequest(API_UPDATE_TRUCK, updatedTruck.toJSON());
    }

    /**
     * Deletes a {@code Truck} entity based on the given id.
     *
     * @param byId the id by which the truck will be deleted.
     * @throws TruckersException if no truck was found with the specified id.
     */
    @Override
    public void deleteTruck(int byId) {
        ServerManager.sendRequest(API_DELETE_TRUCK, String.valueOf(byId));
    }

    /**
     * Returns a filtered {@code List} representing the trucks with the given model.
     *
     * @param model minimum available capacity the garage should have.
     * @return a {@code List} consisting of filtered trucks.
     */
    @Override
    public Future<List<Truck>> getTrucksByModel(String model) {
        return async(() -> {
            var response = await(ServerManager.sendRequest(API_GET_TRUCKS_BY_MODEL, model));

            return Extensions.toJSONArrayStream(response)
                    .map(Truck::fromJSON)
                    .collect(Collectors.toList());
        });
    }
}
