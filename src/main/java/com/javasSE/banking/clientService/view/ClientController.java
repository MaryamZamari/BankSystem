package com.javasSE.banking.clientService.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javasSE.banking.clientService.model.Client;
import com.javasSE.banking.common.model.FileType;
import com.javasSE.banking.clientService.clientException.ClientNotFoundException;
import com.javasSE.banking.clientService.dto.ClientDto;
import com.javasSE.banking.clientService.clientFacade.ClientFacade;
import com.javasSE.banking.clientService.clientFacade.IClientFacade;
import com.javasSE.banking.common.model.DocFile;
import com.javasSE.banking.clientService.clientException.DuplicateClientException;
import com.javasSE.banking.common.exception.FileException;
import com.javasSE.banking.common.exception.ValidationException;
import com.javasSE.banking.common.utility.ScannerWrapperUtil;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.List;

/***
 *
 *     interacting with the services and the View. View is used to interact with the
 *     User and Get the inputs from the user, prepare the Outputs needed for
 *     the Service classes.
 *
 */
public class ClientController{
    private final IClientFacade clientFacade;
    private final ClientConsole view;
    private final ScannerWrapperUtil scannerWrapper;
    private static final ClientController INSTANCE;
    private ClientController(){
        scannerWrapper = ScannerWrapperUtil.getInstance();
        clientFacade = ClientFacade.getInstance();
        view = ClientConsole.getInstance();
    }
    static{
        INSTANCE= new ClientController();
    }
    public static ClientController getInstance() {
        return INSTANCE;
    }

    public void run(){
        clientFacade.initData();
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveOnExit));
        try{
            int choice = 0;
            do{
                view.printClientMenu();
                System.out.println();
                choice = scannerWrapper.getUserInput("Enter your choice: " , Integer::valueOf);
                switch(choice){
                    case 1:
                        ClientDto newClient= view.getClientDetailsFromUser();
                        addClient(newClient);
                        break;
                    case 2:
                        Object clientDetailToSearch= null;
                        try{
                            clientDetailToSearch= view.getClientDetailForSelection();}
                        catch(InvalidParameterException exception){
                            System.out.println("you typed the wrong characters. " +
                                                 "revise your choice to select the client!");
                             }
                        searchClient(clientDetailToSearch);
                        break;
                    case 3:
                        int clientId= view.getIdFromUser();
                        ClientDto oldClient= clientFacade.getClientById(clientId);
                        ClientDto updatedClient = view.getClientInfoFromUserForEdit(oldClient);
                        updateClient(clientId, updatedClient);
                        break;
                    case 4:
                        clientId = view.getIdFromUser();
                        deleteClient(clientId);
                        break;
                    case 5:
                        getAllClients();
                        break;
                    case 6:
                        printAllDeletedClients();
                        break;
                    case 7:
                        System.out.println("Print accounts of client");
                        break;
                    case 8:
                        DocFile file = view.getFileTypeFromUser();
                        saveData(file);
                        break;
                    case 9:
                        file= view.getFileTypeFromUser();
                        loadData(file.getType());
                        break;
                    case 10:
                        view.addData();
                        break;
                    default:
                        if(choice != 0){
                            System.out.println("the selected number is invalid. try again! \n");
                        }
                }
            }while(choice != 0);
        }catch(ParseException ex){
            System.out.println("could not parse the String to produce a Date. check your input or the code! \n");
        }catch(NumberFormatException ex) {
            throw new NumberFormatException("Error: or you entered a character where a number was expected \n");
        } catch (DuplicateClientException e) {
           e.getMessage();
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        } catch (FileException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ClientNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void printAllDeletedClients() {
        System.out.println("All deleted customers: \n");
        List<ClientDto> allClients = clientFacade.getAllDeletedClients();
        allClients.stream().forEach(System.out::println);
    }

    public void saveOnExit() {
        clientFacade.saveOnExit();
    }

    private void loadData(FileType type) throws FileException, FileNotFoundException {
        clientFacade.loadData(type);
        System.out.println("received a command to load the saved data into the system!");
    }

    private void saveData(DocFile file) throws FileException {
        clientFacade.saveData(file);
        System.out.println("data saved successfully!");
    }

    public void addClient(ClientDto client) throws DuplicateClientException, ValidationException {
        try {
            clientFacade.addClient(client);
            System.out.println("Client was added successfully.");
        }catch(DuplicateClientException exception){
            System.out.println("it is not possible to add a duplicate customer!" +
                    " check surname or business person name!");
            clientFacade.addClient(client);
        }catch(ValidationException e){
            System.out.println(e.getMessage());
            clientFacade.addClient(client);
        }
    }
    public ClientDto searchClient(Object clientDetailToSearch) throws ClientNotFoundException {
       return clientFacade.getClient(clientDetailToSearch);
    }

    public void deleteClient(int id) throws ClientNotFoundException {
        clientFacade.deleteClientById(id);
    }

    public void updateClient(int id, ClientDto newClient) throws ValidationException, ClientNotFoundException {
        clientFacade.updateClient(id, newClient);
    }
    public void getAllClients() {
        System.out.println("All customers: \n");
        List<ClientDto> allClients = clientFacade.getAllClients();
        allClients.stream().forEach(System.out::println);
        }

    public void initData() {
    }
}
