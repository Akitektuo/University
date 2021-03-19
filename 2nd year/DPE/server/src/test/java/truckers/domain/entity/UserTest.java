package truckers.domain.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ubb.truckers.domain.entity.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    private static final int ID = 1;
    private static final String FIRST_NAME = "Ion";
    private static final String LAST_NAME = "Popescu";
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "telemea";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1974, 8, 20);
    private static final int TRUCK_ID = 2;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, DATE_OF_BIRTH, TRUCK_ID);
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @Test
    void getFirstName() {
        // Assert
        assertEquals(FIRST_NAME, user.getFirstName());
    }

    @Test
    void setFirstName() {
        // Arrange
        String newFirstName = "Vasile";

        // Act
        user.setFirstName(newFirstName);

        // Assert
        assertEquals(newFirstName, user.getFirstName());
    }

    @Test
    void getLastName() {
        // Assert
        assertEquals(LAST_NAME, user.getLastName());
    }

    @Test
    void setLastName() {
        // Arrange
        String newLastName = "Ionescu";

        // Act
        user.setLastName(newLastName);

        // Assert
        assertEquals(newLastName, user.getLastName());
    }

    @Test
    void getEmail() {
        // Assert
        assertEquals(EMAIL, user.getEmail());
    }

    @Test
    void setEmail() {
        // Arrange
        String newEmail = "test2@test.com";

        // Act
        user.setEmail(newEmail);

        // Assert
        assertEquals(newEmail, user.getEmail());
    }

    @Test
    void getPassword() {
        // Assert
        assertEquals(PASSWORD, user.getPassword());
    }

    @Test
    void setPassword() {
        // Arrange
        String newPassword = "123456789";

        // Act
        user.setPassword(newPassword);

        // Assert
        assertEquals(newPassword, user.getPassword());
    }

    @Test
    void getDateOfBirth() {
        // Assert
        assertEquals(DATE_OF_BIRTH, user.getDateOfBirth());
    }

    @Test
    void setDateOfBirth() {
        // Arrange
        LocalDate newDateOfBirth = LocalDate.of(1973, 9, 3);

        // Act
        user.setDateOfBirth(newDateOfBirth);

        // Assert
        assertEquals(newDateOfBirth, user.getDateOfBirth());
    }

    @Test
    void getTruckId() {
        // Assert
        assertEquals(TRUCK_ID, user.getTruckId());
    }

    @Test
    void setTruckId() {
        // Arrange
        int newTruckId = 4;

        // Act
        user.setTruckId(newTruckId);

        // Assert
        assertEquals(newTruckId, user.getTruckId());
    }
}