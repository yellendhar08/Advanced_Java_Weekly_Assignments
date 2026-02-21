package JunitTest;
import Junit.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {
    StudentService service;
    @BeforeEach
    void setUp() {
        service = new StudentService();
    }

    @Test
    void testCalculateDistinction(){
        assertEquals("Distinction", service.calculateGrade(80));
    }

    @Test
    void testCalculateFIrstClass(){
        assertEquals("First Class", service.calculateGrade(65));
    }

    @Test
    void testcalculateSecondClass(){
        assertEquals("Second Class", service.calculateGrade(55));
    }
    @Test
    void testCalculateFail(){
        assertEquals("Fail", service.calculateGrade(40));
    }

    @Test
    void  testPass(){
        assertTrue(service.isPassed(75));
    }

    @Test
    void testFail(){
        assertFalse(service.isPassed(45));
    }

    @Test
    void testNegativeThrowException(){
        assertThrows(IllegalArgumentException.class, ()->{service.calculateGrade(-10);});
    }
    @Test
    void testAboveMarksThrowException(){
        assertThrows(IllegalArgumentException.class, ()->{service.calculateGrade(120);});
    }

    @Test
    void testNotNull(){
        assertNotNull(service.calculateGrade(70));
    }
}