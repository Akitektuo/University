package ro.ubb.truckers.aggregator;

import org.json.JSONArray;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Delivery;
import ro.ubb.truckers.network.ServerManager;
import ro.ubb.truckers.service.DeliveryServiceInterface;
import ro.ubb.truckers.util.Extensions;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static ro.ubb.truckers.util.network.Utils.async;
import static ro.ubb.truckers.util.network.Utils.await;

public class DeliveryAggregator implements DeliveryServiceInterface {
    /**
     * Adds a new delivery into the repository.
     *
     * @param delivery to add.
     * @throws TruckersException if there is already a delivery with the same id.
     */
    @Override
    public void addDelivery(Delivery delivery) throws TruckersException {
        ServerManager.sendRequest(API_ADD_DELIVERY, delivery.toJSON());
    }

    /**
     * Returns all deliveries.
     *
     * @return a {@code List} consisting of all deliveries.
     */
    @Override
    public Future<List<Delivery>> getAllDeliveries() {
        return async(() -> {
            var response = await(ServerManager.sendRequest(API_GET_ALL_DELIVERIES));

            return Extensions.toJSONArrayStream(response)
                    .map(Delivery::fromJSON)
                    .collect(Collectors.toList());
        });
    }

    /**
     * Updates the given delivery based on the provided id.
     *
     * @param updatedDelivery represents the new data for the delivery with the specified id.
     * @throws TruckersException if there is no delivery with the given id.
     */
    @Override
    public void updateDelivery(Delivery updatedDelivery) {
        ServerManager.sendRequest(API_UPDATE_DELIVERY, updatedDelivery.toJSON());
    }

    /**
     * Deletes a {@code Delivery} entity based on the given id.
     *
     * @param byId the id by which the delivery will be deleted.
     * @throws TruckersException if no entity was deleted.
     */
    @Override
    public void deleteDelivery(int byId) {
        ServerManager.sendRequest(API_DELETE_DELIVERY, String.valueOf(byId));
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
        return async(() -> {
            var parameters = new JSONArray()
                    .put(origin)
                    .put(destination)
                    .toString();

            var response = await(ServerManager.sendRequest(API_GET_DELIVERIES_BY_ORIGIN_AND_DESTINATION, parameters));

            return Extensions.toJSONArrayStream(response)
                    .map(Delivery::fromJSON)
                    .collect(Collectors.toList());
        });
    }
}
