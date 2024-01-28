package main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Weight {

    private static final ArrayList<Weight> weights = new ArrayList<>();
    private double weight;

    public Weight(double weight) {
        this.weight = weight;

        weights.add(this);
    }

    public static void balanceWeight() {
        for (Weight w : weights) {
            if (w.weight == -0.01) {
                w.setWeight((1-getTotal())/getInvalidWeight());
            }
        }
    }

    //getter methods
    private static int getInvalidWeight() {
        int invalid = 0;

        for (Weight w : weights) {
            if (w.weight == -0.01) {
                invalid++;
            }
        }
        return invalid;
    }
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
        this.weight = round(weight);
    }

    //misc
    private static double round(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
