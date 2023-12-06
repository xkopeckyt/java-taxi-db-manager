package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.service.validation.common.BigDecimalValidator;
import cz.muni.fi.pv168.project.business.service.validation.common.StringLengthValidator;

import static cz.muni.fi.pv168.project.business.service.validation.Validator.extracting;

import java.util.List;

public class TemplateValidator implements Validator<Template> {

    @Override
    public ValidationResult validate(Template template) {
        var validators = List.of(
                extracting(Template::getName, new StringLengthValidator(1, 50, "Template name"))
        );

        return Validator.compose(validators).validate(template);
    }
}
