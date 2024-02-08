package com.javase.banking.clientservice.clientfacade;

import com.javase.banking.clientservice.dto.ClientDto;
import com.javase.banking.shared.model.DocFile;
import com.javase.banking.shared.model.FileType;
import com.javase.banking.clientservice.clientexception.DuplicateClientException;
import com.javase.banking.shared.exception.FileException;
import com.javase.banking.shared.exception.ValidationException;
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
