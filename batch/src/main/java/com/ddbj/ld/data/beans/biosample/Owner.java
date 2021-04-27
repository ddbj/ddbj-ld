package com.ddbj.ld.data.beans.biosample;

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
public class Owner {
    private List<NameElement> name;
    private Contacts contacts;

    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Owner.NameElementDeserializer.class)
    public List<NameElement> getName() { return name; }
    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Owner.NameElementDeserializer.class)
    public void setName(List<NameElement> value) { this.name = value; }

    @JsonProperty("Contacts")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Contacts getContacts() { return contacts; }
    @JsonProperty("Contacts")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContacts(Contacts value) { this.contacts = value; }

    static class NameElementDeserializer extends JsonDeserializer<List<NameElement>> {
        @Override
        public List<NameElement> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<NameElement> values = new ArrayList<>();
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<NameElement>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, NameElement.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize Owner.NameElementDeserializer");
            }
            return values;
        }
    }
}