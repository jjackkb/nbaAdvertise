//Utils class contains misc and general-use methods
import java.util.Scanner;

public class Utils extends Main {
    private static final Scanner sc = new Scanner(System.in);

    //user input prompt and command processing
    public static String intrfaceInput() {
        //int y is used as the index for cmd and cmdArray
        int y = 0;
        String output;
        String[] cmd = new String[3];

        //inline input
        System.out.print("~ ");
        String cmdArgs = (sc.nextLine());

        //Assign given command arguments to cmd String array
        for (String s : cmdArgs.split(" ")) {
            cmd[y] = s;
            y++;
        }

        //execute respective command method
        //*all methods called from this switch should return a String output that is then printed to the terminal*
        output = switch (cmd[0]) {
            case "ranked" -> Team.getTeamsStr();
            case "set" -> set(cmd[1], cmd[2]);
            default -> "Incorrect Command! " + cmd[0];
        };

        //Print 14 blank lines between command output and input
        for (int x = 0; x < 14; x++) {
            System.out.println();
        }

        return output;
    }

    //set method for changing a components weight in the algorithm
    //usage 'set <setting> <value>'
    public static String set(String setting, String strVal) {
        if (setting.equals("default")) {
            //Team.capacityWeight = 60;
            //Team.championshipWeight = 40;
            return "Settings reverted to default!";
        }

        //requires a try-catch wrap in case unable to assign int value to newVal
        try {
            int newVal = Integer.parseInt(strVal);

            if (newVal >= 0 && newVal <= 100) {
                switch (setting) {
                    case "capacity":
                        //Team.capacityWeight = newVal;
                        break;
                    case "championship":
                        //Team.championshipWeight = newVal;
                    default:
                        return "Setting " + setting + " not found!";
                }
            } else {
                return "Error! weight value must be between 0 and 100";
            }
        }
        catch(Exception e){
            return "Error while attempting to change setting!";
        }
        return setting + " weight changed to " + strVal;
    }
}