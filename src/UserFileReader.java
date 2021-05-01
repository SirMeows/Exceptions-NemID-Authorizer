import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UserFileReader {

    private final String path = "E:\\IntelliJ Projects\\KEA Software Development\\Exceptions_NemIdAuth\\src\\login_information_file";

    public boolean isValidUser(String cpr, String pw) throws NoSuchUserException {
        try {
            var userData = new File(path);
            var sc = new Scanner(userData);
            sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] splitted = line.split(";");
                if(splitted[0].equals(cpr) && splitted[1].equals(pw)) {
                    return true;
                }
            }
            return false;

        } catch (FileNotFoundException e) {
            System.err.println("file not found");
        }
        throw new NoSuchUserException();
    }
}
