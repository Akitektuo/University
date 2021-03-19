package ro.ubb.truckers.controller;

import org.json.JSONArray;
import ro.ubb.truckers.domain.entity.*;
import ro.ubb.truckers.domain.report.CompanyReport;
import ro.ubb.truckers.domain.report.GarageReport;
import ro.ubb.truckers.domain.report.UserReport;
import ro.ubb.truckers.domain.report.sortable.CompanyReportField;
import ro.ubb.truckers.domain.report.sortable.GarageReportField;
import ro.ubb.truckers.domain.report.sortable.UserReportField;
import ro.ubb.truckers.service.*;
import ro.ubb.truckers.util.Extensions;
import ro.ubb.truckers.util.ServiceProvider;
import ro.ubb.truckers.util.StringValidator;
import ro.ubb.truckers.util.constant.SortType;

import java.util.concurrent.Future;

import static ro.ubb.truckers.util.network.Utils.async;
import static ro.ubb.truckers.util.network.Utils.await;

@SuppressWarnings("unused")
public class Controller {
    private final CompanyService companyService = ServiceProvider.inject(CompanyService.class);
    private final GarageService garageService = ServiceProvider.inject(GarageService.class);
    private final TruckService truckService = ServiceProvider.inject(TruckService.class);
    private final DeliveryService deliveryService = ServiceProvider.inject(DeliveryService.class);
    private final UserService userService = ServiceProvider.inject(UserService.class);

    /**
     * Adds a new company into the application.
     *
     * @param parameters JSON string containing a {@code Company}.
     */
    public void addCompany(String parameters) {
        companyService.addCompany(Company.fromJSON(parameters));
    }

    /**
     * Adds a new delivery into the application.
     *
     * @param parameters JSON string containing a {@code Delivery}.
     */
    public void addDelivery(String parameters) {
        deliveryService.addDelivery(Delivery.fromJSON(parameters));
    }

    /**
     * Adds a new garage into the application.
     *
     * @param parameters JSON string containing a {@code Garage}.
     */
    public void addGarage(String parameters) {
        garageService.addGarage(Garage.fromJSON(parameters));
    }

    /**
     * Adds a new truck into the application.
     *
     * @param parameters JSON string containing a {@code Truck}.
     */
    public void addTruck(String parameters) {
        truckService.addTruck(Truck.fromJSON(parameters));
    }

    /**
     * Adds a new user into the application.
     *
     * @param parameters JSON string containing a {@code User}.
     */
    public void addUser(String parameters) {
        userService.addUser(User.fromJSON(parameters));
    }

    /**
     * Adds a new UserCompany into the application.
     *
     * @param parameters JSON string containing a {@code UserCompany}.
     */
    public void addUserCompany(String parameters) {
        companyService.addUserCompany(UserCompany.fromJSON(parameters));
    }

    /**
     * Returns all companies.
     *
     * @return a {@code List} consisting of all companies.
     */
    public Future<String> getAllCompanies() {
        var result = await(companyService.getAllCompanies());

        return async(Extensions.toJSONArray(result, Company::toJSON).toString());
    }

    /**
     * Returns all deliveries.
     *
     * @return a {@code List} consisting of all deliveries.
     */
    public Future<String> getAllDeliveries() {
        var result = await(deliveryService.getAllDeliveries());

        return async(Extensions.toJSONArray(result, Delivery::toJSON).toString());
    }

    /**
     * Returns all garages.
     *
     * @return a {@code List} consisting of all garages
     */
    public Future<String> getAllGarages() {
        var result = await(garageService.getAllGarages());

        return async(Extensions.toJSONArray(result, Garage::toJSON).toString());
    }

    /**
     * Returns all trucks.
     *
     * @return a {@code List} consisting of all trucks.
     */
    public Future<String> getAllTrucks() {
        var result = await(truckService.getAllTrucks());

        return async(Extensions.toJSONArray(result, Truck::toJSON).toString());
    }

