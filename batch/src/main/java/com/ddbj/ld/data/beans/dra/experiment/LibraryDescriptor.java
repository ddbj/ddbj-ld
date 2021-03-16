package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class LibraryDescriptor {
    private String libraryName;
    private String libraryStrategy;
    private String librarySource;
    private String librarySelection;
    private LibraryLayout libraryLayout;
    private TargetedLoci targetedLoci;
    private String libraryConstructionProtocol;

    @JsonProperty("LIBRARY_NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLibraryName() { return libraryName; }
    @JsonProperty("LIBRARY_NAME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLibraryName(String value) { this.libraryName = value; }

    @JsonProperty("LIBRARY_STRATEGY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLibraryStrategy() { return libraryStrategy; }
    @JsonProperty("LIBRARY_STRATEGY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLibraryStrategy(String value) { this.libraryStrategy = value; }

    @JsonProperty("LIBRARY_SOURCE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLibrarySource() { return librarySource; }
    @JsonProperty("LIBRARY_SOURCE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLibrarySource(String value) { this.librarySource = value; }

    @JsonProperty("LIBRARY_SELECTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLibrarySelection() { return librarySelection; }
    @JsonProperty("LIBRARY_SELECTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLibrarySelection(String value) { this.librarySelection = value; }

    @JsonProperty("LIBRARY_LAYOUT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public LibraryLayout getLibraryLayout() { return libraryLayout; }
    @JsonProperty("LIBRARY_LAYOUT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLibraryLayout(LibraryLayout value) { this.libraryLayout = value; }

    @JsonProperty("TARGETED_LOCI")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public TargetedLoci getTargetedLoci() { return targetedLoci; }
    @JsonProperty("TARGETED_LOCI")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setTargetedLoci(TargetedLoci value) { this.targetedLoci = value; }

    @JsonProperty("LIBRARY_CONSTRUCTION_PROTOCOL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLibraryConstructionProtocol() { return libraryConstructionProtocol; }
    @JsonProperty("LIBRARY_CONSTRUCTION_PROTOCOL")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLibraryConstructionProtocol(String value) { this.libraryConstructionProtocol = value; }
}
