package com.ddbj.ld.data.beans.biosample;

import com.ddbj.ld.data.beans.common.IPropertiesBean;
import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;

public class BioSample implements IPropertiesBean {
    private OffsetDateTime lastUpdate;
    private OffsetDateTime publicationDate;
    private String access;
    private Description description;
    private IDS ids;
    private Owner owner;

    @JsonProperty("last_update")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public OffsetDateTime getLastUpdate() { return lastUpdate; }
    @JsonProperty("last_update")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLastUpdate(OffsetDateTime value) { this.lastUpdate = value; }

    @JsonProperty("publication_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public OffsetDateTime getPublicationDate() { return publicationDate; }
    @JsonProperty("publication_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPublicationDate(OffsetDateTime value) { this.publicationDate = value; }

    @JsonProperty("access")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAccess() { return access; }
    @JsonProperty("access")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAccess(String value) { this.access = value; }

    @JsonProperty("Description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Description getDescription() { return description; }
    @JsonProperty("Description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescription(Description value) { this.description = value; }

    @JsonProperty("Ids")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public IDS getIDS() { return ids; }
    @JsonProperty("Ids")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIDS(IDS value) { this.ids = value; }

    @JsonProperty("Owner")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Owner getOwner() { return owner; }
    @JsonProperty("Owner")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOwner(Owner value) { this.owner = value; }
}
