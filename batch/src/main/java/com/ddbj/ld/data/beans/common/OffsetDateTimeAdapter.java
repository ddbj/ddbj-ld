package com.ddbj.ld.data.beans.common;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeAdapter extends XmlAdapter<String, OffsetDateTime> {
    public OffsetDateTime unmarshal(
            final String v
    ) {
        return OffsetDateTime.parse(v);
    }

    public String marshal(
            final OffsetDateTime v
    ) {
        // DDBJが出力したXML用の日付形式、タイムゾーンがAsia/TokyoになっているがNCBIのものは違うはず
        return v.withOffsetSameInstant(ZoneOffset.of("+09:00")).toString();
    }
}
