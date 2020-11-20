package ddbjld.api.data.model.v1.entry.jvar;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

/**
 * FileResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-20T18:07:07.554893+09:00[Asia/Tokyo]")


public class FileResponse   {
  @JsonProperty("uuid")
  private UUID uuid = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("url")
  private String url = null;

  @JsonProperty("validation_uuid")
  private UUID validationUuid = null;

  @JsonProperty("validation_status")
  private String validationStatus = null;

  @JsonProperty("type")
  private String type = null;

  public FileResponse uuid(UUID uuid) {
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

  public FileResponse name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public FileResponse url(String url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   * @return url
  **/
  @ApiModelProperty(value = "")
  
    public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public FileResponse validationUuid(UUID validationUuid) {
    this.validationUuid = validationUuid;
    return this;
  }

  /**
   * Get validationUuid
   * @return validationUuid
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public UUID getValidationUuid() {
    return validationUuid;
  }

  public void setValidationUuid(UUID validationUuid) {
    this.validationUuid = validationUuid;
  }

  public FileResponse validationStatus(String validationStatus) {
    this.validationStatus = validationStatus;
    return this;
  }

  /**
   * Get validationStatus
   * @return validationStatus
  **/
  @ApiModelProperty(value = "")
  
    public String getValidationStatus() {
    return validationStatus;
  }

  public void setValidationStatus(String validationStatus) {
    this.validationStatus = validationStatus;
  }

  public FileResponse type(String type) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileResponse fileResponse = (FileResponse) o;
    return Objects.equals(this.uuid, fileResponse.uuid) &&
        Objects.equals(this.name, fileResponse.name) &&
        Objects.equals(this.url, fileResponse.url) &&
        Objects.equals(this.validationUuid, fileResponse.validationUuid) &&
        Objects.equals(this.validationStatus, fileResponse.validationStatus) &&
        Objects.equals(this.type, fileResponse.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, name, url, validationUuid, validationStatus, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FileResponse {\n");
    
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    validationUuid: ").append(toIndentedString(validationUuid)).append("\n");
    sb.append("    validationStatus: ").append(toIndentedString(validationStatus)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
