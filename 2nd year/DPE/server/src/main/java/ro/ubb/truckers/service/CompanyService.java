package ro.ubb.truckers.service;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Company;
import ro.ubb.truckers.domain.entity.Delivery;
import ro.ubb.truckers.domain.entity.UserCompany;
import ro.ubb.truckers.domain.report.CompanyReport;
import ro.ubb.truckers.domain.report.sortable.CompanyReportField;
import ro.ubb.truckers.util.Extensions;
import ro.ubb.truckers.util.ServiceProvider;
import ro.ubb.truckers.util.constant.SortType;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static ro.ubb.truckers.util.network.Utils.async;

public class CompanyService extends BaseService implements CompanyServiceInterface {
    /**
     * Adds a new company into the repository.
     *
     * @param company to add.
     * @throws TruckersException if there is already a company with the same id.
     */
    @Override
    public void addCompany(Company company) throws TruckersException {
        provider.getCompanyRepository().save(company).ifPresent(existingCompany -> {
            throw new TruckersException("There is already company %s with the id of %s",
                    existingCompany.getName(), existingCompany.getId());
        });
    }

    /**
     * Adds a UserCompany into the repository.
     *
     * @param userCompany to add.
     * @throws TruckersException if there is already a UserCompany with the same id.
     */
    @Override
    public void addUserCompany(UserCompany userCompany) throws TruckersException {
        if (userCompany != null) {
            var userService = ServiceProvider.inject(UserService.class);

            userService.checkIfUserIdExists(userCompany.getUserId());
            checkIfCompanyIdExists(userCompany.getCompanyId());
        }

        provider.getUserCompanyRepository().save(userCompany).ifPresent(existingUserCompany -> {
            throw new TruckersException("There is already a user company with id (%s, %s)",
                    existingUserCompany.getUserId(), existingUserCompany.getCompanyId());
        });
    }

    /**
     * Returns all companies.
     *
     * @return a {@code List} consisting of all companies.
     */
    @Override
    public Future<List<Company>> getAllCompanies() {
        var allCompanies = Extensions.toList(provider.getCompanyRepository().findAll());

        return async(allCompanies);
    }

    /**
     * Updates an existing company.
     *
     * @param updatedCompany represents the new data for the company with the specified id.
     * @throws TruckersException if there is no company with the given id.
     */
    @Override
    public void updateCompany(Company updatedCompany) throws TruckersException {
        provider.getCompanyRepository().update(updatedCompany).orElseThrow(() ->
                new TruckersException("No company found with id %s", updatedCompany.getId()));
    }

    /**
     * Deletes a {@code Company} entity based on the given id.
     *
     * @param byId the id by which the company will be deleted.
     * @throws TruckersException if no company was found with the specified id.
     */
    @Override
    public void deleteCompany(int byId) {
        var deliveryService = ServiceProvider.inject(DeliveryService.class);
        var garageService = ServiceProvider.inject(GarageService.class);

        deliveryService.deleteAllDeliveriesBy(delivery -> delivery.getCompanyId() == byId);
        garageService.deleteAllGaragesByCompanyId(byId);
        deleteUserCompaniesByCompanyId(byId);

        provider.getCompanyRepository().delete(byId).orElseThrow(() ->
                new TruckersException("No company found with id %s", byId));
    }

    /**
     * Returns a filtered {@code List} representing the companies for which the provided name is
     * a substring of the company's name.
     *
     * @param name that the company should contain.
     * @return a {@code List} consisting of the filtered companies.
     */
    @Override
    public Future<List<Company>> getCompaniesByName(String name) {
        var companies = Extensions.toStream(provider.getCompanyRepository().findAll())
                .filter(delivery -> delivery.getName().contains(name))
                .collect(Collectors.toList());

        return async(companies);
    }

    /**
     * Returns a {@code List<CompanyReport>} consisting of details of completed deliveries for all companies
     * sorted by the given {@code UserReportField}.
     *
     * @param field    by which the company reports will be filtered.
     * @param sortType specifying if sorting will be ascending or descending.
     * @return a sorted {@code List<CompanyReport>}
     */
    @Override
    public Future<List<CompanyReport>> getCompaniesReportSortedBy(CompanyReportField field, SortType sortType) {
        final var completedDeliveries = Extensions.toStream(provider.getDeliveryRepository().findAll())
                .filter(Delivery::isDelivered)
                .collect(Collectors.toList());

        var allCompanies = provider.getCompanyRepository().findAll();

        var report = Extensions.toStream(allCompanies)
                // Map to Delivery and apply sort
                .map(company -> new CompanyReport(company, completedDeliveries))
                .sorted(sortType.getComparator(field::getField))
                .collect(Collectors.toList());

        return async(report);

    }

    /**
     * Deletes all {@code UserCompany} entities by the given companyId
     *
     * @param companyId of all UserCompany entities to be deleted.
     */
    public void deleteUserCompaniesByCompanyId(int companyId) {
        var userCompanyRepository = provider.getUserCompanyRepository();
        var userCompanies = userCompanyRepository.findAll();

        Extensions.toStream(userCompanies)
                .filter(userCompany -> userCompany.getCompanyId() == companyId)
                .forEach(userCompany -> userCompanyRepository.delete(userCompany.getId()));
    }

    /**
     * Checks if a company exits with the given id.
     *
     * @param companyId of the company to be checked.
     * @throws TruckersException in case there is no company with the given id.
     */
    public void checkIfCompanyIdExists(int companyId) throws TruckersException {
        if (companyId < 1) {
            return;
        }

        provider.getCompanyRepository()
                .findOne(companyId)
                .orElseThrow(() -> new TruckersException("There is no company with id %s", companyId));
    }
}
