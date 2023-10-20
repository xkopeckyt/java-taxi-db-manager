package cz.muni.fi.pv168.project.ui.sorters;

import java.util.Comparator;
public class LongComparator implements Comparator<Long>{
    @Override
    public int compare(Long l1, Long l2) {
        return l1.compareTo(l2);
    }
}
