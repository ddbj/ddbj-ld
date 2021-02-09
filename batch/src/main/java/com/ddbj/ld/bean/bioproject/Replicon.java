package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Replicon {
    private String order;
    private Type type;
    private String name;
    private Size size;
    private String description;
    private String synonym;

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
}
