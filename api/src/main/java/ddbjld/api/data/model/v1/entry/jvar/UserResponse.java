package ddbjld.api.data.model.v1.entry.jvar;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

/**
 * UserResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-01-27T15:56:39.577750+09:00[Asia/Tokyo]")


public class UserResponse   {
  @JsonProperty("uuid")
  private UUID uuid = null;

  @JsonProperty("uid")
  private String uid = null;

  @JsonProperty("access_token")
  private String accessToken = null;

  @JsonProperty("mail")
  private String mail = null;

  @JsonProperty("curator")
  private Boolean curator = null;

  public UserResponse uuid(UUID uuid) {
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

  public UserResponse uid(String uid) {
    this.uid = uid;
    return this;
  }

  /**
   * Get uid
   * @return uid
   **/
  @ApiModelProperty(value = "")

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public UserResponse accessToken(String accessToken) {
    this.accessToken = accessToken;
    return this;
  }

  /**
   * Get accessToken
   * @return accessToken
   **/
  @ApiModelProperty(value = "")

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public UserResponse mail(String mail) {
    this.mail = mail;
    return this;
  }

  /**
   * Get mail
   * @return mail
   **/
  @ApiModelProperty(value = "")

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public UserResponse curator(Boolean curator) {
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
    UserResponse userResponse = (UserResponse) o;
    return Objects.equals(this.uuid, userResponse.uuid) &&
            Objects.equals(this.uid, userResponse.uid) &&
            Objects.equals(this.accessToken, userResponse.accessToken) &&
            Objects.equals(this.mail, userResponse.mail) &&
            Objects.equals(this.curator, userResponse.curator);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, uid, accessToken, mail, curator);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserResponse {\n");

    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    uid: ").append(toIndentedString(uid)).append("\n");
    sb.append("    accessToken: ").append(toIndentedString(accessToken)).append("\n");
    sb.append("    mail: ").append(toIndentedString(mail)).append("\n");
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
