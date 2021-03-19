package truckers.domain.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ubb.truckers.domain.entity.UserCompany;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserCompanyTest {
    private static final int USER_ID = 1;
    private static final int COMPANY_ID = 1;
    private static final boolean IS_MANAGER = false;

    private UserCompany userCompany;

    @BeforeEach
    void setUp() {
        userCompany = new UserCompany(USER_ID, COMPANY_ID, IS_MANAGER);
    }

    @AfterEach
    void tearDown() {
        userCompany = null;
    }

    @Test
    void isManager() {
        // Assert
        assertEquals(IS_MANAGER, userCompany.isManager());
    }

    @Test
    void setManager() {
        // Arrange
        var newManagerStatus = true;

        // Act
        userCompany.setManager(newManagerStatus);

        // Assert
        assertEquals(newManagerStatus, userCompany.isManager());
    }

    @Test
    void getUserId() {
        // Assert
        assertEquals(USER_ID, userCompany.getUserId());
    }

    @Test
    void setUserId() {
        // Arrange
        var newUserId = 2;

        // Act
        userCompany.setUserId(newUserId);

        // Assert
        assertEquals(newUserId, userCompany.getUserId());
    }

    @Test
    void getCompanyId() {
        // Assert
        assertEquals(COMPANY_ID, userCompany.getCompanyId());
    }

    @Test
    void setCompanyId() {
        // Arrange
        var newCompanyId = 3;

        // Act
        userCompany.setCompanyId(newCompanyId);

        // Assert
        assertEquals(newCompanyId, userCompany.getCompanyId());
    }
}