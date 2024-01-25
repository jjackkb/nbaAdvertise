import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        instantiate();
        displayTeams();
    }

    private static void instantiate() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray a = (JSONArray) parser.parse(new FileReader(System.getProperty("user.dir") + "/data.json"));

        //Instantiate every team object with given data
        for (Object o : a) {
            JSONObject person = (JSONObject) o;

            String name = (String) person.get("name");
            String arena = (String) person.get("arena");
            String location = (String) person.get("location");
            String conference = (String) person.get("conference");
            long capacity = (Long) person.get("capacity"); //to get int from json it has to be this command as a long data type
            long champs = (Long) person.get("championships");

            new Team(name, arena, location, conference, (int) capacity, (int) champs);
        }
    }

    private static void displayTeams() {
        System.out.println("| Name | Arena | Location | Conference | Capacity | Championships |");
        for (Team t : Team.getTeams()) {
            System.out.println(t.toString());
        }
    }

    private static ArrayList<Team> rankTeams() {
        ArrayList<Team> rankedTeams = new ArrayList<>();

        for (Team t : Team.getTeams()) {
            int tScore = t.getChamps() * t.getCapacity();
            for (Team t2 : rankedTeams) {
                int t2Score = t.getChamps() * t.getCapacity();
                if (tScore > t2Score) {

                }
            }
        }
        return rankedTeams;
    }
}
