package com.ddbj.ld.data.beans.dra.common;

import com.ddbj.ld.data.beans.dra.study.Study;
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
    @JsonDeserialize(using = ExpectedBasecallTable.BasecallDeserializer.class)
    public List<Basecall> getBasecall() { return basecall; }
    @JsonProperty("BASECALL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = ExpectedBasecallTable.BasecallDeserializer.class)
    public void setBasecall(List<Basecall> value) { this.basecall = value; }

    static class BasecallDeserializer extends JsonDeserializer<List<Basecall>> {
        @Override
        public List<Basecall> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Basecall>();
            var mapper = StudyConverter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
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
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize ExpectedBasecallTable.BasecallDeserializer");
            }
            return values;
        }
    }
}
