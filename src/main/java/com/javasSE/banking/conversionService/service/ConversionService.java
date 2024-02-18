package com.javasSE.banking.conversionService.service;

import com.javasSE.banking.common.utility.IdGeneratorUtil;
import com.javasSE.banking.conversionService.utility.ConversionRateCalculatorUtil;
import com.javasSE.banking.conversionService.exception.ConversionNotSupportedException;
import com.javasSE.banking.conversionService.exception.ConversionRateNotFoundException;
import com.javasSE.banking.conversionService.model.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ConversionService {
    private static final ConversionService INSTANCE;

    static{
        INSTANCE = new ConversionService();
        ConversionRateCalculatorUtil.addConversionRate(
                new CurrencyPair(CurrencyType.EUR , CurrencyType.USD) , ConversionRate.EUR_TO_USD);
        ConversionRateCalculatorUtil.addConversionRate(
                new CurrencyPair(CurrencyType.USD , CurrencyType.EUR) , ConversionRate.USD_TO_EUR);
        ConversionRateCalculatorUtil.addConversionRate(
                new CurrencyPair(CurrencyType.USD , CurrencyType.GBP) , ConversionRate.USD_TO_GBP);
        ConversionRateCalculatorUtil.addConversionRate(
                new CurrencyPair(CurrencyType.GBP , CurrencyType.USD) , ConversionRate.GBP_TO_USD);
    }
    public static ConversionService getInstance(){
        return INSTANCE;
    }
    public Transaction createTransaction(TransactionIdPair idPair , CurrencyPair currencyPair ,
                                         BigDecimal amount) throws ConversionNotSupportedException {
        Transaction transaction =
                              new Transaction(IdGeneratorUtil.generateUniqueTransactionId(), idPair,
                              new CurrencyPair(currencyPair.getSource() , currencyPair.getDestination()),
                              ConversionRateCalculatorUtil.pickConversionRate(currencyPair),
                              LocalDateTime.now(),
                              amount);
        return transaction;
    }

    public BigDecimal convert(Transaction transaction) throws ConversionRateNotFoundException {
        ConversionRate conversionRate= transaction.getConversionRate();
        BigDecimal rate = null;
        if(conversionRate != null){
            rate = conversionRate.getRate();
        }else{
            throw new ConversionRateNotFoundException("Conversion service not available for the currency types");
        }
        return transaction.getAmount()
                .multiply(rate);
    }

//TODO: put validation for gmail and password length and format.
    //TODO: check the files
    //TODO: validation for email duplication
    //TODO: fix the null for Id
    //TODO: fix the files saving
    //TODO: delete the balance from entity
    //TODO: print all accounts
    //TODO: check the clientId in account system.
    //TODO: don't use account number for identification or put a search for it
    //TODO: fix the account
    //TODO: test the transactions and put enough validations.



}
