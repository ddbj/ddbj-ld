package com.ddbj.ld.data.beans.dra.sample;

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
public class SampleAttributes {
    private List<SampleAttribute> sampleAttribute;

    @JsonProperty("SAMPLE_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = SampleAttributes.SampleAttributeDeserializer.class)
    public List<SampleAttribute> getSampleAttribute() { return sampleAttribute; }
    @JsonProperty("SAMPLE_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = SampleAttributes.SampleAttributeDeserializer.class)
    public void setSampleAttribute(List<SampleAttribute> value) { this.sampleAttribute = value; }

    static class SampleAttributeDeserializer extends JsonDeserializer<List<SampleAttribute>> {
        @Override
        public List<SampleAttribute> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<SampleAttribute> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<SampleAttribute>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, SampleAttribute.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize SampleAttributeDeserializer");
            }
            return values;
        }
    }
}
