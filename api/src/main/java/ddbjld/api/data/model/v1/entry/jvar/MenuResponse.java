package ddbjld.api.data.model.v1.entry.jvar;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * MenuResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-20T18:07:07.554893+09:00[Asia/Tokyo]")


public class MenuResponse   {
  @JsonProperty("validate")
  private Boolean validate = null;

  @JsonProperty("submit")
  private Boolean submit = null;

  @JsonProperty("request_to_public")
  private Boolean requestToPublic = null;

  @JsonProperty("request_to_cancel")
  private Boolean requestToCancel = null;

  @JsonProperty("request_to_update")
  private Boolean requestToUpdate = null;

  public MenuResponse validate(Boolean validate) {
    this.validate = validate;
    return this;
  }

  /**
   * Get validate
   * @return validate
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isValidate() {
    return validate;
  }

  public void setValidate(Boolean validate) {
    this.validate = validate;
  }

  public MenuResponse submit(Boolean submit) {
    this.submit = submit;
    return this;
  }

  /**
   * Get submit
   * @return submit
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isSubmit() {
    return submit;
  }

  public void setSubmit(Boolean submit) {
    this.submit = submit;
  }

  public MenuResponse requestToPublic(Boolean requestToPublic) {
    this.requestToPublic = requestToPublic;
    return this;
  }

  /**
   * Get requestToPublic
   * @return requestToPublic
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isRequestToPublic() {
    return requestToPublic;
  }

  public void setRequestToPublic(Boolean requestToPublic) {
    this.requestToPublic = requestToPublic;
  }

  public MenuResponse requestToCancel(Boolean requestToCancel) {
    this.requestToCancel = requestToCancel;
    return this;
  }

  /**
   * Get requestToCancel
   * @return requestToCancel
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isRequestToCancel() {
    return requestToCancel;
  }

  public void setRequestToCancel(Boolean requestToCancel) {
    this.requestToCancel = requestToCancel;
  }

  public MenuResponse requestToUpdate(Boolean requestToUpdate) {
    this.requestToUpdate = requestToUpdate;
    return this;
  }

  /**
   * Get requestToUpdate
   * @return requestToUpdate
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isRequestToUpdate() {
    return requestToUpdate;
  }

  public void setRequestToUpdate(Boolean requestToUpdate) {
    this.requestToUpdate = requestToUpdate;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MenuResponse menuResponse = (MenuResponse) o;
    return Objects.equals(this.validate, menuResponse.validate) &&
        Objects.equals(this.submit, menuResponse.submit) &&
        Objects.equals(this.requestToPublic, menuResponse.requestToPublic) &&
        Objects.equals(this.requestToCancel, menuResponse.requestToCancel) &&
        Objects.equals(this.requestToUpdate, menuResponse.requestToUpdate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(validate, submit, requestToPublic, requestToCancel, requestToUpdate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MenuResponse {\n");
    
    sb.append("    validate: ").append(toIndentedString(validate)).append("\n");
    sb.append("    submit: ").append(toIndentedString(submit)).append("\n");
    sb.append("    requestToPublic: ").append(toIndentedString(requestToPublic)).append("\n");
    sb.append("    requestToCancel: ").append(toIndentedString(requestToCancel)).append("\n");
    sb.append("    requestToUpdate: ").append(toIndentedString(requestToUpdate)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