    /**
     * Returns all users.
     *
     * @return a {@code List} consisting of all users.
     */
    public Future<String> getAllUsers() {
        var result = await(userService.getAllUsers());

        return async(Extensions.toJSONArray(result, User::toJSON).toString());
    }

    /**
     * Updates the given company based on the provided id with the new given data.
     *
     * @param parameters JSON string containing a {@code Company}.
     */
    public void updateCompany(String parameters) {
        companyService.updateCompany(Company.fromJSON(parameters));
    }

    /**
     * Updated the delivery identified by existingId with the new given data.
     *
     * @param parameters JSON string containing a {@code Delivery}.
     */
    public void updateDelivery(String parameters) {
        deliveryService.updateDelivery(Delivery.fromJSON(parameters));
    }

    /**
     * Updates the given garage based on the provided id with the new given data.
     *
     * @param parameters JSON string containing a {@code Garage}.
     */
    public void updateGarage(String parameters) {
        garageService.updateGarage(Garage.fromJSON(parameters));
    }

    /**
     * Updates the truck identified by existingId with the new given data.
     *
     * @param parameters JSON string containing a {@code Truck}.
     */
    public void updateTruck(String parameters) {
        truckService.updateTruck(Truck.fromJSON(parameters));
    }

    /**
     * Updates the user identified by existingId with the new given data.
     *
     * @param parameters JSON string containing a {@code User}.
     */
    public void updateUser(String parameters) {
        userService.updateUser(User.fromJSON(parameters));
    }

    /**
     * Deletes a {@code Company} entity based on the given id.
     *
     * @param parameters the id by which the company will be deleted.
     */
    public void deleteCompany(String parameters) {
        var byId = StringValidator.convertToInt(parameters);

        companyService.deleteCompany(byId);
    }

    /**
     * Deletes a {@code Delivery} entity based on the given id.
     *
     * @param parameters the id by which the delivery will de deleted.
     */
    public void deleteDelivery(String parameters) {
        var byId = StringValidator.convertToInt(parameters);

        deliveryService.deleteDelivery(byId);
    }

    /**
     * Deletes a {@code Garage} entity based on the given id
     *
     * @param parameters the id by which the user will be deleted.
     */
    public void deleteGarage(String parameters) {
        var byId = StringValidator.convertToInt(parameters);

        garageService.deleteGarage(byId);
    }

    /**
     * Deletes a {@code Truck} entity based on the given id.
     *
     * @param parameters the id by which the truck will de deleted.
     */
    public void deleteTruck(String parameters) {
        var byId = StringValidator.convertToInt(parameters);

        truckService.deleteTruck(byId);
    }

    /**
     * Deletes a {@code User} entity based on the given id.
     *
     * @param parameters the id by which the user will de deleted.
     */
    public void deleteUser(String parameters) {
        var byId = StringValidator.convertToInt(parameters);

        userService.deleteUser(byId);
    }

    /**
     * Returns a filtered {@code List} representing the companies for which the provided name is
     * a substring of the company's name.
     *
     * @param parameters name that the company should contain.
     * @return a {@code List} consisting of the filtered companies.
     */
    public Future<String> getCompaniesByName(String parameters) {
        var result = await(companyService.getCompaniesByName(parameters));

        return async(Extensions.toJSONArray(result, Company::toJSON).toString());
    }

    /**
     * Returns a filtered {@code List} representing the deliveries for which the provided origin and destination are
     * substrings of the delivery's origin and destination.
     *
     * @param parameters 0 -> origin: String that the delivery should contain.
     *                   1 -> destination: String that the delivery should contain.
     * @return a {@code List} consisting of the filtered deliveries.
     */
    public Future<String> getDeliveriesByOriginAndDestination(String parameters) {
        var array = new JSONArray(parameters);
        var origin = array.getString(0);
        var destination = array.getString(1);

        var result = await(deliveryService.getDeliveriesByOriginAndDestination(origin, destination));

        return async(Extensions.toJSONArray(result, Delivery::toJSON).toString());
    }

