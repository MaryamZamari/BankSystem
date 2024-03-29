package com.javasSE.banking.clientService.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javasSE.banking.clientService.dto.ClientDto;
import com.javasSE.banking.clientService.dto.LegalClientDto;
import com.javasSE.banking.clientService.dto.PersonalClientDto;
import com.javasSE.banking.clientService.model.Client;
import com.javasSE.banking.clientService.model.LegalClient;
import com.javasSE.banking.clientService.model.PersonalClient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper
public interface IClientMapStruct {
    default Client maptoClient(ClientDto clientDto){
        Client c = null;
        if(clientDto instanceof LegalClientDto){
            c =  mapToLegalClient((LegalClientDto)clientDto);
        }else if(clientDto instanceof PersonalClientDto){
            c = mapToPersonalClient((PersonalClientDto) clientDto);
        }
        return c;
    }
    default ClientDto maptoClientDto(Client client){
        ClientDto c = null;
        if(client instanceof LegalClient){
            c = mapToLegalClientDto((LegalClient) client);
        }else if(client instanceof PersonalClient){
            c = mapToPersonalClientDto((PersonalClient) client);
        }
        return c;
    }
    default void updateClientFromDto(ClientDto clientDto, Client client){
        Client c = null;
        if(clientDto instanceof LegalClientDto){
            c =  updateLegalClientFromDto((LegalClientDto) clientDto , (LegalClient) client);
        }else if(clientDto instanceof PersonalClientDto){
            c = updatePersonalClientFromDto((PersonalClientDto) clientDto , (PersonalClient) client);
        }
    }
    @Mapping(ignore = true , target = "id")
    PersonalClient mapToPersonalClient(PersonalClientDto personalClient);
    @Mapping(ignore = true , target = "password")
    PersonalClientDto mapToPersonalClientDto(PersonalClient personalClient);
    @Mapping(ignore = true , target = "password")
    LegalClientDto mapToLegalClientDto(LegalClient legalClient);
    @Mapping(ignore = true , target = "id")
    LegalClient mapToLegalClient(LegalClientDto legalClient);
    @Mapping(ignore = true , target = "id")
    PersonalClient updatePersonalClientFromDto(PersonalClientDto personalClientDto, @MappingTarget PersonalClient client);
    @Mapping(ignore = true , target = "id")
    LegalClient updateLegalClientFromDto(LegalClientDto legalClientDto, @MappingTarget LegalClient legalClient);
    List<Client> mapToClientList(List<ClientDto> clientDtoList);
    List<ClientDto> mapToClientDtoList(List<Client> clientList);


}
