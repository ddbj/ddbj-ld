package com.ddbj.ld.data.beans.dra.common;

import com.ddbj.ld.data.beans.dra.study.StudyConverter;
import com.fasterxml.jackson.annotation.*;
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
public class SpotDecodeSpec {
    private String spotLength;
    private List<ReadSpec> readSpec;

    @JsonProperty("SPOT_LENGTH")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSpotLength() { return spotLength; }
    @JsonProperty("SPOT_LENGTH")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSpotLength(String value) { this.spotLength = value; }

    @JsonProperty("READ_SPEC")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = SpotDecodeSpec.ReadSpecDeserializer.class)
    public List<ReadSpec> getReadSpec() { return readSpec; }
    @JsonProperty("READ_SPEC")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = SpotDecodeSpec.ReadSpecDeserializer.class)
    public void setReadSpec(List<ReadSpec> value) { this.readSpec = value; }

    static class ReadSpecDeserializer extends JsonDeserializer<List<ReadSpec>> {
        @Override
        public List<ReadSpec> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<ReadSpec>();
            var mapper = StudyConverter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<ReadSpec>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, ReadSpec.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize SpotDecodeSpec.ReadSpecDeserializer");
            }
            return values;
        }
    }
}
