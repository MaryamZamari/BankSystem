package com.javasSE.banking.accountService.mapper;

import com.javasSE.banking.accountService.dto.AccountDto;
import com.javasSE.banking.accountService.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper
public interface IAccountMapStruct {
    AccountDto mapToAccountDto(Account account);
    @Mapping(ignore= true, target="id")
    Account mapToAccount(AccountDto accountDto);
    @Mapping(ignore= true, target= "id")
    Account mapToAccount(AccountDto accountDto, @MappingTarget Account account);
    List<AccountDto> mapToAccountDtoList(List<Account> accountList);
    List<Account> mapToAccountList(List<AccountDto> accountDtoList);

}
