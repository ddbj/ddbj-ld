package com.ddbj.ld.data.beans.dra.submission;

import com.fasterxml.jackson.annotation.*;
<<<<<<< HEAD
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
public class Contacts {
    private List<Contact> contact;

    @JsonProperty("CONTACT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Contacts.ContactDeserializer.class)
    public List<Contact> getContact() { return contact; }
    @JsonProperty("CONTACT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = Contacts.ContactDeserializer.class)
    public void setContact(List<Contact> value) { this.contact = value; }

    static class ContactDeserializer extends JsonDeserializer<List<Contact>> {
        @Override
        public List<Contact> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<Contact> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<Contact>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, Contact.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize Contacts.ContactDeserializer");
            }
            return values;
        }
    }
=======

public class Contacts {
    private Contact contact;

    @JsonProperty("CONTACT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Contact getContact() { return contact; }
    @JsonProperty("CONTACT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setContact(Contact value) { this.contact = value; }
>>>>>>> 取り込み、修正
}
