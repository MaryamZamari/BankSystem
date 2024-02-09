package com.javase.banking.conversionservice.service;

import com.javase.banking.conversionservice.ConversionRateCalculatorUtil;
import com.javase.banking.conversionservice.exception.ConversionNotSupportedException;
import com.javase.banking.conversionservice.exception.ConversionRateNotFoundException;
import com.javase.banking.conversionservice.model.*;
import com.javase.banking.shared.utility.IdGeneratorUtil;
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


}
