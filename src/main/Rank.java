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
            double cw = t.getChamps() + 1; //add one to protect against bug caused by team w/ 0 championships
            double ac = t.getCapacity();
            double aa = t.getAvgAtt();

            /* ALGORITHM
            FS = Wac * ac + Wcw * cw + Waa * aa

            where:
            FS: final score for team
            AC: arena capacity for team
            CW: championship wins for team
            AA: average attendance for team
            wAC, wCW, and wAA are the weights assigned respectively
             */

            t.setScore((Wac.getWeight() * ac) + (Wcw.getWeight() * cw) + (Waa.getWeight() * aa));
        }
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