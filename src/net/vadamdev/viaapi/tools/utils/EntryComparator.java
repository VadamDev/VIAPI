package net.vadamdev.viaapi.tools.utils;

import java.util.Comparator;
import java.util.Map;

/**
 * @author VadamDev
 * @since 15/04/2022
 */
public class EntryComparator implements Comparator<Map.Entry<String, Double>> {
    @Override
    public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
        return o1.getValue().compareTo(o2.getValue());
    }
}
