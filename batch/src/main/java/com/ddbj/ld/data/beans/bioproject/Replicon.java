package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

import java.util.List;

public class Replicon {
    private String order;
    private Type type;
    private String name;
    private Size size;
    private String description;
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
}
