package main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static final Scanner sc = new Scanner(System.in);
    public static Weight Wac = new Weight(0.1);
    public static Weight Wcw = new Weight(0.4);
    public static Weight Waa = new Weight(0.5);

    public static void main(String[] args) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray a = (JSONArray) parser.parse(new FileReader(System.getProperty("user.dir") + "/data.json"));

        //Instantiate every main.Team object with given data
        for (Object o : a) {
            JSONObject team = (JSONObject) o;

            String name = (String) team.get("name");
            String arena = (String) team.get("arena");
            String location = (String) team.get("location");
            String conference = (String) team.get("conference");
            long capacity = (Long) team.get("capacity"); //to get int from json it has to be the long datatype
            long champs = (Long) team.get("championships");
            long avg = (Long) team.get("avgAttendance");

            new Team(name, arena, location, conference, (int)capacity, (int)champs, (int)avg);
        }

        //input/output repeats forever
        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.println(Command.process());
        }
    }

    public static String inputPrompt() {
        System.out.print("~ ");
        return sc.nextLine();
    }
}