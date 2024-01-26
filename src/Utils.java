import java.util.ArrayList;
import java.util.Comparator;

public class Utils {
    public static ArrayList<Team> teamRankSort(ArrayList<Team> arrT) {
        arrT.sort(Comparator.comparingInt(o -> ((o.getChamps() + 1) * o.getCapacity())));

        for (int x = 30; x >= 1; x--) {
            arrT.get(x-1).setRank(31-x);
        }

        return arrT;
    }
}
