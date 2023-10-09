package cz.muni.fi.pv168.project.ui.sorters;

import java.util.Comparator;

public class FloatComparator implements Comparator<Float> {
    @Override
    public int compare(Float f1, Float f2) {
        return f1.compareTo(f2);
    }
}