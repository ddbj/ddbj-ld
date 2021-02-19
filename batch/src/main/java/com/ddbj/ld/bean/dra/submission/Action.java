package com.ddbj.ld.bean.dra.submission;

import com.fasterxml.jackson.annotation.*;

public class Action {
    private Add add;
    private Add modify;
    private Release suppress;
    private Hold hold;
    private Release release;
    private Add validate;

    @JsonProperty("ADD")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Add getAdd() { return add; }
    @JsonProperty("ADD")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setAdd(Add value) { this.add = value; }

    @JsonProperty("MODIFY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Add getModify() { return modify; }
    @JsonProperty("MODIFY")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setModify(Add value) { this.modify = value; }

    @JsonProperty("SUPPRESS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Release getSuppress() { return suppress; }
    @JsonProperty("SUPPRESS")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setSuppress(Release value) { this.suppress = value; }

    @JsonProperty("HOLD")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Hold getHold() { return hold; }
    @JsonProperty("HOLD")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setHold(Hold value) { this.hold = value; }

    @JsonProperty("RELEASE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Release getRelease() { return release; }
    @JsonProperty("RELEASE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setRelease(Release value) { this.release = value; }

    @JsonProperty("VALIDATE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Add getValidate() { return validate; }
    @JsonProperty("VALIDATE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void setValidate(Add value) { this.validate = value; }
}
