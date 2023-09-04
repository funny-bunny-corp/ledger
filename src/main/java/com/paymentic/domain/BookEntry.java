package com.paymentic.domain;

import com.paymentic.domain.ids.JournalEntryId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "book_entry")
public class BookEntry {

  @Id
  @Column(name = "book_entry_id")
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;
  @ManyToOne
  private Book book;
  private LocalDateTime at;
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name="id",column=@Column(name="journal_entry_id")),
  })
  private JournalEntryId journalEntry;
  @Column(name = "amount")
  private BigDecimal amount;
  private String currency;
  @Column(name = "operation_type")
  private OperationType operationType;
  public BookEntry() {
  }
  public BookEntry(UUID id, LocalDateTime at, JournalEntryId journalEntry,
      BigDecimal amount, String currency, OperationType operationType) {
    this.id = id;
    this.at = at;
    this.journalEntry = journalEntry;
    this.amount = amount;
    this.operationType = operationType;
    this.currency = currency;
  }
  public static BookEntry paymentEntry(JournalEntryId journalEntry,
      BigDecimal amount,String currency){
    return new BookEntry(UUID.randomUUID(),LocalDateTime.now(),journalEntry,amount,currency,OperationType.CREDIT);
  }
  public static BookEntry pendingEntry(JournalEntryId journalEntry,
      BigDecimal amount,String currency){
    return new BookEntry(UUID.randomUUID(),LocalDateTime.now(),journalEntry,amount,currency,OperationType.DEBIT);
  }
  public UUID getId() {
    return id;
  }
  public LocalDateTime getAt() {
    return at;
  }
  public JournalEntryId getJournalEntry() {
    return journalEntry;
  }
  public BigDecimal getAmount() {
    return amount;
  }
  public OperationType getOperationType() {
    return operationType;
  }
  public Book getBook() {
    return book;
  }
  public String getCurrency() {
    return currency;
  }
}
