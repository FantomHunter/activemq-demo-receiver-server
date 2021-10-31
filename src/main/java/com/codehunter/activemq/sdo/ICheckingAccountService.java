package com.codehunter.activemq.sdo;

/**
 * @author codehunter
 */
public interface ICheckingAccountService {
    public void cancelAccount(Long accountId);
    public void longProcessing();
}
