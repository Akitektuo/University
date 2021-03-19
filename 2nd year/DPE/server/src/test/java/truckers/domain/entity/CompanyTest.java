package truckers.domain.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.ubb.truckers.domain.entity.Company;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyTest {
    private static final int ID = 1;
    private static final String NAME = "TestCompany";

    private Company company;

    @BeforeEach
    void setUp() {
        company = new Company(ID, NAME);
    }

    @AfterEach
    void tearDown() {
        company = null;
    }

    @Test
    void getName() {
        // Assert
        assertEquals(NAME, company.getName());
    }

    @Test
    void setName() {
        // Arrange
        String newName = "NewCompany";

        // Act
        company.setName(newName);

        // Assert
        assertEquals(newName, company.getName());
    }
}
