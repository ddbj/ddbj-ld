package com.ddbj.ld.data.beans.sra.run;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class File {
    @XmlAttribute(name = "filename")
    @JsonProperty("filename")
    private String filename;

    @XmlAttribute(name = "filetype")
    @JsonProperty("filetype")
    private String filetype;

    @XmlAttribute(name = "quality_scoring_system")
    @JsonProperty("quality_scoring_system")
    private String qualityScoringSystem;

    @XmlAttribute(name = "quality_encoding")
    @JsonProperty("quality_encoding")
    private String qualityEncoding;

    @XmlAttribute(name = "ascii_offset")
    @JsonProperty("ascii_offset")
    private String asciiOffset;

    @XmlAttribute(name = "checksum_method")
    @JsonProperty("checksum_method")
    private String checksumMethod;

    @XmlAttribute(name = "checksum")
    @JsonProperty("checksum")
    private String checksum;

    @XmlElement(name = "READ_LABEL")
    @JsonProperty("READ_LABEL")
    private String readLabel;
}
