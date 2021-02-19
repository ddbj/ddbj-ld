package com.ddbj.ld.bean.dra.run;

import com.fasterxml.jackson.annotation.*;

public class Files {
    private File file;

    @JsonProperty("FILE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public File getFile() { return file; }
    @JsonProperty("FILE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setFile(File value) { this.file = value; }
}
