package com.javasSE.banking.clientService.service;

import com.javasSE.banking.clientService.clientException.DuplicateClientException;
import com.javasSE.banking.common.model.DocFile;
import com.javasSE.banking.common.model.FileType;
import com.javasSE.banking.clientService.model.Client;
import com.javasSE.banking.common.exception.FileException;
import com.javasSE.banking.common.exception.ValidationException;
import java.io.FileNotFoundException;
import java.util.List;

public interface IClientService {
    void addClient(Client client) throws DuplicateClientException, ValidationException;
    <T> Client getClient(T clientDetail);
    Client getClientById(int clientId);
    Client getClientByName(String clientName);
    void updateClientList(int clientId, Client newClient);
    void deleteClientById(int cliendId);
    void saveData(DocFile file) throws FileException;
    void loadData(FileType type) throws FileException, FileNotFoundException;
    void initData();
    void saveOnExit() throws FileException;
    void addData(String name) throws FileNotFoundException;
    List<Client> getAllClients();
    void loadData(DocFile file) throws FileNotFoundException;
    List<Client> getAllDeletedClients();
}
