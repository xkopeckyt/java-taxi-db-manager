package cz.muni.fi.pv168.project.ui.export;

import cz.muni.fi.pv168.project.ui.export.batch.Batch;
import cz.muni.fi.pv168.project.ui.export.batch.BatchExporter;
import cz.muni.fi.pv168.project.ui.export.batch.BatchOperationException;
import cz.muni.fi.pv168.project.ui.export.format.Format;
import cz.muni.fi.pv168.project.ui.export.format.FormatMapping;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;

import java.util.Collection;

public class GenericExportService implements ExportService {

    private final RidesTableModel ridesTableModel;
    private final FormatMapping<BatchExporter> exporters;

    public GenericExportService(
            RidesTableModel ridesTableModel,
            Collection<BatchExporter> exporters
    ) {
        this.ridesTableModel = ridesTableModel;
        this.exporters = new FormatMapping<>(exporters);
    }

    @Override
    public Collection<Format> getFormats() {
        return exporters.getFormats();
    }

    @Override
    public void exportData(String filePath) {
        var exporter = getExporter(filePath);

        var batch = new Batch(ridesTableModel.getAllRides());
        exporter.exportBatch(batch, filePath);
    }

    private BatchExporter getExporter(String filePath) {
        var extension = filePath.substring(filePath.lastIndexOf('.') + 1);
        var importer = exporters.findByExtension(extension);
        if (importer == null)
            throw new BatchOperationException("No formatter available for extension %s".formatted(extension));
        return importer;
    }
}
