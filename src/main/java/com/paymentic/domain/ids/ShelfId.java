package com.paymentic.domain.ids;

import jakarta.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class ShelfId {
  private UUID id;
  public ShelfId() {
  }
  public ShelfId(UUID id) {
    this.id = id;
  }
  public UUID getId() {
    return id;
  }
}
