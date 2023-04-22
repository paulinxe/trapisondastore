package com.trapisondastore.trapisondastore.Shared.Infrastructure.Jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PublishDomainEvents {

    private final Log log = LogFactory.getLog(getClass());

    @Scheduled(fixedDelay = 1000)
    public void run() {
        log.info("CORRIENDO");
    }
}
