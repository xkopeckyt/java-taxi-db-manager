package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.business.service.export.format.Format;

import java.util.Collection;

public interface ImportService {

    void importData(String filePath);

    Collection<Format> getFormats();
}
