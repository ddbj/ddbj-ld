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
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat esSimpleDateFormat;

    public String parse(String strDate) {
        try {
            Date date = simpleDateFormat.parse(strDate);
            // TODO
            return esSimpleDateFormat.format(date);
        } catch(Exception e) {
            log.debug(e.getMessage());
            return null;
        }
    }
}
