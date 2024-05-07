package com.hellomeritz.global;


import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CircuitBreakerStateListener implements RegistryEventConsumer<CircuitBreaker>{

    private final CircuitBreakerBot circuitBreakerBot;

    public CircuitBreakerStateListener(CircuitBreakerBot circuitBreakerBot) {
        this.circuitBreakerBot = circuitBreakerBot;
    }

    @Override
    public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
        CircuitBreaker.EventPublisher eventPublisher = entryAddedEvent.getAddedEntry().getEventPublisher();

        eventPublisher.onCallNotPermitted(event -> log.info("onCallNotPermitted {}", event));
        eventPublisher.onError(event -> log.info("onError {}", event));
        eventPublisher.onIgnoredError(event -> log.info("onIgnoredError {}", event));

        eventPublisher.onStateTransition(
            event -> {
                if (event.getStateTransition().getToState() == CircuitBreaker.State.OPEN) {
                    circuitBreakerBot.sendBotMessage("OPEN 상태로 전환되었습니다.");
                }
            });

        eventPublisher.onSlowCallRateExceeded(event -> log.info("onSlowCallRateExceeded {}", event));
        eventPublisher.onFailureRateExceeded(event -> log.info("onFailureRateExceeded {}", event));
    }

    @Override
    public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {

    }

    @Override
    public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {

    }
}
