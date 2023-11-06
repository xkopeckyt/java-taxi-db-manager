package cz.muni.fi.pv168.project.ui.export;


import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;
import cz.muni.fi.pv168.project.ui.export.batch.BatchImporter;
import cz.muni.fi.pv168.project.ui.export.batch.BatchOperationException;
import cz.muni.fi.pv168.project.ui.export.format.Format;
import cz.muni.fi.pv168.project.ui.export.format.FormatMapping;

import java.util.Collection;

public class GenericImportService implements ImportService {

    private final RidesTableModel ridesTableModel;
    private final CategoryListModel categoryListModel;
    private final FormatMapping<BatchImporter> importers;

    public GenericImportService(
            RidesTableModel ridesTableModel,
            CategoryListModel categoryListModel,
            Collection<BatchImporter> importers
    ) {
        this.ridesTableModel = ridesTableModel;
        this.categoryListModel = categoryListModel;
        this.importers = new FormatMapping<>(importers);
    }

    @Override
    public void importData(String filePath) {
        var batch = getImporter(filePath).importBatch(filePath);

        categoryListModel.clearCategories();
        batch.categories().forEach(categoryListModel::add);

        ridesTableModel.deleteAll();
        batch.rides().forEach(ridesTableModel::addRow);
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
