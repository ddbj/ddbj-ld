package com.ddbj.ld.data.beans.sra.experiment;

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
            var values = new ArrayList<Locus>();
            var mapper = ExperimentConverter.getObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // ブランクの文字列があったため除去している
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
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize TargetedLoci.LocusDeserializer");
            }
            return values;
        }
    }
}
