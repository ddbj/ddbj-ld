package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class DataTypeSet {
    @XmlElement(name="DataType")
    @JsonProperty("DataType")
    @JsonDeserialize(using = DataTypeSet.DataTypeDeserializer.class)
    private List<String> dataType;

    static class DataTypeDeserializer extends JsonDeserializer<List<String>> {
        @Override
        public List<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<String>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = BioProjectConverter.getObjectMapper().readValue(jsonParser, new TypeReference<List<String>>() {});
                    values.addAll(list);

                    break;
                case VALUE_STRING:
                    var value = BioProjectConverter.getObjectMapper().readValue(jsonParser, String.class);
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
