package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.service.validation.common.StringLengthValidator;

import static cz.muni.fi.pv168.project.business.service.validation.Validator.extracting;

import java.util.List;

/**
 * Department entity {@link Validator}
 */
public class CategoryValidator implements Validator<Category> {
    @Override
    public ValidationResult validate(Category department) {
        var validators = List.of(
                extracting(
                        Category::getName, new StringLengthValidator(1, 50, "Category name"))
        );

        return Validator.compose(validators).validate(department);
    }
}
