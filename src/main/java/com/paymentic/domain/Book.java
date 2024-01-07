package com.paymentic.domain;

import com.paymentic.domain.ids.ShelfId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "book")
public class Book {
  @Id
  @Column(name = "book_id")
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;
  @Column(name = "idempotence_key")
  private String idempotenceKey;
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name="id",column=@Column(name="shelf_id")),
  })
  private ShelfId shelf;
  @Enumerated(value = EnumType.STRING)
  private BookType type;
  @Version
  public Integer version;
  @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
  private Set<BookEntry> entries;
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name="fromAmount",column=@Column(name="from_amount")),
      @AttributeOverride(name="toAmount",column=@Column(name="to_amount")),
  })
  private AmountSnapshot snapshot;
  public Book(){}
  public Book(String idempotenceKey, ShelfId shelf, BookType type) {
    this.idempotenceKey = idempotenceKey;
    this.shelf = shelf;
    this.type = type;
    this.snapshot = AmountSnapshot.zero();
  }
  public static Book newPaymentBook(String idempotenceKey, ShelfId shelf){
    return new Book(idempotenceKey,shelf,BookType.PAYMENTS);
  }
  public static Book newPendingBook(String idempotenceKey, ShelfId shelf){
    return new Book(idempotenceKey,shelf,BookType.PENDING_BALANCE);
  }
  public static Book newRefundBook(String idempotenceKey, ShelfId shelf){
    return new Book(idempotenceKey,shelf,BookType.REFUND);
  }
  public static Book newPayoutBook(String idempotenceKey, ShelfId shelf){
    return new Book(idempotenceKey,shelf,BookType.PAYOUT);
  }
  public boolean addEntry(BookEntry entry,VersionNumber version){
    this.version = version.version();
    if (OperationType.CREDIT.equals(entry.getOperationType())){
      this.snapshot = new AmountSnapshot(this.snapshot.getToAmount(),this.snapshot.getToAmount().add(entry.getAmount()));
    }else{
      this.snapshot = new AmountSnapshot(this.snapshot.getToAmount(),this.snapshot.getToAmount().subtract(entry.getAmount()));
    }
    return this.entries.add(entry.holdBalance(this.snapshot));
  }
  public UUID getId() {
    return id;
  }
  public String getIdempotenceKey() {
    return idempotenceKey;
  }
  public Integer getVersion() {
    return version;
  }
  public BookType getType() {
    return type;
  }
  public ShelfId getShelf() {
    return shelf;
  }
  public Set<BookEntry> getEntries() {
    return entries;
  }
  public AmountSnapshot getSnapshot() {
    return snapshot;
  }

}
