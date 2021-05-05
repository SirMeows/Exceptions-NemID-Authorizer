import java.util.InputMismatchException;
import java.util.regex.Pattern;
/* New exercise: Write a valid method: isValidPassword, make a JUnit test
Hvis du vælger en adgangskode med minimum 6 tegn, blandede tal og bogstaver og evt. specialtegn.

Skal være mellem 6 og 40 tegn
Skal indeholde både bogstaver og tal
Må ikke indeholde visse specialtegn, herunder æ, ø og å
Må ikke indeholde det samme tegn 4 gange i træk
Må hverken starte eller slutte med et blanktegn
Må ikke indeholde dit cpr- eller NemID-nummer
Der skelnes ikke mellem store og små bogstaver.
Tilladte specialtegn er: { } ! # " $ ’ % ^ & , * ( ) _ + - = : ; ? . og @.*/

public class NemIdAuthorizer {

    public boolean isValidCpr(String cpr) {
        if(cpr.length() == 10 && hasOnlyNumbers(cpr)) {
            return true;
        }
        throw new InputMismatchException("invalid user name or password <<userName>>");
    }

    private boolean hasOnlyNumbers(String cpr) {
        if(cpr.matches("^[0-9]*$")) {
            return true;
        }
        return false;
    }

    public boolean isValidPassword(String cpr, String pw) {
        var lengthOK = isCorrectLength(pw);
        var alphaNumOK = containsRequiredCharacters(pw);
        var noIllegalCh = hasNoIllegalCharacters(pw);
        var noSpace = hasNoSpaceAtStartOrEnd(pw);
        var noQuadruple = noSequenceOfFourIdenticalCharacters(pw);
        var noCpr = containsNoCpr(cpr, pw);

        if (lengthOK && alphaNumOK && noIllegalCh && noSpace && noQuadruple && noCpr) {
            return true;
        }
        throw new InputMismatchException("invalid password" + pwRequirementMessage());
    }

    private boolean hasNoIllegalCharacters(String pw) {
        var allowed = "[a-zA-Z09" + geAllowedSpecialCharacters() + "]";
        var pattern = Pattern.compile(allowed);
        var matcher = pattern.matcher(pw);
        var illegal = matcher.replaceAll("");
        if(illegal.length() > 0) {
            return false;
        }
        return true;
    }

    private boolean containsRequiredCharacters(String pw) {
        return pw.contains("[a-zA-Z09]");
    }

    private boolean containsNoCpr(String cpr, String pw) {
        var birthDate = cpr.substring(0, 3);
        var cprDigits = cpr.substring(4, 9);

        return !pw.contains(birthDate) && !pw.contains(cprDigits);
    }

    private boolean noSequenceOfFourIdenticalCharacters(String pw) {
        var four = "0000";
        for(var c : pw.split("")) {
            four = c+c+c+c;
        }
        return !pw.contains(four);
    }

    private boolean hasNoSpaceAtStartOrEnd(String pw) {
        return !pw.startsWith(" ") || !pw.endsWith(" ");
    }
    // does this need to return plain String or regex w/backslashes?
    private String geAllowedSpecialCharacters() {
        var specialString = "{ } ! # \" $ ’ % ^ & , * ( ) _ + - = : ; ? . @";
        return specialString.replaceAll("[\\W]", "\\\\$0");
    }

    private boolean isCorrectLength(String pw) {
        return pw.length() >= 6 && pw.length() <= 40;
    }

    private String pwRequirementMessage() {
        return "password must contain alphanumeric characters (a-z, A-Z, 0-9)\n" +
                "password may contain special characters (" + geAllowedSpecialCharacters() + "), \n" +
                "but not other special characters, or nordic letters (æøå)";
    }
}
