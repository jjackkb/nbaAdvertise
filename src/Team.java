import java.util.ArrayList;

public class Team extends Main {
    private static final ArrayList<Team> teams = new ArrayList<>();
    private final String name;
    private final String arena;
    private final String location;
    private final String conference;
    private final int capacity;
    private final int champs;

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
    public static ArrayList<Team> getTeams() {
        return teams;
    }

    //toString method
    public String toString() {
        return name+" | "+arena+" | "+location+" | "+conference+" | "+capacity+" | "+champs;
    }
}