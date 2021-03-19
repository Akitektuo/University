package ro.ubb.truckers.controller;

import ro.ubb.truckers.aggregator.*;
import ro.ubb.truckers.domain.entity.*;
import ro.ubb.truckers.domain.report.CompanyReport;
import ro.ubb.truckers.domain.report.GarageReport;
import ro.ubb.truckers.domain.report.UserReport;
import ro.ubb.truckers.domain.report.sortable.CompanyReportField;
import ro.ubb.truckers.domain.report.sortable.GarageReportField;
import ro.ubb.truckers.domain.report.sortable.UserReportField;
import ro.ubb.truckers.util.constant.SortType;

import java.time.LocalDate;
import java.util.List;

import static ro.ubb.truckers.util.network.Utils.await;

public class Controller {
    private final CompanyAggregator companyAggregator = new CompanyAggregator();
    private final DeliveryAggregator deliveryAggregator = new DeliveryAggregator();
    private final GarageAggregator garageAggregator = new GarageAggregator();
    private final TruckAggregator truckAggregator = new TruckAggregator();
    private final UserAggregator userAggregator = new UserAggregator();

    /**
     * Adds a new user into the application.
     *
     * @param id          of the user
     * @param firstName   of the user
     * @param lastName    of the user
     * @param email       of the user
     * @param password    of the user
     * @param dateOfBirth of the user
     * @param truckId     of the user
     */
    public void addUser(int id, String firstName, String lastName, String email, String password, LocalDate dateOfBirth, int truckId) {
        userAggregator.addUser(new User(id, firstName, lastName, email, password, dateOfBirth, truckId));
    }

    /**
     * Adds a new company into the application.
     *
     * @param id   of company.
     * @param name of company.
     */
    public void addCompany(int id, String name) {
        companyAggregator.addCompany(new Company(id, name));
    }

    /**
     * Adds a new garage into the application.
     *
     * @param id              of the garage
     * @param location        of the garage
     * @param capacity        of the garage
     * @param companyId       of the garage
     */
    public void addGarage(int id, String location, int capacity, int companyId) {
        garageAggregator.addGarage(new Garage(id, location, capacity, 0, companyId));
    }


    /**
     * Adds a new truck into the application.
     *
     * @param id           of the truck
     * @param model        of the truck
     * @param licensePlate of the truck
     * @param mileage      of the truck
     * @param driverId     of the truck
     * @param garageId     of the truck
     */
    public void addTruck(int id, String model, String licensePlate, int mileage, int driverId, int garageId) {
        truckAggregator.addTruck(new Truck(id, model, licensePlate, mileage, driverId, garageId));
    }

    /**
     * Adds a new delivery into the application.
     *
     * @param id          of the delivery
     * @param origin      of the delivery
     * @param destination of the delivery
     * @param distance    of the delivery
     * @param delivered   representing the status of the delivery
     * @param load        of the delivery
     * @param truckId     of the delivery
     * @param companyId   of the delivery
     */
    public void addDelivery(int id, String origin, String destination, int distance,
                            boolean delivered, String load, int truckId, int companyId) {
        deliveryAggregator.addDelivery(new Delivery(id, origin, destination, distance,
                delivered, load, truckId, companyId));
    }

    /**
     * Adds a new UserCompany into the application.
     *
     * @param userId    of the UserCompany
     * @param companyId of the UserCompany
     * @param manager   if the user is a manager of the company
     */
    public void addUserCompany(int userId, int companyId, boolean manager) {
        companyAggregator.addUserCompany(new UserCompany(userId, companyId, manager));
    }

    /**
     * Returns all companies.
     *
     * @return a {@code List} consisting of all companies.
     */
    public List<Company> getAllCompanies() {
        return await(companyAggregator.getAllCompanies());
    }

    /**
     * Returns all trucks.
     *
     * @return a {@code List} consisting of all trucks.
     */
    public List<Truck> getAllTrucks() {
        return await(truckAggregator.getAllTrucks());
    }

