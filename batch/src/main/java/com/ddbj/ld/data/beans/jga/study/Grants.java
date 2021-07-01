package com.ddbj.ld.data.beans.jga.study;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Grants {
    private List<Grant> grant;

    @JsonProperty("GRANT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Grants.GrantDeserializer.class)
    public List<Grant> getGrant() { return grant; }
    @JsonProperty("GRANT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Grants.GrantDeserializer.class)
    public void setGrant(List<Grant> value) { this.grant = value; }

    static class GrantDeserializer extends JsonDeserializer<List<Grant>> {
        @Override
        public List<Grant> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<Grant> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Grant>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Grant.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize GRANT");
            }

            return values;
        }
    }
}
