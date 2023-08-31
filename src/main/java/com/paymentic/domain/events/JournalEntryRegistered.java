package com.paymentic.domain.events;

import com.paymentic.domain.BookId;
import com.paymentic.domain.JournalEntryId;

public record JournalEntryRegistered(BookId bookId, JournalEntryId journalEntryId, String amount,String currency) { }
