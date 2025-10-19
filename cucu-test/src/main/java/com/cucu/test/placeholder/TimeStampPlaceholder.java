package com.cucu.test.placeholder;

import com.cucu.test.exceptions.UnmatchedCaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeStampPlaceholder extends Placeholder {

    private TimeStampDetails details;

    @Override
    public String generateValue() throws UnmatchedCaseException {
        ZoneId zone = Objects.isNull(details.getTimezone()) ?
                ZoneId.systemDefault(): ZoneId.of(details.getTimezone());
        ZonedDateTime now = ZonedDateTime.now(zone);
        TimeStampOffsetDetails timeStampOffsetDetails = details.getTimeStampOffsetDetails();
        if (!Objects.isNull(timeStampOffsetDetails)){
            now = switch (timeStampOffsetDetails.getUnit().toLowerCase()) {
                case "minutes" -> now.plusMinutes(timeStampOffsetDetails.getValue());
                case "hours" -> now.plusHours(timeStampOffsetDetails.getValue());
                case "days" -> now.plusDays(timeStampOffsetDetails.getValue());
                case "seconds" -> now.plusSeconds(timeStampOffsetDetails.getValue());
                case "weeks" -> now.plusWeeks(timeStampOffsetDetails.getValue());
                case "months" -> now.plusMonths(timeStampOffsetDetails.getValue());
                case "years" -> now.plusYears(timeStampOffsetDetails.getValue());
                default -> throw new UnmatchedCaseException(String
                        .format("Offset unit %s not yet implemented", timeStampOffsetDetails.getUnit()));
            };
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(details.getFormat());
        return formatter.format(now);
    }
}
