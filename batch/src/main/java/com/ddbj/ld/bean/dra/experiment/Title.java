package com.ddbj.ld.bean.dra.experiment;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum Title {
    STRING;

    @JsonValue
    public String toValue() {
        switch (this) {
            case STRING: return "string";
        }
        return null;
    }

    @JsonCreator
    public static Title forValue(String value) throws IOException {
        if (value.equals("string")) return STRING;
        throw new IOException("Cannot deserialize Title");
    }
}
