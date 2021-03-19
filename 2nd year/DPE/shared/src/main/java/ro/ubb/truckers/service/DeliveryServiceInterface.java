package ro.ubb.truckers.service;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Delivery;

import java.util.List;
import java.util.concurrent.Future;

public interface DeliveryServiceInterface {
    String API_ADD_DELIVERY = "addDelivery";
    String API_GET_ALL_DELIVERIES = "getAllDeliveries";
    String API_UPDATE_DELIVERY = "updateDelivery";
    String API_DELETE_DELIVERY = "deleteDelivery";
    String API_GET_DELIVERIES_BY_ORIGIN_AND_DESTINATION = "getDeliveriesByOriginAndDestination";

    /**
     * Adds a new delivery into the repository.
     *
     * @param delivery to add.
     * @throws TruckersException if there is already a delivery with the same id.
     */
    void addDelivery(Delivery delivery) throws TruckersException;

    /**
     * Returns all deliveries.
     *
     * @return a {@code List} consisting of all deliveries.
     */
    Future<List<Delivery>> getAllDeliveries();

    /**
     * Updates the given delivery based on the provided id.
     *
     * @param updatedDelivery represents the new data for the delivery with the specified id.
     * @throws TruckersException if there is no delivery with the given id.
     */
    void updateDelivery(Delivery updatedDelivery);

    /**
     * Deletes a {@code Delivery} entity based on the given id.
     *
     * @param byId the id by which the delivery will be deleted.
     * @throws TruckersException if no entity was deleted.
     */
    void deleteDelivery(int byId);

    /**
     * Returns a filtered {@code List} representing the deliveries for which the provided origin and destination are
     * substrings of the delivery's origin and destination.
     *
     * @param origin that the delivery should contain.
     * @param destination that the delivery should contain.
     * @return a {@code List} consisting of the filtered deliveries.
     */
    Future<List<Delivery>> getDeliveriesByOriginAndDestination(String origin, String destination);
}
