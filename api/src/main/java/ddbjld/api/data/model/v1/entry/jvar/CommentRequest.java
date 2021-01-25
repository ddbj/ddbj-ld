package ddbjld.api.data.model.v1.entry.jvar;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * CommentRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-01-22T14:45:42.804705+09:00[Asia/Tokyo]")


public class CommentRequest   {
  @JsonProperty("comment")
  private String comment = null;

  @JsonProperty("admin")
  private Boolean admin = null;

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

  public CommentRequest admin(Boolean admin) {
    this.admin = admin;
    return this;
  }

  /**
   * Get admin
   * @return admin
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isAdmin() {
    return admin;
  }

  public void setAdmin(Boolean admin) {
    this.admin = admin;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommentRequest commentRequest = (CommentRequest) o;
    return Objects.equals(this.comment, commentRequest.comment) &&
        Objects.equals(this.admin, commentRequest.admin);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comment, admin);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommentRequest {\n");
    
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    admin: ").append(toIndentedString(admin)).append("\n");
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
