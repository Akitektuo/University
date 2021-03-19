package truckers.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Company;
import ro.ubb.truckers.domain.entity.Delivery;
import ro.ubb.truckers.domain.entity.User;
import ro.ubb.truckers.domain.entity.UserCompany;
import ro.ubb.truckers.domain.report.sortable.UserReportField;
import ro.ubb.truckers.domain.validator.ValidatorException;
import ro.ubb.truckers.repository.RepositoryProvider;
import ro.ubb.truckers.service.UserService;
import ro.ubb.truckers.util.ServiceProvider;
import ro.ubb.truckers.util.constant.SortType;
import truckers.util.Seeder;
import truckers.util.Util;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ro.ubb.truckers.util.network.Utils.await;

class UserServiceTest {
    private final int ID = 1;
    private final String FIRST_NAME = "Test";
    private final String LAST_NAME = "User";
    private final String EMAIL = "mail@x.com";
    private final String PASSWORD = "qwerty";
    private final LocalDate DATE_OF_BIRTH = LocalDate.of(1999, 12, 24);
    private final int TRUCK_ID = 7;

    private UserService service;

    @BeforeEach
    void setUp() {
        Util.initializeServiceProvider();
        service = new UserService();
    }

    @AfterEach
    void tearDown() {
        service = null;
        Util.destroyServiceProvider();
    }

