package com.ddbj.ld.data.beans.dra.experiment;

import com.ddbj.ld.data.beans.dra.sample.SampleAttribute;
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
public class ExpectedBasecallTable {
    private String defaultLength;
    private String baseCoord;
    private List<Basecall> basecall;

    @JsonProperty("default_length")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDefaultLength() { return defaultLength; }
    @JsonProperty("default_length")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDefaultLength(String value) { this.defaultLength = value; }

    @JsonProperty("base_coord")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBaseCoord() { return baseCoord; }
    @JsonProperty("base_coord")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setBaseCoord(String value) { this.baseCoord = value; }

    @JsonProperty("BASECALL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = ExpectedBasecallTable.Deserializer.class)
    public List<Basecall> getBasecall() { return basecall; }
    @JsonProperty("BASECALL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = ExpectedBasecallTable.Deserializer.class)
    public void setBasecall(List<Basecall> value) { this.basecall = value; }

    static class Deserializer extends JsonDeserializer<List<Basecall>> {
        @Override
        public List<Basecall> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<Basecall> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Basecall>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Basecall.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize BaceCall Deserializer");
            }
            return values;
        }
    }
}
