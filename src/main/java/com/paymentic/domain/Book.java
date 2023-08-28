package com.paymentic.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "book")
public class Book {

  @Id
  @Column(name = "book_id")
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name="id",column=@Column(name="owner_id")),
  })
  private OwnerId owner;
  @Column(name = "idempotence_key")
  private String idempotenceKey;

  public Book(){}

  public UUID getId() {
    return id;
  }
  public OwnerId getOwner() {
    return owner;
  }
  public String getIdempotenceKey() {
    return idempotenceKey;
  }

}
