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
 * CommentRespone
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-20T18:07:07.554893+09:00[Asia/Tokyo]")


public class CommentRespone   {
  @JsonProperty("uuid")
  private UUID uuid = null;

  @JsonProperty("comment")
  private String comment = null;

  @JsonProperty("author")
  private String author = null;

  public CommentRespone uuid(UUID uuid) {
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

  public CommentRespone comment(String comment) {
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

  public CommentRespone author(String author) {
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommentRespone commentRespone = (CommentRespone) o;
    return Objects.equals(this.uuid, commentRespone.uuid) &&
        Objects.equals(this.comment, commentRespone.comment) &&
        Objects.equals(this.author, commentRespone.author);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, comment, author);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommentRespone {\n");
    
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
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
