package cz.muni.fi.pv168.project.business.service.validation.common;

import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;

import java.math.BigDecimal;

public class BigDecimalValidator extends PropertyValidator<BigDecimal> {
    private final boolean canBeNegative;
    private final boolean canBeZero;

    public BigDecimalValidator(boolean canBeNegative, boolean canBeZero, String name) {
        super(name);
        this.canBeNegative = canBeNegative;
        this.canBeZero = canBeZero;
    }

    @Override
    public ValidationResult validate(BigDecimal number) {
        var result = new ValidationResult();
        int comparison = number.compareTo(BigDecimal.ZERO);
        if (comparison == 0 && !canBeZero) {
            result.add("'%s' is zero".formatted(number.toString()));
        } else if (comparison < 0 && !canBeNegative) {
            result.add("'%s' less than zero".formatted(number.toString()));
        }
        return result;
    }
}
