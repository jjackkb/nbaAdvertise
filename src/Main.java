import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        int[] caps;

        business b1 = new business("west", 1900, 3);
        Object o = new JSONParser().parse(new FileReader(data.json));
        JSONObject j = (JSONObject) o;

        if (j.get(“coast”) >= b1.getSection()){
            caps[] += j.get("capacities");
        }
    }
}