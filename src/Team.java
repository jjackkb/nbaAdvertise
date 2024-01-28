import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Team extends Main {

    private static final List<Team> teams = new ArrayList<>();
    private final String name;
    private final String arena;
    private final String location;
    private final String conference;
    private final int capacity;
    private final int champs;
    private final int avgAtt;
    private int rank;
    private double score;

    public Team(String name, String arena, String location, String conference, int capacity, int champs, int avgAttendance) {
        this.name = name;
        this.arena = arena;
        this.location = location;
        this.conference = conference;
        this.capacity = capacity;
        this.champs = champs;
        this.avgAtt = avgAttendance;

        teams.add(this);
    }

    //Accessor Methods
    public int getCapacity() {
        return capacity;
    }
    public int getChamps() {
        return champs;
    }
    public int getAvgAtt() {
        return avgAtt;
    }
    public double getScore() {
        return score;
    }
    public static List<Team> getTeams() {
        return teams;
    }

    public static String getTeamsStr() {
        StringBuilder teamsStr = new StringBuilder();

        for (Team t : Utils.teamRankSort(Team.getTeams())) {
            teamsStr.append(t.toString()).append("\n");
        }

        teamsStr.append("Rank | Name | Score | Arena | Location | Conference | Capacity | Championships | Avg Attendance");

        return teamsStr.toString();
    }

    //Mutator method
    public void setScore(double newScore) {
        this.score = newScore;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }

    //toString method
    public String toString() {
        DecimalFormat numFormat = new DecimalFormat("#.00");

        return rank+" | "+name+" | "+numFormat.format(score)+" | "+arena+" | "+location+" | "+conference+" | "+capacity+" | "+champs+" | "+avgAtt;
    }
}