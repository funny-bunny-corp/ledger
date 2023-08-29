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
  public Integer version;
  public Book(){}
  Book(UUID id, OwnerId owner, String idempotenceKey, Integer version) {
    this.id = id;
    this.owner = owner;
    this.idempotenceKey = idempotenceKey;
    this.version = version;
  }

  public static Book newBook(OwnerId owner){
    return new Book(UUID.randomUUID(),owner,UUID.randomUUID().toString(),Integer.parseInt("0"));
  }
  public UUID getId() {
    return id;
  }
  public OwnerId getOwner() {
    return owner;
  }
  public String getIdempotenceKey() {
    return idempotenceKey;
  }
  public Integer getVersion() {
    return version;
  }
}
