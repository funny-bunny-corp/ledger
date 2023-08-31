package com.paymentic.domain.application;

import com.paymentic.domain.events.JournalEntryRegistered;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class BookEntryService {
  public void listen(@Observes JournalEntryRegistered journalEntryRegistered){


  }

}