    /**
     * Returns a filtered ordered {@code List} representing the Garages for which the available capacity is greater
     * or equal to availableCapacityAtLeast.
     *
     * @param parameters availableCapacityAtLeast minimum available capacity the garage should have.
     * @return a {@code List} consisting of filtered ordered garages.
     */
    public Future<String> getGaragesByAvailableCapacityAscending(String parameters) {
        var availableCapacityAtLeast = StringValidator.convertToInt(parameters);

        var result = await(garageService.getGaragesByAvailableCapacityAscending(availableCapacityAtLeast));

        return async(Extensions.toJSONArray(result, Garage::toJSON).toString());
    }

    /**
     * Returns a filtered {@code List} representing the trucks with the given model.
     *
     * @param parameters model that the truck should contain.
     * @return a {@code List} consisting of filtered trucks.
     */
    public Future<String> getTrucksByModel(String parameters) {
        var result = await(truckService.getTrucksByModel(parameters));

        return async(Extensions.toJSONArray(result, Truck::toJSON).toString());
    }

    /**
     * Returns a filtered {@code List} representing the users with the given year of birth.
     *
     * @param parameters yearOfBirth the year the users should be born in.
     * @return a {@code List} consisting of the filtered users.
     */
    public Future<String> getUsersByYearOfBirth(String parameters) {
        var yearOfBirth = StringValidator.convertToInt(parameters);

        var result = await(userService.getUsersByYearOfBirth(yearOfBirth));

        return async(Extensions.toJSONArray(result, User::toJSON).toString());
    }

    /**
     * Returns a {@code List<UserReport>} consisting of details of completed deliveries for all drivers per all
     * companies and sorted by the given {@code UserReportField}.
     *
     * @param parameters 0 -> field: UserReportField by which the user reports will be filtered.
     *                   1 -> sortType: SortType specifying if sorting will be ascending or descending.
     * @return a sorted {@code List<UserReport>}
     */
    public Future<String> getUsersReportSortedBy(String parameters) {
        var array = new JSONArray(parameters);
        var field = UserReportField.valueOf(array.getString(0));
        var sortType = SortType.valueOf(array.getString(1));

        var result = await(userService.getUsersReportSortedBy(field, sortType));

        return async(Extensions.toJSONArray(result, UserReport::toJSON).toString());
    }

    /**
     * Returns a {@code List<GarageReport>} consisting of the minimum, average, maximum mileage of trucks in every
     * garage, sorted by the given {@code GarageReportField}.
     *
     * @param parameters 0 -> field: GarageReportField by which the garage reports will be filtered.
     *                   1 -> sortType: SortType specifying if sorting will be ascending or descending.
     * @return a sorted {@code List<GarageReport>}
     */
    public Future<String> getGaragesReportSortedBy(String parameters) {
        var array = new JSONArray(parameters);
        var field = GarageReportField.valueOf(array.getString(0));
        var sortType = SortType.valueOf(array.getString(1));

        var result = await(garageService.getGaragesReportSortedBy(field, sortType));

        return async(Extensions.toJSONArray(result, GarageReport::toJSON).toString());
    }

    /**
     * Returns a {@code List<CompanyReport>} consisting of details of completed deliveries for all companies
     * sorted by the given {@code CompanyReportField}.
     *
     * @param parameters 0 -> field: CompanyReportField by which the company reports will be filtered.
     *                   1 -> sortType: SortType specifying if sorting will be ascending or descending.
     * @return a sorted {@code List<CompanyReport>}
     */
    public Future<String> getCompaniesReportSortedBy(String parameters) {
        var array = new JSONArray(parameters);
        var field = CompanyReportField.valueOf(array.getString(0));
        var sortType = SortType.valueOf(array.getString(1));

        var result = await(companyService.getCompaniesReportSortedBy(field, sortType));

        return async(Extensions.toJSONArray(result, CompanyReport::toJSON).toString());
    }
}