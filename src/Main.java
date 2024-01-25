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

        for (Object o : a)
        {
            JSONObject person = (JSONObject) o;

            String name = (String) person.get("name");
            String arena = (String) person.get("arena");
            String location = (String) person.get("location");
            String coast = (String) person.get("coast");
            //to get ints from json it has to be this command as a long data type
            long capacity = (Long) person.get("capacity");
            long champs = (Long) person.get("championships");
            System.out.println("Name: "+name+", "+"Arena: "+arena+", "+"Location: "+location+", "+"Coast: "+coast+", "+"Capacity: "+capacity+", "+"Championships: "+champs);
        }
    }
}