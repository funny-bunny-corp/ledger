package com.paymentic.adapter.http.apis.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.Valid;
import java.util.Objects;

/**
 *
 **/

@JsonTypeName("requestShelfCreation")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2023-12-09T10:59:29.842184-03:00[America/Sao_Paulo]")
public class RequestShelfCreation   {
  private @Valid ShelfOwner owner;

  /**
   **/
  public RequestShelfCreation owner(ShelfOwner owner) {
    this.owner = owner;
    return this;
  }


  @JsonProperty("owner")
  public ShelfOwner getOwner() {
    return owner;
  }

  @JsonProperty("owner")
  public void setOwner(ShelfOwner owner) {
    this.owner = owner;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestShelfCreation requestShelfCreation = (RequestShelfCreation) o;
    return Objects.equals(this.owner, requestShelfCreation.owner);
  }

  @Override
  public int hashCode() {
    return Objects.hash(owner);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestShelfCreation {\n");

    sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
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

