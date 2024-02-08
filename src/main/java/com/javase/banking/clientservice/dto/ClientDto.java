package com.javase.banking.clientservice.dto;

import com.javase.banking.clientservice.model.ClientType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public abstract class ClientDto {
    private Integer id;
    private String name;
    private String fiscalCode;
    private String email;
    private String address;
    private ClientType type;
    private String  number;
    private String password;

    public ClientDto(Integer id,ClientType type) {
        this.id= (id != null)? id: -1;
        this.type = type;
    }

    public ClientDto(ClientType type) {this.id= -1; }


}
