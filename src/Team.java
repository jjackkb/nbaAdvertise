import java.util.ArrayList;

public class Team extends Main {
    private static final ArrayList<Team> teams = new ArrayList<>();
    private final String name;
    private final String arena;
    private final String location;
    private final String conference;
    private final int capacity;
    private final int champs;
    private int rank;

    public Team(String name, String arena, String location, String conference, int capacity, int champs) {
        this.name = name;
        this.arena = arena;
        this.location = location;
        this.conference = conference;
        this.capacity = capacity;
        this.champs = champs;

        teams.add(this);
    }

    //Accessor Methods
    public String getName() {
        return name;
    }
    public String getArena() {
        return arena;
    }
    public String getLocation() {
        return location;
    }
    public String getConference() {
        return conference;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getChamps() {
        return champs;
    }
    public int getRank() {
        return rank;
    }
    public static ArrayList<Team> getTeams() {
        return teams;
    }
    public static String getTeamsStr() {
        StringBuilder teamsStr = new StringBuilder();

        teamsStr.append("Name | Arena | Location | Conference | Capacity | Championships\n");
        for (Team t : Team.getTeams()) {
            teamsStr.append(t.toString()).append("\n");
        }

        teamsStr.delete(teamsStr.length()-1,teamsStr.length());

        return teamsStr.toString();
    }

    //Mutator method
    public void setRank(int rank) {
        this.rank = rank;
    }

    //toString method
    public String toString() {
        return rank+" | "+name+" | "+arena+" | "+location+" | "+conference+" | "+capacity+" | "+champs;
    }
}