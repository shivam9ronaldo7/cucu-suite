package com.cucu.test.placeholder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeStampDetails {
    private String format;
    private String timezone;
    private TimeStampOffsetDetails timeStampOffsetDetails;
}
