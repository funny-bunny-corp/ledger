package com.paymentic.domain.events;

import com.paymentic.domain.shared.PaymentOrderId;

public record TransactionBooked(PaymentOrderId paymentOrder) { }
