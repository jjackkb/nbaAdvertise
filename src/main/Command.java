package main;

import java.util.ArrayList;

import static main.Main.inputPrompt;
import static main.Main.sc;

public class Command {

    private static final ArrayList<Command> cmdList = new ArrayList<>();
    private String cmdClass;
    private String className;
    private String disc;
    private String usage;

    public Command() {}

    public Command(String cmdClass, String className, String disc, String usage) {
        this.cmdClass = cmdClass;
        this.className = className;
        this.disc = disc;
        this.usage = usage;

        cmdList.add(this);
    }

    public String getCmdClass() {
        return cmdClass;
    }
    public String getClassName() {
        return className;
    }
    public String getDisc() {
        return disc;
    }
    public String getUsage() {
        return usage;
    }

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
            case "wt" -> wt.ctlProcess(args[1], args[2], args[3]);
            case "tm" -> tm.teamProcess(args[1], args[2], args[3]);
            case "help", "h" -> help();
            case "alg"-> getAlg();
            default -> "Invalid command!";
        };
        return output;
    }

    private static String getAlg() {
        return """
            fs = (Wac * ac) + (Wcw * cw) + (Waa * aa) + (Wat * at)

            where:
            fs: final score for team
            ac: arena capacity
            cw: championship wins
            aa: average attendance
            at: average ticket price
            Wac, Wcw, Waa, Wat are the weights assigned, respectively
            
            **Each team-specific variable is normalized before calculating rank**
                """;
    }

    private static String help() {
        StringBuilder help = new StringBuilder();
        String cmdClassName = "";

        for (Command c : cmdList) {
            if (!c.getCmdClass().equals(cmdClassName) && cmdClassName.isEmpty()) {
                help.append(" --- Command Help --- \n* ").append(c.getCmdClass()).append(" (").append(c.getClassName()).append(")\n");
                cmdClassName = c.getCmdClass();
            } else if (!c.getCmdClass().equals(cmdClassName)) {
                help.append("\n* ").append(c.getCmdClass()).append(" (").append(c.getClassName()).append(")\n");
                cmdClassName = c.getCmdClass();
            }

            help.append("    ").append(c.getUsage()).append("\n        -").append(c.getDisc()).append("\n");
        }
        return help.toString();
    }
}


class wt extends Command {

    private static final Weight Wac = Main.Wac;
    private static final Weight Wcw = Main.Wcw;
    private static final Weight Waa = Main.Waa;
    private static final Weight Wat = Main.Wat;

    protected static String ctlProcess(String cmd, String arg1, String arg2) {
        return switch(cmd) {
            case "set" -> set(arg1, arg2);
            case "setall" -> setAll();
            case "", "settings" -> displaySetting();
            default -> "wt: Error: Unexpected argument " + cmd;
        };
    }

    //set method for changing a components weight in the algorithm
    //usage 'set <setting> <value>'
    protected static String set(String setting, String strVal) {
        try { //requires a try-catch wrap in case unable to assign int value to newVal
            strVal = (strVal == null) ? "" : strVal;
            if (setting.equals("default")) {
                Wac.setWeight(0.05);
                Wcw.setWeight(0.2);
                Waa.setWeight(0.25);
                Wat.setWeight(0.3);
                return "wt: Settings reverted to default";
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
                case "ticket", "at":
                    Wat.setWeight(Double.parseDouble(strVal)/100);
                    break;
                default:
                    return "wt: Error: Setting " + setting + " not found!";
            }
        }
        catch(Exception e) { return "wt: Error: while attempting to change setting!"; }
        return String.format("New Algorithm Weight Settings\n  Capacity: %s%%\n  Championship: %s%%\n  Attendance: %s%%\n  Ticket: %s%%", Wac.getPercent(), Wcw.getPercent(), Waa.getPercent(), Wat.getPercent()).replace("%%", "%");
    }

    private static String setAll() {
        double oldWac = Wac.getWeight();
        double oldWcw = Wcw.getWeight();
        double oldWaa = Waa.getWeight();
        double oldWat = Wat.getWeight();

        System.out.println("Algorithm Weight Percentage Setter\nAll values must have a sum of 100%\nLeave blank to skip specification");

        System.out.print("-- Capacity (Wac) = ");
        Wac.setWeight(queryWeight());

        System.out.print("-- Championship Win (Wcw) = ");
        Wcw.setWeight(queryWeight());

        System.out.print("-- Average Attendance (Waa) = ");
        Waa.setWeight(queryWeight());

        System.out.println("-- Average Ticket (Wat) = ");
        Wat.setWeight(queryWeight());

        Weight.balanceWeight();

        if (Weight.getTotal() != 1) {
            Wac.setWeight(oldWac);
            Wcw.setWeight(oldWcw);
            Waa.setWeight(oldWaa);
            Wat.setWeight(oldWat);

            return "wt: Error: values do not have a sum of 100%! Changes reverted!";
        }

        return "wt: Settings saved";
    }

    private static String displaySetting() {
        return String.format("Algorithm Weight Settings\n  Capacity: %s%%\n  Championship: %s%%\n  Attendance: %s%%\n  Ticket: %s%%", Wac.getPercent(), Wcw.getPercent(), Waa.getPercent(), Wat.getPercent()).replace("%%", "%");
    }

    private static Double queryWeight() {
        String s = sc.nextLine();

        s = (s.isEmpty()) ? "-1" : s;
        return Double.parseDouble(s)/100;
    }
}


class tm extends Command {

    protected static String teamProcess(String cmd, String arg1, String arg2) {
        return switch(cmd) {
            case "", "list" -> teamListDisplay(arg1);
            default -> "tm: Error: unexpected argument " + cmd;
        };
    }

    protected static String teamListDisplay(String arg) {
        StringBuilder teamsStr = new StringBuilder();
        int maxTeam;

        if (arg.isEmpty()) {
            maxTeam = 30;
        } else {
            try { maxTeam = Integer.parseInt(arg); }
            catch (Exception e) { return "tm: Error: invalid team limit! " + arg; }
        }

        for (Team t : Rank.sort()) {
            if (t.getRank() <= maxTeam)
                teamsStr.append(t).append("\n");
        }

        teamsStr.append("""
                - rank -
                ____________________
                name | location
                arena | conference
                ac: capacity | cw: championship wins
                aa: avg attendance | at: avg ticket price
                
                """);
        return teamsStr.toString();
    }
}