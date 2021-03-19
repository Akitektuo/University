package ro.ubb.truckers.service;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.User;
import ro.ubb.truckers.domain.report.UserReport;
import ro.ubb.truckers.domain.report.sortable.UserReportField;
import ro.ubb.truckers.util.constant.SortType;

import java.util.List;
import java.util.concurrent.Future;

public interface UserServiceInterface {
    String API_ADD_USER = "addUser";
    String API_GET_ALL_USERS = "getAllUsers";
    String API_UPDATE_USER = "updateUser";
    String API_DELETE_USER = "deleteUser";
    String API_GET_USERS_BY_YEAR_OF_BIRTH = "getUsersByYearOfBirth";
    String API_GET_USERS_REPORT_SORTED_BY = "getUsersReportSortedBy";

    /**
     * Adds a new user into the repository.
     *
     * @param user to add.
     * @throws TruckersException if there is already a user with the same id or if the truckId,
     *                           different from 0, is already in use.
     */
    void addUser(User user) throws TruckersException;

    /**
     * Returns all users.
     *
     * @return a {@code List} consisting of all users.
     */
    Future<List<User>> getAllUsers();

    /**
     * Updates the truck based on the provided id.
     *
     * @param updatedUser represents the new data for the user with the specified id.
     * @throws TruckersException if there is no user with given id.
     */
    void updateUser(User updatedUser) throws TruckersException;

    /**
     * Deletes a {@code User} entity based on the given id.
     *
     * @param byId the id by which the user will be deleted.
     * @throws TruckersException if no user was found with the specified id.
     */
    void deleteUser(int byId);

    /**
     * Returns a filtered ordered {@code List} representing the Users which are born in the specified year.
     *
     * @param yearOfBirth of the users.
     * @return a {@code List} consisting of the filtered ordered users.
     */
    Future<List<User>> getUsersByYearOfBirth(int yearOfBirth);

    /**
     * Returns a {@code List<UserReport>} consisting of details of completed deliveries for all drivers per all
     * companies and sorted by the given {@code UserReportField}.
     *
     * @param field    by which the user reports will be filtered.
     * @param sortType specifying if sorting will be ascending or descending.
     * @return a sorted {@code List<UserReport>}
     */
    Future<List<UserReport>> getUsersReportSortedBy(UserReportField field, SortType sortType);
}
