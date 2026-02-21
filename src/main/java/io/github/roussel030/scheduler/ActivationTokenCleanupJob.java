package io.github.roussel030.scheduler;

import io.github.roussel030.activationToken.repository.ActivationTokenRepositoryImpl;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ActivationTokenCleanupJob {

    private final ActivationTokenRepositoryImpl activationTokenRepository;

    public ActivationTokenCleanupJob(ActivationTokenRepositoryImpl activationTokenRepository) {
        this.activationTokenRepository = activationTokenRepository;
    }

    @Scheduled(every = "${activation-token.cleanup.interval}")
    @Transactional
    void cleanup() {
        activationTokenRepository.deleteExpired();
    }

}
