package com.javasSE.banking.clientService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasSE.banking.clientService.clientException.DuplicateClientException;
import com.javasSE.banking.clientService.clientFacade.ClientFacade;
import com.javasSE.banking.clientService.clientFacade.IClientFacade;
import com.javasSE.banking.common.model.DocFile;
import com.javasSE.banking.common.model.FileType;
import com.javasSE.banking.clientService.model.Client;
import com.javasSE.banking.clientService.model.LegalClient;
import com.javasSE.banking.clientService.model.PersonalClient;
import com.javasSE.banking.common.exception.FileException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientService implements IClientService {
    private final IClientFacade clientFacade;
    private static final ClientService INSTANCE;
    private final ObjectMapper objectMapper;
    private static List<Client> clientList;
    static {
        INSTANCE = new ClientService();
    }
    private ClientService(){
        objectMapper = new ObjectMapper();
        clientFacade= ClientFacade.getInstance();
        clientList = new ArrayList<>();
    }
    public static ClientService getInstance() {
        return INSTANCE;
    }
    public static List<Client> getClientList() {
        return clientList;
    }

    @Override
    public void addClient(Client client) throws DuplicateClientException {
        Optional<Client> duplicateClient = clientList.stream()
                                            .filter(isDuplicate(client)).findFirst();
        if (duplicateClient.isPresent()) {
            throw new DuplicateClientException("it is not possible to add duplicate client! " +
                                                "check again the name and surname " +
                                                "or name and contact person's name!");
        }
        clientList.add(client);
        System.out.println("Client id: " + client.getId());
    }

    private static Predicate<Client> isDuplicate(Client client) {
        return x -> {
            if (!x.getName().equals(client.getName())) return false;
            if (!isPersonalDuplicate(client, (PersonalClient) x)) {
                isLegalDuplicate(client, (LegalClient) x);
            }
            return true;
        };
    }

    private static boolean isLegalDuplicate(Client client, LegalClient x) {
        return client instanceof LegalClient &&
                        x.getContactPerson().equals(((LegalClient) client).getContactPerson());
    }

    private static boolean isPersonalDuplicate(Client client, PersonalClient x) {
        return client instanceof PersonalClient &&
                x.getSurname().equals(((PersonalClient) client).getSurname());
    }
    @Override
    public <T> Client getClient(T clientDetail) {
        Client selectedClient = null;
        if (clientDetail instanceof Integer) {
            selectedClient = getClientById((Integer) clientDetail);
        } else if (clientDetail instanceof String) {
            selectedClient = getClientByName((String) clientDetail);
        }
        System.out.println("The searched client is: " + selectedClient.toString());
        return selectedClient;
    }
    @Override
    public Client getClientById(int clientId) {
        Stream<Client> client = getClientList().stream()
                .filter((x) -> !x.isDeleted())
                .filter(x -> x.getId().equals(clientId));
        return client.findFirst().orElseThrow(() ->
                new NoSuchElementException("there is no client with the Id " + clientId));
    }
    @Override
    public Client getClientByName(String clientName) {
        Optional<Client> optionalClient = getClientList().stream()
                .filter((x) -> !x.isDeleted())
                .filter(x -> x.getName().equalsIgnoreCase(clientName)).findFirst();
        return optionalClient.orElseThrow(() ->
                new NoSuchElementException("there is no client with the Name " + clientName));
    }
    @Override
    public void updateClientList(int clientId, Client newClient) {
        Client c = getClientList().stream()
                        .filter((x) -> !x.isDeleted())
                        .filter(x -> x.getId() == clientId)
                        .findFirst().orElseThrow(() ->
                        new NoSuchElementException("The client with the Id"
                                + clientId + "does not exist!Try again!"));
        int index = getClientList().indexOf(c);
        getClientList().set(index, newClient);
        System.out.println("Modification was done! The updated client is: " + c.toString());
    }
    @Override
    public void deleteClientById(int cliendId) {
        Client clientToDelete = getClientById(cliendId);
        clientToDelete.setDeleted(true);
        System.out.println("ClientDto removed successfully! The updated list is: " + getClientList().toString());
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> allClients=  getClientList().stream()
                .filter(x -> !x.isDeleted())
                .collect(Collectors.toList());
        return allClients;
    }

    @Override
    public List<Client> getAllDeletedClients() {
        return getClientList().stream()
                .filter(Client::isDeleted)
                .collect(Collectors.toList());
    }

    @Override
    public void saveData(DocFile file) throws FileException {
        FileType type= file.getType();
        String fileName= file.getName();
        switch (type){
            case SERIALISED -> saveSerialised(fileName);
            case JSON -> saveJson(fileName);
        }
    }

    @Override
    public void loadData(FileType type) throws FileException, FileNotFoundException {

    }

    @Override
    public void initData() {
        try{
            loadJson("initData");
        } catch(FileNotFoundException ignored){
        }
    }

    @Override
    public void saveOnExit(){
        saveJson("initData");
    }

    @Override
    public void addData(String fileName) throws FileNotFoundException {
        try{
            clientList= objectMapper.readValue(new File(fileName + ".jason"),
                    new TypeReference<List<Client>>() { });
        } catch (FileNotFoundException exception){
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveJson(String fileName) {
          try{
            File file= new File(fileName + ".json");
            if(!file.exists()){
                file.createNewFile();
            }
            objectMapper.writeValue(file, clientList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveSerialised(String fileName) throws FileException {
        try{
            File file= new File(fileName + ".crm");
            if(!file.exists()){
                file.createNewFile();
            }
            try(FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);){
                objectOutputStream.writeObject(clientList);
            }
        }catch(IOException exception){
            throw new FileException();
        }
    }

    @Override
    public void loadData(DocFile file) throws FileNotFoundException {
        FileType type= file.getType();
        String fileName= file.getName();
        switch (type){
            case SERIALISED -> loadSerialised(fileName);
            case JSON -> loadJson(fileName);
        }
    }

    private void loadJson(String fileName) throws FileNotFoundException {
        try{
            clientList= objectMapper.readValue(new File(fileName + ".jason"),
                    new TypeReference<List<Client>>() { }); //to give it a more specific object
        } catch (FileNotFoundException exception){
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSerialised(String fileName) throws FileNotFoundException {
        try(FileInputStream fileInputStream = new FileInputStream(fileName)){
            ObjectInputStream objectInputStream= new ObjectInputStream(fileInputStream);
            clientList= (List<Client>) objectInputStream.readObject();
        } catch (FileNotFoundException exception){
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
