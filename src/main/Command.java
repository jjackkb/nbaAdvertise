package main;

import static main.Main.inputPrompt;

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