import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        instantiate();

        //input/output repeats forever
        //noinspection InfiniteLoopStatement
        while (true) {
            dspIntrface();
        }
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
            long capacity = (Long) person.get("capacity"); //to get int from json it has to be the long datatype
            long champs = (Long) person.get("championships");

            new Team(name, arena, location, conference, (int) capacity, (int) champs);
        }
    }

    //this method is what is run in the while loop to prompt for input repeatedly
    //the Utils.intrfaceInput is executed, prompts for input, runs respective method and returns String output
    private static void dspIntrface() {
        System.out.println(Utils.intrfaceInput());
    }
    }