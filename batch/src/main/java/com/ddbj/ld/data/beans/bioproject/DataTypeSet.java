package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DataTypeSet {
    private List<String> dataType;

    @JsonProperty("DataType")
    @JsonDeserialize(using = DataTypeSet.DataTypeDeserializer.class)
    public List<String> getDataType() { return dataType; }
    @JsonProperty("DataType")
    @JsonDeserialize(using = DataTypeSet.DataTypeDeserializer.class)
    public void setDataType(List<String> value) { this.dataType = value; }

    static class DataTypeDeserializer extends JsonDeserializer<List<String>> {
        @Override
        public List<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<String> values = new ArrayList<>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = Converter.getObjectMapper().readValue(jsonParser, new TypeReference<List<String>>() {});
                    values.addAll(list);

                    break;
                case VALUE_STRING:
                    var value = Converter.getObjectMapper().readValue(jsonParser, String.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize DataType");
            }
            return values;
        }
    }
}
