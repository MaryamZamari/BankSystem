package com.javasSE.banking.conversionService.model;

import com.javasSE.banking.common.utility.IdGeneratorUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
public class Transaction {
    private Integer id = IdGeneratorUtil.generateUniqueTransactionId();
    private TransactionIdPair idPair;
    private CurrencyPair currencyPair;
    private ConversionRate conversionRate;
    private LocalDateTime timeStamp;
    private BigDecimal amount;





}
