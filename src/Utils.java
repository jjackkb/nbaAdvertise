//Utils class contains misc and general-use methods
import java.util.*;

public class Utils extends Main {

    private static final Scanner sc = new Scanner(System.in);
    //arena capacity weight, championship win weight and average attendance weight, respectively
    private static double Wac = 0.1;
    private static double Wcw = 0.4;
    private static double Waa = 0.5;

    //user input prompt and command processing
    public static String intrfaceInput() {
        int y = 0; //int y is used as the index for cmd and cmdArray
        String output;
        String[] cmd = new String[4];

        System.out.print("~ "); //inline input
        String cmdArgs = (sc.nextLine());

        //Assign given command arguments to cmd String array
        for (String s : cmdArgs.split(" ")) {
            cmd[y] = s;
            y++;
        }

        //execute respective command method
        //*all methods called from this switch should return a String output that is then printed to the terminal*
        output = switch (cmd[0]) {
            case "team" -> switch(cmd[1]) {
                case null -> Team.getTeamsStr();
                default -> "team: Invalid team argument '" + cmd[1];
            };
            case "ctl" -> switch(cmd[1]) {
                case "set" -> set(cmd[2], cmd[3]);
                case null ->  sList();
                default -> "ctl: invalid ctl argument '" + cmd[1];
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
        double cw;
        double ac;
        int aa;
        int x;

        for (Team t : Team.getTeams()) {
            cw = t.getChamps()+1; //add one to protect against bug caused by team w/ 0 championships
            ac = t.getCapacity();
            aa = t.getAvgAtt();

            /* ALGORITHM
            FS = Wac * ac + Wcw * cw + Waa * aa

            where:
            FS: final score for team
            AC: arena capacity for team
            CW: championship wins for team
            AA: average attendance for team
            wAC, wCW, and wAA are the weights assigned respectively
             */

            t.setScore((Wac*ac) + (Wcw*cw) + (Waa*aa));
        }

        ta.sort(new PlayerComparator());

        for (x = 30; x >= 1; x--) {
            ta.get(x-1).setRank(31-x);
        }
        return ta;
    }

    private static String setAll() {
        double oldWac = Wac;
        double oldWcw = Wcw;
        double oldWaa = Waa;
        double val = 0.00;

        System.out.println("Algorithm Weight Percentage Setter\nAll values must have a sum of 100\nLeave blank to skip specification");
        for (int x = 0; x < 3; x++) {
            if (x == 0) {
                System.out.print("-- Capacity (Wac) = ");
                Wac = (double) Integer.parseInt(sc.nextLine()) /100;
            }
            if (x == 1) {
                System.out.print("-- Championship Win (Wcw) = ");
                Wcw = (double) Integer.parseInt(sc.nextLine()) /100;
            }
            if (x == 2) {
                System.out.print("-- Average Attendance (Waa) = ");
                Waa = (double) Integer.parseInt(sc.nextLine()) /100;

            }
        }

        if (Wac+Wcw+Waa != 1) {
            Wac = oldWac;
            Wcw = oldWcw;
            Waa = oldWaa;

            return "ctl: Error: values do not have a sum of 100! Changes reverted!";
        }
        return "ctl: Settings saved";
    }

    //set method for changing a components weight in the algorithm
    //usage 'set <setting> <value>'
    private static String set(String setting, String strVal) {
        if (setting.equals("default")) {
            Wac = 0.1;
            Wcw = 0.4;
            Waa = 0.5;
            return "Settings reverted to default";
        }

        try { //requires a try-catch wrap in case unable to assign int value to newVal

                switch (setting.toLowerCase()) {
                    case "all", "a":
                        return setAll();
                    case "capacity", "ac":
                        Wac = Integer.parseInt(strVal);
                        break;
                    case "championship", "cw":
                        Wcw = Integer.parseInt(strVal);
                        break;
                    case "attendance", "aa":
                        Waa = Integer.parseInt(strVal);
                        break;
                    default:
                        return "Setting " + setting + " not found!";
                }
        }
        catch(Exception e) { return "Error while attempting to change setting!"; }
        return String.format("New Algorithm Weight Settings\n  Capacity: %s%%\n  Championship: %s%%\n  Attendance: %s%%", Wac*100, Wcw*100, Waa*100).replace("%%", "%");
    }

    public static String sList() {
        return String.format("Algorithm Weight Settings\n  Capacity: %s%%\n  Championship: %s%%\n  Attendance: %s%%", Wac*100, Wcw*100, Waa*100).replace("%%", "%");
    }
}

class PlayerComparator implements Comparator<Team> {
    @Override
    public int compare(Team o1, Team o2) {
        return Double.compare(o1.getScore(), o2.getScore());
    }
}