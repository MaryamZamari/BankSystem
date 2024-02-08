package com.javase.banking.clientservice.clientvalidation;

import com.javase.banking.clientservice.dto.ClientDto;
import com.javase.banking.shared.validation.ValidationContext;

public class ClientValidationContext extends ValidationContext<ClientDto> {
    public ClientValidationContext(){
        addValidationItem(new ClientValidation());
        addValidationItem(new PersonalClientValidation());
        addValidationItem(new LegalClientValidation());
    }


}
