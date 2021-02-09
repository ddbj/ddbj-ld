package com.ddbj.ld.common.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
//@AllArgsConstructor
@Component
public class DateHelper {
    private SimpleDateFormat  esSimpleDateFormat;
    private DateTimeFormatter esFormatter;

    public DateHelper() {
        this.esSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ");
        this.esFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZZ");
    }

    public String parse(Timestamp timestamp) {
        try {
            return esSimpleDateFormat.format(timestamp);
        } catch(Exception e) {
            log.debug(e.getMessage());
            return null;
        }
    }

    public String parse(OffsetDateTime timestamp) {
        try {
            return timestamp.format(esFormatter);
        } catch(Exception e) {
            log.debug(e.getMessage());
            return null;
        }
    }
}
