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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-20T20:24:59.024199+09:00[Asia/Tokyo]")


public class EntryInformationResponse   {
  @JsonProperty("uuid")
  private UUID uuid = null;

  @JsonProperty("label")
  private String label = null;

  @JsonProperty("title")
  private String title = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("validation_status")
  private String validationStatus = null;

  @JsonProperty("menu")
  private MenuResponse menu = null;

  @JsonProperty("admin_menu")
  private AdminMenuResponse adminMenu = null;

  @JsonProperty("files")
  @Valid
  private List<FileResponse> files = null;

  @JsonProperty("comments")
  @Valid
  private List<CommentResponse> comments = null;

  public EntryInformationResponse uuid(UUID uuid) {
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

  public EntryInformationResponse label(String label) {
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

  public EntryInformationResponse title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  **/
  @ApiModelProperty(value = "")
  
    public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public EntryInformationResponse description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public EntryInformationResponse status(String status) {
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

  public EntryInformationResponse validationStatus(String validationStatus) {
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

  public EntryInformationResponse menu(MenuResponse menu) {
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

  public EntryInformationResponse adminMenu(AdminMenuResponse adminMenu) {
    this.adminMenu = adminMenu;
    return this;
  }

  /**
   * Get adminMenu
   * @return adminMenu
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public AdminMenuResponse getAdminMenu() {
    return adminMenu;
  }

  public void setAdminMenu(AdminMenuResponse adminMenu) {
    this.adminMenu = adminMenu;
  }

  public EntryInformationResponse files(List<FileResponse> files) {
    this.files = files;
    return this;
  }

  public EntryInformationResponse addFilesItem(FileResponse filesItem) {
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

  public EntryInformationResponse comments(List<CommentResponse> comments) {
    this.comments = comments;
    return this;
  }

  public EntryInformationResponse addCommentsItem(CommentResponse commentsItem) {
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EntryInformationResponse entryInformationResponse = (EntryInformationResponse) o;
    return Objects.equals(this.uuid, entryInformationResponse.uuid) &&
        Objects.equals(this.label, entryInformationResponse.label) &&
        Objects.equals(this.title, entryInformationResponse.title) &&
        Objects.equals(this.description, entryInformationResponse.description) &&
        Objects.equals(this.status, entryInformationResponse.status) &&
        Objects.equals(this.validationStatus, entryInformationResponse.validationStatus) &&
        Objects.equals(this.menu, entryInformationResponse.menu) &&
        Objects.equals(this.adminMenu, entryInformationResponse.adminMenu) &&
        Objects.equals(this.files, entryInformationResponse.files) &&
        Objects.equals(this.comments, entryInformationResponse.comments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, label, title, description, status, validationStatus, menu, adminMenu, files, comments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EntryInformationResponse {\n");
    
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    validationStatus: ").append(toIndentedString(validationStatus)).append("\n");
    sb.append("    menu: ").append(toIndentedString(menu)).append("\n");
    sb.append("    adminMenu: ").append(toIndentedString(adminMenu)).append("\n");
    sb.append("    files: ").append(toIndentedString(files)).append("\n");
    sb.append("    comments: ").append(toIndentedString(comments)).append("\n");
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
