package ro.ubb.truckers.service;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Garage;
import ro.ubb.truckers.domain.report.GarageReport;
import ro.ubb.truckers.domain.report.sortable.GarageReportField;
import ro.ubb.truckers.util.constant.SortType;

import java.util.List;
import java.util.concurrent.Future;

public interface GarageServiceInterface {
    String API_ADD_GARAGE = "addGarage";
    String API_GET_ALL_GARAGES = "getAllGarages";
    String API_UPDATE_GARAGE = "updateGarage";
    String API_DELETE_GARAGE = "deleteGarage";
    String API_GET_GARAGES_BY_AVAILABLE_CAPACITY_ASCENDING = "getGaragesByAvailableCapacityAscending";
    String API_GET_GARAGES_REPORT_SORTED_BY = "getGaragesReportSortedBy";

    /**
     * Adds a new garage into the repository.
     *
     * @param garage to add.
     * @throws TruckersException if there is already a garage with the same id.
     */
    void addGarage(Garage garage);

    /**
     * Returns all garages
     *
     * @return a {@code List} consisting of all garages.
     */
    Future<List<Garage>> getAllGarages();

    /**
     * Updates the given garage based on the provided id.
     *
     * @param updatedGarage represents the new data for the garage with the specified id.
     * @throws TruckersException if there is no garage with the given id.
     */
    void updateGarage(Garage updatedGarage);

    /**
     * Deletes a {@code Garage} entity based on the given id.
     *
     * @param byId the id by which the garage will be deleted.
     * @throws TruckersException if no garage was found with the specified id.
     */
    void deleteGarage(int byId);

    /**
     * Returns a filtered ordered {@code List} representing the Garages for which the available capacity is greater
     * or equal to availableCapacityAtLeast.
     *
     * @param availableCapacityAtLeast minimum available capacity the garage should have.
     * @return a {@code List} consisting of filtered ordered garages.
     */
    Future<List<Garage>> getGaragesByAvailableCapacityAscending(int availableCapacityAtLeast);

    /**
     * Returns a {@code List<GarageReport>} consisting of the minimum, average, maximum mileage of trucks in every
     * garage, sorted by the given {@code GarageReportField}.
     *
     * @param field    by which the garage reports will be filtered.
     * @param sortType specifying if sorting will be ascending or descending.
     * @return a sorted {@code List<GarageReport>}
     */
    Future<List<GarageReport>> getGaragesReportSortedBy(GarageReportField field, SortType sortType);
}
