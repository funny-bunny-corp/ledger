package com.paymentic.domain.events;

import com.paymentic.domain.shared.BuyerInfo;
import com.paymentic.domain.shared.CheckoutId;
import com.paymentic.domain.shared.PaymentOrderId;
import com.paymentic.domain.shared.SellerInfo;
import com.paymentic.domain.shared.TransactionId;
import java.time.LocalDateTime;
import org.apache.kafka.common.protocol.types.Field.Str;

public record PaymentBookFailed(TransactionId transaction, String amount,
                                String currency, CheckoutId checkout,
                                BuyerInfo buyer, PaymentOrderId payment,
                                SellerInfo seller, LocalDateTime rejectedAt,
                                String reason) { }
