package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class Action {
    private String actionID;
    private File processFile;
    private AddFiles addFiles;
    private AddData addData;
    private ChangeStatus changeStatus;

    @JsonProperty("action_id")
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
    public void setChangeStatus(ChangeStatus value) { this.changeStatus = value; }
}
