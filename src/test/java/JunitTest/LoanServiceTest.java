package JunitTest;
import Junit.LoanService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoanServiceTest {
    LoanService s = new LoanService();
    @Test
    void testValidEligibility() {
        assertTrue(s.isEligible(30, 50000));
    }
    @Test
    void testInvalidAgeBelow() {
        assertFalse(s.isEligible(18, 50000));
    }
    @Test
    void testInvalidAgeAbove() {
        assertFalse(s.isEligible(65, 50000));
    }
    @Test
    void testInvalidSalary() {
        assertFalse(s.isEligible(30, 20000));
    }
    @Test
    void testCalculateEMI() {
        assertEquals(10000, s.calculateEMI(120000, 1));
    }
    @Test
    void testInvalidLoanAmount() {
        assertThrows(IllegalArgumentException.class, () -> s.calculateEMI(0, 1));
    }

    @Test
    void testInvalidTenure() {
        assertThrows(IllegalArgumentException.class, () -> s.calculateEMI(10000, 0));
    }

    @Test
    void testPremiumCategory() {
        assertEquals("Premium", s.getLoanCategory(800));
    }
    @Test
    void testStandardCategory() {
        assertEquals("Standard", s.getLoanCategory(700));
    }
    @Test
    void testHighRiskCategory() {
        assertEquals("High Risk", s.getLoanCategory(500));
    }
    @Test
    void testBoundaryValues() {
        assertAll(
                () -> assertTrue(s.isEligible(21, 25000)),
                () -> assertTrue(s.isEligible(60, 30000)),
                () -> assertEquals("Standard", s.getLoanCategory(600)),
                () -> assertEquals("Premium", s.getLoanCategory(750))
        );
    }
    @Test
    void testNotNullCategory() {
        assertNotNull(s.getLoanCategory(650));
    }
}

