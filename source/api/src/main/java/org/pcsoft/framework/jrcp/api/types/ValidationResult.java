package org.pcsoft.framework.jrcp.api.types;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class ValidationResult {
    public static ValidationResult createPositive() {
        return new ValidationResult(null);
    }

    public static ValidationResult createNegative(String error) {
        return new ValidationResult(error);
    }

    private final String error;

    public boolean hasError() {
        return !StringUtils.isEmpty(error);
    }
}