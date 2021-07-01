package com.ddbj.ld.data.beans.jga.dataset;

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
public class DatasetLinks {
    private List<DatasetLink> datasetLink;

    @JsonProperty("DATASET_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = DatasetLinks.DatasetLinkDeserializer.class)
    public List<DatasetLink> getDatasetLink() { return datasetLink; }
    @JsonProperty("DATASET_LINK")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = DatasetLinks.DatasetLinkDeserializer.class)
    public void setDatasetLink(List<DatasetLink> value) { this.datasetLink = value; }

    static class DatasetLinkDeserializer extends JsonDeserializer<List<DatasetLink>> {
        @Override
        public List<DatasetLink> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<DatasetLink> values = new ArrayList<>();

            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<DatasetLink>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, DatasetLink.class);

                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize DatasetLink");
            }
            return values;
        }
    }
}
