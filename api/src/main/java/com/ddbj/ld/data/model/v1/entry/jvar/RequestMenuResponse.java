package com.ddbj.ld.data.model.v1.entry.jvar;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * RequestMenuResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-01T15:02:39.183486+09:00[Asia/Tokyo]")


public class RequestMenuResponse   {
  @JsonProperty("request")
  private Boolean request = null;

  @JsonProperty("request_to_public")
  private Boolean requestToPublic = null;

  @JsonProperty("request_to_cancel")
  private Boolean requestToCancel = null;

  @JsonProperty("request_to_update")
  private Boolean requestToUpdate = null;

  public RequestMenuResponse request(Boolean request) {
    this.request = request;
    return this;
  }

  /**
   * Get request
   * @return request
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isRequest() {
    return request;
  }

  public void setRequest(Boolean request) {
    this.request = request;
  }

  public RequestMenuResponse requestToPublic(Boolean requestToPublic) {
    this.requestToPublic = requestToPublic;
    return this;
  }

  /**
   * Get requestToPublic
   * @return requestToPublic
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isRequestToPublic() {
    return requestToPublic;
  }

  public void setRequestToPublic(Boolean requestToPublic) {
    this.requestToPublic = requestToPublic;
  }

  public RequestMenuResponse requestToCancel(Boolean requestToCancel) {
    this.requestToCancel = requestToCancel;
    return this;
  }

  /**
   * Get requestToCancel
   * @return requestToCancel
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isRequestToCancel() {
    return requestToCancel;
  }

  public void setRequestToCancel(Boolean requestToCancel) {
    this.requestToCancel = requestToCancel;
  }

  public RequestMenuResponse requestToUpdate(Boolean requestToUpdate) {
    this.requestToUpdate = requestToUpdate;
    return this;
  }

  /**
   * Get requestToUpdate
   * @return requestToUpdate
  **/
  @ApiModelProperty(value = "")
  
    public Boolean isRequestToUpdate() {
    return requestToUpdate;
  }

  public void setRequestToUpdate(Boolean requestToUpdate) {
    this.requestToUpdate = requestToUpdate;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestMenuResponse requestMenuResponse = (RequestMenuResponse) o;
    return Objects.equals(this.request, requestMenuResponse.request) &&
        Objects.equals(this.requestToPublic, requestMenuResponse.requestToPublic) &&
        Objects.equals(this.requestToCancel, requestMenuResponse.requestToCancel) &&
        Objects.equals(this.requestToUpdate, requestMenuResponse.requestToUpdate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(request, requestToPublic, requestToCancel, requestToUpdate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestMenuResponse {\n");
    
    sb.append("    request: ").append(toIndentedString(request)).append("\n");
    sb.append("    requestToPublic: ").append(toIndentedString(requestToPublic)).append("\n");
    sb.append("    requestToCancel: ").append(toIndentedString(requestToCancel)).append("\n");
    sb.append("    requestToUpdate: ").append(toIndentedString(requestToUpdate)).append("\n");
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
