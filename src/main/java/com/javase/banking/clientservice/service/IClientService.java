package com.javase.banking.clientservice.service;

import com.javase.banking.clientservice.model.Client;
import com.javase.banking.shared.model.DocFile;
import com.javase.banking.shared.model.FileType;
import com.javase.banking.clientservice.clientexception.DuplicateClientException;
import com.javase.banking.shared.exception.FileException;
import com.javase.banking.shared.exception.ValidationException;
import java.io.FileNotFoundException;
import java.util.List;

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
    List<Client> getAllClients();
    void loadData(DocFile file) throws FileNotFoundException;
}
