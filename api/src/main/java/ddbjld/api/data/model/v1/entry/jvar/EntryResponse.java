package ddbjld.api.data.model.v1.entry.jvar;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * EntryResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-12-21T10:36:32.519323+09:00[Asia/Tokyo]")


public class EntryResponse   {
  @JsonProperty("uuid")
  private UUID uuid = null;

  @JsonProperty("label")
  private String label = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("validation_status")
  private String validationStatus = null;

  @JsonProperty("isDeletable")
  private Boolean isDeletable = null;

  @JsonProperty("update_token")
  private UUID updateToken = null;

  public EntryResponse uuid(UUID uuid) {
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

  public EntryResponse label(String label) {
    this.label = label;
    return this;
  }

  /**
   * Get label
   * @return label
  **/
  @ApiModelProperty(value = "")
  
    public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public EntryResponse type(String type) {
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

  public EntryResponse status(String status) {
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

  public EntryResponse validationStatus(String validationStatus) {
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

  public EntryResponse isDeletable(Boolean isDeletable) {
    this.isDeletable = isDeletable;
    return this;
  }

  /**
   * Get isDeletable
   * @return isDeletable
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isIsDeletable() {
    return isDeletable;
  }

  public void setIsDeletable(Boolean isDeletable) {
    this.isDeletable = isDeletable;
  }

  public EntryResponse updateToken(UUID updateToken) {
    this.updateToken = updateToken;
    return this;
  }

  /**
   * Get updateToken
   * @return updateToken
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public UUID getUpdateToken() {
    return updateToken;
  }

  public void setUpdateToken(UUID updateToken) {
    this.updateToken = updateToken;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EntryResponse entryResponse = (EntryResponse) o;
    return Objects.equals(this.uuid, entryResponse.uuid) &&
        Objects.equals(this.label, entryResponse.label) &&
        Objects.equals(this.type, entryResponse.type) &&
        Objects.equals(this.status, entryResponse.status) &&
        Objects.equals(this.validationStatus, entryResponse.validationStatus) &&
        Objects.equals(this.isDeletable, entryResponse.isDeletable) &&
        Objects.equals(this.updateToken, entryResponse.updateToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, label, type, status, validationStatus, isDeletable, updateToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EntryResponse {\n");
    
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    validationStatus: ").append(toIndentedString(validationStatus)).append("\n");
    sb.append("    isDeletable: ").append(toIndentedString(isDeletable)).append("\n");
    sb.append("    updateToken: ").append(toIndentedString(updateToken)).append("\n");
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
