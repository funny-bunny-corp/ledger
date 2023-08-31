package com.paymentic.domain.shared;

import jakarta.persistence.Embeddable;
import java.util.UUID;
@Embeddable
public class TransactionId {
  private UUID id;
  public TransactionId() {
  }
  public TransactionId(UUID id) {
    this.id = id;
  }
  public TransactionId newTransactionId(UUID id){
    return new TransactionId(id);
  }
  public UUID getId() {
    return id;
  }
}
