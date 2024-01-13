package com.paymentic.domain.events;

import com.paymentic.domain.shared.BuyerInfo;
import com.paymentic.domain.shared.CheckoutId;
import com.paymentic.domain.shared.PaymentOrderId;
import com.paymentic.domain.shared.SellerInfo;
import com.paymentic.domain.shared.TransactionId;

public record JournalEntryUnregistered(TransactionId transaction, String amount,
                                       String currency, CheckoutId checkout,
                                       BuyerInfo buyer, PaymentOrderId payment,
                                       SellerInfo seller) { }
