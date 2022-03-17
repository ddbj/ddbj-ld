package com.ddbj.ld.data.beans.common;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeAdapter extends XmlAdapter<String, OffsetDateTime> {
    public OffsetDateTime unmarshal(
            final String v
    ) {
        return null == v ? null : OffsetDateTime.parse(v);
    }

    public String marshal(
            final OffsetDateTime v
    ) {
        // DDBJが出力したXML用の日付形式、タイムゾーンがAsia/TokyoになっているがNCBIのものは違うはず
        return null == v ? null : v.withOffsetSameInstant(ZoneOffset.of("+09:00")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
