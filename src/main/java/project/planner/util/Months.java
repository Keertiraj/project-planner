package project.planner.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Months enum to store the Month details.
 */
public enum Months {
    JAN(0), FEB(1), MAR(2),APR(3), MAY(4),JUN(5),JUL(6),AUG(7), SEP(8), OCT(9), NOV(10),DEC(11);

    private int month;

    private static Map<Integer, Months> map = new HashMap<Integer, Months>();

    static {
        for (Months months : Months.values()) {
            map.put(months.month, months);
        }
    }

    private Months(final int id) { month = id; }

    public static Months valueOf(int month) {
        return map.get(month);
    }
}