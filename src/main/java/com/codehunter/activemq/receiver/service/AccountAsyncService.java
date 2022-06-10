package com.codehunter.activemq.receiver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

public class AccountAsyncService {
    private static Logger log = LoggerFactory.getLogger(AccountAsyncService.class);

//    @Async
    public void process() {
        log.info("async processing start");
        try {
            Thread.sleep(600L*1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("async processing end");
    }
}
