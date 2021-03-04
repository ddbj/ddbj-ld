package com.ddbj.ld.data.beans.bioproject;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BioSampleSet {
    private List<BioSampleSetID> id;

    @JsonProperty("ID")
    @JsonDeserialize(using = BioSampleSet.BioSampleSetIDDeserializer.class)
    public List<BioSampleSetID> getID() { return id; }
    @JsonProperty("ID")
    @JsonDeserialize(using = BioSampleSet.BioSampleSetIDDeserializer.class)
    public void setID(List<BioSampleSetID> value) { this.id = value; }

    static class BioSampleSetIDDeserializer extends JsonDeserializer<List<BioSampleSetID>> {
        @Override
        public List<BioSampleSetID> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<BioSampleSetID> values = new ArrayList<>();
            BioSampleSetID value = new BioSampleSetID();

            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case VALUE_STRING:
                    value.setContent(jsonParser.readValueAs(String.class));

                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<BioSampleSetID>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    value = mapper.readValue(jsonParser, BioSampleSetID.class);
                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize ID");
            }
            return values;
        }
    }
}
=======
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BioSampleSet {
    private List<ID> id;

    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<ID> getID() { return id; }
    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(List<ID> value) { this.id = value; }
}
>>>>>>> 取り込み、修正
