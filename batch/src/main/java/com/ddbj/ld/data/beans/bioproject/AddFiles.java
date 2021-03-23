package com.ddbj.ld.data.beans.bioproject;

import com.fasterxml.jackson.annotation.*;

public class AddFiles {
    private String targetDB;
    private String targetDBContext;
    private File file;

    @JsonProperty("target_db")
    public String getTargetDB() { return targetDB; }
    @JsonProperty("target_db")
    public void setTargetDB(String value) { this.targetDB = value; }

    @JsonProperty("target_db_context")
    public String getTargetDBContext() { return targetDBContext; }
    @JsonProperty("target_db_context")
    public void setTargetDBContext(String value) { this.targetDBContext = value; }

    @JsonProperty("File")
    public File getFile() { return file; }
    @JsonProperty("File")
    public void setFile(File value) { this.file = value; }
}
