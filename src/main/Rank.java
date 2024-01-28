package main;

import java.util.Comparator;
import java.util.List;

import static main.Main.*;

public class Rank extends Team {
    private double score;
    private int rankVal;

   public Rank() {}

    public static List<Team> sort() {
        List<Team> tArray = Team.getTeams();

        calcScore();
        tArray.sort(new RankComparator());
        calcRank();

        return tArray;
    }

    private static void calcScore() {
        for (Team t : Team.getTeams()) {
            double ac = normalise(t.getCapacity(), 1);
            double cw = normalise((t.getChamps()+1), 2); //add one to protect against bug caused by team w/ 0 championships
            double aa = normalise(t.getAvgAtt(), 3);
            double at = normalise(t.getTicket(), 4);

            /* ALGORITHM
            FS = Wac * ac + Wcw * cw + Waa * aa + Wat * at

            where:
            FS: final score for team
            AC: arena capacity for team
            CW: championship wins for team
            AA: average attendance for team
            wAC, wCW, and wAA are the weights assigned respectively
             */

            t.setScore((Wac.getWeight() * ac) + (Wcw.getWeight() * cw) + (Waa.getWeight() * aa) + (Wat.getWeight() * at));
        }
    }
    private static double normalise(double i, int t) {
       return switch(t) {
           case 1 -> (i-17923)/(21600-17923);
           case 2 -> (i-0)/(17);
           case 3 -> (i-16306)/(20177-16304);
           case 4 -> (i-138)/(611-138);
           default -> 0;
       };
    }
    private static void calcRank() {
       int x = 30;

        for (Team t : Team.getTeams()) {
            t.setRank(x);
            x--;
        }
    }

    //getter methods
    public double getScore() {
        return score;
    }
    public int getRankVal() {
        return rankVal;
    }

    //setter methods
    public void setScore(double score) {
       this.score = score;
    }
    public void setRank(int rankVal) {
        this.rankVal = rankVal;
    }
}

class RankComparator implements Comparator<Team> {
    @Override
    public int compare(Team t1, Team t2) {
        return Double.compare(t1.getScore(), t2.getScore());
    }
}