package com.ddbj.ld.data.beans.bioproject;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class BioProject implements IPropertiesBean {
    private List<Package> bioProjectPackage;

    @JsonProperty("Package")
    @JsonDeserialize(using = BioProject.PackageDeserializer.class)
    public List<Package> getBioProjectPackage() { return bioProjectPackage; }
    @JsonProperty("Package")
    @JsonDeserialize(using = BioProject.PackageDeserializer.class)
    public void setBioProjectPackage(List<Package> value) { this.bioProjectPackage = value; }

    static class PackageDeserializer extends JsonDeserializer<List<Package>> {
        @Override
        public List<Package> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<Package> values = new ArrayList<>();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = Converter.getObjectMapper().readValue(jsonParser, new TypeReference<List<Package>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = Converter.getObjectMapper().readValue(jsonParser, Package.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Package");
            }
            return values;
        }
    }
}
