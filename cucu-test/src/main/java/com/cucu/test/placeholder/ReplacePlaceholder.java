package com.cucu.test.placeholder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplacePlaceholder extends Placeholder {

    private ReplacePlaceholderDetails details;

    @Override
    public String generateValue() {
        return details.getText();
    }
}
