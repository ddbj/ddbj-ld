package com.ddbj.ld.data.beans.dra.common;

import com.fasterxml.jackson.annotation.*;
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
            List<ReadSpec> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
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