    /**
     * Returns all users.
     *
     * @return a {@code List} consisting of all users.
     */
    public List<User> getAllUsers() {
        return await(userAggregator.getAllUsers());
    }

    /**
     * Returns all deliveries.
     *
     * @return a {@code List} consisting of all deliveries.
     */
    public List<Delivery> getAllDeliveries() {
        return await(deliveryAggregator.getAllDeliveries());
    }

    /**
     * Returns all garages.
     *
     * @return a {@code List} consisting of all garages
     */
    public List<Garage> getAllGarages() {
        return await(garageAggregator.getAllGarages());
    }

    /**
     * Updates the given garage based on the provided id with the new given data.
     *
     * @param existingId         of the garage.
     * @param newLocation        of the garage to update.
     * @param newCapacity        of the garage to update.
     * @param newCompanyId       of the garage to update.
     */
    public void updateGarage(int existingId, String newLocation, int newCapacity, int newCompanyId) {
        garageAggregator.updateGarage(new Garage(existingId, newLocation, newCapacity, 0, newCompanyId));
    }

    /**
     * Updates the given company based on the provided id with the new given data.
     *
     * @param existingId of the company.
     * @param newName    of the company to update.
     */
    public void updateCompany(int existingId, String newName) {
        companyAggregator.updateCompany(new Company(existingId, newName));
    }

    /**
     * Updates the truck identified by existingId with the new given data.
     *
     * @param existingId      of the truck.
     * @param newModel        of the truck to update.
     * @param newLicensePlate of the truck to update.
     * @param newMileage      of the truck to update.
     * @param newDriverId     of the truck to update.
     * @param newGarageId     of the truck to update.
     */
    public void updateTruck(int existingId, String newModel, String newLicensePlate, int newMileage, int newDriverId, int newGarageId) {
        truckAggregator.updateTruck(new Truck(existingId, newModel, newLicensePlate, newMileage, newDriverId, newGarageId));
    }

    /**
     * Updates the user identified by existingId with the new given data.
     *
     * @param existingId     of the user.
     * @param newFirstName   of the user to update.
     * @param newLastName    of the user to update.
     * @param newEmail       of the user to update.
     * @param newPassword    of the user to update.
     * @param newDateOfBirth of the user to update.
     * @param newTruckId     of the user to update.
     */
    public void updateUser(int existingId, String newFirstName, String newLastName, String newEmail, String newPassword,
                           LocalDate newDateOfBirth, int newTruckId) {
        userAggregator.updateUser(new User(existingId, newFirstName, newLastName, newEmail, newPassword, newDateOfBirth, newTruckId));
    }

    /**
     * Updated the delivery identified by existingId with the new given data.
     *
     * @param existingId        of the delivery
     * @param newOrigin         of the delivery to update
     * @param newDestination    of the delivery to update
     * @param newDistance       of the delivery to update
     * @param newDeliveryStatus of the delivery to update
     * @param newLoad           of the delivery to update
     * @param newTruckId        of the delivery to update
     * @param newCompanyId      of the delivery to update
     */
    public void updateDelivery(int existingId, String newOrigin, String newDestination, int newDistance,
                               boolean newDeliveryStatus, String newLoad, int newTruckId, int newCompanyId) {
        deliveryAggregator.updateDelivery(new Delivery(existingId, newOrigin, newDestination, newDistance,
                newDeliveryStatus, newLoad, newTruckId, newCompanyId));
    }

    /**
     * Deletes a {@code Truck} entity based on the given id.
     *
     * @param byId the id by which the truck will de deleted.
     */
    public void deleteTruck(int byId) {
        truckAggregator.deleteTruck(byId);
    }

    /**
     * Deletes a {@code Company} entity based on the given id.
     *
     * @param byId the id by which the company will be deleted.
     */
    public void deleteCompany(int byId) {
        companyAggregator.deleteCompany(byId);
    }

    /**
     * Deletes a {@code User} entity based on the given id.
     *
     * @param byId the id by which the user will de deleted.
     */
    public void deleteUser(int byId) {
        userAggregator.deleteUser(byId);
    }

