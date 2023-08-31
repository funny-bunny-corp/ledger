package com.paymentic.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class BookId {
  private String id;
  public BookId(){}
  public BookId(String id) {
    this.id = id;
  }
  public String getId() {
    return id;
  }

}
