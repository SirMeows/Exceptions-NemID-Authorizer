import org.junit.jupiter.api.Assertions;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;

class NemIdAuthorizerTest {
    private NemIdAuthorizer classUnderTest;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        classUnderTest = new NemIdAuthorizer();
    }

    @org.junit.jupiter.api.Test
    void isValidCprTest_OnLengthAndNr() {
        var cprOKLength = "1111111111";
        assertTrue(classUnderTest.isValidCpr(cprOKLength));
    }

    @org.junit.jupiter.api.Test
    void isValidCprTest_CprTooShort() {
        var cprShort = "1234";
        Assertions.assertThrows(InputMismatchException.class, ()-> {
            var result = classUnderTest.isValidCpr(cprShort);
        });
    }

    @org.junit.jupiter.api.Test
    void IsValidCpr_CprTooLong() {
        var cprLong = "112233445566778899";
        Assertions.assertThrows(InputMismatchException.class, ()-> {
            var result = classUnderTest.isValidCpr(cprLong);
        });
    }

    @org.junit.jupiter.api.Test
    void IsValidCpr_NonNrDigits() {
        var notJustNrs = "aabbccddee";
        Assertions.assertThrows(InputMismatchException.class, ()-> {
            var result = classUnderTest.isValidCpr(notJustNrs);
        });
    }

    @org.junit.jupiter.api.Test
    void isValidPasswordTest() {
    }
}