package truckers.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.Delivery;
import ro.ubb.truckers.domain.validator.ValidatorException;
import ro.ubb.truckers.repository.RepositoryProvider;
import ro.ubb.truckers.service.DeliveryService;
import ro.ubb.truckers.util.ServiceProvider;
import truckers.util.Seeder;
import truckers.util.Util;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static ro.ubb.truckers.util.network.Utils.await;

public class DeliveryServiceTest {
    private static final int ID = 1;
    private static final String ORIGIN = "testOrigin";
    private static final String DESTINATION = "testDestination";
    private static final int DISTANCE = 100;
    private static final boolean DELIVERED = false;
    private static final String LOAD = "FURNITURE";
    private static final int TRUCK_ID = 1;
    private static final int COMPANY_ID = 1;

    private DeliveryService service;

    @BeforeEach
    void setUp() {
        Util.initializeServiceProvider();
        service = new DeliveryService();
    }

    @AfterEach
    void tearDown() {
        service = null;
        Util.destroyServiceProvider();
    }

    @Test
    void addDelivery_IsNull() {
        // Arrange
        Delivery delivery = null;
        var wasExceptionThrown = false;

        // Act
        try {
            service.addDelivery(delivery);
        } catch (IllegalArgumentException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addDelivery_IsDuplicate() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID);
        Seeder.seedTruck(TRUCK_ID);

        var delivery = new Delivery(ID, ORIGIN, DESTINATION, DISTANCE, DELIVERED,
                LOAD, TRUCK_ID, COMPANY_ID);
        var wasExceptionThrown = false;

        // Act
        service.addDelivery(delivery);
        try {
            service.addDelivery(delivery);
        } catch (TruckersException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addDelivery_IdIsInvalid() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID);
        Seeder.seedTruck(TRUCK_ID);

        var delivery = new Delivery(0, ORIGIN, DESTINATION, DISTANCE, DELIVERED,
                LOAD, TRUCK_ID, COMPANY_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addDelivery(delivery);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addDelivery_OriginIsInvalid() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID);
        Seeder.seedTruck(TRUCK_ID);

        var delivery = new Delivery(ID, "", DESTINATION, DISTANCE, DELIVERED,
                LOAD, TRUCK_ID, COMPANY_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addDelivery(delivery);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addDelivery_DestinationIsInvalid() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID);
        Seeder.seedTruck(TRUCK_ID);

        var delivery = new Delivery(ID, ORIGIN, "", DISTANCE, DELIVERED,
                LOAD, TRUCK_ID, COMPANY_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addDelivery(delivery);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addDelivery_DistanceIsInvalid() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID);
        Seeder.seedTruck(TRUCK_ID);

        var delivery = new Delivery(ID, ORIGIN, DESTINATION, 0, DELIVERED,
                LOAD, TRUCK_ID, COMPANY_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addDelivery(delivery);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addDelivery_LoadIsInvalid() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID);
        Seeder.seedTruck(TRUCK_ID);

        var delivery = new Delivery(ID, ORIGIN, DESTINATION, DISTANCE, DELIVERED,
                "", TRUCK_ID, COMPANY_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addDelivery(delivery);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addDelivery_TruckIdIsInvalid() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID);

        var delivery = new Delivery(ID, ORIGIN, DESTINATION, DISTANCE, DELIVERED,
                LOAD, 0, COMPANY_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addDelivery(delivery);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addDelivery_CompanyIdIsInvalid() {
        // Arrange
        Seeder.seedTruck(TRUCK_ID);

        var delivery = new Delivery(ID, ORIGIN, DESTINATION, DISTANCE, DELIVERED,
                LOAD, TRUCK_ID, 0);
        var wasExceptionThrown = false;

        // Act
        try {
            service.addDelivery(delivery);
        } catch (ValidatorException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void addDelivery() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID);
        Seeder.seedTruck(TRUCK_ID);

        var delivery = new Delivery(ID, ORIGIN, DESTINATION, DISTANCE, DELIVERED,
                LOAD, TRUCK_ID, COMPANY_ID);
        var deliveryRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getDeliveryRepository();

        // Act
        service.addDelivery(delivery);
        var found = deliveryRepository.findOne(ID);

        // Assert
        assertTrue(found.isPresent());
    }

    @Test
    void getAllDeliveries() {
        // Arrange
        var deliveryRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getDeliveryRepository();
        var delivery1 = new Delivery(ID, ORIGIN, DESTINATION, DISTANCE, DELIVERED,
                LOAD, TRUCK_ID, COMPANY_ID);
        var delivery2 = new Delivery(ID + 1, ORIGIN + 1, DESTINATION + 1, DISTANCE + 1, DELIVERED,
                LOAD + 1, TRUCK_ID + 1, COMPANY_ID + 1);
        var delivery3 = new Delivery(ID + 2, ORIGIN + 2, DESTINATION + 2, DISTANCE + 2, DELIVERED,
                LOAD + 2, TRUCK_ID + 2, COMPANY_ID + 2);

        // Act
        deliveryRepository.save(delivery1);
        deliveryRepository.save(delivery2);
        deliveryRepository.save(delivery3);

        var result = await(service.getAllDeliveries());

        // Assert
        assertEquals(3, result.size());
    }

    @Test
    void updateDelivery_NotExistent() {
        // Arrange
        var delivery = new Delivery(ID, ORIGIN, DESTINATION, DISTANCE, DELIVERED, LOAD, TRUCK_ID, COMPANY_ID);
        var wasExceptionThrown = false;

        // Act
        try {
            service.updateDelivery(delivery);
        } catch (TruckersException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void updateDelivery() {
        // Arrange
        Seeder.seedCompany(COMPANY_ID + 1);
        Seeder.seedTruck(TRUCK_ID + 1);

        var delivery = new Delivery(ID, ORIGIN, DESTINATION, DISTANCE, DELIVERED, LOAD, TRUCK_ID, COMPANY_ID);
        var deliveryRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getDeliveryRepository();
        AtomicBoolean wasNotUpdated = new AtomicBoolean(false);

        // Act
        deliveryRepository.save(delivery);
        delivery = new Delivery(ID, ORIGIN + "1", DESTINATION + "1", DISTANCE + 1, !DELIVERED,
                LOAD + "1", TRUCK_ID + 1, COMPANY_ID + 1);

        service.updateDelivery(delivery);

        deliveryRepository.findOne(ID).ifPresent(found -> wasNotUpdated.set(
                wasNotUpdated.get() ||
                        found.getOrigin().equals(ORIGIN) ||
                        found.getDestination().equals(DESTINATION) ||
                        found.getDistance() == DISTANCE ||
                        found.isDelivered() == DELIVERED ||
                        found.getLoad().equals(LOAD) ||
                        found.getTruckId() == TRUCK_ID ||
                        found.getCompanyId() == COMPANY_ID
        ));

        // Assert
        assertFalse(wasNotUpdated.get());
    }

    @Test
    void getDeliveriesByOriginAndDestination() {
        // Arrange
        var deliveryRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getDeliveryRepository();
        var delivery1 = new Delivery(ID, ORIGIN, DESTINATION, DISTANCE, DELIVERED,
                LOAD, TRUCK_ID, COMPANY_ID);
        var delivery2 = new Delivery(ID + 1, ORIGIN + 1, DESTINATION + 1, DISTANCE + 1, DELIVERED,
                LOAD + 1, TRUCK_ID + 1, COMPANY_ID + 1);
        var delivery3 = new Delivery(ID + 2, ORIGIN + 2, DESTINATION + 2, DISTANCE + 2, DELIVERED,
                LOAD + 2, TRUCK_ID + 2, COMPANY_ID + 2);

        // Act
        deliveryRepository.save(delivery1);
        deliveryRepository.save(delivery2);
        deliveryRepository.save(delivery3);

        var result = await(service.getDeliveriesByOriginAndDestination(ORIGIN + 1, DESTINATION + 1));

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void deleteDelivery_NoDeliveryWithId() {
        // Arrange
        var wasExceptionThrown = false;

        // Act
        try {
            service.deleteDelivery(ID);
        } catch (TruckersException exception) {
            wasExceptionThrown = true;
        }

        // Assert
        assertTrue(wasExceptionThrown);
    }

    @Test
    void deleteDelivery() {
        // Arrange
        var deliveryRepository = ServiceProvider.inject(RepositoryProvider.class)
                .getDeliveryRepository();
        var delivery = new Delivery(ID, ORIGIN, DESTINATION, DISTANCE, DELIVERED,
                LOAD, TRUCK_ID, COMPANY_ID);

        // Act
        deliveryRepository.save(delivery);

        service.deleteDelivery(ID);

        var wasDeleted = deliveryRepository.findOne(ID).isEmpty();

        // Assert
        Assertions.assertTrue(wasDeleted);
    }
}

