package com.paymentic.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity(name = "journal_entry")
public class JournalEntry {

  @Id
  @Column(name = "journal_entry_id")
  private UUID id;
  @Enumerated(value = EnumType.STRING)
  @Column(name = "journal_entry_type")
  private TransactionType journalEntryType;
  @Column(name = "idempotence_key")
  private String idempotenceKey;
  @Column(name = "registered_at")
  private LocalDateTime registeredAt;
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "metadata")
  private Metadata metadata;
  @ManyToOne
  private Shelf shelf;
  public JournalEntry() {
  }
  public JournalEntry(UUID id,TransactionType journalEntryType, String idempotenceKey,
      LocalDateTime registeredAt, Metadata metadata,Shelf shelf) {
    this.id = id;
    this.journalEntryType = journalEntryType;
    this.idempotenceKey = idempotenceKey;
    this.registeredAt = registeredAt;
    this.metadata = metadata;
    this.shelf = shelf;
  }
  public static JournalEntry newJournalEntry(TransactionType journalEntryType, String idempotenceKey,
      Metadata metadata,Shelf shelf){
    return new JournalEntry(UUID.randomUUID(),journalEntryType,idempotenceKey,LocalDateTime.now(),metadata,shelf);
  }
  public UUID getId() {
    return id;
  }
  public TransactionType getJournalEntryType() {
    return journalEntryType;
  }
  public String getIdempotenceKey() {
    return idempotenceKey;
  }
  public LocalDateTime getRegisteredAt() {
    return registeredAt;
  }
  public Shelf getShelf() {
    return shelf;
  }
  public Metadata getMetadata() {
    return metadata;
  }

}
