package truckers.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Truck;
import ro.ubb.truckers.domain.validator.ValidatorException;
import ro.ubb.truckers.repository.RepositoryProvider;
import ro.ubb.truckers.service.TruckService;
import ro.ubb.truckers.util.ServiceProvider;
import truckers.util.Seeder;
import truckers.util.Util;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static ro.ubb.truckers.util.network.Utils.await;


class TruckServiceTest {

    private static final int ID = 1;
    private static final String MODEL = "Scania";
    private static final String LICENSE_PLATE = "99UBB";
    private static final int MILEAGE = 4;
    private static final int DRIVER_ID = 1;
    private static final int GARAGE_ID = 2;

    private TruckService service;

    @BeforeEach
    void setUp() {
        Util.initializeServiceProvider();
        service = new TruckService();
    }

    @AfterEach
    void tearDown() {
        service = null;
        Util.destroyServiceProvider();
    }

    @Test
    void addTruck_IsNull() {
        // Arrange
        Truck truck = null;
        var wasExceptionThrown = false;

        // Act
        try {
            service.addTruck(truck);
        } catch (IllegalArgumentException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addTruck_IsDuplicate() {
        // Arrange
        Seeder.seedGarage(GARAGE_ID);
        Seeder.seedUser(DRIVER_ID);

        var truck = new Truck(ID, MODEL, LICENSE_PLATE, MILEAGE, DRIVER_ID, GARAGE_ID);
        var wasExceptionThrown = false;

        // Act
        service.addTruck(truck);
        try {
            service.addTruck(truck);
        } catch (TruckersException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addTruck_IdIsInvalid() {
        // Arrange
        Seeder.seedGarage(GARAGE_ID);
        Seeder.seedUser(DRIVER_ID);

        var truck = new Truck(0, MODEL, LICENSE_PLATE, MILEAGE, DRIVER_ID, GARAGE_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addTruck(truck);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addTruck_ModelIsInvalid() {
        // Arrange
        Seeder.seedGarage(GARAGE_ID);
        Seeder.seedUser(DRIVER_ID);

        var truck = new Truck(ID, "", LICENSE_PLATE, MILEAGE, DRIVER_ID, GARAGE_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addTruck(truck);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addTruck_LicensePlateIsInvalid() {
        // Arrange
        Seeder.seedGarage(GARAGE_ID);
        Seeder.seedUser(DRIVER_ID);

        var truck = new Truck(ID, MODEL, "", MILEAGE, DRIVER_ID, GARAGE_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addTruck(truck);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addTruck_MileageIsInvalid() {
        // Arrange
        Seeder.seedGarage(GARAGE_ID);
        Seeder.seedUser(DRIVER_ID);

        var truck = new Truck(ID, MODEL, LICENSE_PLATE, -1, DRIVER_ID, GARAGE_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addTruck(truck);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addTruck_DriverIdInvalid() {
        // Arrange
        Seeder.seedGarage(GARAGE_ID);

        var truck = new Truck(ID, MODEL, LICENSE_PLATE, MILEAGE, -1, GARAGE_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addTruck(truck);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addTruck_GarageIdInvalid() {
        // Arrange
        Seeder.seedUser(DRIVER_ID);

        var truck = new Truck(ID, MODEL, LICENSE_PLATE, MILEAGE, DRIVER_ID, 0);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addTruck(truck);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addTruck() {
        // Arrange
        Seeder.seedGarage(GARAGE_ID);
        Seeder.seedUser(DRIVER_ID);

        var truck = new Truck(ID, MODEL, LICENSE_PLATE, MILEAGE, DRIVER_ID, GARAGE_ID);
        var truckRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getTruckRepository();

        // Act
        service.addTruck(truck);
        var found = truckRepository.findOne(ID);

        // Assert
        assertTrue(found.isPresent());
    }

    @Test
    void getAllTrucks() {
        // Arrange
        var truckRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getTruckRepository();
        var truck1 = new Truck(ID, MODEL, LICENSE_PLATE, MILEAGE, DRIVER_ID, GARAGE_ID);
        var truck2 = new Truck(ID + 1, "Ford", "ATAR", 50, 5, 8);
        var truck3 = new Truck(ID + 2, "Dacia", "ATAR-2", 100, 7, 8);

        // Act
        truckRepository.save(truck1);
        truckRepository.save(truck2);
        truckRepository.save(truck3);

        var result = await(service.getAllTrucks());

        // Assert
        assertEquals(3, result.size());
    }

    @Test
    void deleteTruck_NoTruckWithId() {
        // Arrange
        var wasExceptionThrown = false;

        // Act
        try {
            service.deleteTruck(ID);
        } catch (TruckersException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void deleteTruck() {
        // Arrange
        var truckRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getTruckRepository();
        var truck = new Truck(ID, MODEL, LICENSE_PLATE, MILEAGE, DRIVER_ID, GARAGE_ID);

        // Act
        truckRepository.save(truck);

        service.deleteTruck(ID);

        var wasDeleted = truckRepository.findOne(ID).isEmpty();

        // Assert
        Assertions.assertTrue(wasDeleted);
    }

    @Test
    void updateTruck_NotExistent() {
        // Arrange
        var truck = new Truck(ID, MODEL, LICENSE_PLATE, MILEAGE, DRIVER_ID, GARAGE_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.updateTruck(truck);
        } catch (TruckersException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void updateTruck() {
        // Arrange
        Seeder.seedGarage(GARAGE_ID + 1);
        Seeder.seedUser(DRIVER_ID + 1);

        var truckRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getTruckRepository();
        var truck = new Truck(ID, MODEL, LICENSE_PLATE, MILEAGE, DRIVER_ID, GARAGE_ID);
        AtomicBoolean wasNotUpdated = new AtomicBoolean(false);

        // Act
        truckRepository.save(truck);
        truck = new Truck(ID, MODEL + "1", LICENSE_PLATE + "1", MILEAGE + 1, DRIVER_ID + 1, GARAGE_ID + 1);

        service.updateTruck(truck);

        truckRepository.findOne(ID).ifPresent(found -> wasNotUpdated.set(
                wasNotUpdated.get() ||
                        found.getModel().equals(MODEL) ||
                        found.getLicensePlate().equals(LICENSE_PLATE) ||
                        found.getDriverId() == DRIVER_ID ||
                        found.getMileage() == MILEAGE
        ));

        // Assert
        assertFalse(wasNotUpdated.get());
    }

    @Test
    void getTrucksByModel_testSize() {
        // Arrange
        var truckRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getTruckRepository();
        var truck1 = new Truck(ID, MODEL, LICENSE_PLATE, MILEAGE, DRIVER_ID, GARAGE_ID);
        var truck2 = new Truck(ID + 1, MODEL, LICENSE_PLATE + 1, MILEAGE, DRIVER_ID + 1, GARAGE_ID);
        var truck3 = new Truck(ID + 2, "Man", LICENSE_PLATE + 2, MILEAGE, DRIVER_ID + 2, GARAGE_ID);
        var truck4 = new Truck(ID + 3, MODEL + 1, LICENSE_PLATE + 2, MILEAGE, DRIVER_ID + 3, GARAGE_ID);
        var truck5 = new Truck(ID + 4, "Man", LICENSE_PLATE + 2, MILEAGE, DRIVER_ID + 4, GARAGE_ID);
        var filteredModel = "Scania";

        // Act
        truckRepository.save(truck1);
        truckRepository.save(truck2);
        truckRepository.save(truck3);
        truckRepository.save(truck4);
        truckRepository.save(truck5);

        var result = await(service.getTrucksByModel(filteredModel));

        // Assert
        assertEquals(3, result.size());
    }
}