    /**
     * Deletes a {@code Delivery} entity based on the given id.
     *
     * @param byId the id by which the delivery will de deleted.
     */
    public void deleteDelivery(int byId) {
        deliveryAggregator.deleteDelivery(byId);
    }

    /**
     * Deletes a {@code Garage} entity based on the given id
     *
     * @param byId the id by which the user will be deleted.
     */
    public void deleteGarage(int byId) {
        garageAggregator.deleteGarage(byId);
    }

    /**
     * Returns a filtered {@code List} representing the deliveries for which the provided origin and destination are
     * substrings of the delivery's origin and destination.
     *
     * @param origin      that the delivery should contain.
     * @param destination that the delivery should contain.
     * @return a {@code List} consisting of the filtered deliveries.
     */
    public List<Delivery> getDeliveriesByOriginAndDestination(String origin, String destination) {
        return await(deliveryAggregator.getDeliveriesByOriginAndDestination(origin, destination));
    }

    /**
     * Returns a filtered {@code List} representing the companies for which the provided name is
     * a substring of the company's name.
     *
     * @param name that the company should contain.
     * @return a {@code List} consisting of the filtered companies.
     */
    public List<Company> getCompaniesByName(String name) {
        return await(companyAggregator.getCompaniesByName(name));
    }

    /**
     * Returns a filtered ordered {@code List} representing the Garages for which the available capacity is greater
     * or equal to availableCapacityAtLeast.
     *
     * @param availableCapacityAtLeast minimum available capacity the garage should have.
     * @return a {@code List} consisting of filtered ordered garages.
     */
    public List<Garage> getGaragesByAvailableCapacityAscending(int availableCapacityAtLeast) {
        return await(garageAggregator.getGaragesByAvailableCapacityAscending(availableCapacityAtLeast));
    }

    /**
     * Returns a filtered {@code List} representing the trucks with the given model.
     *
     * @param model that the truck should contain.
     * @return a {@code List} consisting of filtered trucks.
     */
    public List<Truck> getTrucksByModel(String model) {
        return await(truckAggregator.getTrucksByModel(model));
    }

    /**
     * Returns a filtered {@code List} representing the users with the given year of birth.
     *
     * @param yearOfBirth the year the users should be born in.
     * @return a {@code List} consisting of the filtered users.
     */
    public List<User> getUsersByYearOfBirth(int yearOfBirth) {
        return await(userAggregator.getUsersByYearOfBirth(yearOfBirth));
    }

    /**
     * Returns a {@code List<UserReport>} consisting of details of completed deliveries for all drivers per all
     * companies and sorted by the given {@code UserReportField}.
     *
     * @param field    by which the user reports will be filtered.
     * @param sortType specifying if sorting will be ascending or descending.
     * @return a sorted {@code List<UserReport>}
     */
    public List<UserReport> getUsersReportSortedBy(UserReportField field, SortType sortType) {
        return await(userAggregator.getUsersReportSortedBy(field, sortType));
    }

    /**
     * Returns a {@code List<GarageReport>} consisting of the minimum, average, maximum mileage of trucks in every
     * garage, sorted by the given {@code GarageReportField}.
     *
     * @param field    by which the garage reports will be filtered.
     * @param sortType specifying if sorting will be ascending or descending.
     * @return a sorted {@code List<GarageReport>}
     */
    public List<GarageReport> getGaragesReportSortedBy(GarageReportField field, SortType sortType) {
        return await(garageAggregator.getGaragesReportSortedBy(field, sortType));
    }

    /**
     * Returns a {@code List<CompanyReport>} consisting of details of completed deliveries for all companies
     * sorted by the given {@code CompanyReportField}.
     *
     * @param field    by which the company reports will be filtered.
     * @param sortType specifying if sorting will be ascending or descending.
     * @return a sorted {@code List<CompanyReport>}
     */
    public List<CompanyReport> getCompaniesReportSortedBy(CompanyReportField field, SortType sortType) {
        return await(companyAggregator.getCompaniesReportSortedBy(field, sortType));
    }
}