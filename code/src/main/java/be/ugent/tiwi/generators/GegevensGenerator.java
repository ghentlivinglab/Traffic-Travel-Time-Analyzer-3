package be.ugent.tiwi.generators;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eigenaar on 15/04/2016.
 */
public class GegevensGenerator {

    public static void main(String[] args){
        GegevensGenerator g = new GegevensGenerator();
        g.generate();
    }


    public void generate(){
        Map<LocalTime, Double> vertragingen = new HashMap<>();
        vertragingen.put(LocalTime.MIDNIGHT, 1.0);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(1), 1.00);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(2), 1.00);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(3), 1.01);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(4), 1.02);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(5), 1.05);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(6), 1.20);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(7), 2.00);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(8), 2.50);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(9), 1.95);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(10), 1.41);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(11), 1.32);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(12), 1.43);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(13), 1.35);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(15), 1.30);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(16), 1.82);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(17), 2.39);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(18), 1.93);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(19), 1.39);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(20), 1.15);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(21), 1.08);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(22), 1.07);
        vertragingen.put(LocalTime.MIDNIGHT.plusHours(23), 1.02);

        // TODO
    }

}
