package com.paymentic.adapter.kafka;

import com.paymentic.domain.events.TransactionRegistered;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.ce.IncomingCloudEventMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import java.util.concurrent.CompletionStage;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

@ApplicationScoped
public class TransactionRegisteredProcessor {
  private static final String TRANSACTION_REGISTERED_EVENT_TYPE = "paymentic.payments-gateway.v1.transaction-registered";
  private final Event<TransactionRegistered> trigger;
  public TransactionRegisteredProcessor(Event<TransactionRegistered> trigger) {
    this.trigger = trigger;
  }
  @Blocking
  @Incoming("transaction-registered")
  public CompletionStage<Void> process(Message<TransactionRegistered> message) {
    var event = message.getMetadata(IncomingCloudEventMetadata.class).orElseThrow(() -> new IllegalArgumentException("Expected a Cloud Event"));
    var transaction = message.getPayload();
    if (TRANSACTION_REGISTERED_EVENT_TYPE.equals(event.getType())){
      this.trigger.fire(transaction);
    }
    return message.ack();
  }

}
