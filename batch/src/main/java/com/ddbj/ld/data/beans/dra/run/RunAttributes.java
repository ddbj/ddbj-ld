package com.ddbj.ld.data.beans.dra.run;

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
public class RunAttributes {
    private List<RunAttribute> runAttribute;

    @JsonProperty("RUN_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = RunAttributes.RunAttributeDeserializer.class)
    public List<RunAttribute> getRunAttribute() { return runAttribute; }
    @JsonProperty("RUN_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = RunAttributes.RunAttributeDeserializer.class)
    public void setRunAttribute(List<RunAttribute> value) { this.runAttribute = value; }

    static class RunAttributeDeserializer extends JsonDeserializer<List<RunAttribute>> {
        @Override
        public List<RunAttribute> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<RunAttribute> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<RunAttribute>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, RunAttribute.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize IDDeserializer");
            }
            return values;
        }
    }
}
