package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class PipeSection {
    private Title sectionName;
    private Title stepIndex;
    private Title prevStepIndex;
    private Title program;
    private Title version;
    private Title notes;

    @JsonProperty("section_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getSectionName() { return sectionName; }
    @JsonProperty("section_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSectionName(Title value) { this.sectionName = value; }

    @JsonProperty("STEP_INDEX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getStepIndex() { return stepIndex; }
    @JsonProperty("STEP_INDEX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setStepIndex(Title value) { this.stepIndex = value; }

    @JsonProperty("PREV_STEP_INDEX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getPrevStepIndex() { return prevStepIndex; }
    @JsonProperty("PREV_STEP_INDEX")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setPrevStepIndex(Title value) { this.prevStepIndex = value; }

    @JsonProperty("PROGRAM")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getProgram() { return program; }
    @JsonProperty("PROGRAM")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProgram(Title value) { this.program = value; }

    @JsonProperty("VERSION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getVersion() { return version; }
    @JsonProperty("VERSION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setVersion(Title value) { this.version = value; }

    @JsonProperty("NOTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getNotes() { return notes; }
    @JsonProperty("NOTES")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setNotes(Title value) { this.notes = value; }
}
