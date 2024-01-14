package com.paymentic.domain.events;

import com.paymentic.domain.VersionNumber;
import com.paymentic.domain.ids.JournalEntryId;
import com.paymentic.domain.ids.ShelfId;
import com.paymentic.domain.shared.SellerInfo;

public record PayoutJournalEntryRegistered(SellerInfo seller, JournalEntryId journalEntryId,
                                           ShelfId shelfId, String amount, String currency,
                                           VersionNumber versionNumber) { }
