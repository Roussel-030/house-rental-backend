package io.github.roussel030.scheduler;

import io.github.roussel030.module.activationToken.repository.ActivationTokenRepository;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ActivationTokenCleanupJob {

    private final ActivationTokenRepository activationTokenRepository;

    public ActivationTokenCleanupJob(ActivationTokenRepository activationTokenRepository) {
        this.activationTokenRepository = activationTokenRepository;
    }

    @Scheduled(every = "${activation-token.cleanup.interval}")
    @Transactional
    void cleanup() {
        activationTokenRepository.deleteExpired();
    }

}
