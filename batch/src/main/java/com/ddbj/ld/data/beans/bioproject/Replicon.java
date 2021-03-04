package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> 取り込み、修正
public class Replicon {
    private String order;
    private Type type;
    private String name;
    private Size size;
    private String description;
<<<<<<< HEAD
    private String synonym;
    private String accession;
    private String version;

    @JsonProperty("order")
    public String getOrder() { return order; }
    @JsonProperty("order")
    public void setOrder(String value) { this.order = value; }

    @JsonProperty("Type")
    public Type getType() { return type; }
    @JsonProperty("Type")
    public void setType(Type value) { this.type = value; }

    @JsonProperty("Name")
    public String getName() { return name; }
    @JsonProperty("Name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("Size")
    public Size getSize() { return size; }
    @JsonProperty("Size")
    public void setSize(Size value) { this.size = value; }

    @JsonProperty("Description")
    public String getDescription() { return description; }
    @JsonProperty("Description")
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("Synonym")
    public String getSynonym() { return synonym; }
    @JsonProperty("Synonym")
    public void setSynonym(String value) { this.synonym = value; }

    @JsonProperty("accession")
    public String getAccession() { return accession; }
    @JsonProperty("accession")
    public void setAccession(String value) { this.accession = value; }

    @JsonProperty("version")
    public String getVersion() { return version; }
    @JsonProperty("version")
    public void setVersion(String value) { this.version = value; }
=======
    private List<String> synonym;

    @JsonProperty("order")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getOrder() { return order; }
    @JsonProperty("order")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setOrder(String value) { this.order = value; }

    @JsonProperty("Type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Type getType() { return type; }
    @JsonProperty("Type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setType(Type value) { this.type = value; }

    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getName() { return name; }
    @JsonProperty("Name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setName(String value) { this.name = value; }

    @JsonProperty("Size")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Size getSize() { return size; }
    @JsonProperty("Size")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSize(Size value) { this.size = value; }

    @JsonProperty("Description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDescription() { return description; }
    @JsonProperty("Description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDescription(String value) { this.description = value; }

    @JsonProperty("Synonym")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> getSynonym() { return synonym; }
    @JsonProperty("Synonym")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSynonym(List<String> value) { this.synonym = value; }
>>>>>>> 取り込み、修正
}
