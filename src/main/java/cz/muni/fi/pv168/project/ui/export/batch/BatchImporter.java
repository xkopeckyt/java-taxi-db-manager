package cz.muni.fi.pv168.project.ui.export.batch;

import cz.muni.fi.pv168.project.ui.export.format.FileFormat;

public interface BatchImporter extends FileFormat {
    Batch importBatch(String filePath);
}