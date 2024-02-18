package com.javasSE.banking.clientService.clientValidation;

import com.javasSE.banking.clientService.dto.ClientDto;
import com.javasSE.banking.common.validation.ValidationContext;

public class ClientValidationContext extends ValidationContext<ClientDto> {
    public ClientValidationContext(){
        addValidationItem(new ClientValidation());
        addValidationItem(new PersonalClientValidation());
        addValidationItem(new LegalClientValidation());
        addValidationItem(new PasswordValidation());
        addValidationItem(new EmailValidation());
    }


}
