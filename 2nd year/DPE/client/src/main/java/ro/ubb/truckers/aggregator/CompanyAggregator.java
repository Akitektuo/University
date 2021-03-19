package ro.ubb.truckers.aggregator;

import org.json.JSONArray;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Company;
import ro.ubb.truckers.domain.entity.UserCompany;
import ro.ubb.truckers.domain.report.CompanyReport;
import ro.ubb.truckers.domain.report.sortable.CompanyReportField;
import ro.ubb.truckers.network.ServerManager;
import ro.ubb.truckers.service.CompanyServiceInterface;
import ro.ubb.truckers.util.Extensions;
import ro.ubb.truckers.util.constant.SortType;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static ro.ubb.truckers.util.network.Utils.async;
import static ro.ubb.truckers.util.network.Utils.await;

public class CompanyAggregator implements CompanyServiceInterface {
    /**
     * Adds a new company into the repository.
     *
     * @param company to add.
     * @throws TruckersException if there is already a company with the same id.
     */
    @Override
    public void addCompany(Company company) throws TruckersException {
        ServerManager.sendRequest(API_ADD_COMPANY, company.toJSON());
    }

    /**
     * Adds a UserCompany into the repository.
     *
     * @param userCompany to add.
     * @throws TruckersException if there is already a UserCompany with the same id.
     */
    @Override
    public void addUserCompany(UserCompany userCompany) throws TruckersException {
        ServerManager.sendRequest(API_ADD_USER_COMPANY, userCompany.toJSON());
    }

    /**
     * Returns all companies.
     *
     * @return a {@code List} consisting of all companies.
     */
    @Override
    public Future<List<Company>> getAllCompanies() {
        return async(() -> {
            var response = await(ServerManager.sendRequest(API_GET_ALL_COMPANIES));

            return Extensions.toJSONArrayStream(response)
                    .map(Company::fromJSON)
                    .collect(Collectors.toList());
        });
    }

    /**
     * Updates an existing company.
     *
     * @param updatedCompany represents the new data for the company with the specified id.
     * @throws TruckersException if there is no company with the given id.
     */
    @Override
    public void updateCompany(Company updatedCompany) throws TruckersException {
        ServerManager.sendRequest(API_UPDATE_COMPANY, updatedCompany.toJSON());
    }

    /**
     * Deletes a {@code Company} entity based on the given id.
     *
     * @param byId the id by which the company will be deleted.
     * @throws TruckersException if no company was found with the specified id.
     */
    @Override
    public void deleteCompany(int byId) {
        ServerManager.sendRequest(API_DELETE_COMPANY, String.valueOf(byId));
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
        return async(() -> {
            var response = await(ServerManager.sendRequest(API_GET_COMPANIES_BY_NAME, name));

            return Extensions.toJSONArrayStream(response)
                    .map(Company::fromJSON)
                    .collect(Collectors.toList());
        });
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
        return async(() -> {
            var parameters = new JSONArray()
                    .put(field)
                    .put(sortType)
                    .toString();

            var response = await(ServerManager.sendRequest(API_GET_COMPANIES_REPORT_SORTED_BY, parameters));

            return Extensions.toJSONArrayStream(response)
                    .map(CompanyReport::fromJSON)
                    .collect(Collectors.toList());
        });
    }
}
