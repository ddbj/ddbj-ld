package com.ddbj.ld.common.helper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@AllArgsConstructor
@Component
public class DateHelper {
    private SimpleDateFormat sdf;

    public Date parse(String date) {
        try {
            return sdf.parse(date);
        } catch(Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
