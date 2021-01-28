package ddbjld.api.data.model.v1.entry.jvar;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * CommentRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-01-27T15:56:39.577750+09:00[Asia/Tokyo]")


public class CommentRequest   {
  @JsonProperty("comment")
  private String comment = null;

  @JsonProperty("curator")
  private Boolean curator = null;

  public CommentRequest comment(String comment) {
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

  public CommentRequest curator(Boolean curator) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommentRequest commentRequest = (CommentRequest) o;
    return Objects.equals(this.comment, commentRequest.comment) &&
            Objects.equals(this.curator, commentRequest.curator);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comment, curator);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommentRequest {\n");

    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    curator: ").append(toIndentedString(curator)).append("\n");
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
