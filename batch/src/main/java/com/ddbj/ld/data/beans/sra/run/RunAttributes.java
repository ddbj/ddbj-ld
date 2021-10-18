package com.ddbj.ld.data.beans.sra.run;

import com.ddbj.ld.data.beans.sra.common.Attribute;
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
public class RunAttributes {
    private List<Attribute> runAttribute;

    @JsonProperty("RUN_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = RunAttributes.AttributeDeserializer.class)
    public List<Attribute> getRunAttribute() { return runAttribute; }
    @JsonProperty("RUN_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = RunAttributes.AttributeDeserializer.class)
    public void setRunAttribute(List<Attribute> value) { this.runAttribute = value; }

    static class AttributeDeserializer extends JsonDeserializer<List<Attribute>> {
        @Override
        public List<Attribute> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            var values = new ArrayList<Attribute>();
            var mapper = RunConverter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Attribute>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Attribute.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize RunAttributes.AttributeDeserializer");
            }
            return values;
        }
    }
}