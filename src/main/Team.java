package main;

import java.text.DecimalFormat;
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

    public Team(String name, String arena, String location, String conference, int capacity, int champs, int avgAttendance) {
        this.name = name;
        this.arena = arena;
        this.location = location;
        this.conference = conference;
        this.capacity = capacity;
        this.champs = champs;
        this.avgAtt = avgAttendance;
        this.rank = new Rank();

        teams.add(this);
    }

    public Team() {}

    //getter methods
    public static List<Team> getTeams() {
        return teams;
    }
    public double getScore() {
        return rank.getScore();
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


    //setter methods
    public void setScore(double s) {
        rank.setScore(s);
    }
    public void setRank(int r) {
        rank.setRank(r);
    }

    //toString method
    public String toString() {
        DecimalFormat numFormat = new DecimalFormat("#.00");

        return rank.getRankVal()+" | "+name+" | "+numFormat.format(rank.getScore())+" | "+arena+" | "+location+" | "+conference+" | "+capacity+" | "+champs+" | "+avgAtt;
    }
}