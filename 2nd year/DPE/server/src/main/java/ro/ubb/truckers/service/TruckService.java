package ro.ubb.truckers.service;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Truck;
import ro.ubb.truckers.util.Extensions;
import ro.ubb.truckers.util.ServiceProvider;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static ro.ubb.truckers.util.network.Utils.async;

public class TruckService extends BaseService implements TruckServiceInterface {
    /**
     * Adds a new truck into the repository.
     *
     * @param truck to add.
     * @throws TruckersException if there is already a truck with the same id.
     */
    @Override
    public void addTruck(Truck truck) throws TruckersException {
        if (truck != null) {
            var garageService = ServiceProvider.inject(GarageService.class);
            var userService = ServiceProvider.inject(UserService.class);

            garageService.checkIfGarageIdExists(truck.getGarageId());
            userService.checkIfUserIdExists(truck.getDriverId());
            checkUserIdInUse(truck.getDriverId());
        }

        provider.getTruckRepository()
                .save(truck)
                .ifPresent(existingTruck -> {
                    throw new TruckersException("Truck with id %s already exists", existingTruck.getId());
                });

        incrementGarageTrucks(truck.getGarageId());
    }

    /**
     * Returns all trucks.
     *
     * @return a {@code List} consisting of all trucks.
     */
    @Override
    public Future<List<Truck>> getAllTrucks() {
        var allTrucks = Extensions.toList(provider.getTruckRepository().findAll());

        return async(allTrucks);
    }

    /**
     * Updates the truck based on the provided id.
     *
     * @param updatedTruck represents the new data for the truck with the specified id.
     * @throws TruckersException if there is no truck with given id.
     */
    @Override
    public void updateTruck(Truck updatedTruck) {
        AtomicReference<Truck> initialTruck = new AtomicReference<>();

        if (updatedTruck != null) {
            var garageService = ServiceProvider.inject(GarageService.class);
            var userService = ServiceProvider.inject(UserService.class);

            garageService.checkIfGarageIdExists(updatedTruck.getGarageId());
            userService.checkIfUserIdExists(updatedTruck.getDriverId());
            checkUserIdInUse(updatedTruck.getDriverId(), updatedTruck.getId());

            provider.getTruckRepository().findOne(updatedTruck.getId()).ifPresent(initialTruck::set);
        }

        provider.getTruckRepository().update(updatedTruck).orElseThrow(() ->
                new TruckersException("No truck found with id %s", updatedTruck.getId()));

        var initialGarageId = initialTruck.get().getGarageId();
        if (initialGarageId == updatedTruck.getGarageId()) {
            return;
        }

        decrementGarageTrucks(initialGarageId);
        incrementGarageTrucks(updatedTruck.getGarageId());
    }

    /**
     * Deletes a {@code Truck} entity based on the given id.
     *
     * @param byId the id by which the truck will be deleted.
     * @throws TruckersException if no truck was found with the specified id.
     */
    @Override
    public void deleteTruck(int byId) {
        var deliveryService = ServiceProvider.inject(DeliveryService.class);
        var userService = ServiceProvider.inject(UserService.class);

        deliveryService.deleteAllDeliveriesBy(delivery -> delivery.getTruckId() == byId);
        userService.deallocateTruckId(byId);

        var deletedTruck = provider.getTruckRepository().delete(byId).orElseThrow(() ->
                new TruckersException("No truck found with id %s", byId));

        decrementGarageTrucks(deletedTruck.getGarageId());
    }

    /**
     * Returns a filtered {@code List} representing the trucks with the given model.
     *
     * @param model minimum available capacity the garage should have.
     * @return a {@code List} consisting of filtered trucks.
     */
    @Override
    public Future<List<Truck>> getTrucksByModel(String model) {
        var trucks = Extensions.toStream(provider.getTruckRepository().findAll())
                .filter(truck -> truck.getModel().contains(model))
                .collect(Collectors.toList());

        return async(trucks);
    }

    /**
     * Deletes all trucks that have the given garageId.
     *
     * @param garageId the id by which the truck will be deleted.
     */
    public void deleteAllTrucksByGarageId(int garageId) {
        var allTrucks = provider.getTruckRepository().findAll();

        Extensions.toStream(allTrucks)
                .filter(truck -> truck.getGarageId() == garageId)
                .forEach(truck -> deleteTruck(truck.getId()));
    }

    /**
     * Deallocates the userId for the truck that has it.
     *
     * @param userId to deallocate.
     */
    public void deallocateUserId(int userId) {
        if (userId < 1) {
            return;
        }

        var allTrucks = provider.getTruckRepository().findAll();

        Extensions.toStream(allTrucks)
                .filter(truck -> truck.getDriverId() == userId)
                .findAny()
                .ifPresent(truck -> deleteTruck(truck.getId()));
    }

    /**
     * Checks if a truck exists with the given id.
     *
     * @param truckId of the truck to be checked.
     * @throws TruckersException in case there is no truck with the given id.
     */
    public void checkIfTruckIdExists(int truckId) throws TruckersException {
        if (truckId < 1) {
            return;
        }

        provider.getTruckRepository()
                .findOne(truckId)
                .orElseThrow(() -> new TruckersException("There is no truck with id %s", truckId));
    }

    /**
     * Checks if a truck with given userId exists excluding the specified truckId.
     *
     * @param userId         to look for.
     * @param excludeTruckId is the truck ID to exclude.
     * @throws TruckersException if there is already a truck with same userId, and its id is not ignored.
     */
    private void checkUserIdInUse(int userId, int excludeTruckId) throws TruckersException {
        if (userId < 1) {
            return;
        }

        var allTrucks = provider.getTruckRepository().findAll();

        Extensions.toStream(allTrucks)
                .filter(existingTruck -> existingTruck.getId() != excludeTruckId &&
                        existingTruck.getDriverId() == userId)
                .findAny()
                .ifPresent(existingTruck -> {
                    throw new TruckersException("There is already truck %s with id %s and driverId %s",
                            existingTruck.getModel(), existingTruck.getId(), existingTruck.getDriverId());
                });
    }

    /**
     * Checks if a truck with given userId exists.
     *
     * @param userId to look for.
     * @throws TruckersException if there is already a truck with same userId.
     */
    private void checkUserIdInUse(int userId) throws TruckersException {
        if (userId < 1) {
            return;
        }

        checkUserIdInUse(userId, 0);
    }

    /**
     * Increments the garage's allocated trucks by the garageId.
     *
     * @param garageId specifies which garage to update.
     */
    private void incrementGarageTrucks(int garageId) {
        provider.getGarageRepository()
                .findOne(garageId)
                .ifPresent(garage -> {
                    garage.incrementAllocatedTrucks();
                    provider.getGarageRepository().update(garage);
                });
    }

    /**
     * Decrements the garage's allocated trucks by the garageId.
     *
     * @param garageId specifies which garage to update.
     */
    private void decrementGarageTrucks(int garageId) {
        provider.getGarageRepository()
                .findOne(garageId)
                .ifPresent(garage -> {
                    garage.decrementAllocatedTrucks();
                    provider.getGarageRepository().update(garage);
                });
    }
}