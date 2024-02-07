package com.javase.banking.service.client;

import com.javase.banking.model.client.Client;
import com.javase.banking.model.DocFile;
import com.javase.banking.model.FileType;
import com.javase.banking.service.exception.clientexception.DuplicateClientException;
import com.javase.banking.service.exception.FileException;
import com.javase.banking.service.exception.ValidationException;
import java.io.FileNotFoundException;

public interface IClientService {
    void addClient(Client client) throws DuplicateClientException, ValidationException;
    <T> Client getClient(T clientDetail);
    Client getClientById(int clientId);
    Client getClientByName(String clientName);
    void updateClientList(int clientId, Client newClient);
    void deleteClientById(int cliendId);
    void printAllClients();
    void saveData(DocFile file) throws FileException;
    void loadData(FileType type) throws FileException, FileNotFoundException;
    void initData();
    void saveOnExit() throws FileException;
    void addData(String name) throws FileNotFoundException;

    void loadData(DocFile file) throws FileNotFoundException;
}
