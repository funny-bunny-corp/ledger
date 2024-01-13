package com.paymentic.infra.app;

import com.paymentic.adapter.kafka.in.TransactionRegisteredProcessor;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ApplicationLifeCycle {
  private static final Logger LOGGER = Logger.getLogger(TransactionRegisteredProcessor.class);
  void onStart(@Observes StartupEvent ev) {
    LOGGER.info("The application is starting with profile " + ProfileManager.getActiveProfile());
  }

}
