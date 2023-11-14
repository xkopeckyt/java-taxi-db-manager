package cz.muni.fi.pv168.project.ui.model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class StringListModel extends AbstractListModel<String> {

    private final List<String> stringList;

    public StringListModel(List<String> stringList) {
        this.stringList = new ArrayList<>(stringList);
    }

    @Override
    public int getSize() {
        return stringList.size();
    }

    @Override
    public String getElementAt(int index) {
        return stringList.get(index);
    }
}
