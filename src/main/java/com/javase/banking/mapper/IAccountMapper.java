package com.javase.banking.mapper;

import com.javase.banking.dto.client.account.AccountDto;
import com.javase.banking.model.account.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface IAccountMapper {
    AccountDto mapToAccountDto(Account account);
    @Mapping(ignore= true, target= "id")
    Account mapToAccount(AccountDto accountDto);
    List<Account> mapToAccountList(List<AccountDto> accountDtoList);
    List<AccountDto> mapToAccountDtoList(List<Account> accountList);
    @Mapping(ignore= true, target= "id")
    void updateAccountFromDto(AccountDto accountDto, @MappingTarget Account account);

}
