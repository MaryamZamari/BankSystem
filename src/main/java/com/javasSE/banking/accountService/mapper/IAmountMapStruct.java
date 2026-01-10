package com.javasSE.banking.accountService.mapper;

import com.javasSE.banking.accountService.dto.AmountDto;
import com.javasSE.banking.accountService.model.Amount;
import org.mapstruct.Mapper;

@Mapper
public interface IAmountMapStruct {
    AmountDto mapToAmountDto(Amount amount);
    Amount mapToAmount(AmountDto amountDto);
}
