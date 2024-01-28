package main;

import static main.Main.inputPrompt;
import static main.Main.sc;

public class Command {

    public static String process() {
        int y = 0;
        String output;
        String[] args = new String[4];

        String cmd = inputPrompt();

        for (String s : cmd.split(" ")) {
            args[y] = s;
            y++;
        }

        for (int x = 0; x < 4; x++) {
            if (args[x] == null) {
                args[x] = "";
            }
        }
        
        output = switch(args[0]) {
            case "ctl" -> ctl.ctlProcess(args[1], args[2], args[3]);
            case "team" -> tm.teamProcess(args[1], args[2], args[3]);
            default -> "Invalid command!";
        };
        return output;
    }
}


class ctl extends Command {

    private static final Weight Wac = Main.Wac;
    private static final Weight Wcw = Main.Wcw;
    private static final Weight Waa = Main.Waa;

    protected static String ctlProcess(String cmd, String arg1, String arg2) {
        return switch(cmd) {
            case "set" -> switch(arg1) {
                case "a", "all" -> setAll();
                default -> set(arg1, arg2);
            };
            case "", "settings" -> displaySetting();
            default -> "ctl: Error: Unexpected argument " + cmd;
        };
    }

    //set method for changing a components weight in the algorithm
    //usage 'set <setting> <value>'
    protected static String set(String setting, String strVal) {
        try { //requires a try-catch wrap in case unable to assign int value to newVal
            strVal = (strVal == null) ? "" : strVal;
            if (setting.equals("default")) {
                Wac.setWeight(0.1);
                Wcw.setWeight(0.4);
                Waa.setWeight(0.5);
                return "ctl: Settings reverted to default";
            }

            switch (setting.toLowerCase()) {
                case "capacity", "ac":
                    Wac.setWeight(Double.parseDouble(strVal)/100);
                    break;
                case "championship", "cw":
                    Wcw.setWeight(Double.parseDouble(strVal)/100);
                    break;
                case "attendance", "aa":
                    Waa.setWeight(Double.parseDouble(strVal)/100);
                    break;
                default:
                    return "ctl: Error: Setting " + setting + " not found!";
            }
        }
        catch(Exception e) { return "ctl: Error: while attempting to change setting!"; }
        return String.format("New Algorithm Weight Settings\n  Capacity: %s%%\n  Championship: %s%%\n  Attendance: %s%%", Wac.getPercent(), Wcw.getPercent(), Waa.getPercent()).replace("%%", "%");
    }

    private static String setAll() {
        double oldWac = Wac.getWeight();
        double oldWcw = Wcw.getWeight();
        double oldWaa = Waa.getWeight();

        System.out.println("Algorithm Weight Percentage Setter\nAll values must have a sum of 100\nLeave blank to skip specification");

        System.out.print("-- Capacity (Wac) = ");
        Wac.setWeight(queryWeight());

        System.out.print("-- Championship Win (Wcw) = ");
        Wcw.setWeight(queryWeight());

        System.out.print("-- Average Attendance (Waa) = ");
        Waa.setWeight(queryWeight());

        Weight.balanceWeight();

        if (Weight.getTotal() != 1) {
            Wac.setWeight(oldWac);
            Wcw.setWeight(oldWcw);
            Waa.setWeight(oldWaa);

            return "ctl: Error: values do not have a sum of 100! Changes reverted!";
        }

        return "ctl: Settings saved";
    }

    private static Double queryWeight() {
        String s = sc.nextLine();

        s = (s.isEmpty()) ? "-1" : s;
        return Double.parseDouble(s)/100;
    }

    private static String displaySetting() {
        return String.format("Algorithm Weight Settings\n  Capacity: %s%%\n  Championship: %s%%\n  Attendance: %s%%", Wac.getPercent(), Wcw.getPercent(), Waa.getPercent()).replace("%%", "%");
    }
}


class tm extends Command {

    protected static String teamProcess(String cmd, String arg1, String arg2) {
        return switch(cmd) {
            case "", "list" -> teamListDisplay();
            default -> "team: Error: unexpected argument " + cmd;
        };
    }

    protected static String teamListDisplay() {
        StringBuilder teamsStr = new StringBuilder();

        for (Team t : Rank.sort()) {
            teamsStr.append(t.toString()).append("\n");
        }
        teamsStr.append("main.Rank | Name | Score | Arena | Location | Conference | Capacity | Championships | Avg Attendance");
        return teamsStr.toString();
    }
}