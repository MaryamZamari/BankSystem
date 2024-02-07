package com.javase.banking.facade.validation.clientvalidation;

import com.javase.banking.dto.client.ClientDto;
import com.javase.banking.facade.validation.ValidationContext;

public class ClientValidationContext extends ValidationContext<ClientDto> {
    public ClientValidationContext(){
        addValidationItem(new ClientValidation());
        addValidationItem(new PersonalClientValidation());
        addValidationItem(new LegalClientValidation());
    }


}
