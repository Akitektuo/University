package truckers.domain.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ubb.truckers.domain.entity.Garage;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GarageTest {
    private static final int ID = 1;
    private static final String LOCATION = "Cluj";
    private static final int CAPACITY = 4;
    private static final int ALLOCATED_TRUCKS = 1;
    private static final int COMPANY_ID = 2;

    private Garage garage;

    @BeforeEach
    void setUp() {
        garage = new Garage(ID, LOCATION, CAPACITY, ALLOCATED_TRUCKS, COMPANY_ID);
    }

    @AfterEach
    void tearDown() {
        garage = null;
    }

    @Test
    void getLocation() {
        // Assert
        assertEquals(LOCATION, garage.getLocation());
    }

    @Test
    void setLocation() {
        // Arrange
        var newLocation = "Bucharest";

        // Act
        garage.setLocation(newLocation);

        // Assert
        assertEquals(newLocation, garage.getLocation());
    }

    @Test
    void getCapacity() {
        // Assert
        assertEquals(CAPACITY, garage.getCapacity());
    }

    @Test
    void setCapacity() {
        // Arrange
        var newCapacity = 6;

        // Act
        garage.setCapacity(newCapacity);

        // Assert
        assertEquals(newCapacity, garage.getCapacity());
    }

    @Test
    void getCompanyId() {
        // Assert
        assertEquals(COMPANY_ID, garage.getCompanyId());
    }

    @Test
    void setCompanyId() {
        // Arrange
        var newCompanyId = 3;

        // Act
        garage.setCompanyId(newCompanyId);

        // Assert
        assertEquals(newCompanyId, garage.getCompanyId());
    }

    @Test
    void getAllocatedTrucks() {
        // Assert
        assertEquals(ALLOCATED_TRUCKS, garage.getAllocatedTrucks());
    }

    @Test
    void setAllocatedTrucks() {
        // Arrange
        var allocatedTrucks = 2;

        // Act
        garage.setAllocatedTrucks(allocatedTrucks);

        // Assert
        assertEquals(allocatedTrucks, garage.getAllocatedTrucks());
    }
}