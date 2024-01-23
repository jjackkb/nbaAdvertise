import java.io.FileReader;

public class business extends Main{
    private String section;
    private int numConsumers;
    private int champs;

    public business(String section, int numConsumers, int champs){
        this.section = section;
        this.numConsumers = numConsumers;
        this.champs = champs;
    }
    public String getSection(){
        return section;
    }
    public int getNumConsumers(){
        return numConsumers;
    }
    public int getChamps(){
        return champs;
    }
}
