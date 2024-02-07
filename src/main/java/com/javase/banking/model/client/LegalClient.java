package com.javase.banking.model.client;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class LegalClient extends Client implements Serializable {
    private String contactPerson;
    private String industry;
    private String registrationNumber;
    private Date establishmentDate;
    private String website;
    private int employeeCount;




}
