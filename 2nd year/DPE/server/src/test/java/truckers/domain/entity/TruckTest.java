package truckers.domain.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ubb.truckers.domain.entity.Truck;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TruckTest {
    private static final int ID = 1;
    private static final String MODEL = "MAN";
    private static final String LICENSE_PLATE = "CJ99AAA";
    private static final int MILEAGE = 1234;
    private static final int DRIVER_ID = 5;
    private static final int GARAGE_ID = 3;

    private Truck truck;

    @BeforeEach
    void setUp() {
        truck = new Truck(ID, MODEL, LICENSE_PLATE, MILEAGE, DRIVER_ID, GARAGE_ID);
    }

    @AfterEach
    void tearDown() {
        truck = null;
    }

    @Test
    void getModel() {
        // Assert
        assertEquals(MODEL, truck.getModel());
    }

    @Test
    void setModel() {
        // Arrange
        var newModel = "Scania";

        // Act
        truck.setModel(newModel);

        // Assert
        assertEquals(newModel, truck.getModel());
    }

    @Test
    void getLicensePlate() {
        // Assert
        assertEquals(LICENSE_PLATE, truck.getLicensePlate());
    }

    @Test
    void setLicensePlate() {
        // Arrange
        var newLicensePlate = "SV00BBB";

        // Act
        truck.setLicensePlate(newLicensePlate);

        // Assert
        assertEquals(newLicensePlate, truck.getLicensePlate());
    }

    @Test
    void getMileage() {
        // Assert
        assertEquals(MILEAGE, truck.getMileage());
    }

    @Test
    void setMileage() {
        // Arrange
        var newMileage = 2000;

        // Act
        truck.setMileage(newMileage);

        // Assert
        assertEquals(newMileage, truck.getMileage());
    }

    @Test
    void getDriverId() {
        // Assert
        assertEquals(DRIVER_ID, truck.getDriverId());
    }

    @Test
    void setDriverId() {
        // Arrange
        var newDriverId = 0;

        // Act
        truck.setDriverId(newDriverId);

        // Assert
        assertEquals(newDriverId, truck.getDriverId());
    }

    @Test
    void getGarageId() {
        // Assert
        assertEquals(GARAGE_ID, truck.getGarageId());
    }

    @Test
    void setGarageId() {
        // Arrange
        var newGarageId = 7;

        // Act
        truck.setGarageId(newGarageId);

        // Assert
        assertEquals(newGarageId, truck.getGarageId());
    }
}
