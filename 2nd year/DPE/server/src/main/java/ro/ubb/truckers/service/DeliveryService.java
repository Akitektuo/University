package ro.ubb.truckers.service;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Delivery;
import ro.ubb.truckers.util.Extensions;
import ro.ubb.truckers.util.ServiceProvider;

import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ro.ubb.truckers.util.network.Utils.async;

public class DeliveryService extends BaseService implements DeliveryServiceInterface {
    /**
     * Adds a new delivery into the repository.
     *
     * @param delivery to add.
     * @throws TruckersException if there is already a delivery with the same id.
     */
    @Override
    public void addDelivery(Delivery delivery) throws TruckersException {
        if (delivery != null) {
            var companyService = ServiceProvider.inject(CompanyService.class);
            var truckService = ServiceProvider.inject(TruckService.class);

            companyService.checkIfCompanyIdExists(delivery.getCompanyId());
            truckService.checkIfTruckIdExists(delivery.getTruckId());
        }

        provider.getDeliveryRepository().save(delivery).ifPresent(existingDelivery -> {
            throw new TruckersException("There is already a delivery with the id of %s",
                    existingDelivery.getId());
        });
    }

    /**
     * Returns all deliveries.
     *
     * @return a {@code List} consisting of all deliveries.
     */
    @Override
    public Future<List<Delivery>> getAllDeliveries() {
        var allDeliveries = Extensions.toList(provider.getDeliveryRepository().findAll());

        return async(allDeliveries);
    }

    /**
     * Updates the given delivery based on the provided id.
     *
     * @param updatedDelivery represents the new data for the delivery with the specified id.
     * @throws TruckersException if there is no delivery with the given id.
     */
    @Override
    public void updateDelivery(Delivery updatedDelivery) {
        if (updatedDelivery != null) {
            var companyService = ServiceProvider.inject(CompanyService.class);
            var truckService = ServiceProvider.inject(TruckService.class);

            companyService.checkIfCompanyIdExists(updatedDelivery.getCompanyId());
            truckService.checkIfTruckIdExists(updatedDelivery.getTruckId());
        }

        provider.getDeliveryRepository().update(updatedDelivery).orElseThrow(() ->
                new TruckersException("No delivery found with id %s", updatedDelivery.getId()));
    }

    /**
     * Deletes a {@code Delivery} entity based on the given id.
     *
     * @param byId the id by which the delivery will be deleted.
     * @throws TruckersException if no entity was deleted.
     */
    @Override
    public void deleteDelivery(int byId) {
        provider.getDeliveryRepository().delete(byId).orElseThrow(() ->
                new TruckersException("No delivery found with id %s", byId));
    }

    /**
     * Deletes all deliveries specified by the deleteIf lambda function.
     *
     * @param deleteIf lambda function by which deliveries are deleted.
     */
    public void deleteAllDeliveriesBy(Predicate<Delivery> deleteIf) {
        var allDeliveries = provider.getDeliveryRepository().findAll();

        Extensions.toStream(allDeliveries)
                .filter(deleteIf)
                .forEach(delivery -> deleteDelivery(delivery.getId()));
    }

    /**
     * Returns a filtered {@code List} representing the deliveries for which the provided origin and destination are
     * substrings of the delivery's origin and destination.
     *
     * @param origin      that the delivery should contain.
     * @param destination that the delivery should contain.
     * @return a {@code List} consisting of the filtered deliveries.
     */
    @Override
    public Future<List<Delivery>> getDeliveriesByOriginAndDestination(String origin, String destination) {
        var deliveries = Extensions.toStream(provider.getDeliveryRepository().findAll())
                .filter(delivery -> delivery.getOrigin().contains(origin) &&
                        delivery.getDestination().contains(destination))
                .collect(Collectors.toList());

        return async(deliveries);

    }
}