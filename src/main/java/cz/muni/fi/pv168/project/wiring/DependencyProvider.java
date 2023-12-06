package cz.muni.fi.pv168.project.wiring;


import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Ride;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.storage.Repository;
import cz.muni.fi.pv168.project.storage.sql.db.DatabaseManager;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionExecutor;
import cz.muni.fi.pv168.project.business.service.export.ExportService;
import cz.muni.fi.pv168.project.business.service.export.ImportService;

/**
 * Interface for instance wiring
 */
public interface DependencyProvider {

    DatabaseManager getDatabaseManager();

    Repository<Category> getCategoryRepository();

    Repository<Ride> getRideRepository();

    Repository<Template> getTemplateRepository();

    TransactionExecutor getTransactionExecutor();

    CrudService<Category> getCategoryCrudService();

    CrudService<Ride> getRideCrudService();

    CrudService<Template> getTemplateCrudService();

    ImportService getImportService();

    ExportService getExportService();

    Validator<Ride> getRideValidator();

    Validator<Template> getTemplateValidator();
}

