package com.javase.banking.mapper;

import com.javase.banking.dto.client.ClientDto;
import com.javase.banking.dto.client.LegalClientDto;
import com.javase.banking.dto.client.PersonalClientDto;
import com.javase.banking.model.client.Client;
import com.javase.banking.model.client.LegalClient;
import com.javase.banking.model.client.PersonalClient;
import org.mapstruct.Mapper;
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
    PersonalClient mapToPersonalClient(PersonalClientDto personalClient);
    PersonalClientDto mapToPersonalClientDto(PersonalClient personalClient);
    LegalClientDto mapToLegalClientDto(LegalClient legalClient);
    LegalClient mapToLegalClient(LegalClientDto legalClient);
    PersonalClient updatePersonalClientFromDto(PersonalClientDto personalClientDto, @MappingTarget PersonalClient client);
    LegalClient updateLegalClientFromDto(LegalClientDto legalClientDto, @MappingTarget LegalClient legalClient);
    List<Client> mapToClientList(List<ClientDto> clientDtoList);
    List<ClientDto> mapToClientDtoList(List<Client> clientList);


}
