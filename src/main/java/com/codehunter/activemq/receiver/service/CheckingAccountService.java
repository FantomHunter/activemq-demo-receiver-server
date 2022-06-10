package com.codehunter.activemq.receiver.service;

import com.codehunter.activemq.sdo.ICheckingAccountService;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author codehunter
 */
public class CheckingAccountService implements ICheckingAccountService {
    private static Logger log = LoggerFactory.getLogger(CheckingAccountService.class);
    private final AccountAsyncService accountAsyncService;

    public CheckingAccountService(AccountAsyncService accountAsyncService) {
        this.accountAsyncService = accountAsyncService;
    }

    @Override
    public void cancelAccount(Long accountId) {
        log.info("Cancelling account [" + accountId + "]");
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void longProcessing() {
        log.info("long processing");
//        try {
//            Thread.sleep(600L*1000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        accountAsyncService.process();

        log.info("long processing end");
    }

    @Override
    public String getData() {

        try {
            Thread.sleep(15000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return LocalDateTime.now().toString();
    }
}
