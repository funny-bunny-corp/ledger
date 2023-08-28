package com.paymentic.domain;

import jakarta.persistence.Embeddable;
@Embeddable
public class OwnerId {

  private String id;

  public OwnerId(){}

  public OwnerId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

}
