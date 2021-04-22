package com.ddbj.ld.data.beans.bioproject;

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
public class ProjectID {
    private ArchiveID archiveID;
    private ArchiveID secondaryArchiveID;
    private List<CenterID> centerID;
    private LocalID localID;

    @JsonProperty("ArchiveID")
    public ArchiveID getArchiveID() { return archiveID; }
    @JsonProperty("ArchiveID")
    public void setArchiveID(ArchiveID value) { this.archiveID = value; }

    @JsonProperty("SecondaryArchiveID")
    public ArchiveID getSecondaryArchiveID() { return secondaryArchiveID; }
    @JsonProperty("SecondaryArchiveID")
    public void setSecondaryArchiveID(ArchiveID value) { this.secondaryArchiveID = value; }

    @JsonProperty("CenterID")
    @JsonDeserialize(using = ProjectID.ProjectIDDeserializer.class)
    public List<CenterID> getCenterID() { return centerID; }
    @JsonProperty("CenterID")
    @JsonDeserialize(using = ProjectID.ProjectIDDeserializer.class)
    public void setCenterID(List<CenterID> value) { this.centerID = value; }

    @JsonProperty("LocalID")
    public LocalID getLocalID() { return localID; }
    @JsonProperty("LocalID")
    public void setLocalID(LocalID value) { this.localID = value; }

    static class ProjectIDDeserializer extends JsonDeserializer<List<ProjectID>> {
        @Override
        public List<ProjectID> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            List<ProjectID> values = new ArrayList<>();
            // FIXME ObjectMapperはSpringのエコシステムに入らないUtil化したほうがよい
            var mapper = new ObjectMapper();

            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                case VALUE_STRING:
                    // FIXME ブランクの文字列があったため除去しているが、捨てて良いのか確認が必要
                    break;
                case START_ARRAY:
                    var list = mapper.readValue(jsonParser, new TypeReference<List<ProjectID>>() {});
                    values.addAll(list);

                    break;
                case START_OBJECT:
                    var value = mapper.readValue(jsonParser, ProjectID.class);

                    values.add(value);

                    break;
                default:
                    log.error("Cannot deserialize ProjectID.ProjectIDDeserializer");
            }
            return values;
        }
    }
}
