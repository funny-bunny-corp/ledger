package com.paymentic.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "book_entry")
public class BookEntry {

  @Id
  @Column(name = "book_id")
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name="id",column=@Column(name="book_id")),
  })
  private BookId book;
  private LocalDateTime at;
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name="id",column=@Column(name="journal_entry_id")),
  })
  private JournalEntryId journalEntry;
  @Column(name = "from_amount")
  private BigDecimal fromAmount;
  @Column(name = "to_amount")
  private BigDecimal toAmount;
  @Enumerated(value = EnumType.STRING)
  @Column(name = "journal_entry_type")
  private TransactionType journalEntryType;
  @Column(name = "book_version")
  private Long bookVersion;
  @Enumerated(value = EnumType.STRING)
  @Column(name = "operation_type")
  private TransactionType operationType;

  public BookEntry() {
  }
  public UUID getId() {
    return id;
  }
  public BookId getBook() {
    return book;
  }
  public LocalDateTime getAt() {
    return at;
  }
  public JournalEntryId getJournalEntry() {
    return journalEntry;
  }
  public BigDecimal getFromAmount() {
    return fromAmount;
  }
  public BigDecimal getToAmount() {
    return toAmount;
  }
  public TransactionType getJournalEntryType() {
    return journalEntryType;
  }
  public Long getBookVersion() {
    return bookVersion;
  }
  public TransactionType getOperationType() {
    return operationType;
  }
}
