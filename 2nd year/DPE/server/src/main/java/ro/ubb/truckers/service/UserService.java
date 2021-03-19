package ro.ubb.truckers.service;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Delivery;
import ro.ubb.truckers.domain.entity.User;
import ro.ubb.truckers.domain.report.UserReport;
import ro.ubb.truckers.domain.report.sortable.UserReportField;
import ro.ubb.truckers.util.Extensions;
import ro.ubb.truckers.util.Pair;
import ro.ubb.truckers.util.ServiceProvider;
import ro.ubb.truckers.util.constant.SortType;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static ro.ubb.truckers.util.network.Utils.async;

public class UserService extends BaseService implements UserServiceInterface {
    /**
     * Adds a new user into the repository.
     *
     * @param user to add.
     * @throws TruckersException if there is already a user with the same id or if the truckId,
     *                           different from 0, is already in use.
     */
    @Override
    public void addUser(User user) throws TruckersException {
        if (user != null) {
            var truckService = ServiceProvider.inject(TruckService.class);

            truckService.checkIfTruckIdExists(user.getTruckId());
            checkTruckIdInUse(user.getTruckId());
        }

        provider.getUserRepository().save(user).ifPresent(existingUser -> {
            throw new TruckersException("There is already user %s with id %s",
                    existingUser.getFullName(), existingUser.getId());
        });
    }

    /**
     * Returns all users.
     *
     * @return a {@code List} consisting of all users.
     */
    @Override
    public Future<List<User>> getAllUsers() {
        var allUsers = Extensions.toList(provider.getUserRepository().findAll());

        return async(allUsers);
    }

    /**
     * Updates the truck based on the provided id.
     *
     * @param updatedUser represents the new data for the user with the specified id.
     * @throws TruckersException if there is no user with given id.
     */
    @Override
    public void updateUser(User updatedUser) throws TruckersException {
        if (updatedUser != null) {
            var truckService = ServiceProvider.inject(TruckService.class);

            truckService.checkIfTruckIdExists(updatedUser.getTruckId());
            checkTruckIdInUse(updatedUser.getTruckId(), updatedUser.getId());
        }

        provider.getUserRepository().update(updatedUser).orElseThrow(() ->
                new TruckersException("No user found with id %s", updatedUser.getId()));
    }

    /**
     * Deletes a {@code User} entity based on the given id.
     *
     * @param byId the id by which the user will be deleted.
     * @throws TruckersException if no user was found with the specified id.
     */
    @Override
    public void deleteUser(int byId) {
        var truckService = ServiceProvider.inject(TruckService.class);

        truckService.deallocateUserId(byId);
        deleteUserCompaniesByUserId(byId);

        provider.getUserRepository().delete(byId).orElseThrow(() ->
                new TruckersException("No user found with id %s", byId));
    }

    /**
     * Returns a filtered ordered {@code List} representing the Users which are born in the specified year.
     *
     * @param yearOfBirth of the users.
     * @return a {@code List} consisting of the filtered ordered users.
     */
    @Override
    public Future<List<User>> getUsersByYearOfBirth(int yearOfBirth) {
        var users = Extensions.toStream(provider.getUserRepository().findAll())
                .filter(user -> user.getDateOfBirth().getYear() == yearOfBirth)
                .sorted(Comparator.comparingLong(user -> user.getDateOfBirth().toEpochDay()))
                .collect(Collectors.toList());

        return async(users);
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
        final var completedDeliveries = Extensions.toStream(provider.getDeliveryRepository().findAll())
                .filter(Delivery::isDelivered)
                .collect(Collectors.toList());

        var allUserCompanies = provider.getUserCompanyRepository().findAll();

        var report = Extensions.toStream(allUserCompanies)
                .filter(userCompany -> !userCompany.isManager())

                // Mapping UserCompany to Pair<User, Company>
                .map(userCompany -> {
                    var user = provider.getUserRepository().findOne(userCompany.getUserId());
                    var company = provider.getCompanyRepository().findOne(userCompany.getCompanyId());

                    return new Pair<>(user, company);
                })
                .filter(pair -> pair.getFirst().isPresent() && pair.getSecond().isPresent())
                .map(pair -> new Pair<>(pair.getFirst().get(), pair.getSecond().get()))

                // Filter users with trucks and sort by user ID
                .filter(pair -> pair.getFirst().getTruckId() != 0)
                .sorted(Comparator.comparingInt(pair -> pair.getFirst().getId()))

                // Map to UserReport and apply sort
                .map(pair -> new UserReport(pair, completedDeliveries))
                .sorted(sortType.getComparator(field::getField))
                .collect(Collectors.toList());

        return async(report);
    }

    /**
     * Deallocates the truckId for the user that has it.
     *
     * @param truckId to deallocate.
     */
    public void deallocateTruckId(int truckId) {
        if (truckId < 1) {
            return;
        }

        var allUsers = provider.getUserRepository().findAll();

        Extensions.toStream(allUsers)
                .filter(user -> user.getTruckId() == truckId)
                .findAny()
                .ifPresent(user -> deleteUser(user.getId()));
    }

    /**
     * Deletes all {@code UserCompany} entities by the given userId
     *
     * @param userId of all UserCompany entities to be deleted.
     */
    public void deleteUserCompaniesByUserId(int userId) {
        var userCompanyRepository = provider.getUserCompanyRepository();
        var userCompanies = userCompanyRepository.findAll();

        Extensions.toStream(userCompanies)
                .filter(userCompany -> userCompany.getUserId() == userId)
                .forEach(userCompany -> userCompanyRepository.delete(userCompany.getId()));
    }

    /**
     * Checks if a user exists with the given id.
     *
     * @param userId of an existing user.
     * @throws TruckersException in case there is no user with the given id.
     */
    public void checkIfUserIdExists(int userId) throws TruckersException {
        if (userId < 1) {
            return;
        }

        provider.getUserRepository()
                .findOne(userId)
                .orElseThrow(() -> new TruckersException("There is no user with id %s", userId));
    }

    /**
     * Checks if a user with given truckId exists excluding the specified userId.
     *
     * @param truckId       to look for.
     * @param excludeUserId is the user ID to exclude.
     * @throws TruckersException if there is already an user with same truckId, and its id is not ignored.
     */
    private void checkTruckIdInUse(int truckId, int excludeUserId) throws TruckersException {
        if (truckId < 1) {
            return;
        }

        var allUsers = provider.getUserRepository().findAll();

        Extensions.toStream(allUsers)
                .filter(existingUser -> existingUser.getId() != excludeUserId &&
                        existingUser.getTruckId() == truckId)
                .findAny()
                .ifPresent(existingUser -> {
                    throw new TruckersException("The user %s with id %s and truckId %s already exists",
                            existingUser.getFullName(), existingUser.getId(), existingUser.getTruckId());
                });
    }

    /**
     * Checks if a user with a given truckId exists.
     *
     * @param truckId to look for.
     * @throws TruckersException if there is already an user with same truckId.
     */
    private void checkTruckIdInUse(int truckId) throws TruckersException {
        if (truckId < 1) {
            return;
        }

        checkTruckIdInUse(truckId, 0);
    }
}
