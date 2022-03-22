package com.ddbj.ld.data.beans.sra.analysis;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

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

    @XmlAttribute(name = "checksum_method")
    @JsonProperty("checksum_method")
    private String checksumMethod;

    @XmlAttribute(name = "checksum")
    @JsonProperty("checksum")
    private String checksum;
}
