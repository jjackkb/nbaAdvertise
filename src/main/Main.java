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

    public static Weight Wac = new Weight(0.05);
    public static Weight Wcw = new Weight(0.20);
    public static Weight Waa = new Weight(0.25);
    public static Weight Wat = new Weight(0.30);

    public static void main(String[] args) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray a = (JSONArray) parser.parse(new FileReader(System.getProperty("user.dir") + "/data.json"));

        new Command("wt", "weight", "set weight for a component of the algorithm", "set <component> <new value>");
        new Command("wt", "weight", "set weight for every component of the algorithm", "setall");
        new Command("wt", "weight", "displays current weight settings", "settings");

        new Command("tm","team", "Display list of NBA teams (leave <number of teams> blank to display all)", "list <number of teams>");

        new Command("alg", "Algorithm", "Displays algorithm the program uses", "alg");

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
            long ticket = (Long) team.get("ticket");

            new Team(name, arena, location, conference, (int)capacity, (int)champs, (int)avg, (int)ticket);
        }

        //input/output repeats forever
        System.out.println("""
        _                       _                _   _          
       | |             /\\      | |              | | (_)         
  _ __ | |__   __ _   /  \\   __| |_   _____ _ __| |_ _ ___  ___ 
 | '_ \\| '_ \\ / _` | / /\\ \\ / _` \\ \\ / / _ \\ '__| __| / __|/ _ \\
 | | | | |_) | (_| |/ ____ \\ (_| |\\ V /  __/ |  | |_| \\__ \\  __/
 |_| |_|_.__/ \\__,_/_/    \\_\\__,_| \\_/ \\___|_|   \\__|_|___/\\___|
                                                                
""");
        System.out.println("Welcome to nbaAdvertise!\nThis program uses NBA team and arena data to determine the best arenas to put your ad in!\nIf you are lost, start with the 'help' command.\nCreated by Jack and Cam");
        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.println(Command.process());
            System.out.println("\n\n");
        }
    }

    public static String inputPrompt() {
        System.out.print("~ ");
        return sc.nextLine();
    }
}
