package com.paymentic.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity(name = "journal_entry")
public class JournalEntry {

  @Id
  @Column(name = "book_id")
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;
  @Enumerated(value = EnumType.STRING)
  @Column(name = "journal_entry_type")
  private TransactionType journalEntryType;
  @Column(name = "idempotence_key")
  private String idempotenceKey;

  @Column(name = "registered_at")
  private LocalDateTime registeredAt;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "payment_order")
  private PaymentOrder paymentOrder;

  public JournalEntry() {
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
  public PaymentOrder getPaymentOrder() {
    return paymentOrder;
  }

}
