package main;

import java.util.ArrayList;

public class Weight {

    private static final ArrayList<Weight> weights = new ArrayList<>();
    private double weight;

    //constructor
    public Weight(double weight) {
        this.weight = weight;

        weights.add(this);
    }

    //set unspecified Weight's to even solution
    public static void balanceWeight() {
        for (Weight w : weights) {
            if (w.weight == -0.01) { //check if Weight has given value or not
                w.setWeight((1-getTotal())/getInvalidWeight()); //if not assign Weight that makes sum 100
            }
        }
    }

    //getter methods
    //Count number of invalid/unspecified Weight's
    private static int getInvalidWeight() {
        int invalid = 0;

        for (Weight w : weights) {
            if (w.weight == -0.01) {
                invalid++;
            }
        }
        return invalid;
    }
    //Get sum of all Weights
    public static double getTotal() {
        double total = 0;

        for (Weight w : weights) {
            if (w.weight != -0.01) {
                total += w.weight;
            }
        }
        return total;
    }
    public double getWeight() {
        return weight;
    }
    public double getPercent() {
        return weight*100;
    }

    //setter methods
    public void setWeight(double weight) {
        this.weight = (int) weight+0.5;
    }
}
