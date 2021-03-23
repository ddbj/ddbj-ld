package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class ProjectTypeSubmission {
    private ProjectTypeSubmissionTarget target;
    private TargetBioSampleSet targetBioSampleSet;
    private Method method;
    private Objectives objectives;
    private DataTypeSet intendedDataTypeSet;
    private DataTypeSet projectDataTypeSet;

    @JsonProperty("Target")
    public ProjectTypeSubmissionTarget getTarget() { return target; }
    @JsonProperty("Target")
    public void setTarget(ProjectTypeSubmissionTarget value) { this.target = value; }

    @JsonProperty("TargetBioSampleSet")
    public TargetBioSampleSet getTargetBioSampleSet() { return targetBioSampleSet; }
    @JsonProperty("TargetBioSampleSet")
    public void setTargetBioSampleSet(TargetBioSampleSet value) { this.targetBioSampleSet = value; }

    @JsonProperty("Method")
    public Method getMethod() { return method; }
    @JsonProperty("Method")
    public void setMethod(Method value) { this.method = value; }

    @JsonProperty("Objectives")
    public Objectives getObjectives() { return objectives; }
    @JsonProperty("Objectives")
    public void setObjectives(Objectives value) { this.objectives = value; }

    @JsonProperty("IntendedDataTypeSet")
    public DataTypeSet getIntendedDataTypeSet() { return intendedDataTypeSet; }
    @JsonProperty("IntendedDataTypeSet")
    public void setIntendedDataTypeSet(DataTypeSet value) { this.intendedDataTypeSet = value; }

    @JsonProperty("ProjectDataTypeSet")
    public DataTypeSet getProjectDataTypeSet() { return projectDataTypeSet; }
    @JsonProperty("ProjectDataTypeSet")
    public void setProjectDataTypeSet(DataTypeSet value) { this.projectDataTypeSet = value; }
}
