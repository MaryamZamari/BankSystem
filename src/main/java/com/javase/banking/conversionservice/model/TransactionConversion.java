package com.javase.banking.conversionservice.model;

import com.javase.banking.conversionservice.exception.ConversionRateNotFoundException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

@Getter
@Setter
@ToString
public class TransactionConversion {
    private Currency source;
    private Currency destination;
    private ConversionRate conversionRate;
    private Date timeStamp;
    private BigDecimal amount;


    public BigDecimal convert(TransactionConversion transaction) throws ConversionRateNotFoundException {
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

}
