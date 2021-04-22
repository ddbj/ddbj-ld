package ddbjld.api.data.model.v1.entry.jvar;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;

/**
 * MenuResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-01T15:02:39.183486+09:00[Asia/Tokyo]")


public class MenuResponse   {
  @JsonProperty("validate")
  private Boolean validate = null;

  @JsonProperty("submit")
  private Boolean submit = null;

  @JsonProperty("request_menu")
  private RequestMenuResponse requestMenu = null;

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

  public MenuResponse requestMenu(RequestMenuResponse requestMenu) {
    this.requestMenu = requestMenu;
    return this;
  }

  /**
   * Get requestMenu
   * @return requestMenu
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public RequestMenuResponse getRequestMenu() {
    return requestMenu;
  }

  public void setRequestMenu(RequestMenuResponse requestMenu) {
    this.requestMenu = requestMenu;
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
        Objects.equals(this.requestMenu, menuResponse.requestMenu);
  }

  @Override
  public int hashCode() {
    return Objects.hash(validate, submit, requestMenu);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MenuResponse {\n");
    
    sb.append("    validate: ").append(toIndentedString(validate)).append("\n");
    sb.append("    submit: ").append(toIndentedString(submit)).append("\n");
    sb.append("    requestMenu: ").append(toIndentedString(requestMenu)).append("\n");
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
