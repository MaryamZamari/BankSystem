package com.javasSE.banking.clientService.clientFacade;

import com.javasSE.banking.clientService.clientException.ClientNotFoundException;
import com.javasSE.banking.clientService.clientException.DuplicateClientException;
import com.javasSE.banking.clientService.clientValidation.ClientValidationContext;
import com.javasSE.banking.clientService.dto.ClientDto;
import com.javasSE.banking.common.model.DocFile;
import com.javasSE.banking.common.model.FileType;
import com.javasSE.banking.common.validation.ValidationContext;
import com.javasSE.banking.clientService.mapper.IClientMapStruct;
import com.javasSE.banking.clientService.model.Client;
import com.javasSE.banking.clientService.service.ClientService;
import com.javasSE.banking.common.exception.FileException;
import com.javasSE.banking.common.exception.ValidationException;
import org.mapstruct.factory.Mappers;

import java.io.FileNotFoundException;
import java.util.List;

public class ClientFacade implements IClientFacade {
    /*
        client will interact with facade instead of the service. and facade takes and returns DTOs.
     */
    private static final ClientFacade INSTANCE;

    static {
        INSTANCE = new ClientFacade();
    }

    public static ClientFacade getInstance() {
        return INSTANCE;
    }

    private final ClientService clientService;
    private final IClientMapStruct clientMapStruct;
    private ValidationContext<ClientDto> validationContext = new ClientValidationContext();

    private ClientFacade() {
        clientService = ClientService.getInstance();
        clientMapStruct = Mappers.getMapper(IClientMapStruct.class);
    }


    @Override
    public void addClient(ClientDto client) throws DuplicateClientException, ValidationException {
        validationContext.validate(client);
        clientService
                .addClient(clientMapStruct.maptoClient(client));
    }

    @Override
    public <T> ClientDto getClient(T clientDetail) throws ClientNotFoundException {
        return
                clientMapStruct
                        .maptoClientDto(clientService.getClient(clientDetail));
    }

    @Override
    public ClientDto getClientById(int clientId) throws ClientNotFoundException {
        return clientMapStruct
                .maptoClientDto(clientService.getClientById(clientId));
    }

    @Override
    public ClientDto getClientByName(String clientName) throws ClientNotFoundException {
        return clientMapStruct
                .maptoClientDto(clientService.getClientByName(clientName));
    }

    @Override
    public void updateClient(int id, ClientDto newClientDto) throws ValidationException {
        validationContext.validate(newClientDto);
        Client client = clientService.getClientById(id);
        clientMapStruct.updateClientFromDto(newClientDto, client);
        clientService.updateClientList(id, client);
    }

    @Override
    public void deleteClientById(int cliendId) {
        clientService.deleteClientById(cliendId);
    }

    @Override
    public List<ClientDto> getAllClients() {
        return
                clientMapStruct.mapToClientDtoList(clientService.getAllClients());
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

    @Override
    public List<ClientDto> getAllDeletedClients() {
        return clientMapStruct
                .mapToClientDtoList(clientService.getAllDeletedClients());
    }


}
