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
        // should also check that it's numbers only
        if(!(cpr.length() == 10)) {
            throw new InputMismatchException("invalid user name or password <<userName>>");
        }
        return true;
    }

    public boolean isValidPassword(String cpr, String pw) {
        var lengthOK = isCorrectLength(pw);
        var charOK = hasLegalCharacters(pw);
        var noSpace = hasNoSpaceAtStartOrEnd(pw);
        var noQuadruple = noSequenceOfFourIdenticalCharacters(pw);
        var noCpr = containsNoCpr(cpr, pw);

        if (lengthOK && charOK && noSpace && noQuadruple && noCpr) {
            return true;
        }

        throw new InputMismatchException("invalid password" + pwRequirementMessage());

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
 /*       var first = pw.charAt(0);
        var last = pw.charAt(pw.length() -1);
        return first != ' ' && last != ' ';*/
        return !pw.startsWith(" ") || !pw.endsWith(" ");
    }

    private boolean hasLegalCharacters(String pw) {
        var nrs = "[0-9]"; // or \\d+ ?
        var letters = "[a-zA-Z]";
        var nordics = "[^æøå]";
        //var specialString = Pattern.quote("{ } ! # \" $ ’ % ^ & , * ( ) _ + - = : ; ? . @");
        // \W are non-word characters. They can ALL be escaped (not just reserved specials)
        var allowedSpecial = geAllowedSpecialCharacters();
        var pattern = Pattern.compile(nrs+letters+nordics+allowedSpecial);
        var matcher = pattern.matcher(pw);

        return matcher.matches();
    }

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
