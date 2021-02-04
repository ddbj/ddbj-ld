package ddbjld.api.data.model.v1.entry.jvar;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

/**
 * RequestResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-03T10:51:53.369836+09:00[Asia/Tokyo]")


public class RequestResponse   {
  @JsonProperty("uuid")
  private UUID uuid = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("comment")
  private String comment = null;

  @JsonProperty("author")
  private String author = null;

  @JsonProperty("is_editable")
  private Boolean isEditable = null;

  @JsonProperty("is_cancelable")
  private Boolean isCancelable = null;

  @JsonProperty("is_applyable")
  private Boolean isApplyable = null;

  public RequestResponse uuid(UUID uuid) {
    this.uuid = uuid;
    return this;
  }

  /**
   * Get uuid
   * @return uuid
   **/
  @ApiModelProperty(value = "")

  @Valid
  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public RequestResponse type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   **/
  @ApiModelProperty(value = "")

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public RequestResponse status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   **/
  @ApiModelProperty(value = "")

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public RequestResponse comment(String comment) {
    this.comment = comment;
    return this;
  }

  /**
   * Get comment
   * @return comment
   **/
  @ApiModelProperty(value = "")

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public RequestResponse author(String author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
   **/
  @ApiModelProperty(value = "")

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public RequestResponse isEditable(Boolean isEditable) {
    this.isEditable = isEditable;
    return this;
  }

  /**
   * Get isEditable
   * @return isEditable
   **/
  @ApiModelProperty(value = "")

  public Boolean isIsEditable() {
    return isEditable;
  }

  public void setIsEditable(Boolean isEditable) {
    this.isEditable = isEditable;
  }

  public RequestResponse isCancelable(Boolean isCancelable) {
    this.isCancelable = isCancelable;
    return this;
  }

  /**
   * Get isCancelable
   * @return isCancelable
   **/
  @ApiModelProperty(value = "")

  public Boolean isIsCancelable() {
    return isCancelable;
  }

  public void setIsCancelable(Boolean isCancelable) {
    this.isCancelable = isCancelable;
  }

  public RequestResponse isApplyable(Boolean isApplyable) {
    this.isApplyable = isApplyable;
    return this;
  }

  /**
   * Get isApplyable
   * @return isApplyable
   **/
  @ApiModelProperty(value = "")

  public Boolean isIsApplyable() {
    return isApplyable;
  }

  public void setIsApplyable(Boolean isApplyable) {
    this.isApplyable = isApplyable;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestResponse requestResponse = (RequestResponse) o;
    return Objects.equals(this.uuid, requestResponse.uuid) &&
            Objects.equals(this.type, requestResponse.type) &&
            Objects.equals(this.status, requestResponse.status) &&
            Objects.equals(this.comment, requestResponse.comment) &&
            Objects.equals(this.author, requestResponse.author) &&
            Objects.equals(this.isEditable, requestResponse.isEditable) &&
            Objects.equals(this.isCancelable, requestResponse.isCancelable) &&
            Objects.equals(this.isApplyable, requestResponse.isApplyable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, type, status, comment, author, isEditable, isCancelable, isApplyable);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestResponse {\n");

    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    isEditable: ").append(toIndentedString(isEditable)).append("\n");
    sb.append("    isCancelable: ").append(toIndentedString(isCancelable)).append("\n");
    sb.append("    isApplyable: ").append(toIndentedString(isApplyable)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
