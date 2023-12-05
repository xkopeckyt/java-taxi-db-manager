package cz.muni.fi.pv168.project.wiring;


import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Ride;
import cz.muni.fi.pv168.project.business.model.UuidGuidProvider;
import cz.muni.fi.pv168.project.business.service.crud.CategoryCrudService;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.crud.RideCrudService;
import cz.muni.fi.pv168.project.business.service.export.*;
import cz.muni.fi.pv168.project.business.service.validation.CategoryValidator;
import cz.muni.fi.pv168.project.business.service.validation.RideValidator;
import cz.muni.fi.pv168.project.storage.Repository;
import cz.muni.fi.pv168.project.storage.sql.CategorySqlRepository;
import cz.muni.fi.pv168.project.storage.sql.RideSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.dao.CategoryDao;
import cz.muni.fi.pv168.project.storage.sql.dao.RideDao;
import cz.muni.fi.pv168.project.storage.sql.db.*;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.CategoryMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.RideMapper;

import java.util.List;

/**
 * Common dependency provider for both production and test environment.
 */
public class CommonDependencyProvider implements DependencyProvider {

    private final Repository<Category> categoryRepository;
    private final Repository<Ride> rideRepository;
    private final DatabaseManager databaseManager;
    private final TransactionExecutor transactionExecutor;
    private final CrudService<Ride> rideCrudService;
    private final CrudService<Category> categoryCrudService;
    private final ImportService importService;
    private final ExportService exportService;
    private final RideValidator rideValidator;

    public CommonDependencyProvider(DatabaseManager databaseManager) {
        rideValidator = new RideValidator();
        var departmentValidator = new CategoryValidator();
        var guidProvider = new UuidGuidProvider();

        this.databaseManager = databaseManager;
        var transactionManager = new TransactionManagerImpl(databaseManager);
        this.transactionExecutor = new TransactionExecutorImpl(transactionManager::beginTransaction);
        var transactionConnectionSupplier = new TransactionConnectionSupplier(transactionManager, databaseManager);

        var departmentMapper = new CategoryMapper();
        var departmentDao = new CategoryDao(transactionConnectionSupplier);

        var employeeMapper = new RideMapper(departmentDao, departmentMapper);

        this.categoryRepository = new CategorySqlRepository(
                departmentDao,
                departmentMapper
        );
        this.rideRepository = new RideSqlRepository(
                new RideDao(transactionConnectionSupplier),
                employeeMapper
        );
        categoryCrudService = new CategoryCrudService(categoryRepository, departmentValidator, guidProvider);
        rideCrudService = new RideCrudService(rideRepository, rideValidator, guidProvider);
        exportService = new GenericExportService(rideCrudService, categoryCrudService,
                List.of(new CsvExporter()));
        importService = new GenericImportService(rideCrudService, categoryCrudService,
                List.of(new CsvImporter()));
    }

    @Override
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public Repository<Category> getCategoryRepository() {
        return categoryRepository;
    }

    @Override
    public Repository<Ride> getRideRepository() {
        return rideRepository;
    }

    @Override
    public TransactionExecutor getTransactionExecutor() {
        return transactionExecutor;
    }


    @Override
    public CrudService<Category> getCategoryCrudService() {
        return categoryCrudService;
    }

    @Override
    public CrudService<Ride> getRideCrudService() {
        return rideCrudService;
    }


    @Override
    public ImportService getImportService() {
        return importService;
    }

    @Override
    public ExportService getExportService() {
        return exportService;
    }

    @Override
    public RideValidator getRideValidator() {
        return rideValidator;
    }
}
