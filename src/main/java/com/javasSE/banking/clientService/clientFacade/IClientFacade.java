package com.javasSE.banking.clientService.clientFacade;

import com.javasSE.banking.clientService.clientException.ClientNotFoundException;
import com.javasSE.banking.clientService.clientException.DuplicateClientException;
import com.javasSE.banking.clientService.dto.ClientDto;
import com.javasSE.banking.common.model.DocFile;
import com.javasSE.banking.common.model.FileType;
import com.javasSE.banking.common.exception.FileException;
import com.javasSE.banking.common.exception.ValidationException;
import java.io.FileNotFoundException;

public interface IClientFacade {
    void addClient(ClientDto client) throws DuplicateClientException, ValidationException;
    <T> ClientDto getClient(T clientDetail)throws ClientNotFoundException;
    ClientDto getClientById(int clientId)throws ClientNotFoundException;
    ClientDto getClientByName(String clientName)throws ClientNotFoundException;
    void updateClient(int id, ClientDto newClient) throws ValidationException, ClientNotFoundException;
    void deleteClientById(int cliendId)throws ClientNotFoundException;
    void printAllClients();
    void saveData(DocFile file) throws FileException;
    void loadData(FileType type) throws FileException, FileNotFoundException;
    void initData();
    void saveOnExit();
    void addData(String name) throws FileNotFoundException;


}
