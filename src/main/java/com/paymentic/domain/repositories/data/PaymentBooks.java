package com.paymentic.domain.repositories.data;

import com.paymentic.domain.Book;
import com.paymentic.domain.BookEntry;
import com.paymentic.domain.VersionNumber;

public record PaymentBooks(Book payment,Book pending) {
  public boolean addPayment(BookEntry payment,VersionNumber version){
    return this.payment.addEntry(payment,version);
  }
  public boolean addPending(BookEntry pending, VersionNumber version){
    return this.pending.addEntry(pending,version);
  }

}
