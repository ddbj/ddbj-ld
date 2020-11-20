package ddbjld.api.data.jvar;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import ddbjld.api.data.model.v1.entry.jvar.CommentRespone;
import ddbjld.api.data.model.v1.entry.jvar.FileResponse;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * EntryInformationResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-18T17:16:22.728930+09:00[Asia/Tokyo]")


public class EntryInformationResponse   {
  @JsonProperty("title")
  private String title = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("validation_status")
  private String validationStatus = null;

  @JsonProperty("file_list")
  @Valid
  private List<FileResponse> fileList = null;

  @JsonProperty("validation_summary")
  @Valid
  private List<ValidationSummaryResponse> validationSummary = null;

  @JsonProperty("comments")
  @Valid
  private List<CommentRespone> comments = null;

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

  public EntryInformationResponse fileList(List<FileResponse> fileList) {
    this.fileList = fileList;
    return this;
  }

  public EntryInformationResponse addFileListItem(FileResponse fileListItem) {
    if (this.fileList == null) {
      this.fileList = new ArrayList<FileResponse>();
    }
    this.fileList.add(fileListItem);
    return this;
  }

  /**
   * Get fileList
   * @return fileList
  **/
  @ApiModelProperty(value = "")
      @Valid
    public List<FileResponse> getFileList() {
    return fileList;
  }

  public void setFileList(List<FileResponse> fileList) {
    this.fileList = fileList;
  }

  public EntryInformationResponse validationSummary(List<ValidationSummaryResponse> validationSummary) {
    this.validationSummary = validationSummary;
    return this;
  }

  public EntryInformationResponse addValidationSummaryItem(ValidationSummaryResponse validationSummaryItem) {
    if (this.validationSummary == null) {
      this.validationSummary = new ArrayList<ValidationSummaryResponse>();
    }
    this.validationSummary.add(validationSummaryItem);
    return this;
  }

  /**
   * Get validationSummary
   * @return validationSummary
  **/
  @ApiModelProperty(value = "")
      @Valid
    public List<ValidationSummaryResponse> getValidationSummary() {
    return validationSummary;
  }

  public void setValidationSummary(List<ValidationSummaryResponse> validationSummary) {
    this.validationSummary = validationSummary;
  }

  public EntryInformationResponse comments(List<CommentRespone> comments) {
    this.comments = comments;
    return this;
  }

  public EntryInformationResponse addCommentsItem(CommentRespone commentsItem) {
    if (this.comments == null) {
      this.comments = new ArrayList<CommentRespone>();
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
    public List<CommentRespone> getComments() {
    return comments;
  }

  public void setComments(List<CommentRespone> comments) {
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
    return Objects.equals(this.title, entryInformationResponse.title) &&
        Objects.equals(this.description, entryInformationResponse.description) &&
        Objects.equals(this.status, entryInformationResponse.status) &&
        Objects.equals(this.validationStatus, entryInformationResponse.validationStatus) &&
        Objects.equals(this.fileList, entryInformationResponse.fileList) &&
        Objects.equals(this.validationSummary, entryInformationResponse.validationSummary) &&
        Objects.equals(this.comments, entryInformationResponse.comments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, description, status, validationStatus, fileList, validationSummary, comments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EntryInformationResponse {\n");
    
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    validationStatus: ").append(toIndentedString(validationStatus)).append("\n");
    sb.append("    fileList: ").append(toIndentedString(fileList)).append("\n");
    sb.append("    validationSummary: ").append(toIndentedString(validationSummary)).append("\n");
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
