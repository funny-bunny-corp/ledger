package com.paymentic.domain.application;

import com.paymentic.adapter.persistence.JpaBookRepository;
import com.paymentic.domain.Book;
import com.paymentic.domain.BookEntry;
import com.paymentic.domain.events.PaymentBookEntryRegistered;
import com.paymentic.domain.events.PaymentJournalEntryRegistered;
import com.paymentic.domain.events.PayoutJournalEntryRegistered;
import com.paymentic.domain.events.ShelfRegistered;
import com.paymentic.domain.ids.BookId;
import com.paymentic.domain.ids.ShelfId;
import com.paymentic.domain.repositories.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class BookService {
  private final BookRepository bookRepository;
  private final Event<PaymentBookEntryRegistered> trigger;
  public BookService(JpaBookRepository bookRepository, Event<PaymentBookEntryRegistered> trigger) {
    this.bookRepository = bookRepository;
    this.trigger = trigger;
  }
  public Book get(UUID id){
    return this.bookRepository.findById(id);
  }
  public List<Book> byShelf(ShelfId shelfId){
    return this.bookRepository.booksByShelf(shelfId);
  }
  @Transactional
  public void recordPaymentInBooks(@Observes PaymentJournalEntryRegistered paymentJournalEntryRegistered){
    var books = this.bookRepository.findBooksForPayment(paymentJournalEntryRegistered.shelfId());
    books.addPayment(BookEntry.paymentEntry(paymentJournalEntryRegistered.journalEntryId(), BigDecimal.valueOf(Double.parseDouble(
            paymentJournalEntryRegistered.amount())),
        paymentJournalEntryRegistered.currency(),books.payment(),
        paymentJournalEntryRegistered.versionNumber().version()), paymentJournalEntryRegistered.versionNumber());
    books.addPending(BookEntry.pendingEntry(paymentJournalEntryRegistered.journalEntryId(), BigDecimal.valueOf(Double.parseDouble(
            paymentJournalEntryRegistered.amount())),
        paymentJournalEntryRegistered.currency(),books.pending(),
        paymentJournalEntryRegistered.versionNumber().version()), paymentJournalEntryRegistered.versionNumber());
    this.bookRepository.save(books.payment());
    this.bookRepository.save(books.pending());
    this.trigger.fire(new PaymentBookEntryRegistered(paymentJournalEntryRegistered.journalEntryId(),
        paymentJournalEntryRegistered.paymentOrderId()));
  }
  @Transactional
  public void recordPayoutInBooks(@Observes PayoutJournalEntryRegistered payoutJournalEntryRegistered){
    var books = this.bookRepository.findBooksForPayout(payoutJournalEntryRegistered.shelfId());
    books.addPayout(BookEntry.payoutEntry(payoutJournalEntryRegistered.journalEntryId(), BigDecimal.valueOf(Double.parseDouble(
            payoutJournalEntryRegistered.amount())),
        payoutJournalEntryRegistered.currency(),books.payout(),
        payoutJournalEntryRegistered.versionNumber().version()), payoutJournalEntryRegistered.versionNumber());
    books.addPending(BookEntry.pendingEntry(payoutJournalEntryRegistered.journalEntryId(), BigDecimal.valueOf(Double.parseDouble(
            payoutJournalEntryRegistered.amount())),
        payoutJournalEntryRegistered.currency(),books.pending(),
        payoutJournalEntryRegistered.versionNumber().version()), payoutJournalEntryRegistered.versionNumber());
    this.bookRepository.save(books.payout());
    this.bookRepository.save(books.pending());
//    this.trigger.fire(new PaymentBookEntryRegistered(payoutJournalEntryRegistered.journalEntryId(),
//        payoutJournalEntryRegistered.paymentOrderId()));
  }

  @Transactional
  public void registerBooks(@Observes ShelfRegistered shelfRegistered){
    this.bookRepository.save(
        Book.newPendingBook(UUID.randomUUID().toString(),new ShelfId(shelfRegistered.shelfId().getId())),
        Book.newPaymentBook(UUID.randomUUID().toString(),new ShelfId(shelfRegistered.shelfId().getId())),
        Book.newPayoutBook(UUID.randomUUID().toString(),new ShelfId(shelfRegistered.shelfId().getId())),
        Book.newRefundBook(UUID.randomUUID().toString(),new ShelfId(shelfRegistered.shelfId().getId()))
    );
  }
  public Book bookByShelfAndId(ShelfId shelfId, BookId bookId){
    return this.bookRepository.bookByShelfAndId(shelfId,bookId);
  }

}
