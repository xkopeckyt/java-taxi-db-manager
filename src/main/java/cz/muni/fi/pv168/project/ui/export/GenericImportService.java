package cz.muni.fi.pv168.project.ui.export;


import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;
import cz.muni.fi.pv168.project.ui.export.batch.BatchImporter;
import cz.muni.fi.pv168.project.ui.export.batch.BatchOperationException;
import cz.muni.fi.pv168.project.ui.export.format.Format;
import cz.muni.fi.pv168.project.ui.export.format.FormatMapping;

import java.util.Collection;

public class GenericImportService implements ImportService {

    private final RidesTableModel ridesTableModel;
    private final FormatMapping<BatchImporter> importers;

    public GenericImportService(
            RidesTableModel ridesTableModel,
            Collection<BatchImporter> importers
    ) {
        this.ridesTableModel = ridesTableModel;
        this.importers = new FormatMapping<>(importers);
    }

    @Override
    public void importData(String filePath) {

        var batch = getImporter(filePath).importBatch(filePath);
        ridesTableModel.deleteAll();
        batch.rides().forEach(this::createRide);
        System.out.println();
    }


    private void createRide(Ride ride) {
        ridesTableModel.addRow(ride);
    }

    @Override
    public Collection<Format> getFormats() {
        return importers.getFormats();
    }

    private BatchImporter getImporter(String filePath) {
        var extension = filePath.substring(filePath.lastIndexOf('.') + 1);
        var importer = importers.findByExtension(extension);
        if (importer == null) {
            throw new BatchOperationException("No formatter available for extension %s".formatted(extension));
        }

        return importer;
    }
}
