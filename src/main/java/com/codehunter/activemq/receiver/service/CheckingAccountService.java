package com.codehunter.activemq.receiver.service;

import com.codehunter.activemq.sdo.ICheckingAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author codehunter
 */
public class CheckingAccountService implements ICheckingAccountService {
    private static Logger log = LoggerFactory.getLogger(CheckingAccountService.class);

    @Override
    public void cancelAccount(Long accountId) {
        log.info("Cancelling account [" + accountId + "]");
    }

    @Override
    public void longProcessing() {
        log.info("long processing");
        try {
            Thread.sleep(600L*1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
