import java.util.InputMismatchException;
import java.util.Scanner;
/*Exercise 1
        Create a class called "NemIdAuthorizer". NemIdAuthorizer has a method called
        isValidInput that returns a boolean value. isValidInput receives two parameters:
        String cpr & String password.
        • If cpr does not adhere to a valid cpr number - it will throw a new Input-
        MismatchException
        • If InputMismatchException is thrown - the program will inform the user
        and request another input
Exercise 2
        Write a data source for NemIdAuthorizer. The data source is a .csv le with
        2 columns: Username & Password. Expand the program such that a custom
        exception "NoSuchUserException" is thrown, if a certain user does not exist.*/

public class Application {

    public static void main(String[] args) {
        var nemIdAuth = new NemIdAuthorizer();
        var reader = new UserFileReader();
        var sc = new Scanner(System.in);
        var cpr = "";
        var pw = "";

        while (true) {
            try {
                System.out.println("type your cpr nr");
                cpr = sc.nextLine();
                if(nemIdAuth.isValidCpr(cpr)) {
                    System.out.println("type your NemID password");
                    pw = sc.nextLine();
                    if(reader.isValidUser(cpr, pw)) {
                        System.out.println("login successful");
                        break;
                    }
                }
            } catch (InputMismatchException | NoSuchUserException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
