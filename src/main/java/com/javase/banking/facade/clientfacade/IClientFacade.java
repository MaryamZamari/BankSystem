package com.javase.banking.facade.clientfacade;

import com.javase.banking.dto.client.ClientDto;
import com.javase.banking.model.DocFile;
import com.javase.banking.model.FileType;
import com.javase.banking.service.exception.clientexception.DuplicateClientException;
import com.javase.banking.service.exception.FileException;
import com.javase.banking.service.exception.ValidationException;
import java.io.FileNotFoundException;

public interface IClientFacade {
    void addClient(ClientDto client) throws DuplicateClientException, ValidationException;
    <T> ClientDto getClient(T clientDetail);
    ClientDto getClientById(int clientId);
    ClientDto getClientByName(String clientName);
    void updateClient(int id, ClientDto newClient) throws ValidationException;
    void deleteClientById(int cliendId);
    void printAllClients();
    void saveData(DocFile file) throws FileException;
    void loadData(FileType type) throws FileException, FileNotFoundException;
    void initData();
    void saveOnExit();
    void addData(String name) throws FileNotFoundException;
}
