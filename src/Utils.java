import java.util.Scanner;

public class Utils extends Main {
	private static final Scanner sc = new Scanner(System.in);

    public static String intrfaceInput() {
        String output;

        System.out.print("~ ");
        output = switch (sc.nextLine()) {
            case "ranked" -> Team.getTeamsStr();
            case "test" -> "Test Complete!";
            default -> "Incorrect Command!";
        };

        for (int x = 0; x < 14; x++) {
            System.out.println();
        }

        return output;
    }
}
