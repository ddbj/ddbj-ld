package com.ddbj.ld.common.helper;

import com.ddbj.ld.common.annotation.Helper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@AllArgsConstructor
@Helper
public class DateHelper {

    private SimpleDateFormat  esSimpleDateFormat;
    private DateTimeFormatter esFormatter;

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

    public String parse(LocalDate localDate) {
        try {
            return esSimpleDateFormat.format(Timestamp.valueOf(localDate.atStartOfDay()));
        } catch(Exception e) {
            log.debug(e.getMessage());
            return null;
        }
    }
}
