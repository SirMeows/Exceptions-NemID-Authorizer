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
Tilladte specialtegn er: { } ! # " $ ’ % ^ & , * ( ) _ + - = : ; ? . og @.
*/

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
        var hasCorrectLength = isCorrectLength(pw);
        var hasAlphaNum = containsRequiredCharacters(pw);
        var hasIllegalCh = hasIllegalCharacters(pw);
        var hasSpace = hasSpaceAtStartOrEnd(pw);
        var hasQuadruple = hasSequenceOfFourIdenticalChars(pw);
        var hasCpr = containsCpr(cpr, pw);

        if (hasCorrectLength && hasAlphaNum && !hasIllegalCh && !hasSpace && !hasQuadruple && !hasCpr) {
            return true;
        }
        throw new InputMismatchException("invalid password" + pwRequirementMessage());
    }

    boolean hasIllegalCharacters(String pw) {
        var allowed =  "[a-zA-Z0-9"+ geAllowedSpecialCharacters()+ "]";
        var pattern = Pattern.compile(allowed);
        var matcher = pattern.matcher(pw);
        var illegal = matcher.replaceAll("");
        if(illegal.length() > 0) {
            return true;
        }
        return false;
    }

    boolean containsRequiredCharacters(String pw) {
        var lower= containsPattern("[a-z]", pw);
        var upper= containsPattern("[A-Z]", pw);
        var number= containsPattern("[0-9]", pw);

        return lower && upper && number;
    }

    private boolean containsPattern(String pattern, String pw) {
        return Pattern.compile(pattern).matcher(pw).find();
    }

    boolean containsCpr(String cpr, String pw) {
        var birthDate = cpr.substring(0, 6);
        var cprDigits = cpr.substring(6, 10);

        return pw.contains(birthDate) || pw.contains(cprDigits);
    }

    boolean hasSequenceOfFourIdenticalChars(String pw) {
        var four = "0000";
        for(var c : pw.split("")) {
            four = c+c+c+c;
            if(pw.contains(four)) {
                return true;
            }
        }
        return false;
    }

    boolean hasSpaceAtStartOrEnd(String pw) {
        return pw.startsWith(" ") || pw.endsWith(" ");
        // pw.equals(pw.trim()); removes white space from start and end (only), then compares original with trimmed
    }

    boolean isCorrectLength(String pw) {
        return pw.length() >= 6 && pw.length() <= 40;
    }

    private String geAllowedSpecialCharacters() {
        var specialString = "{ } ! # \" $ ’ % ^ & , * ( ) _ + - = : ; ? . @";
        return specialString.replaceAll("[\\W]", "\\\\$0");
    }

    private String pwRequirementMessage() {
        return "password must contain alphanumeric characters (a-z, A-Z, 0-9)\n" +
                "password may contain special characters (" + geAllowedSpecialCharacters() + "), \n" +
                "but not other special characters, or nordic letters (æøå)";
    }
}