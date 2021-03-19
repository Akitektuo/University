package truckers.domain.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ubb.truckers.domain.entity.Delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryTest {

    private static final int ID = 1;
    private static final String ORIGIN = "Cluj";
    private static final String DESTINATION = "Deva";
    private static final int DISTANCE = 4;
    private static final boolean DELIVERED = false;
    private static final String LOAD = "Grain";
    private static final int TRUCK_ID = 1;
    private static final int COMPANY_ID = 2;

    private Delivery delivery;

    @BeforeEach
    void setUP() {
        delivery = new Delivery(ID, ORIGIN, DESTINATION, DISTANCE, DELIVERED, LOAD, TRUCK_ID, COMPANY_ID);
    }

    @AfterEach
    void tearDown() {
        delivery = null;
    }

    @Test
    void getOrigin() {
        // Assert
        assertEquals(ORIGIN, delivery.getOrigin());
    }

    @Test
    void setOrigin() {
        // Arrange
        var newOrigin = "Dolhasca";

        // Act
        delivery.setOrigin(newOrigin);

        //Assert
        assertEquals(newOrigin, delivery.getOrigin());
    }

    @Test
    void getDestination() {
        // Assert
        assertEquals(DESTINATION, delivery.getDestination());
    }

    @Test
    void setDestination() {
        // Arrange
        var newDestination = "Pitesti";

        // Act
        delivery.setDestination(newDestination);

        //Assert
        assertEquals(newDestination, delivery.getDestination());
    }

    @Test
    void getDistance() {
        //Assert
        assertEquals(DISTANCE, delivery.getDistance());
    }

    @Test
    void setDistance() {
        // Arrange
        var newDistance = 90;

        // Act
        delivery.setDistance(newDistance);

        //Assert
        assertEquals(newDistance, delivery.getDistance());
    }

    @Test
    void isDelivered() {
        //Assert
        assertEquals(DELIVERED, delivery.isDelivered());
    }

    @Test
    void setDelivered() {
        // Arrange
        var newDelivered = true;

        // Act
        delivery.setDelivered(newDelivered);

        //Assert
        assertEquals(newDelivered, delivery.isDelivered());
    }

    @Test
    void getLoad() {
        //Assert
        assertEquals(LOAD, delivery.getLoad());
    }

    @Test
    void setLoad() {
        // Arrange
        var newLoad = "Potatoes";

        // Act
        delivery.setLoad(newLoad);

        //Assert
        assertEquals(newLoad, delivery.getLoad());
    }

    @Test
    void getTruckId() {
        //Assert
        assertEquals(TRUCK_ID, delivery.getTruckId());
    }

    @Test
    void setTruckId() {
        // Arrange
        var newTruckId = 5;

        // Act
        delivery.setTruckId(newTruckId);

        //Assert
        assertEquals(newTruckId, delivery.getTruckId());
    }

    @Test
    void getCompanyId() {
        //Assert
        assertEquals(COMPANY_ID, delivery.getCompanyId());
    }

    @Test
    void setCompanyId() {
        // Arrange
        var newCompanyId = 7;

        // Act
        delivery.setCompanyId(newCompanyId);

        //Assert
        assertEquals(newCompanyId, delivery.getCompanyId());
    }
}
