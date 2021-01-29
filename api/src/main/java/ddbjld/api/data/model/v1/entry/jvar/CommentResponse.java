package ddbjld.api.data.model.v1.entry.jvar;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

/**
 * CommentResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-01-29T16:08:39.835932+09:00[Asia/Tokyo]")


public class CommentResponse   {
  @JsonProperty("uuid")
  private UUID uuid = null;

  @JsonProperty("comment")
  private String comment = null;

  @JsonProperty("visibility")
  private String visibility = null;

  @JsonProperty("curator")
  private Boolean curator = null;

  @JsonProperty("author")
  private String author = null;

  public CommentResponse uuid(UUID uuid) {
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

  public CommentResponse comment(String comment) {
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

  public CommentResponse visibility(String visibility) {
    this.visibility = visibility;
    return this;
  }

  /**
   * Get visibility
   * @return visibility
   **/
  @ApiModelProperty(value = "")

  public String getVisibility() {
    return visibility;
  }

  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }

  public CommentResponse curator(Boolean curator) {
    this.curator = curator;
    return this;
  }

  /**
   * Get curator
   * @return curator
   **/
  @ApiModelProperty(value = "")

  public Boolean isCurator() {
    return curator;
  }

  public void setCurator(Boolean curator) {
    this.curator = curator;
  }

  public CommentResponse author(String author) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommentResponse commentResponse = (CommentResponse) o;
    return Objects.equals(this.uuid, commentResponse.uuid) &&
            Objects.equals(this.comment, commentResponse.comment) &&
            Objects.equals(this.visibility, commentResponse.visibility) &&
            Objects.equals(this.curator, commentResponse.curator) &&
            Objects.equals(this.author, commentResponse.author);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, comment, visibility, curator, author);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommentResponse {\n");

    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    visibility: ").append(toIndentedString(visibility)).append("\n");
    sb.append("    curator: ").append(toIndentedString(curator)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
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
