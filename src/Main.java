import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray a = (JSONArray) parser.parse(new FileReader(System.getProperty("user.dir")+"/data.json"));

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

        //Print out every Team in teams arrayList
        System.out.println("| Name | Arena | Location | Conference | Capacity | Championships |");
        for (Team t : Team.getTeams()) {
            System.out.println(t.toString());
        }
    }
}
