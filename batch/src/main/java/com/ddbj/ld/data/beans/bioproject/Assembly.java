package com.ddbj.ld.data.beans.bioproject;

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
public class Assembly {
    private String assemblyName;
    private String assemblyAccession;
    private String wgSprefix;
    private String locusTagPrefix;
    private List<Replicon> replicon;

    @JsonProperty("assemblyName")
    public String getAssemblyName() { return assemblyName; }
    @JsonProperty("assemblyName")
    public void setAssemblyName(String value) { this.assemblyName = value; }

    @JsonProperty("assemblyAccession")
    public String getAssemblyAccession() { return assemblyAccession; }
    @JsonProperty("assemblyAccession")
    public void setAssemblyAccession(String value) { this.assemblyAccession = value; }

    @JsonProperty("WGSprefix")
    public String getWgSprefix() { return wgSprefix; }
    @JsonProperty("WGSprefix")
    public void setWgSprefix(String value) { this.wgSprefix = value; }

    @JsonProperty("LocusTagPrefix")
    public String getLocusTagPrefix() { return locusTagPrefix; }
    @JsonProperty("LocusTagPrefix")
    public void setLocusTagPrefix(String value) { this.locusTagPrefix = value; }

    @JsonProperty("Replicon")
    @JsonDeserialize(using = Assembly.RepliconDeserializer.class)
    public List<Replicon> getReplicon() { return replicon; }
    @JsonProperty("Replicon")
    @JsonDeserialize(using = Assembly.RepliconDeserializer.class)
    public void setReplicon(List<Replicon> value) { this.replicon = value; }

    static class RepliconDeserializer extends JsonDeserializer<List<Replicon>> {
        @Override
        public List<Replicon> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<Replicon> values = new ArrayList<>();

            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    var list = Converter.getObjectMapper().readValue(jsonParser, new TypeReference<List<Replicon>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = Converter.getObjectMapper().readValue(jsonParser, Replicon.class);
                    values.add(value);

                    break;
                default:
                    log.error(jsonParser.getCurrentLocation().getSourceRef().toString());
                    log.error("Cannot deserialize Replicon");
            }
            return values;
        }
    }
}
