package cz.muni.fi.pv168.project.ui.sorters;

import java.util.Comparator;

public class IntCompatator implements Comparator<Integer> {
    @Override
    public int compare(Integer i1, Integer i2) {
        return i1.compareTo(i2);
    }
}
