package com.paymentic.adapter.http.apis.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Objects;



@JsonTypeName("records_inner")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2023-12-09T10:59:29.842184-03:00[America/Sao_Paulo]")
public class RecordsInner   {
  private @Valid String id;
  private @Valid LocalDate at;
  private @Valid Double amount;
  private @Valid String currency;
  private @Valid String type;

  /**
   **/
  public RecordsInner id(String id) {
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
  public RecordsInner at(LocalDate at) {
    this.at = at;
    return this;
  }


  @JsonProperty("at")
  public LocalDate getAt() {
    return at;
  }

  @JsonProperty("at")
  public void setAt(LocalDate at) {
    this.at = at;
  }

  /**
   **/
  public RecordsInner amount(Double amount) {
    this.amount = amount;
    return this;
  }


  @JsonProperty("amount")
  public Double getAmount() {
    return amount;
  }

  @JsonProperty("amount")
  public void setAmount(Double amount) {
    this.amount = amount;
  }

  /**
   **/
  public RecordsInner currency(String currency) {
    this.currency = currency;
    return this;
  }


  @JsonProperty("currency")
  public String getCurrency() {
    return currency;
  }

  @JsonProperty("currency")
  public void setCurrency(String currency) {
    this.currency = currency;
  }

  /**
   **/
  public RecordsInner type(String type) {
    this.type = type;
    return this;
  }


  @JsonProperty("type")
  public String getType() {
    return type;
  }

  @JsonProperty("type")
  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RecordsInner recordsInner = (RecordsInner) o;
    return Objects.equals(this.id, recordsInner.id) &&
        Objects.equals(this.at, recordsInner.at) &&
        Objects.equals(this.amount, recordsInner.amount) &&
        Objects.equals(this.currency, recordsInner.currency) &&
        Objects.equals(this.type, recordsInner.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, at, amount, currency, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecordsInner {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    at: ").append(toIndentedString(at)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

