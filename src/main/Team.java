package main;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private static final List<Team> teams = new ArrayList<>();

    private Rank rank;
    private String name;
    private String arena;
    private String location;
    private String conference;
    private int capacity;
    private int champs;
    private int avgAtt;
    private int avgTicket;

    //main constructor
    public Team(String name, String arena, String location, String conference, int capacity, int champs, int avgAttendance, int avgTicket) {
        this.name = name;
        this.arena = arena;
        this.location = location;
        this.conference = conference;
        this.capacity = capacity;
        this.champs = champs;
        this.avgAtt = avgAttendance;
        this.avgTicket = avgTicket;
        this.rank = new Rank();

        teams.add(this);
    }

    //alt constructor
    public Team() {}

    //getter methods
    public static List<Team> getTeams() {
        return teams;
    }
    public double getScore() {
        return rank.getScore();
    }
    public int getRank() {
        return rank.getRankVal();
    }
    public int getCapacity() {
        return capacity;
    }
    public int getChamps() {
        return champs;
    }
    public int getAvgAtt() {
        return avgAtt;
    }
    public int getTicket() {
        return avgTicket;
    }

    //setter methods
    public void setScore(double s) {
        rank.setScore(s);
    }
    public void setRank(int r) {
        rank.setRank(r);
    }

    //toString method
    public String toString() {
        return String.format("""
                - %s -
                ____________________
                %s | %s
                %s | %s
                ac: %s | cw: %s
                aa: %s | at: $%s
                """, rank.getRankVal(), name, location, arena, conference, capacity, champs, avgAtt, avgTicket);
    }
}