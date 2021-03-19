package ro.ubb.truckers.aggregator;

import org.json.JSONArray;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.User;
import ro.ubb.truckers.domain.report.UserReport;
import ro.ubb.truckers.domain.report.sortable.UserReportField;
import ro.ubb.truckers.network.ServerManager;
import ro.ubb.truckers.service.UserServiceInterface;
import ro.ubb.truckers.util.Extensions;
import ro.ubb.truckers.util.constant.SortType;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static ro.ubb.truckers.util.network.Utils.async;
import static ro.ubb.truckers.util.network.Utils.await;

public class UserAggregator implements UserServiceInterface {
    /**
     * Adds a new user into the repository.
     *
     * @param user to add.
     * @throws TruckersException if there is already a user with the same id or if the truckId,
     *                           different from 0, is already in use.
     */
    @Override
    public void addUser(User user) throws TruckersException {
        ServerManager.sendRequest(API_ADD_USER, user.toJSON());
    }

    /**
     * Returns all users.
     *
     * @return a {@code List} consisting of all users.
     */
    @Override
    public Future<List<User>> getAllUsers() {
        return async(() -> {
            var response = await(ServerManager.sendRequest(API_GET_ALL_USERS));

            return Extensions.toJSONArrayStream(response)
                    .map(User::fromJSON)
                    .collect(Collectors.toList());
        });
    }

    /**
     * Updates the truck based on the provided id.
     *
     * @param updatedUser represents the new data for the user with the specified id.
     * @throws TruckersException if there is no user with given id.
     */
    @Override
    public void updateUser(User updatedUser) throws TruckersException {
        ServerManager.sendRequest(API_UPDATE_USER, updatedUser.toJSON());
    }

    /**
     * Deletes a {@code User} entity based on the given id.
     *
     * @param byId the id by which the user will be deleted.
     * @throws TruckersException if no user was found with the specified id.
     */
    @Override
    public void deleteUser(int byId) {
        ServerManager.sendRequest(API_DELETE_USER, String.valueOf(byId));
    }

    /**
     * Returns a filtered ordered {@code List} representing the Users which are born in the specified year.
     *
     * @param yearOfBirth of the users.
     * @return a {@code List} consisting of the filtered ordered users.
     */
    @Override
    public Future<List<User>> getUsersByYearOfBirth(int yearOfBirth) {
        return async(() -> {
            var response = await(ServerManager.sendRequest(API_GET_ALL_USERS, String.valueOf(yearOfBirth)));

            return Extensions.toJSONArrayStream(response)
                    .map(User::fromJSON)
                    .collect(Collectors.toList());
        });
    }

    /**
     * Returns a {@code List<UserReport>} consisting of details of completed deliveries for all drivers per all
     * companies and sorted by the given {@code UserReportField}.
     *
     * @param field    by which the user reports will be filtered.
     * @param sortType specifying if sorting will be ascending or descending.
     * @return a sorted {@code List<UserReport>}
     */
    @Override
    public Future<List<UserReport>> getUsersReportSortedBy(UserReportField field, SortType sortType) {
        return async(() -> {
            var parameters = new JSONArray()
                    .put(field)
                    .put(sortType)
                    .toString();

            var response = await(ServerManager.sendRequest(API_GET_USERS_REPORT_SORTED_BY, parameters));

            return Extensions.toJSONArrayStream(response)
                    .map(UserReport::fromJSON)
                    .collect(Collectors.toList());
        });
    }
}
