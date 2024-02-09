package com.javase.banking.conversionservice;

import com.javase.banking.conversionservice.exception.ConversionNotSupportedException;
import com.javase.banking.conversionservice.model.ConversionRate;
import com.javase.banking.conversionservice.model.CurrencyPair;
import java.util.HashMap;
import java.util.Map;

public class ConversionRateCalculatorUtil {
    private static final Map<CurrencyPair, ConversionRate> conversionRateMap = new HashMap<>();
    public static void addConversionRate(CurrencyPair pair , ConversionRate rate){
        conversionRateMap.put(pair , conversionRateMap.getOrDefault(pair , rate));
    }

    public static ConversionRate pickConversionRate(CurrencyPair pair) throws ConversionNotSupportedException {
        return conversionRateMap.get(pair);
    }
}
