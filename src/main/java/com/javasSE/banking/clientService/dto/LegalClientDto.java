package com.javasSE.banking.clientService.dto;

import com.javasSE.banking.accountService.model.Account;
import com.javasSE.banking.clientService.model.ClientType;
import lombok.*;

import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class LegalClientDto extends ClientDto {
    private String contactPerson;
    private String industry;
    private String registrationNumber;
    private Date establishmentDate;
    private List<Account> accounts;

    public LegalClientDto(Integer id, ClientType type, String name, String contactPerson, String industry,
                          String fiscalCode, String registrationNumber, Date establishmentDate, String email,
                          String address, String number, String password ) {
        super(id, name, fiscalCode, email, address, type, number, password);
        this.contactPerson = contactPerson;
        this.industry = industry;
        this.registrationNumber = registrationNumber;
        this.establishmentDate = establishmentDate;
    }




}
