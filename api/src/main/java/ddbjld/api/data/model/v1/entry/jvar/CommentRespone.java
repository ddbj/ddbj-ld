package ddbjld.api.data.model.v1.entry.jvar;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * CommentRespone
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-18T15:28:38.312718+09:00[Asia/Tokyo]")


public class CommentRespone   {
  @JsonProperty("comment")
  private String comment = null;

  @JsonProperty("author")
  private String author = null;

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
    return Objects.equals(this.comment, commentRespone.comment) &&
        Objects.equals(this.author, commentRespone.author);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comment, author);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommentRespone {\n");
    
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
