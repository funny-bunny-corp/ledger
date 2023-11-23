package com.paymentic.adapter.http.apis.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.Valid;
import java.util.Objects;

/**
 *
 **/

@JsonTypeName("shelf")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2023-11-23T08:33:25.220758-03:00[America/Sao_Paulo]")
public class Shelf   {
  private @Valid String id;
  private @Valid ShelfOwner owner;
  private @Valid Integer version;

  /**
   **/
  public Shelf id(String id) {
    this.id = id;
    return this;
  }


  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(String id) {
    this.id = id;
  }

  /**
   **/
  public Shelf owner(ShelfOwner owner) {
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

  /**
   **/
  public Shelf version(Integer version) {
    this.version = version;
    return this;
  }


  @JsonProperty("version")
  public Integer getVersion() {
    return version;
  }

  @JsonProperty("version")
  public void setVersion(Integer version) {
    this.version = version;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Shelf shelf = (Shelf) o;
    return Objects.equals(this.id, shelf.id) &&
        Objects.equals(this.owner, shelf.owner) &&
        Objects.equals(this.version, shelf.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, owner, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Shelf {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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

