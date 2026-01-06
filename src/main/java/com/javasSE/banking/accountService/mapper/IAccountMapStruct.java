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
    Account mapToAccountForUpdate(AccountDto accountDto, @MappingTarget Account newAccount);

    List<AccountDto> mapToAccountDtoList(List<Account> accountList);

    @Mapping(ignore= true, target= "id")
    List<Account> mapToAccountList(List<AccountDto> accountDtoList);

}
