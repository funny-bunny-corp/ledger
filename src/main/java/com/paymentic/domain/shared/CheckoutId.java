package com.paymentic.domain.shared;

import jakarta.persistence.Embeddable;
import java.util.UUID;
@Embeddable
public class CheckoutId{
  private UUID id;
  public CheckoutId() {
  }
  public CheckoutId(UUID id) {
    this.id = id;
  }
  public static CheckoutId newCheckoutId(UUID id){
    return new CheckoutId(id);
  }
  public UUID getId() {
    return id;
  }
}
