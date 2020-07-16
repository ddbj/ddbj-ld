package com.ddbj.ld.common.helper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Slf4j
@AllArgsConstructor
@Component
public class DateHelper {
    private SimpleDateFormat esSimpleDateFormat;

    public String parse(Timestamp timestamp) {
        try {
            return esSimpleDateFormat.format(timestamp);
        } catch(Exception e) {
            log.debug(e.getMessage());
            return null;
        }
    }
}
