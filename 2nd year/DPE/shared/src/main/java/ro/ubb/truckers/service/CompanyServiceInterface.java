package ro.ubb.truckers.service;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Company;
import ro.ubb.truckers.domain.entity.UserCompany;
import ro.ubb.truckers.domain.report.CompanyReport;
import ro.ubb.truckers.domain.report.sortable.CompanyReportField;
import ro.ubb.truckers.util.constant.SortType;

import java.util.List;
import java.util.concurrent.Future;

public interface CompanyServiceInterface {
    String API_ADD_COMPANY = "addCompany";
    String API_ADD_USER_COMPANY = "addUserCompany";
    String API_GET_ALL_COMPANIES = "getAllCompanies";
    String API_UPDATE_COMPANY = "updateCompany";
    String API_DELETE_COMPANY = "deleteCompany";
    String API_GET_COMPANIES_BY_NAME = "getCompaniesByName";
    String API_GET_COMPANIES_REPORT_SORTED_BY = "getCompaniesReportSortedBy";

    /**
     * Adds a new company into the repository.
     *
     * @param company to add.
     * @throws TruckersException if there is already a company with the same id.
     */
    void addCompany(Company company) throws TruckersException;

    /**
     * Adds a UserCompany into the repository.
     *
     * @param userCompany to add.
     * @throws TruckersException if there is already a UserCompany with the same id.
     */

    void addUserCompany(UserCompany userCompany) throws TruckersException;

    /**
     * Returns all companies.
     *
     * @return a {@code List} consisting of all companies.
     */
    Future<List<Company>> getAllCompanies();

    /**
     * Updates an existing company.
     *
     * @param updatedCompany represents the new data for the company with the specified id.
     * @throws TruckersException if there is no company with the given id.
     */
    void updateCompany(Company updatedCompany) throws TruckersException;

    /**
     * Deletes a {@code Company} entity based on the given id.
     *
     * @param byId the id by which the company will be deleted.
     * @throws TruckersException if no company was found with the specified id.
     */
    void deleteCompany(int byId);

    /**
     * Returns a filtered {@code List} representing the companies for which the provided name is
     * a substring of the company's name.
     *
     * @param name that the company should contain.
     * @return a {@code List} consisting of the filtered companies.
     */
    Future<List<Company>> getCompaniesByName(String name);

    /**
     * Returns a {@code List<CompanyReport>} consisting of details of completed deliveries for all companies
     * sorted by the given {@code UserReportField}.
     *
     * @param field    by which the company reports will be filtered.
     * @param sortType specifying if sorting will be ascending or descending.
     * @return a sorted {@code List<CompanyReport>}
     */
    Future<List<CompanyReport>> getCompaniesReportSortedBy(CompanyReportField field, SortType sortType);
}
