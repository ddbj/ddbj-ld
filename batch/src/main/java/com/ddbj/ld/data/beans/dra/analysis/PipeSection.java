package com.ddbj.ld.data.beans.dra.analysis;

import com.fasterxml.jackson.annotation.*;

public class PipeSection {
    private String sectionName;
    private String stepIndex;
    private String prevStepIndex;
    private String program;
    private String version;
    private String notes;

    @JsonProperty("section_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getSectionName() { return sectionName; }
    @JsonProperty("section_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSectionName(String value) { this.sectionName = value; }

    @JsonProperty("STEP_INDEX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStepIndex() { return stepIndex; }
    @JsonProperty("STEP_INDEX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStepIndex(String value) { this.stepIndex = value; }

    @JsonProperty("PREV_STEP_INDEX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPrevStepIndex() { return prevStepIndex; }
    @JsonProperty("PREV_STEP_INDEX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPrevStepIndex(String value) { this.prevStepIndex = value; }

    @JsonProperty("PROGRAM")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getProgram() { return program; }
    @JsonProperty("PROGRAM")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProgram(String value) { this.program = value; }

    @JsonProperty("VERSION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getVersion() { return version; }
    @JsonProperty("VERSION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setVersion(String value) { this.version = value; }

    @JsonProperty("NOTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getNotes() { return notes; }
    @JsonProperty("NOTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setNotes(String value) { this.notes = value; }
}
