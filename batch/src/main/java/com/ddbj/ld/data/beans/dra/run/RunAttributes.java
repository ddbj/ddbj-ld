package com.ddbj.ld.data.beans.dra.run;

<<<<<<< HEAD
import com.ddbj.ld.data.beans.dra.common.Attribute;
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
            List<Attribute> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
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
=======
import com.fasterxml.jackson.annotation.*;

public class RunAttributes {
    private RunAttribute runAttribute;

    @JsonProperty("RUN_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public RunAttribute getRunAttribute() { return runAttribute; }
    @JsonProperty("RUN_ATTRIBUTE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRunAttribute(RunAttribute value) { this.runAttribute = value; }
>>>>>>> 取り込み、修正
}
