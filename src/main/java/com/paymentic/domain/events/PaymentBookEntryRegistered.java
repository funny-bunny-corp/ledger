package com.paymentic.domain.events;

import com.paymentic.domain.ids.JournalEntryId;
import com.paymentic.domain.shared.PaymentOrderId;

public record PaymentBookEntryRegistered(JournalEntryId journalEntryId, PaymentOrderId paymentOrderId) { }
