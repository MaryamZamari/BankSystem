package com.javasSE.banking.clientService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.javasSE.banking.common.utility.IdGeneratorUtil;
import com.javasSE.banking.common.utility.PasswordEncoderUtil;
import lombok.*;
import java.io.Serializable;

@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, property= "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value= LegalClient.class, name="LEGAL"),
        @JsonSubTypes.Type(value= PersonalClient.class, name= "PERSONAL")
})
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class Client implements Serializable {
    @JsonIgnore
    private Integer id;      //TODO: CHECK why id comes out null
    private String name;
    private String fiscalCode;
    private String email;
    private String address;
    private ClientType type;
    private String number;
    private boolean deleted;
    private String password;

    public Client(String name, String fiscalCode, String email,
                  String address, ClientType type, String number,
                  boolean deleted, String password){
        this.id= IdGeneratorUtil.generateUniqueClientId();
        this.name= capitaliseFirstLetter(name);
        this.fiscalCode= fiscalCode;
        this.email= email;
        this.address= address;
        this.type= type;
        this.number= number;
        this.deleted= false;
        this.password= PasswordEncoderUtil.encodePassword(password, this.id);
    }

    public Client(int id, ClientType type, String name, String fiscalCode, String email, String address, String number, boolean deleted, String password){

    }

    private static String capitaliseFirstLetter(String str){
        char firstChar= str.toUpperCase().charAt(0);
        return firstChar+ str.substring(1);
    }


}
