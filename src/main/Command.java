package main;

import java.util.ArrayList;

import static main.Main.inputPrompt;
import static main.Main.sc;

public class Command {

    private static final ArrayList<Command> cmdList = new ArrayList<>();
    private static boolean running = true;
    private String cmdClass;
    private String className;
    private String disc;
    private String usage;

    public Command() {}

    //constructor
    public Command(String cmdClass, String className, String disc, String usage) {
        this.cmdClass = cmdClass;
        this.className = className;
        this.disc = disc;
        this.usage = usage;

        cmdList.add(this);
    }

    //getter methods
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
    public static boolean getRunning() {
        return running;
    }

    //set method
    public static void setRunning() {
        running = false;
    }

    //called by Main input loop to run given command
    public static String process() {
        //Fill String[] args with given command and args (null is replaced with "")
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

        //switch operator to assign String output return of command method
        output = switch(args[0]) {
            case "wt" -> wt.ctlProcess(args[1], args[2], args[3]);
            case "tm" -> tm.teamProcess(args[1], args[2], args[3]);
            case "help", "h" -> help();
            case "alg"-> getAlg();
            case "quit", "q" -> quit();
            default -> "Invalid command!";
        };
        return output;
    }

    //basic command to retrieve text explaining the algorithm
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

    //changes running boolean to false, stopping program on next iteration of while loop
    private static String quit() {
        setRunning();
        return "Quitting...";
    }

    //returns String help message for all commands
    private static String help() {
        StringBuilder help = new StringBuilder();
        String cmdClassName = "";

        for (Command c : cmdList) {
            //Check if command is first to be added to final String
            if (!c.getCmdClass().equals(cmdClassName) && cmdClassName.isEmpty()) {
                help.append(" --- Command Help --- \n* ").append(c.getCmdClass()).append(" (").append(c.getClassName()).append(")\n");
                cmdClassName = c.getCmdClass();
            } else if (!c.getCmdClass().equals(cmdClassName)) { //Check if other subcommands from same main command have been added
                if (c.getClassName().isEmpty()) {
                    help.append("\n* ").append(c.getCmdClass()).append("\n");
                } else {
                    help.append("\n* ").append(c.getCmdClass()).append(" (").append(c.getClassName()).append(")\n");
                }
                cmdClassName = c.getCmdClass();
            }
            help.append("    ").append(c.getUsage()).append("\n        -").append(c.getDisc()).append("\n"); //append final subcommand message
        }
        return help.toString();
    }
}

//Weight Command
class wt extends Command {

    //instantiate reference to Main Weight instances
    private static final Weight Wac = Main.Wac;
    private static final Weight Wcw = Main.Wcw;
    private static final Weight Waa = Main.Waa;
    private static final Weight Wat = Main.Wat;

    //proccess subcommand and arguments
    protected static String ctlProcess(String cmd, String arg1, String arg2) {
        return switch(cmd) {
            case "set" -> set(arg1, arg2);
            case "setall" -> setAll();
            case "", "settings" -> displaySetting();
            default -> "wt: Error: Unexpected argument " + cmd;
        };
    }

    //set method to change a components weight in the algorithm
    protected static String set(String setting, String strVal) {
        try { //requires a try-catch wrap in case unable to assign int value to newVal
            strVal = (strVal == null) ? "" : strVal;

            //set default settings
            if (setting.equals("default")) {
                Wac.setWeight(0.10);
                Wcw.setWeight(0.2);
                Waa.setWeight(0.3);
                Wat.setWeight(0.4);
                return "wt: Settings reverted to default";
            }

            //switch to assign given arg val to given arg component
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

    //Provides prompts to user to set every component's weight
    private static String setAll() {
        //save Weight's in case given values !=100
        double oldWac = Wac.getWeight();
        double oldWcw = Wcw.getWeight();
        double oldWaa = Waa.getWeight();
        double oldWat = Wat.getWeight();

        //intro
        System.out.println("Algorithm Weight Percentage Setter\nAll values must have a sum of 100%\nLeave blank to skip specification");

        //go through each weight
        System.out.print("-- Capacity (Wac) = ");
        Wac.setWeight(queryWeight()); //prompt what user wants Weight to be

        System.out.print("-- Championship Win (Wcw) = ");
        Wcw.setWeight(queryWeight());

        System.out.print("-- Average Attendance (Waa) = ");
        Waa.setWeight(queryWeight());

        System.out.println("-- Average Ticket (Wat) = ");
        Wat.setWeight(queryWeight());

        //call Weight class balance method
        Weight.balanceWeight();

        //check if weights do or do not equal 100
        if (Weight.getTotal() != 1) {
            //if they do not have a sum of 100 then revert all Weight's to earlier version
            Wac.setWeight(oldWac);
            Wcw.setWeight(oldWcw);
            Waa.setWeight(oldWaa);
            Wat.setWeight(oldWat);

            return "wt: Error: values do not have a sum of 100%! Changes reverted!";
        }
        return "wt: Settings saved";
    }

    private static String displaySetting() { //display current Weight settings
        return String.format("Algorithm Weight Settings\n  Capacity: %s%%\n  Championship: %s%%\n  Attendance: %s%%\n  Ticket: %s%%", Wac.getPercent(), Wcw.getPercent(), Waa.getPercent(), Wat.getPercent()).replace("%%", "%");
    }

    private static Double queryWeight() { //prompt user to input new Weight value
        String s = sc.nextLine();

        s = (s.isEmpty()) ? "-1" : s;
        return Double.parseDouble(s)/100;
    }
}

//Team command
class tm extends Command {

    //process given cmd and args
    protected static String teamProcess(String cmd, String arg1, String arg2) {
        //redundant but compatible switch operator
        return switch(cmd) {
            case "", "list" -> teamListDisplay(arg1);
            default -> "tm: Error: unexpected argument " + cmd;
        };
    }

    //List every Team and their stats
    protected static String teamListDisplay(String arg) {
        StringBuilder teamsStr = new StringBuilder();
        int maxTeam;

        if (arg.isEmpty()) { //if user does not specify displayed Teams limit
            maxTeam = 30;
        } else {
            try { maxTeam = Integer.parseInt(arg); } //try and get team int limit
            catch (Exception e) { return "tm: Error: invalid team limit! " + arg; }
        }

        //loop through Teams in sorted array by rank
        for (Team t : Rank.sort()) {
            if (t.getRank() <= maxTeam)
                teamsStr.append(t).append("\n");
        }

        //append Team String
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
