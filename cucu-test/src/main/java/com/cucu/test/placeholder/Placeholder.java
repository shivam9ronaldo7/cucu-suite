package com.cucu.test.placeholder;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.cucu.test.exceptions.UnmatchedCaseException;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RandomPlaceholder.class, name = "random"),
        @JsonSubTypes.Type(value = TimeStampPlaceholder.class, name = "timestamp"),
        @JsonSubTypes.Type(value = ReplacePlaceholder.class, name = "replace"),
})
public abstract class Placeholder {
    private String key;
    private String type;
    public abstract String generateValue() throws UnmatchedCaseException;
}
