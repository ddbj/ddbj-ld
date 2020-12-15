package ddbjld.api.data.model.v1.entry.jvar;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * AdminMenuResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-25T21:00:24.184335+09:00[Asia/Tokyo]")


public class AdminMenuResponse   {
  @JsonProperty("to_unsubmitted")
  private Boolean toUnsubmitted = null;

  @JsonProperty("to_private")
  private Boolean toPrivate = null;

  @JsonProperty("to_public")
  private Boolean toPublic = null;

  @JsonProperty("to_supressed")
  private Boolean toSupressed = null;

  @JsonProperty("to_killed")
  private Boolean toKilled = null;

  @JsonProperty("to_replaced")
  private Boolean toReplaced = null;

  public AdminMenuResponse toUnsubmitted(Boolean toUnsubmitted) {
    this.toUnsubmitted = toUnsubmitted;
    return this;
  }

  /**
   * Get toUnsubmitted
   * @return toUnsubmitted
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isToUnsubmitted() {
    return toUnsubmitted;
  }

  public void setToUnsubmitted(Boolean toUnsubmitted) {
    this.toUnsubmitted = toUnsubmitted;
  }

  public AdminMenuResponse toPrivate(Boolean toPrivate) {
    this.toPrivate = toPrivate;
    return this;
  }

  /**
   * Get toPrivate
   * @return toPrivate
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isToPrivate() {
    return toPrivate;
  }

  public void setToPrivate(Boolean toPrivate) {
    this.toPrivate = toPrivate;
  }

  public AdminMenuResponse toPublic(Boolean toPublic) {
    this.toPublic = toPublic;
    return this;
  }

  /**
   * Get toPublic
   * @return toPublic
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isToPublic() {
    return toPublic;
  }

  public void setToPublic(Boolean toPublic) {
    this.toPublic = toPublic;
  }

  public AdminMenuResponse toSupressed(Boolean toSupressed) {
    this.toSupressed = toSupressed;
    return this;
  }

  /**
   * Get toSupressed
   * @return toSupressed
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isToSupressed() {
    return toSupressed;
  }

  public void setToSupressed(Boolean toSupressed) {
    this.toSupressed = toSupressed;
  }

  public AdminMenuResponse toKilled(Boolean toKilled) {
    this.toKilled = toKilled;
    return this;
  }

  /**
   * Get toKilled
   * @return toKilled
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isToKilled() {
    return toKilled;
  }

  public void setToKilled(Boolean toKilled) {
    this.toKilled = toKilled;
  }

  public AdminMenuResponse toReplaced(Boolean toReplaced) {
    this.toReplaced = toReplaced;
    return this;
  }

  /**
   * Get toReplaced
   * @return toReplaced
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isToReplaced() {
    return toReplaced;
  }

  public void setToReplaced(Boolean toReplaced) {
    this.toReplaced = toReplaced;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AdminMenuResponse adminMenuResponse = (AdminMenuResponse) o;
    return Objects.equals(this.toUnsubmitted, adminMenuResponse.toUnsubmitted) &&
        Objects.equals(this.toPrivate, adminMenuResponse.toPrivate) &&
        Objects.equals(this.toPublic, adminMenuResponse.toPublic) &&
        Objects.equals(this.toSupressed, adminMenuResponse.toSupressed) &&
        Objects.equals(this.toKilled, adminMenuResponse.toKilled) &&
        Objects.equals(this.toReplaced, adminMenuResponse.toReplaced);
  }

  @Override
  public int hashCode() {
    return Objects.hash(toUnsubmitted, toPrivate, toPublic, toSupressed, toKilled, toReplaced);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AdminMenuResponse {\n");
    
    sb.append("    toUnsubmitted: ").append(toIndentedString(toUnsubmitted)).append("\n");
    sb.append("    toPrivate: ").append(toIndentedString(toPrivate)).append("\n");
    sb.append("    toPublic: ").append(toIndentedString(toPublic)).append("\n");
    sb.append("    toSupressed: ").append(toIndentedString(toSupressed)).append("\n");
    sb.append("    toKilled: ").append(toIndentedString(toKilled)).append("\n");
    sb.append("    toReplaced: ").append(toIndentedString(toReplaced)).append("\n");
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
