package com.ddbj.ld.data.beans.dra.experiment;

import com.fasterxml.jackson.annotation.*;

public class Design {
    private Title designDescription;
    private StudyRef sampleDescriptor;
    private LibraryDescriptor libraryDescriptor;
    private SpotDescriptor spotDescriptor;

    @JsonProperty("DESIGN_DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Title getDesignDescription() { return designDescription; }
    @JsonProperty("DESIGN_DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDesignDescription(Title value) { this.designDescription = value; }

    @JsonProperty("SAMPLE_DESCRIPTOR")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public StudyRef getSampleDescriptor() { return sampleDescriptor; }
    @JsonProperty("SAMPLE_DESCRIPTOR")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSampleDescriptor(StudyRef value) { this.sampleDescriptor = value; }

    @JsonProperty("LIBRARY_DESCRIPTOR")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public LibraryDescriptor getLibraryDescriptor() { return libraryDescriptor; }
    @JsonProperty("LIBRARY_DESCRIPTOR")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setLibraryDescriptor(LibraryDescriptor value) { this.libraryDescriptor = value; }

    @JsonProperty("SPOT_DESCRIPTOR")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public SpotDescriptor getSpotDescriptor() { return spotDescriptor; }
    @JsonProperty("SPOT_DESCRIPTOR")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSpotDescriptor(SpotDescriptor value) { this.spotDescriptor = value; }
}
