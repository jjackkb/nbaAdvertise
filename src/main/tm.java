package main;

public class tm extends Command {

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
