package com.ddbj.ld.bean.bioproject;

import com.fasterxml.jackson.annotation.*;

public class ProjectTypeSubmission {
    private Target target;
    private TargetBioSampleSet targetBioSampleSet;
    private Method method;
    private Objectives objectives;

    @JsonProperty("Target")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Target getTarget() { return target; }
    @JsonProperty("Target")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTarget(Target value) { this.target = value; }

    @JsonProperty("TargetBioSampleSet")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public TargetBioSampleSet getTargetBioSampleSet() { return targetBioSampleSet; }
    @JsonProperty("TargetBioSampleSet")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTargetBioSampleSet(TargetBioSampleSet value) { this.targetBioSampleSet = value; }

    @JsonProperty("Method")
    public Method getMethod() { return method; }
    @JsonProperty("Method")
    public void setMethod(Method value) { this.method = value; }

    @JsonProperty("Objectives")
    public Objectives getObjectives() { return objectives; }
    @JsonProperty("Objectives")
    public void setObjectives(Objectives value) { this.objectives = value; }
}
