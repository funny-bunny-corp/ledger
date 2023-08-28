package com.paymentic.domain.shared;

import jakarta.persistence.Embeddable;
import java.util.UUID;
@Embeddable
public class TransactionId {
  private UUID id;
  public TransactionId() {
  }
  public UUID getId() {
    return id;
  }
}
