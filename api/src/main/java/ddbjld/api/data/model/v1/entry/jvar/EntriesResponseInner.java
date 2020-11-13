package ddbjld.api.data.model.v1.entry.jvar;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;

/**
 * EntriesResponseInner
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-12T15:36:38.385906+09:00[Asia/Tokyo]")


public class EntriesResponseInner   {
  @JsonProperty("schemas")
  private EntryResponse schemas = null;

  public EntriesResponseInner schemas(EntryResponse schemas) {
    this.schemas = schemas;
    return this;
  }

  /**
   * Get schemas
   * @return schemas
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public EntryResponse getSchemas() {
    return schemas;
  }

  public void setSchemas(EntryResponse schemas) {
    this.schemas = schemas;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EntriesResponseInner entriesResponseInner = (EntriesResponseInner) o;
    return Objects.equals(this.schemas, entriesResponseInner.schemas);
  }

  @Override
  public int hashCode() {
    return Objects.hash(schemas);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EntriesResponseInner {\n");
    
    sb.append("    schemas: ").append(toIndentedString(schemas)).append("\n");
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
