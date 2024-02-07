package com.javase.banking.dto.client;

import com.javase.banking.model.account.Account;
import com.javase.banking.model.client.ClientType;
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
    private String website;
    private int employeeCount;
    private List<Account> accounts;

    public LegalClientDto(Integer id, ClientType type, String name, String contactPerson, String industry,
                          String fiscalCode, String registrationNumber, Date establishmentDate, String email,
                          String website, String address, int employeeCount, String number, String password ) {
        super(id, name, fiscalCode, email, address, type, number, password);
        this.contactPerson = contactPerson;
        this.industry = industry;
        this.registrationNumber = registrationNumber;
        this.establishmentDate = establishmentDate;
        this.website = website;
        this.employeeCount = employeeCount;
    }




}
