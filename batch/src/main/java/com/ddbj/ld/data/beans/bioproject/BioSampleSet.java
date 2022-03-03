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
public class BioSampleSet {
    @XmlElement(name="ID")
    @JsonProperty("ID")
    @JsonDeserialize(using = BioSampleSet.IDDeserializer.class)
    private List<BioSampleSetID> id;

    static class IDDeserializer extends JsonDeserializer<List<BioSampleSetID>> {
        @Override
        public List<BioSampleSetID> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<BioSampleSetID>();
            BioSampleSetID value = new BioSampleSetID();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_NUMBER_INT:
                    value.setContent(jsonParser.readValueAs(Integer.class).toString());
                    values.add(value);

                    break;
                case VALUE_STRING:
                    value.setContent(jsonParser.readValueAs(String.class));

                    break;
                case START_ARRAY:
                    var list = BioProjectConverter.getObjectMapper().readValue(jsonParser, new TypeReference<List<BioSampleSetID>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    value = BioProjectConverter.getObjectMapper().readValue(jsonParser, BioSampleSetID.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ID");
            }
            return values;
        }
    }
}
