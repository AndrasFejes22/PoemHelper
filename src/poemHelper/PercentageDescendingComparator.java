package poemHelper;

import java.util.Map;
import java.util.Comparator;

public class PercentageDescendingComparator implements Comparator<Map.Entry<Character, Double>> {

    @Override
    public int compare(Map.Entry<Character, Double> o1, Map.Entry<Character, Double> o2) {
        // TODO Auto-generated method stub
        return Double.compare(o2.getValue(), o1.getValue()); //csökkenő sorrendhez
    }
}
