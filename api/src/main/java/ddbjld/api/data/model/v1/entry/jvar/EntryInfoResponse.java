package ddbjld.api.data.model.v1.entry.jvar;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * EntryInformationResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-01-27T15:56:39.577750+09:00[Asia/Tokyo]")


public class EntryInfoResponse {
  @JsonProperty("uuid")
  private UUID uuid = null;

  @JsonProperty("revision")
  private Integer revision = null;

  @JsonProperty("label")
  private String label = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("validation_status")
  private String validationStatus = null;

  @JsonProperty("update_token")
  private UUID updateToken = null;

  @JsonProperty("published_revision")
  private Integer publishedRevision = null;

  @JsonProperty("published_at")
  private String publishedAt = null;

  @JsonProperty("created_at")
  private String createdAt = null;

  @JsonProperty("updated_at")
  private String updatedAt = null;

  @JsonProperty("menu")
  private MenuResponse menu = null;

  @JsonProperty("curator_menu")
  private CuratorMenuResponse curatorMenu = null;

  @JsonProperty("files")
  @Valid
  private List<FileResponse> files = null;

  @JsonProperty("comments")
  @Valid
  private List<CommentResponse> comments = null;

  public EntryInfoResponse uuid(UUID uuid) {
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

  public EntryInfoResponse revision(Integer revision) {
    this.revision = revision;
    return this;
  }

  /**
   * Get revision
   * @return revision
   **/
  @ApiModelProperty(value = "")

  public Integer getRevision() {
    return revision;
  }

  public void setRevision(Integer revision) {
    this.revision = revision;
  }

  public EntryInfoResponse label(String label) {
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

  public EntryInfoResponse type(String type) {
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

  public EntryInfoResponse status(String status) {
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

  public EntryInfoResponse validationStatus(String validationStatus) {
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

  public EntryInfoResponse updateToken(UUID updateToken) {
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

  public EntryInfoResponse publishedRevision(Integer publishedRevision) {
    this.publishedRevision = publishedRevision;
    return this;
  }

  /**
   * Get publishedRevision
   * @return publishedRevision
   **/
  @ApiModelProperty(value = "")

  public Integer getPublishedRevision() {
    return publishedRevision;
  }

  public void setPublishedRevision(Integer publishedRevision) {
    this.publishedRevision = publishedRevision;
  }

  public EntryInfoResponse publishedAt(String publishedAt) {
    this.publishedAt = publishedAt;
    return this;
  }

  /**
   * Get publishedAt
   * @return publishedAt
   **/
  @ApiModelProperty(value = "")

  public String getPublishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(String publishedAt) {
    this.publishedAt = publishedAt;
  }

  public EntryInfoResponse createdAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
   **/
  @ApiModelProperty(value = "")

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public EntryInfoResponse updatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   * @return updatedAt
   **/
  @ApiModelProperty(value = "")

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public EntryInfoResponse menu(MenuResponse menu) {
    this.menu = menu;
    return this;
  }

  /**
   * Get menu
   * @return menu
   **/
  @ApiModelProperty(value = "")

  @Valid
  public MenuResponse getMenu() {
    return menu;
  }

  public void setMenu(MenuResponse menu) {
    this.menu = menu;
  }

  public EntryInfoResponse curatorMenu(CuratorMenuResponse curatorMenu) {
    this.curatorMenu = curatorMenu;
    return this;
  }

  /**
   * Get curatorMenu
   * @return curatorMenu
   **/
  @ApiModelProperty(value = "")

  @Valid
  public CuratorMenuResponse getCuratorMenu() {
    return curatorMenu;
  }

  public void setCuratorMenu(CuratorMenuResponse curatorMenu) {
    this.curatorMenu = curatorMenu;
  }

  public EntryInfoResponse files(List<FileResponse> files) {
    this.files = files;
    return this;
  }

  public EntryInfoResponse addFilesItem(FileResponse filesItem) {
    if (this.files == null) {
      this.files = new ArrayList<FileResponse>();
    }
    this.files.add(filesItem);
    return this;
  }

  /**
   * Get files
   * @return files
   **/
  @ApiModelProperty(value = "")
  @Valid
  public List<FileResponse> getFiles() {
    return files;
  }

  public void setFiles(List<FileResponse> files) {
    this.files = files;
  }

  public EntryInfoResponse comments(List<CommentResponse> comments) {
    this.comments = comments;
    return this;
  }

  public EntryInfoResponse addCommentsItem(CommentResponse commentsItem) {
    if (this.comments == null) {
      this.comments = new ArrayList<CommentResponse>();
    }
    this.comments.add(commentsItem);
    return this;
  }

  /**
   * Get comments
   * @return comments
   **/
  @ApiModelProperty(value = "")
  @Valid
  public List<CommentResponse> getComments() {
    return comments;
  }

  public void setComments(List<CommentResponse> comments) {
    this.comments = comments;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EntryInfoResponse entryInformationResponse = (EntryInfoResponse) o;
    return Objects.equals(this.uuid, entryInformationResponse.uuid) &&
            Objects.equals(this.revision, entryInformationResponse.revision) &&
            Objects.equals(this.label, entryInformationResponse.label) &&
            Objects.equals(this.type, entryInformationResponse.type) &&
            Objects.equals(this.status, entryInformationResponse.status) &&
            Objects.equals(this.validationStatus, entryInformationResponse.validationStatus) &&
            Objects.equals(this.updateToken, entryInformationResponse.updateToken) &&
            Objects.equals(this.publishedRevision, entryInformationResponse.publishedRevision) &&
            Objects.equals(this.publishedAt, entryInformationResponse.publishedAt) &&
            Objects.equals(this.createdAt, entryInformationResponse.createdAt) &&
            Objects.equals(this.updatedAt, entryInformationResponse.updatedAt) &&
            Objects.equals(this.menu, entryInformationResponse.menu) &&
            Objects.equals(this.curatorMenu, entryInformationResponse.curatorMenu) &&
            Objects.equals(this.files, entryInformationResponse.files) &&
            Objects.equals(this.comments, entryInformationResponse.comments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, revision, label, type, status, validationStatus, updateToken, publishedRevision, publishedAt, createdAt, updatedAt, menu, curatorMenu, files, comments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EntryInformationResponse {\n");

    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    revision: ").append(toIndentedString(revision)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    validationStatus: ").append(toIndentedString(validationStatus)).append("\n");
    sb.append("    updateToken: ").append(toIndentedString(updateToken)).append("\n");
    sb.append("    publishedRevision: ").append(toIndentedString(publishedRevision)).append("\n");
    sb.append("    publishedAt: ").append(toIndentedString(publishedAt)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    menu: ").append(toIndentedString(menu)).append("\n");
    sb.append("    curatorMenu: ").append(toIndentedString(curatorMenu)).append("\n");
    sb.append("    files: ").append(toIndentedString(files)).append("\n");
    sb.append("    comments: ").append(toIndentedString(comments)).append("\n");
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
