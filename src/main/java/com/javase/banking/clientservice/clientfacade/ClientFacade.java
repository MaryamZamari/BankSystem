package com.javase.banking.clientservice.clientfacade;

import com.javase.banking.clientservice.dto.ClientDto;
import com.javase.banking.clientservice.clientvalidation.ClientValidationContext;
import com.javase.banking.shared.validation.ValidationContext;
import com.javase.banking.clientservice.mapper.IClientMapStruct;
import com.javase.banking.clientservice.model.Client;
import com.javase.banking.shared.model.DocFile;
import com.javase.banking.shared.model.FileType;
import com.javase.banking.clientservice.service.ClientService;
import com.javase.banking.clientservice.clientexception.DuplicateClientException;
import com.javase.banking.shared.exception.FileException;
import com.javase.banking.shared.exception.ValidationException;
import org.mapstruct.factory.Mappers;
import java.io.FileNotFoundException;

public class ClientFacade implements IClientFacade {
    /*
        client will interact with facade instead of the service. and facade takes and returns DTOs.
     */
    private static final ClientFacade INSTANCE;
    static{
        INSTANCE= new ClientFacade();
    }
    public static ClientFacade getInstance(){
        return INSTANCE;
    }
    private final ClientService clientService;
    private final IClientMapStruct clientMapStruct;
    private ValidationContext<ClientDto> validationContext= new ClientValidationContext();
    private ClientFacade(){
        clientService = ClientService.getInstance();
        clientMapStruct= Mappers.getMapper(IClientMapStruct.class);
    }


    @Override
    public void addClient(ClientDto client) throws DuplicateClientException, ValidationException {
        validationContext.validate(client);
        clientService
                .addClient(clientMapStruct.maptoClient(client));
    }

    @Override
    public <T> ClientDto getClient(T clientDetail) {
        return
                clientMapStruct
                        .maptoClientDto(clientService.getClient(clientDetail));
    }

    @Override
    public ClientDto getClientById(int clientId) {
        return clientMapStruct
                .maptoClientDto(clientService.getClientById(clientId));
    }

    @Override
    public ClientDto getClientByName(String clientName) {
        return clientMapStruct
                .maptoClientDto(clientService.getClientByName(clientName));
    }

    @Override
    public void updateClient(int id, ClientDto newClientDto) throws ValidationException { //TODO: you can even delete this id. control again.
        validationContext.validate(newClientDto);
        Client client= clientService.getClientById(id);
        clientMapStruct.updateClientFromDto(newClientDto, client);
        clientService.updateClientList(id, client);
    }

    @Override
    public void deleteClientById(int cliendId) {
        clientService.deleteClientById(cliendId);
    }

    @Override
    public void printAllClients() {
        clientService.printAllClients();
    }

    @Override
    public void saveData(DocFile file) throws FileException {
        clientService.saveData(file);
    }

    @Override
    public void loadData(FileType type) throws FileException, FileNotFoundException {
        clientService.loadData(type);
           }

    @Override
    public void initData() {
        clientService.initData();
    }

    @Override
    public void saveOnExit() {
        clientService.saveOnExit();
    }

    @Override
    public void addData(String name) {
        try {
            clientService.addData(name);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
