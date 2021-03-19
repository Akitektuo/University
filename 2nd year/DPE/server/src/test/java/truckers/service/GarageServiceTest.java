package truckers.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Garage;
import ro.ubb.truckers.domain.entity.Truck;
import ro.ubb.truckers.domain.report.sortable.GarageReportField;
import ro.ubb.truckers.domain.validator.ValidatorException;
import ro.ubb.truckers.repository.RepositoryProvider;
import ro.ubb.truckers.service.GarageService;
import ro.ubb.truckers.util.ServiceProvider;
import ro.ubb.truckers.util.constant.SortType;
import truckers.util.Seeder;
import truckers.util.Util;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static ro.ubb.truckers.util.network.Utils.await;

public class GarageServiceTest {
    private static final int ID = 1;
    private static final String LOCATION = "Cluj";
    private static final int CAPACITY = 4;
    private static final int ALLOCATED_TRUCKS = 1;
    private static final int COMPANY_ID = 2;

    private GarageService service;

    @BeforeEach
    void setUp() {
        Util.initializeServiceProvider();
        service = new GarageService();
    }

    @AfterEach
    void tearDown() {
        service = null;
        Util.destroyServiceProvider();
    }

    @Test
    void addGarage_IsNull() {
        // Arrange
        Garage garage = null;
        var wasExceptionThrown = false;

        // Act
        try {
            service.addGarage(garage);
        } catch (IllegalArgumentException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addGarage_IsDuplicate() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID);

        var garage = new Garage(ID, LOCATION, CAPACITY, ALLOCATED_TRUCKS, COMPANY_ID);
        var wasExceptionThrown = false;

        // Act
        service.addGarage(garage);
        try {
            service.addGarage(garage);
        } catch (TruckersException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addGarage_IdIsInvalid() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID);

        var garage = new Garage(0, LOCATION, CAPACITY, ALLOCATED_TRUCKS, COMPANY_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addGarage(garage);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addGarage_LocationIsInvalid() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID);

        var garage = new Garage(ID, "", CAPACITY, ALLOCATED_TRUCKS, COMPANY_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addGarage(garage);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addGarage_CapacityIsInvalid() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID);

        var garage = new Garage(ID, LOCATION, 0, ALLOCATED_TRUCKS, COMPANY_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addGarage(garage);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addGarage_AllocatedTrucksIsInvalid() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID);

        var garage = new Garage(ID, LOCATION, CAPACITY, -1, COMPANY_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addGarage(garage);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addGarage_AllocatedTrucksMoreThanCapacity() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID);

        var garage = new Garage(ID, LOCATION, CAPACITY, CAPACITY + 1, COMPANY_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addGarage(garage);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addGarage_CompanyIdInvalid() {
        // Arrange
        var garage = new Garage(ID, LOCATION, CAPACITY, ALLOCATED_TRUCKS, 0);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addGarage(garage);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addGarage() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID);

        var garage = new Garage(ID, LOCATION, CAPACITY, ALLOCATED_TRUCKS, COMPANY_ID);
        var garageRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getGarageRepository();

        // Act
        service.addGarage(garage);
        var found = garageRepository.findOne(ID);

        // Assert
        assertTrue(found.isPresent());
    }

    @Test
    void getAllGarages() {
        // Arrange
        var garageRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getGarageRepository();
        var garage1 = new Garage(ID, LOCATION, CAPACITY, ALLOCATED_TRUCKS, COMPANY_ID);
        var garage2 = new Garage(ID + 1, "Timisoara", 150, 14, 3);
        var garage3 = new Garage(ID + 2, "Bacau", 1500, 999, 5);

        // Act

        garageRepository.save(garage1);
        garageRepository.save(garage2);
        garageRepository.save(garage3);

        var result = await(service.getAllGarages());

        // Assert
        assertEquals(3, result.size());
    }

    @Test
    void updateGarage_NotExistent() {
        // Arrange
        var garage = new Garage(ID, LOCATION, CAPACITY, ALLOCATED_TRUCKS, COMPANY_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.updateGarage(garage);
        } catch (TruckersException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void updateGarage() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID + 1);

        var garageRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getGarageRepository();
        var garage = new Garage(ID, LOCATION, CAPACITY, ALLOCATED_TRUCKS, COMPANY_ID);
        AtomicBoolean wasNotUpdated = new AtomicBoolean(false);

        // Act
        garageRepository.save(garage);
        garage = new Garage(ID, LOCATION + "1", CAPACITY + 1, ALLOCATED_TRUCKS + 1, COMPANY_ID + 1);

        service.updateGarage(garage);

        garageRepository.findOne(ID).ifPresent(found -> wasNotUpdated.set(
                wasNotUpdated.get() ||
                        found.getLocation().equals(LOCATION) ||
                        found.getCapacity() == CAPACITY ||
                        found.getAllocatedTrucks() == ALLOCATED_TRUCKS + 1 ||
                        found.getCompanyId() == COMPANY_ID
        ));

        // Assert
        assertFalse(wasNotUpdated.get());
    }

    @Test
    void deleteGarage_NoGarageWithId() {
        // Arrange
        var wasExceptionThrown = false;

        // Act
        try {
            service.deleteGarage(ID);
        } catch (TruckersException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void deleteGarage() {
        // Arrange
        var garageRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getGarageRepository();
        var garage = new Garage(ID, LOCATION, CAPACITY, ALLOCATED_TRUCKS, COMPANY_ID);

        // Act
        garageRepository.save(garage);

        service.deleteGarage(ID);

        var wasDeleted = garageRepository.findOne(ID).isEmpty();

        // Assert
        Assertions.assertTrue(wasDeleted);
    }

    @Test
    void getGaragesByAvailableCapacityAscending_testSize() {
        // Arrange
        var garageRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getGarageRepository();
        var garage1 = new Garage(ID, LOCATION, CAPACITY, CAPACITY - 3, COMPANY_ID);
        var garage2 = new Garage(ID + 1, LOCATION, CAPACITY, CAPACITY - 4, COMPANY_ID);
        var garage3 = new Garage(ID + 2, LOCATION, CAPACITY, CAPACITY, COMPANY_ID);
        var garage4 = new Garage(ID + 3, LOCATION, CAPACITY, CAPACITY - 2, COMPANY_ID);
        var garage5 = new Garage(ID + 4, LOCATION, CAPACITY, CAPACITY - 1, COMPANY_ID);

        // Act
        garageRepository.save(garage1);
        garageRepository.save(garage2);
        garageRepository.save(garage3);
        garageRepository.save(garage4);
        garageRepository.save(garage5);

        var result = await(service.getGaragesByAvailableCapacityAscending(2));

        // Assert
        assertEquals(result.size(), 3);
    }

    @Test
    void getGaragesByAvailableCapacityAscending_testOrder() {
        // Arrange
        var garageRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getGarageRepository();
        var garage1 = new Garage(ID, LOCATION, CAPACITY, CAPACITY - 3, COMPANY_ID);
        var garage2 = new Garage(ID + 1, LOCATION, CAPACITY, CAPACITY - 4, COMPANY_ID);
        var garage3 = new Garage(ID + 2, LOCATION, CAPACITY, CAPACITY, COMPANY_ID);
        var garage4 = new Garage(ID + 3, LOCATION, CAPACITY, CAPACITY - 2, COMPANY_ID);
        var garage5 = new Garage(ID + 4, LOCATION, CAPACITY, CAPACITY - 1, COMPANY_ID);
        var minimumCapacity = 2;

        // Act
        garageRepository.save(garage1);
        garageRepository.save(garage2);
        garageRepository.save(garage3);
        garageRepository.save(garage4);
        garageRepository.save(garage5);

        var result = await(service.getGaragesByAvailableCapacityAscending(minimumCapacity));

        // Assert
        assertEquals(result.get(0).getCapacity() - result.get(0).getAllocatedTrucks(), minimumCapacity);
    }

    @Test
    void getGaragesReportSortedBy() {
        // Arrange
        var provider = ServiceProvider.inject(RepositoryProvider.class);
        var garageRepository = provider.getGarageRepository();
        var truckRepository = provider.getTruckRepository();

        var garageId1 = 1;
        var garageId2 = 2;
        var garageId3 = 3;

        var garage1 = new Garage(garageId1, "location1", 4, 2, 1);
        var garage2 = new Garage(garageId2, "location2", 4, 2, 2);
        var garage3 = new Garage(garageId3, "location3", 4, 2, 3);

        var truck1 = new Truck(1, "model1", "CJ01AAA", 15, 1, 1);
        var truck2 = new Truck(2, "model2", "CJ02BBB", 37, 2, 1);
        var truck3 = new Truck(3, "model3", "CJ03CCC", 49, 3, 2);
        var truck4 = new Truck(4, "model4", "CJ04DDD", 12, 4, 2);
        var truck5 = new Truck(5, "model5", "CJ05EEE", 80, 5, 3);
        var truck6 = new Truck(6, "model6", "CJ06FFF", 99, 6, 3);

        // Act
        garageRepository.save(garage1);
        garageRepository.save(garage2);
        garageRepository.save(garage3);
        truckRepository.save(truck1);
        truckRepository.save(truck2);
        truckRepository.save(truck3);
        truckRepository.save(truck4);
        truckRepository.save(truck5);
        truckRepository.save(truck6);

        var report = await(service.getGaragesReportSortedBy(GarageReportField.MIN, SortType.ASCENDING));

        // Assert
        assertEquals(3, report.size());
        assertEquals(garageId2, report.get(0).getGarageId());
        assertEquals("location2", report.get(0).getGarageLocation());
        assertEquals(12, report.get(0).getTruckMinimumMileage());
        assertEquals(49, report.get(0).getTruckMaximumMileage());
        assertEquals(30.5, report.get(0).getTruckAverageMileage());
    }
}
