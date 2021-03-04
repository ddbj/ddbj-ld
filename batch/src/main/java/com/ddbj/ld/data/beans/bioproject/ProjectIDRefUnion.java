package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.util.List;

@JsonDeserialize(using = ProjectIDRefUnion.Deserializer.class)
@JsonSerialize(using = ProjectIDRefUnion.Serializer.class)
public class ProjectIDRefUnion {
    public List<ProjectIDRef> projectIDRefArrayValue;
    public ProjectIDRef projectIDRefValue;

    static class Deserializer extends JsonDeserializer<ProjectIDRefUnion> {
        @Override
        public ProjectIDRefUnion deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            ProjectIDRefUnion value = new ProjectIDRefUnion();
            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    value.projectIDRefArrayValue = jsonParser.readValueAs(new TypeReference<List<ProjectIDRef>>() {});
                    break;
                case START_OBJECT:
                    value.projectIDRefValue = jsonParser.readValueAs(ProjectIDRef.class);
                    break;
                default: throw new IOException("Cannot deserialize ProjectIDRefUnion");
            }
            return value;
        }
    }

    static class Serializer extends JsonSerializer<ProjectIDRefUnion> {
        @Override
        public void serialize(ProjectIDRefUnion obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (obj.projectIDRefArrayValue != null) {
                jsonGenerator.writeObject(obj.projectIDRefArrayValue);
                return;
            }
            if (obj.projectIDRefValue != null) {
                jsonGenerator.writeObject(obj.projectIDRefValue);
                return;
            }
            jsonGenerator.writeNull();
        }
    }
}
