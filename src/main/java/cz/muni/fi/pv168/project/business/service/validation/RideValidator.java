package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Ride;
import cz.muni.fi.pv168.project.business.service.validation.common.BigDecimalValidator;

import static cz.muni.fi.pv168.project.business.service.validation.Validator.extracting;

import java.util.List;

/**
 * Employee entity {@link Validator}.
 */
public class RideValidator implements Validator<Ride> {

    @Override
    public ValidationResult validate(Ride model) {
        var validators = List.of(
                extracting(Ride::getDistance, new BigDecimalValidator(false, false, "Distance")),
                extracting(Ride::getPrice, new BigDecimalValidator(false, true, "Price"))
        );

        return Validator.compose(validators).validate(model);
    }
}
