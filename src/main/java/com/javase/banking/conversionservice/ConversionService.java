package com.javase.banking.conversionservice;

import com.javase.banking.conversionservice.exception.ConversionNotSupportedException;
import com.javase.banking.conversionservice.exception.ConversionRateNotFoundException;
import com.javase.banking.conversionservice.model.ConversionRate;
import com.javase.banking.conversionservice.model.CurrencyPair;
import com.javase.banking.conversionservice.model.CurrencyType;
import com.javase.banking.conversionservice.model.Transaction;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ConversionService {
    private static final Map<CurrencyPair , ConversionRate> conversionRateMap = new HashMap<>();
    static{
        addConversionRate(new CurrencyPair(CurrencyType.EUR , CurrencyType.USD) , ConversionRate.EUR_TO_USD);
        addConversionRate(new CurrencyPair(CurrencyType.USD , CurrencyType.EUR) , ConversionRate.USD_TO_EUR);
        addConversionRate(new CurrencyPair(CurrencyType.USD , CurrencyType.GBP) , ConversionRate.USD_TO_GBP);
        addConversionRate(new CurrencyPair(CurrencyType.GBP , CurrencyType.USD) , ConversionRate.GBP_TO_USD);
    }
    private static void addConversionRate(CurrencyPair pair , ConversionRate rate){
        conversionRateMap.put(pair , conversionRateMap.getOrDefault(pair , rate));
    }
    public BigDecimal convert(Transaction transaction) throws ConversionRateNotFoundException {
        ConversionRate conversionRate= transaction.getConversionRate();
        BigDecimal rate = null;
        if(conversionRate != null){
            rate = conversionRate.getRate();
        }else{
            throw new ConversionRateNotFoundException();
        }
        return transaction.getAmount()
                .multiply(rate);
    }

    public ConversionRate pickConversionRate(Transaction transaction) throws ConversionNotSupportedException {
        CurrencyPair pair = transaction.getCurrencyPair();
        return conversionRateMap.get(pair);
    }
}
