package truckers.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Company;
import ro.ubb.truckers.domain.entity.Delivery;
import ro.ubb.truckers.domain.entity.UserCompany;
import ro.ubb.truckers.domain.report.sortable.CompanyReportField;
import ro.ubb.truckers.domain.validator.ValidatorException;
import ro.ubb.truckers.repository.RepositoryProvider;
import ro.ubb.truckers.service.CompanyService;
import ro.ubb.truckers.util.Pair;
import ro.ubb.truckers.util.ServiceProvider;
import ro.ubb.truckers.util.constant.SortType;
import truckers.util.Seeder;
import truckers.util.Util;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static ro.ubb.truckers.util.network.Utils.await;

class CompanyServiceTest {
    private final int ID = 1;
    private final String NAME = "test";

    private final int USER_ID = 1;
    private final int COMPANY_ID = 2;
    private final boolean IS_MANAGER = false;

    private CompanyService service;

    @BeforeEach
    void setUp() {
        Util.initializeServiceProvider();
        service = new CompanyService();
    }

    @AfterEach
    void tearDown() {
        service = null;
        Util.destroyServiceProvider();
    }

    @Test
    void addCompany_IsNull() {
        // Arrange
        Company company = null;
        var wasExceptionThrown = false;

        // Act
        try {
            service.addCompany(company);
        } catch (IllegalArgumentException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addCompany_IsDuplicate() {
        // Arrange
        var company = new Company(ID, NAME);
        var wasExceptionThrown = false;

        // Act
        service.addCompany(company);
        try {
            service.addCompany(company);
        } catch (TruckersException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addCompany_IdIsInvalid() {
        // Arrange
        var company = new Company(0, NAME);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addCompany(company);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addCompany_NameIsInvalid() {
        // Arrange
        var company = new Company(ID, "");
        var wasExceptionThrown = false;

        // Act
        try {
            service.addCompany(company);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addCompany() {
        // Arrange
        var company = new Company(ID, NAME);
        var companyRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getCompanyRepository();

        // Act
        service.addCompany(company);
        var found = companyRepository.findOne(ID);

        // Assert
        assertTrue(found.isPresent());
    }

    @Test
    void addUserCompany_UserIdIsInvalid() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID);

        var userCompany = new UserCompany(0, COMPANY_ID, IS_MANAGER);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addUserCompany(userCompany);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addUserCompany_CompanyIdIsInvalid() {
        // Arrange
        Seeder.seedUser(USER_ID);

        var userCompany = new UserCompany(USER_ID, 0, IS_MANAGER);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addUserCompany(userCompany);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addUserCompany() {
        // Arrange
        Seeder.seedUser(USER_ID);
        Seeder.seedCompany(COMPANY_ID);

        var userCompany = new UserCompany(USER_ID, COMPANY_ID, IS_MANAGER);
        var userCompanyRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getUserCompanyRepository();

        // Act
        service.addUserCompany(userCompany);
        var found = userCompanyRepository.findOne(new Pair<>(USER_ID, COMPANY_ID));

        // Assert
        assertTrue(found.isPresent());
    }

    @Test
    void getAllCompanies() {
        // Arrange
        var companyRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getCompanyRepository();
        var company1 = new Company(ID, NAME + 1);
        var company2 = new Company(ID + 1, NAME + 2);
        var company3 = new Company(ID + 2, NAME + 3);

        // Act
        companyRepository.save(company1);
        companyRepository.save(company2);
        companyRepository.save(company3);

        var result = await(service.getAllCompanies());

        // Assert
        assertEquals(3, result.size());
    }

    @Test
    void updateCompany_NotExistent() {
        // Arrange
        var company = new Company(ID, NAME);
        var wasExceptionThrown = false;

        // Act
        try {
            service.updateCompany(company);
        } catch (TruckersException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void updateCompany() {
        // Arrange
        var company = new Company(ID, NAME);
        var companyRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getCompanyRepository();
        AtomicBoolean wasNotUpdated = new AtomicBoolean(false);

        // Act
        companyRepository.save(company);
        company = new Company(ID, NAME + 1);

        service.updateCompany(company);

        companyRepository.findOne(ID).ifPresent(found -> wasNotUpdated.set(
                wasNotUpdated.get() || found.getName().equals(NAME)));

        // Assert
        assertFalse(wasNotUpdated.get());
    }

    @Test
    void deleteCompany_NoCompanyWithId() {
        // Arrange
        var wasExceptionThrown = false;

        // Act
        try {
            service.deleteCompany(ID);
        } catch (TruckersException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void deleteCompany() {
        // Arrange
        var companyRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getCompanyRepository();
        var company = new Company(ID, NAME);

        // Act
        companyRepository.save(company);

        service.deleteCompany(ID);

        var wasDeleted = companyRepository.findOne(ID).isEmpty();

        // Assert
        Assertions.assertTrue(wasDeleted);
    }

    @Test
    void getCompaniesByName() {
        // Arrange
        var companyRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getCompanyRepository();
        var company1 = new Company(ID, NAME);
        var company2 = new Company(ID + 1, NAME + 1);
        var company3 = new Company(ID + 2, NAME + 2);

        // Act
        companyRepository.save(company1);
        companyRepository.save(company2);
        companyRepository.save(company3);

        var result = await(service.getCompaniesByName(NAME + 1));

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void getCompaniesReportsSortedBy() {
        // Arrange
        var provider = ServiceProvider.inject(RepositoryProvider.class);
        var companyRepository = provider.getCompanyRepository();
        var deliveryRepository = provider.getDeliveryRepository();

        var truckId = 1;
        var companyId1 = 1;
        var companyId2 = 2;
        var companyId3 = 3;

        var deliveryCompany1 = new Delivery(
                1, "A", "B", 100, true, "goodies", truckId, companyId1);
        var deliveryCompany2 = new Delivery(
                2, "A", "B", 200, true, "goodies", truckId, companyId2);
        var deliveryCompany3 = new Delivery(
                3, "A", "B", 300, true, "goodies", truckId, companyId3);
        var deliveryNotCompletedCompany1 = new Delivery(
                4, "A", "B", 400, false, "goodies", truckId, companyId1);
        var deliveryNotCompletedCompany2 = new Delivery(
                5, "A", "B", 500, false, "goodies", truckId, companyId2);
        var secondDeliveryCompany1 = new Delivery(
                6, "A", "B", 50, true, "goodies", truckId, companyId1);

        // Act
        companyRepository.save(new Company(companyId1, "Company1"));
        companyRepository.save(new Company(companyId2, "Company2"));
        companyRepository.save(new Company(companyId3, "Company3"));
        deliveryRepository.save(deliveryCompany1);
        deliveryRepository.save(deliveryCompany2);
        deliveryRepository.save(deliveryCompany3);
        deliveryRepository.save(deliveryNotCompletedCompany1);
        deliveryRepository.save(deliveryNotCompletedCompany2);
        deliveryRepository.save(secondDeliveryCompany1);

        var report = await(service.getCompaniesReportSortedBy(
                CompanyReportField.COUNT,
                SortType.DESCENDING));

        // Assert
        assertEquals(3, report.size());
        assertTrue(report.get(0).getNumberOfDeliveries() > report.get(1).getNumberOfDeliveries());
        assertEquals(75, report.get(0).getAverageOfDistance());
    }
}