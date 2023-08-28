package com.paymentic.adapter.kafka;

import com.paymentic.domain.events.TransactionRegistered;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.ce.IncomingCloudEventMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

@ApplicationScoped
public class TransactionRegisteredProcessor {
  private static final String TRANSACTION_REGISTERED_EVENT_TYPE = "paymentic.payments-gateway.v1.transaction-registered";

  @Blocking
  @Incoming("transaction-registered")
  public CompletionStage<Void> process(Message<TransactionRegistered> message) {
    var event = message.getMetadata(IncomingCloudEventMetadata.class).orElseThrow(() -> new IllegalArgumentException("Expected a Cloud Event"));
    if (TRANSACTION_REGISTERED_EVENT_TYPE.equals(event.getType())){

    }
    return message.ack();

  }

}
