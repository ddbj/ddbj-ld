package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

<<<<<<< HEAD
public class TargetBioSampleSet {
    private String id;

    @JsonProperty("ID")
    public String getID() { return id; }
    @JsonProperty("ID")
    public void setID(String value) { this.id = value; }
=======
import java.util.List;

public class TargetBioSampleSet {
    private List<String> id;

    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> getID() { return id; }
    @JsonProperty("ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setID(List<String> value) { this.id = value; }
>>>>>>> 取り込み、修正
}