    @Test
    void addUser_IsNull() {
        // Arrange
        User user = null;
        var wasExceptionThrown = false;

        // Act
        try {
            service.addUser(user);
        } catch (IllegalArgumentException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addUser_IsDuplicate() {
        // Arrange
        Seeder.seedTruck(TRUCK_ID);

        var user = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, DATE_OF_BIRTH, TRUCK_ID);
        var wasExceptionThrown = false;

        // Act
        service.addUser(user);
        try {
            service.addUser(user);
        } catch (TruckersException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addUser_TruckIdIsDuplicate() {
        // Arrange
        Seeder.seedTruck(TRUCK_ID);

        var user = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, DATE_OF_BIRTH, TRUCK_ID);
        var userDuplicateTruckId = new User(ID + 1, "a", "b", "c@yahoo.com", "passwd", DATE_OF_BIRTH, TRUCK_ID);
        var wasExceptionThrown = false;

        // Act
        service.addUser(user);
        try {
            service.addUser(userDuplicateTruckId);
        } catch (TruckersException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addUser_IdIsInvalid() {
        // Arrange
        Seeder.seedTruck(TRUCK_ID);

        var user = new User(0, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, DATE_OF_BIRTH, TRUCK_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addUser(user);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addUser_FirstNameIsInvalid() {
        // Arrange
        Seeder.seedTruck(TRUCK_ID);

        var user = new User(ID, "", LAST_NAME, EMAIL, PASSWORD, DATE_OF_BIRTH, TRUCK_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addUser(user);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addUser_LastNameIsInvalid() {
        // Arrange
        Seeder.seedTruck(TRUCK_ID);

        var user = new User(ID, FIRST_NAME, "", EMAIL, PASSWORD, DATE_OF_BIRTH, TRUCK_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addUser(user);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addUser_EmailIsInvalid() {
        // Arrange
        Seeder.seedTruck(TRUCK_ID);

        var user = new User(ID, FIRST_NAME, LAST_NAME, "invalid_mail", PASSWORD, DATE_OF_BIRTH, TRUCK_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addUser(user);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addUser_PasswordIsInvalid() {
        // Arrange
        Seeder.seedTruck(TRUCK_ID);

        var user = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, "", DATE_OF_BIRTH, TRUCK_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addUser(user);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addUser_DateOfBirthIsInvalid() {
        // Arrange
        Seeder.seedTruck(TRUCK_ID);

        var user = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, LocalDate.of(2010, 12, 24), TRUCK_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addUser(user);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addUser_TruckIdIsInvalid() {
        // Arrange
        var user = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, DATE_OF_BIRTH, -1);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addUser(user);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addUser() {
        // Arrange
        Seeder.seedTruck(TRUCK_ID);

        var user = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, DATE_OF_BIRTH, TRUCK_ID);
        var userRepository = ServiceProvider.inject(RepositoryProvider.class).getUserRepository();

        // Act
        service.addUser(user);
        var found = userRepository.findOne(ID);

        // Assert
        assertTrue(found.isPresent());
    }

    @Test
    void getAllUsers() {
        // Arrange
        var userRepository = ServiceProvider.inject(RepositoryProvider.class).getUserRepository();
        var user1 = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, DATE_OF_BIRTH, TRUCK_ID);
        var user2 = new User(ID + 1, "Adrian", "Condrea", "adrian@yahoo.com", "parola1", LocalDate.of(2000, 8, 7), TRUCK_ID + 1);
        var user3 = new User(ID + 2, "Mihai", "Popescu", "mihai@yahoo.com", "parola2", LocalDate.of(1970, 4, 5), TRUCK_ID + 2);

        // Act
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        var result = await(service.getAllUsers());

        // Assert
        assertEquals(3, result.size());
    }

    @Test
    void deleteUser_NoUserWithId() {
        // Arrange
        var wasExceptionThrown = false;

        // Act
        try {
            service.deleteUser(ID);
        } catch (TruckersException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void deleteUser() {
        // Arrange
        var userRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getUserRepository();
        var user = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, DATE_OF_BIRTH, TRUCK_ID);

        // Act
        userRepository.save(user);

        service.deleteUser(ID);

        var wasDeleted = userRepository.findOne(ID).isEmpty();

        // Assert
        Assertions.assertTrue(wasDeleted);
    }

    @Test
    void updateUser_NotExistent() {
        // Arrange
        var user = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, DATE_OF_BIRTH, TRUCK_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.updateUser(user);
        } catch (TruckersException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void updateUser() {
        // Arrange
        Seeder.seedTruck(5);

        var userRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getUserRepository();
        var user = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, DATE_OF_BIRTH, TRUCK_ID);
        AtomicBoolean wasUpdated = new AtomicBoolean(false);

        // Act
        userRepository.save(user);
        user = new User(ID, "AT", "AR", "ATAR@gmail.com", "ATAR!!100",
                LocalDate.of(1930, 1, 1), 5);

        service.updateUser(user);

        userRepository.findOne(ID).ifPresent(found -> wasUpdated.set(found.getTruckId() != TRUCK_ID));

        // Assert
        assertTrue(wasUpdated.get());
    }

    @Test
    void getUsersByYearOfBirth() {
        // Arrange
        var userRepository = ServiceProvider.inject(RepositoryProvider.class).getUserRepository();
        var user1 = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD,
                LocalDate.of(1997, 5, 3), TRUCK_ID);
        var user2 = new User(ID + 1, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD,
                LocalDate.of(1997, 6, 3), TRUCK_ID + 1);
        var user3 = new User(ID + 2, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD,
                LocalDate.of(1996, 2, 19), TRUCK_ID + 2);
        var user4 = new User(ID + 3, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD,
                LocalDate.of(1997, 5, 4), TRUCK_ID + 3);
        var user5 = new User(ID + 4, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD,
                LocalDate.of(1998, 12, 13), TRUCK_ID + 4);

        // Act
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);

        var result = await(service.getUsersByYearOfBirth(1997));

        assertEquals(3, result.size());
    }

    @Test
    void getUsersReportSortedBy() {
        // Arrange
        var provider = ServiceProvider.inject(RepositoryProvider.class);
        var userCompanyRepository = provider.getUserCompanyRepository();
        var companyRepository = provider.getCompanyRepository();
        var deliveryRepository = provider.getDeliveryRepository();

        var userId = 1;
        var truckId = 1;
        var companyId1 = 1;
        var companyId2 = 2;
        var companyId3 = 3;

        var user = new User(userId, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, DATE_OF_BIRTH, truckId);
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
        userCompanyRepository.save(new UserCompany(userId, companyId1, false));
        userCompanyRepository.save(new UserCompany(userId, companyId2, false));
        userCompanyRepository.save(new UserCompany(userId, companyId3, true));
        provider.getUserRepository().save(user);
        companyRepository.save(new Company(companyId1, "Company1"));
        companyRepository.save(new Company(companyId2, "Company2"));
        companyRepository.save(new Company(companyId3, "Company3"));
        deliveryRepository.save(deliveryCompany1);
        deliveryRepository.save(deliveryCompany2);
        deliveryRepository.save(deliveryCompany3);
        deliveryRepository.save(deliveryNotCompletedCompany1);
        deliveryRepository.save(deliveryNotCompletedCompany2);
        deliveryRepository.save(secondDeliveryCompany1);

        var report = await(service.getUsersReportSortedBy(UserReportField.COUNT, SortType.DESCENDING));

        // Assert
        assertEquals(2, report.size());
        assertEquals(150, report.get(0).getTotalDistance());
        assertEquals(2, report.get(0).getNumberOfDeliveries());
        assertTrue(report.get(0).getNumberOfDeliveries() > report.get(1).getNumberOfDeliveries());
        assertEquals(75, report.get(0).getAverageOfDistance());
    }
}
