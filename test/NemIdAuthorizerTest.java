import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;

class NemIdAuthorizerTest {
    private NemIdAuthorizer classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new NemIdAuthorizer();
    }

    @Test
    void isValidCprTest_OnLengthAndNr() {
        var cprOKLength = "1111111111";
        assertTrue(classUnderTest.isValidCpr(cprOKLength));
    }

    @Test
    void cprTooShortTest() {
        var cprShort = "1234";
        Assertions.assertThrows(InputMismatchException.class, ()-> {
            var result = classUnderTest.isValidCpr(cprShort);
        });
    }

    @Test
    void cprTooLong() {
        var cprLong = "112233445566778899";
        Assertions.assertThrows(InputMismatchException.class, () -> classUnderTest.isValidCpr(cprLong) );
    }

    @Test
    void nonNrDigits() {
        var notJustNrs = "aabbccddee";
        Assertions.assertThrows(InputMismatchException.class, ()-> {
            var result = classUnderTest.isValidCpr(notJustNrs);
        });
    }

    @Test
    void isCorrectLengthTest() {
        var six = "111111";
        var less = "123";
        var more = makeLongPw();
        assertTrue(classUnderTest.isCorrectLength(six));
        assertFalse(classUnderTest.isCorrectLength(less));
        assertFalse(classUnderTest.isCorrectLength(more));
    }


    @Test
    void containsRequiredCharactersTest() {
        assertFalse(classUnderTest.containsRequiredCharacters("aA"));
        assertFalse(classUnderTest.containsRequiredCharacters("a1"));
        assertFalse(classUnderTest.containsRequiredCharacters("A1"));
        assertTrue(classUnderTest.containsRequiredCharacters("aB1"));
    }

    @Test
    void hasIllegalCharactersTest_legal() {
        var alpha = "abcdefghijklmnopqrstuvwxyz";
        var num = "1234567890";
        var special = "{ } ! # \" $ ’ % ^ & , * ( ) _ + - = : ; ? . @";

        assertFalse(classUnderTest.hasIllegalCharacters(alpha));
        assertFalse(classUnderTest.hasIllegalCharacters(alpha.toUpperCase()));
        assertFalse(classUnderTest.hasIllegalCharacters( alpha + alpha.toUpperCase()));
        assertFalse(classUnderTest.hasIllegalCharacters(num));
        assertFalse(classUnderTest.hasIllegalCharacters(special));
        assertFalse(classUnderTest.hasIllegalCharacters(special+alpha));
    }

    @Test
    void hasIllegalCharactersTest_illegal() {
        var alpha = "æøå";
        var special = "€~|";
        var mix = "æøå€~|abcABC123";

        assertTrue(classUnderTest.hasIllegalCharacters(alpha));
        assertTrue(classUnderTest.hasIllegalCharacters(special));
        assertTrue(classUnderTest.hasIllegalCharacters(mix));
    }

    @Test
    void hasSpaceAtStartOrEndTest() {
        var start = " k1Ebb";
        var middle = "jgehaoth 00";
        var end = "keui78 ";
        assertTrue(classUnderTest.hasSpaceAtStartOrEnd(start));
        assertFalse(classUnderTest.hasSpaceAtStartOrEnd(middle));
        assertTrue(classUnderTest.hasSpaceAtStartOrEnd(end));
    }

    @Test
    void hasSequenceOfFourIdenticalCharsTest() {
        var middle = "21111t";
        var start = "9999k23";
        var end = "henF8888";

        assertTrue(classUnderTest.hasSequenceOfFourIdenticalChars(middle));
        assertTrue(classUnderTest.hasSequenceOfFourIdenticalChars(start));
        assertTrue(classUnderTest.hasSequenceOfFourIdenticalChars(end));
    }

    @Test
    void containsCprTest() {
        var cpr = "1122331234";
        var firstSix = "112233";
        var lastFour = "1234";
        var justThreeMatch = "234";
        var other = "kuhjdkfgjgkjjf";

        assertTrue(classUnderTest.containsCpr(cpr, firstSix));
        assertTrue(classUnderTest.containsCpr(cpr, lastFour));
        assertFalse(classUnderTest.containsCpr(cpr, justThreeMatch));
        assertFalse(classUnderTest.containsCpr(cpr, other));
    }

    private String makeLongPw() {
        var pw = "123ABCdfg";
        for(var i = 0; i < 10; i++) {
            pw += pw;
        }
        return pw;
    }
}