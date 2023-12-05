package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.storage.sql.entity.CategoryEntity;
import cz.muni.fi.pv168.project.business.model.Category;

public final class CategoryMapper implements EntityMapper<CategoryEntity, Category> {
    @Override
    public Category mapToBusiness(CategoryEntity entity) {
        return new Category(
                entity.name(),
                entity.guid()
        );
    }

    @Override
    public CategoryEntity mapNewEntityToDatabase(Category category) {
        return getCategoryEntity(category, null);
    }

    @Override
    public CategoryEntity mapExistingEntityToDatabase(Category category, Long id) {
        return getCategoryEntity(category, id);
    }

    private static CategoryEntity getCategoryEntity(Category category, Long id) {
        return new CategoryEntity(
                id,
                category.getName(),
                category.getGuid()
        );
    }
}
