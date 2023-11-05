package cz.muni.fi.pv168.project.ui.sorters;

import java.util.Comparator;

public class EnumComparator implements Comparator<Enum> {


    @Override
    public int compare(Enum e1, Enum e2) {
        return e1.name().compareTo(e2.name());
    }
}
