package cz.muni.fi.pv168.project.ui.export;

import cz.muni.fi.pv168.project.ui.export.format.Format;

import java.util.Collection;

public interface ExportService {

    void exportData(String filePath);

    Collection<Format> getFormats();
}