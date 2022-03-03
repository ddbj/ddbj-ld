package com.ddbj.ld.data.beans.common;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    public LocalDate unmarshal(
            final String v
    ) {
        return LocalDate.parse(v);
    }

    public String marshal(
            final LocalDate v
    ) {
        return v.toString();
    }
}
