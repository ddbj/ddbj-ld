package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

<<<<<<< HEAD
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
=======
import java.util.List;

public class ProjectTypeSubmission {
    private Target target;
    private TargetBioSampleSet targetBioSampleSet;
    private Method method;
    private Objectives objectives;
    private ProjectDataTypeSet projectDataTypeSet;
    private List<IntendedDataTypeSet> intendedDataTypeSet;

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Method getMethod() { return method; }
    @JsonProperty("Method")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setMethod(Method value) { this.method = value; }

    @JsonProperty("Objectives")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Objectives getObjectives() { return objectives; }
    @JsonProperty("Objectives")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setObjectives(Objectives value) { this.objectives = value; }

    @JsonProperty("ProjectDataTypeSet")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ProjectDataTypeSet getProjectDataTypeSet() { return projectDataTypeSet; }
    @JsonProperty("ProjectDataTypeSet")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProjectDataTypeSet(ProjectDataTypeSet value) { this.projectDataTypeSet = value; }

    @JsonProperty("IntendedDataTypeSet")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<IntendedDataTypeSet> getIntendedDataTypeSet() { return intendedDataTypeSet; }
    @JsonProperty("IntendedDataTypeSet")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setIntendedDataTypeSet(List<IntendedDataTypeSet> value) { this.intendedDataTypeSet = value; }
>>>>>>> 取り込み、修正
}
