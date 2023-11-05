package cz.muni.fi.pv168.project.ui.export.batch;

import cz.muni.fi.pv168.project.ui.export.format.FileFormat;

public interface BatchExporter extends FileFormat {
    void exportBatch(Batch batch, String filePath);
}
