package ro.ubb.truckers.service;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Garage;
import ro.ubb.truckers.domain.report.GarageReport;
import ro.ubb.truckers.domain.report.sortable.GarageReportField;
import ro.ubb.truckers.util.Extensions;
import ro.ubb.truckers.util.ServiceProvider;
import ro.ubb.truckers.util.constant.SortType;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static ro.ubb.truckers.util.network.Utils.async;

public class GarageService extends BaseService implements GarageServiceInterface {
    /**
     * Adds a new garage into the repository.
     *
     * @param garage to add.
     * @throws TruckersException if there is already a garage with the same id.
     */
    @Override
    public void addGarage(Garage garage) throws TruckersException {
        if (garage != null) {
            var companyService = ServiceProvider.inject(CompanyService.class);

            companyService.checkIfCompanyIdExists(garage.getCompanyId());
        }

        provider.getGarageRepository().save(garage).ifPresent(existingGarage -> {
            throw new TruckersException("Garage at %s with the id of %s already exists",
                    existingGarage.getLocation(), existingGarage.getId());
        });
    }

    /**
     * Returns all garages
     *
     * @return a {@code List} consisting of all garages.
     */
    @Override
    public Future<List<Garage>> getAllGarages() {
        var allGarages = Extensions.toList(provider.getGarageRepository().findAll());

        return async(allGarages);
    }

    /**
     * Updates the given garage based on the provided id.
     *
     * @param updatedGarage represents the new data for the garage with the specified id.
     * @throws TruckersException if there is no garage with the given id.
     */
    @Override
    public void updateGarage(Garage updatedGarage) {
        var repository = provider.getGarageRepository();
        Supplier<TruckersException> noIdThrowable = () ->
                new TruckersException("No garage found with id %s", updatedGarage.getId());

        if (updatedGarage != null) {
            var companyService = ServiceProvider.inject(CompanyService.class);

            companyService.checkIfCompanyIdExists(updatedGarage.getCompanyId());
            var allocatedTrucks = repository.findOne(updatedGarage.getId())
                    .orElseThrow(noIdThrowable)
                    .getAllocatedTrucks();
            updatedGarage.setAllocatedTrucks(allocatedTrucks);
        }

        repository.update(updatedGarage).orElseThrow(noIdThrowable);
    }

    /**
     * Deletes a {@code Garage} entity based on the given id.
     *
     * @param byId the id by which the garage will be deleted.
     * @throws TruckersException if no garage was found with the specified id.
     */
    @Override
    public void deleteGarage(int byId) {
        var truckService = ServiceProvider.inject(TruckService.class);

        truckService.deleteAllTrucksByGarageId(byId);

        provider.getGarageRepository().delete(byId).orElseThrow(() ->
                new TruckersException("No garage found with id %s", byId));
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
        var garages = Extensions.toStream(provider.getGarageRepository().findAll())
                .filter(garage -> garage.getCapacity() - garage.getAllocatedTrucks() >= availableCapacityAtLeast)
                .sorted(Comparator.comparingInt(garage -> garage.getCapacity() - garage.getAllocatedTrucks()))
                .collect(Collectors.toList());

        return async(garages);
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
        final var allTrucks = Extensions.toStream(provider.getTruckRepository().findAll())
                .collect(Collectors.toList());
        var allGarages = provider.getGarageRepository().findAll();

        var report = Extensions.toStream(allGarages)
                .map(garage -> new GarageReport(garage, allTrucks))
                .sorted(sortType.getComparator(field::getField))
                .collect(Collectors.toList());

        return async(report);
    }

    /**
     * Deletes all garages that have the given companyId.
     *
     * @param companyId the id by which the garages will be deleted.
     */
    public void deleteAllGaragesByCompanyId(int companyId) {
        var allGarages = provider.getGarageRepository().findAll();

        Extensions.toStream(allGarages)
                .filter(garage -> garage.getCompanyId() == companyId)
                .forEach(garage -> deleteGarage(garage.getId()));
    }

    /**
     * Checks if a garage exists with the given id.
     *
     * @param garageId of the garage to be checked.
     * @throws TruckersException in case there is no garage with the given id.
     */
    public void checkIfGarageIdExists(int garageId) throws TruckersException {
        if (garageId < 1) {
            return;
        }

        provider.getGarageRepository()
                .findOne(garageId)
                .orElseThrow(() -> new TruckersException("There is no garage with id %s", garageId));
    }
}
