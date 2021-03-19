package ro.ubb.truckers.aggregator;

import org.json.JSONArray;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Garage;
import ro.ubb.truckers.domain.report.GarageReport;
import ro.ubb.truckers.domain.report.sortable.GarageReportField;
import ro.ubb.truckers.network.ServerManager;
import ro.ubb.truckers.service.GarageServiceInterface;
import ro.ubb.truckers.util.Extensions;
import ro.ubb.truckers.util.constant.SortType;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static ro.ubb.truckers.util.network.Utils.async;
import static ro.ubb.truckers.util.network.Utils.await;

public class GarageAggregator implements GarageServiceInterface {
    /**
     * Adds a new garage into the repository.
     *
     * @param garage to add.
     * @throws TruckersException if there is already a garage with the same id.
     */
    @Override
    public void addGarage(Garage garage) {
        ServerManager.sendRequest(API_ADD_GARAGE, garage.toJSON());
    }

    /**
     * Returns all garages
     *
     * @return a {@code List} consisting of all garages.
     */
    @Override
    public Future<List<Garage>> getAllGarages() {
        return async(() -> {
            var response = await(ServerManager.sendRequest(API_GET_ALL_GARAGES));

            return Extensions.toJSONArrayStream(response)
                    .map(Garage::fromJSON)
                    .collect(Collectors.toList());
        });
    }

    /**
     * Updates the given garage based on the provided id.
     *
     * @param updatedGarage represents the new data for the garage with the specified id.
     * @throws TruckersException if there is no garage with the given id.
     */
    @Override
    public void updateGarage(Garage updatedGarage) {
        ServerManager.sendRequest(API_UPDATE_GARAGE, updatedGarage.toJSON());
    }

    /**
     * Deletes a {@code Garage} entity based on the given id.
     *
     * @param byId the id by which the garage will be deleted.
     * @throws TruckersException if no garage was found with the specified id.
     */
    @Override
    public void deleteGarage(int byId) {
        ServerManager.sendRequest(API_DELETE_GARAGE, String.valueOf(byId));
    }

    /**
     * Returns a filtered ordered {@code List} representing the Garages for which the available capacity is greater
     * or equal to availableCapacityAtLeast.
     *
     * @param availableCapacityAtLeast minimum available capacity the garage should have.
     * @return a {@code List} consisting of filtered ordered garages.
     */
    @Override
    public Future<List<Garage>> getGaragesByAvailableCapacityAscending(int availableCapacityAtLeast) {
        return async(() -> {
            var response = await(ServerManager.sendRequest(
                    API_GET_GARAGES_BY_AVAILABLE_CAPACITY_ASCENDING,
                    String.valueOf(availableCapacityAtLeast)));

            return Extensions.toJSONArrayStream(response)
                    .map(Garage::fromJSON)
                    .collect(Collectors.toList());
        });
    }

    /**
     * Returns a {@code List<GarageReport>} consisting of the minimum, average, maximum mileage of trucks in every
     * garage, sorted by the given {@code GarageReportField}.
     *
     * @param field    by which the garage reports will be filtered.
     * @param sortType specifying if sorting will be ascending or descending.
     * @return a sorted {@code List<GarageReport>}
     */
    @Override
    public Future<List<GarageReport>> getGaragesReportSortedBy(GarageReportField field, SortType sortType) {
        return async(() -> {
            var parameters = new JSONArray()
                    .put(field)
                    .put(sortType)
                    .toString();

            var response = await(ServerManager.sendRequest(API_GET_GARAGES_REPORT_SORTED_BY, parameters));

            return Extensions.toJSONArrayStream(response)
                    .map(GarageReport::fromJSON)
                    .collect(Collectors.toList());
        });
    }
}
