package com.paymentic.domain.events;

import com.paymentic.domain.ids.JournalEntryId;
import com.paymentic.domain.ids.ShelfId;
import com.paymentic.domain.shared.BuyerInfo;
import com.paymentic.domain.shared.PaymentOrderId;
import com.paymentic.domain.shared.SellerInfo;

public record JournalEntryRegistered(BuyerInfo buyer, SellerInfo seller, JournalEntryId journalEntryId,
                                     ShelfId shelfId, String amount, String currency,
                                     PaymentOrderId paymentOrderId) { }
