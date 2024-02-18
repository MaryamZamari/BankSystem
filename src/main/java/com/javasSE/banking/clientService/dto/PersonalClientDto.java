package com.javasSE.banking.clientService.dto;

import com.javasSE.banking.clientService.model.ClientType;
import lombok.*;
import java.util.Date;
@Getter
@Setter
@ToString(callSuper= true)
public class PersonalClientDto extends ClientDto{
    private String surname;
    private Date birthday;
    private String nationality;
    public PersonalClientDto(Integer id, String name, String surname, Date birthday,
                             String nationality, String fiscalCode, String email,
                             String address, ClientType type, String number, String password) {
        super(id, name, fiscalCode, email, address, type,  number, password);
        this.surname= surname;
        this.birthday= birthday;
        this.nationality= nationality;
    }





}
