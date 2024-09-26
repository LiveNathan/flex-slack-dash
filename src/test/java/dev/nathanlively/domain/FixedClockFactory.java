package dev.nathanlively.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FixedClockFactory {

    public static MyClock createFixedClock(String dateTime, String zoneId) {
        ZoneId zid = ZoneId.of(zoneId);
        Instant fixedInstant = LocalDateTime.parse(dateTime)
                .atZone(zid)
                .toInstant();
        return new FixedClock(fixedInstant);
    }

    public static MyClock feb2at9am() {
        return createFixedClock("2024-02-02T09:00:00", "America/Chicago");
    }
}
