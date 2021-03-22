package com.ddbj.ld.data.beans.dra.experiment;

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
public class TargetedLoci {
    private List<Locus> locus;

    @JsonProperty("LOCUS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = TargetedLoci.LocusDeserializer.class)
    public List<Locus> getLocus() { return locus; }
    @JsonProperty("LOCUS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = TargetedLoci.LocusDeserializer.class)
    public void setLocus(List<Locus> value) { this.locus = value; }

    static class LocusDeserializer extends JsonDeserializer<List<Locus>> {
        @Override
        public List<Locus> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<Locus> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Locus>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Locus.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize TargetedLoci.LocusDeserializer");
            }
            return values;
        }
    }
}
