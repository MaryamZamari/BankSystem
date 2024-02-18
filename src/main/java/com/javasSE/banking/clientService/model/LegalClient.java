package com.javasSE.banking.clientService.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class LegalClient extends Client implements Serializable {
    private String contactPerson;
    private String industry;
    private String registrationNumber;
    private Date establishmentDate;

public LegalClient(){
    super();
}


}
