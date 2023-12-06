package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.Ride;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.entity.CategoryEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.RideEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.TemplateEntity;

public class TemplateMapper implements EntityMapper<TemplateEntity, Template> {

    private final DataAccessObject<CategoryEntity> categoryDao;
    private final CategoryMapper categoryMapper;

    public TemplateMapper(
            DataAccessObject<CategoryEntity> categoryDao,
            CategoryMapper categoryMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Template mapToBusiness(TemplateEntity entity) {
        var category = categoryDao
                .findById(entity.categoryId())
                .map(categoryMapper::mapToBusiness)
                .orElseThrow(() -> new RuntimeException("Category not found: " +
                        entity.categoryId()));

        return new Template(
                entity.name(),
                entity.distance(),
                entity.dateTime(),
                entity.price(),
                entity.originalCurrency(),
                category,
                entity.passengersCount()
        );
    }

    @Override
    public TemplateEntity mapNewEntityToDatabase(Template template) {
        CategoryEntity categoryEntity = categoryDao
                .findByGuid(template.getCategory().getGuid())
                .orElseThrow(() -> new RuntimeException("Category not found with name: " +
                        template.getCategory().getName()));

        return new TemplateEntity(
                template.getName(),
                template.getDistance(),
                template.getTemplateDateTime(),
                template.getPrice(),
                template.getOriginalCurrency(),
                categoryEntity.id(),
                template.getPassengersCount(),
                template.getGuid()
        );
    }

    @Override
    public TemplateEntity mapExistingEntityToDatabase(Template template, Long id) {
        CategoryEntity categoryEntity = categoryDao
                .findByGuid(template.getCategory().getGuid())
                .orElseThrow(() -> new RuntimeException("Category not found with name: " +
                        template.getCategory().getName()));

        return new TemplateEntity(
                id,
                template.getName(),
                template.getDistance(),
                template.getTemplateDateTime(),
                template.getPrice(),
                template.getOriginalCurrency(),
                categoryEntity.id(),
                template.getPassengersCount(),
                template.getGuid()
        );
    }
}
