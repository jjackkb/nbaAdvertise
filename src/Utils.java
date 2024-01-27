//Utils class contains misc and general-use methods
import java.util.*;

public class Utils extends Main {
    private static final Scanner sc = new Scanner(System.in);
    private static double capacityWeight = 50;
    private static double championshipWeight = 50;

    //user input prompt and command processing
    public static String intrfaceInput() {
        //int y is used as the index for cmd and cmdArray
        int y = 0;
        String output;
        String[] cmd = new String[4];

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
            case "list" -> Team.getTeamsStr();
            case "setting" -> switch(cmd[1]) {
                case "set" -> set(cmd[2], cmd[3]);
                case null ->  sList();
                default -> "Error: Specify command argument";
            };
            default -> "Incorrect Command! " + cmd[0];
        };

        //Print 14 blank lines between command output and input
        for (int x = 0; x < 14; x++) {
            System.out.println();
        }
        return output;
    }

    public static List<Team> teamRankSort(List<Team> ta) {
        double champPercent = (championshipWeight/100);
        double capPercent = (capacityWeight/100);

        for (int x = 30; x >= 1; x--) {
            ta.get(x-1).setScore(((champPercent * ta.get(x-1).getChamps()+1) + (capPercent * ta.get(x-1).getCapacity())));
        }

        ta.sort(new PlayerComparator());

        for (int x = 30; x >= 1; x--) {
            ta.get(x-1).setRank(31-x);
        }

        return ta;
    }

    //set method for changing a components weight in the algorithm
    //usage 'set <setting> <value>'
    public static String set(String setting, String strVal) {
        if (setting.equals("default")) {
            capacityWeight = 50;
            championshipWeight = 50;
            return "Settings reverted to default!";
        }

        //requires a try-catch wrap in case unable to assign int value to newVal
        try {
            int newVal = Integer.parseInt(strVal);

            if (newVal >= 0 && newVal <= 100) {
                switch (setting) {
                    case "capacity":
                        capacityWeight = newVal;
                        championshipWeight = 100-newVal;
                        break;
                    case "championship":
                        championshipWeight = newVal;
                        capacityWeight = 100-newVal;
                        break;
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
        return String.format("New Algorithm Weight Settings\n  Capacity: %s%%\n  Championship: %s%%", capacityWeight, championshipWeight).replace("%%", "%");
    }

    public static String sList() {
        return String.format("Algorithm Weight Settings\n  Capacity: %s%%\n  Championship: %s%%", capacityWeight, championshipWeight).replace("%%", "%");
    }
}
class PlayerComparator implements Comparator<Team> {
    @Override
    public int compare(Team o1, Team o2) {
        return Double.compare(o1.getScore(), o2.getScore());
    }
}