package com.paymentic.domain.events;

import com.paymentic.domain.shared.PaymentOrderId;
import java.time.LocalDateTime;

public record TransactionBooked(PaymentOrderId paymentOrder, LocalDateTime at) { }
