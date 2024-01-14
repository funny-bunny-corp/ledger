package com.paymentic.domain.repositories.data;

import com.paymentic.domain.Book;
import com.paymentic.domain.BookEntry;
import com.paymentic.domain.OperationType;
import com.paymentic.domain.VersionNumber;

public record PayoutBooks(Book payout, Book pending) {
  public boolean addPayout(BookEntry payment,VersionNumber version){
    if(OperationType.CREDIT.equals(payment.getOperationType())){
      throw new RuntimeException("Operation should be credit");
    }
    return this.payout.addEntry(payment,version);
  }
  public boolean addPending(BookEntry pending, VersionNumber version){
    if (OperationType.DEBIT.equals(pending.getOperationType())){
      throw new RuntimeException("Operation should be debit");
    }
    return this.pending.addEntry(pending,version);
  }

}
