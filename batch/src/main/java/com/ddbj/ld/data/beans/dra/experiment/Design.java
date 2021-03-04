package com.ddbj.ld.data.beans.dra.experiment;

<<<<<<< HEAD
import com.ddbj.ld.data.beans.dra.common.SpotDescriptor;
import com.fasterxml.jackson.annotation.*;

public class Design {
    private String designDescription;
=======
import com.fasterxml.jackson.annotation.*;

public class Design {
    private Title designDescription;
>>>>>>> 取り込み、修正
    private StudyRef sampleDescriptor;
    private LibraryDescriptor libraryDescriptor;
    private SpotDescriptor spotDescriptor;

    @JsonProperty("DESIGN_DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
<<<<<<< HEAD
    public String getDesignDescription() { return designDescription; }
    @JsonProperty("DESIGN_DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDesignDescription(String value) { this.designDescription = value; }
=======
    public Title getDesignDescription() { return designDescription; }
    @JsonProperty("DESIGN_DESCRIPTION")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setDesignDescription(Title value) { this.designDescription = value; }
>>>>>>> 取り込み、修正

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
