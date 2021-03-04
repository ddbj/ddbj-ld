package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Action {
    private String actionID;
    private File processFile;
    private AddFiles addFiles;
    private AddData addData;
    private ChangeStatus changeStatus;

    @JsonProperty("action_id")
<<<<<<< HEAD
    public String getActionID() { return actionID; }
    @JsonProperty("action_id")
    public void setActionID(String value) { this.actionID = value; }

    @JsonProperty("ProcessFile")
    public File getProcessFile() { return processFile; }
    @JsonProperty("ProcessFile")
    public void setProcessFile(File value) { this.processFile = value; }

    @JsonProperty("AddFiles")
    public AddFiles getAddFiles() { return addFiles; }
    @JsonProperty("AddFiles")
    public void setAddFiles(AddFiles value) { this.addFiles = value; }

    @JsonProperty("AddData")
    public AddData getAddData() { return addData; }
    @JsonProperty("AddData")
    public void setAddData(AddData value) { this.addData = value; }

    @JsonProperty("ChangeStatus")
    public ChangeStatus getChangeStatus() { return changeStatus; }
    @JsonProperty("ChangeStatus")
=======
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getActionID() { return actionID; }
    @JsonProperty("action_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setActionID(String value) { this.actionID = value; }

    @JsonProperty("ProcessFile")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public File getProcessFile() { return processFile; }
    @JsonProperty("ProcessFile")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setProcessFile(File value) { this.processFile = value; }

    @JsonProperty("AddFiles")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AddFiles getAddFiles() { return addFiles; }
    @JsonProperty("AddFiles")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAddFiles(AddFiles value) { this.addFiles = value; }

    @JsonProperty("AddData")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AddData getAddData() { return addData; }
    @JsonProperty("AddData")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAddData(AddData value) { this.addData = value; }

    @JsonProperty("ChangeStatus")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ChangeStatus getChangeStatus() { return changeStatus; }
    @JsonProperty("ChangeStatus")
    @JsonInclude(JsonInclude.Include.NON_NULL)
>>>>>>> 取り込み、修正
    public void setChangeStatus(ChangeStatus value) { this.changeStatus = value; }
}
