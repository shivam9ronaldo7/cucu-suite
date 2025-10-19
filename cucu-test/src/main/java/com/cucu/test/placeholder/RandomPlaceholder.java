package com.cucu.test.placeholder;

import com.cucu.test.exceptions.UnmatchedCaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RandomPlaceholder extends Placeholder {

    private RandomDetails details;

    @Override
    public String generateValue() throws UnmatchedCaseException {
        return switch (details.getType().toLowerCase()) {
            case "numeric" -> RandomStringUtils.randomNumeric(details.getLength());
            case "alphanumeric" -> RandomStringUtils.randomAlphanumeric(details.getLength()).toLowerCase();
            case "alphabetic" -> RandomStringUtils.randomAlphabetic(details.getLength()).toLowerCase();
            case "uuid" -> UUID.randomUUID().toString();
            default -> throw new UnmatchedCaseException(String
                    .format("Random type %s is not implemented", details.getType()));
        };
    }
}
