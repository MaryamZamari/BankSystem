package com.javase.banking.clientservice.dto;

import com.javase.banking.clientservice.model.ClientType;
import lombok.*;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
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
