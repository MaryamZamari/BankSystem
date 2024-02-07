package com.javase.banking.model.client;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class PersonalClient extends Client implements Serializable {
    private String surname;
    private Date birthday;
    private String nationality;
    public PersonalClient(int id, String name, String surname, Date birthday,
                          String nationality, String fiscalCode, String email,
                          String address, ClientType type, String number,
                          boolean deleted, String password) {
        super(id, name, fiscalCode, email, address, type,  number, deleted, password);
        this.surname= capitaliseFirstLetter(surname);
        this.birthday= birthday;
        this.nationality= nationality;
    }

      private static String capitaliseFirstLetter(String str){
        char firstChar= str.toUpperCase().charAt(0);
        return firstChar+ str.substring(1);
    }



}
