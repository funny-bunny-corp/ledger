package com.paymentic.adapter.http.apis.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 **/

@JsonTypeName("record")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2023-12-09T10:59:29.842184-03:00[America/Sao_Paulo]")
public class Record   {
  private @Valid String id;
  private @Valid LocalDate at;
  private @Valid Double amount;
  private @Valid String currency;
  private @Valid String type;

  /**
   **/
  public Record id(String id) {
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
  public Record at(LocalDate at) {
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
  public Record amount(Double amount) {
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
  public Record currency(String currency) {
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
  public Record type(String type) {
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
    Record record = (Record) o;
    return Objects.equals(this.id, record.id) &&
        Objects.equals(this.at, record.at) &&
        Objects.equals(this.amount, record.amount) &&
        Objects.equals(this.currency, record.currency) &&
        Objects.equals(this.type, record.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, at, amount, currency, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Record {\n");

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

