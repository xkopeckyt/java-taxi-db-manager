package cz.muni.fi.pv168.project.database.entity.mapper;

import cz.muni.fi.pv168.project.database.entity.CategoryEntity;
import cz.muni.fi.pv168.project.model.Category;

public class CategoryMapper {
    public Category mapToBusiness(CategoryEntity entity) {
        return new Category(
                entity.name()
        );
    }

    public CategoryEntity mapNewCategoryToDatabase(Category category) {
        return getCategoryEntity(category, null);
    }

    public CategoryEntity mapExistingCategoryToDatabase(Category category, Integer id) {
        return getCategoryEntity(category, id);
    }

    private static CategoryEntity getCategoryEntity(Category category, Integer id) {
        return new CategoryEntity(
                id,
                category.getName()
        );
    }
}
