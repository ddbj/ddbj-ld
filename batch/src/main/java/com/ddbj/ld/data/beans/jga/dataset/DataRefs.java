package com.ddbj.ld.data.beans.jga.dataset;

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
public class DataRefs {
    private List<Ref> dataRef;

    @JsonProperty("DATA_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = DataRefs.DataRefDeserializer.class)
    public List<Ref> getDataRef() { return dataRef; }
    @JsonProperty("DATA_REF")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = DataRefs.DataRefDeserializer.class)
    public void setDataRef(List<Ref> value) { this.dataRef = value; }

    static class DataRefDeserializer extends JsonDeserializer<List<Ref>> {
        @Override
        public List<Ref> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<Ref> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Ref>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Ref.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize DataRef");
            }
            return values;
        }
    }
}
