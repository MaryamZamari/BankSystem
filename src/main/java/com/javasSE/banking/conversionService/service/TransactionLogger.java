package com.javasSE.banking.conversionService.service;

import com.javasSE.banking.conversionService.model.Transaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionLogger {
    private static final TransactionLogger INSTANCE;
    private static final List<Transaction> transactionLogList;
    static {
        INSTANCE = new TransactionLogger();
        transactionLogList = new ArrayList<>();
    }
    private TransactionLogger(){}
    public static TransactionLogger getInstance(){
        return INSTANCE;
    }
    public void logTransaction(Transaction transaction){
        transactionLogList.add(transaction);
    }

    public List<Transaction> getTransactionLogList(){
        return transactionLogList;
    }


}
