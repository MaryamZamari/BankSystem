package com.javase.banking.clientservice.view;

import com.javase.banking.clientservice.dto.ClientDto;
import com.javase.banking.clientservice.clientfacade.ClientFacade;
import com.javase.banking.clientservice.clientfacade.IClientFacade;
import com.javase.banking.shared.model.DocFile;
import com.javase.banking.shared.model.FileType;
import com.javase.banking.clientservice.clientexception.DuplicateClientException;
import com.javase.banking.shared.exception.FileException;
import com.javase.banking.shared.exception.ValidationException;

import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import static java.lang.Integer.parseInt;

/***
 *
 *     interacting with the services and the View. View is used to interact with the
 *     User and Get the inputs from the user, prepare the Outputs needed for
 *     the Service classes.
 *
 */
public class ClientController{
    private static final IClientFacade clientFacade= ClientFacade.getInstance();
    private static final ClientConsole view = new ClientConsole();
    private static AbstractCustomerUI view1;

    public void run(){
        clientFacade.initData();
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveOnExit));
        try(Scanner input= new Scanner(System.in)){
            int choice = 0;
            do{
                view.printClientMenu();
                System.out.println();
                choice= parseInt(input.nextLine());
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
                            System.out.println("you typed the wrong characters. revise your choice to select the client!");
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
                        printAllClients();
                        break;
                    case 7:
                        DocFile file = view.getFileTypeFromUser();
                        saveData(file);
                        break;
                    case 8:
                        file= view.getFileTypeFromUser();
                        loadData(file.getType());
                        break;
                    case 9:
                        view.addData();
                    default:
                        if(choice != 0){
                            System.out.println("the selected number is invalid. try again!");
                        }
                }
            }while(choice != 0);
        }catch(ParseException ex){
            ex.printStackTrace();
            System.out.println("could not parse the String to produce a Date. check your input or the code!");
        }catch(NumberFormatException ex){
            ex.printStackTrace();
            System.out.println("Error: " + ex.getMessage());
        }catch(InputMismatchException ex){
            System.out.println("invalid input.please enter a valid output");
        } catch (DuplicateClientException e) {
           e.getMessage();
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        } catch (FileException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void saveOnExit() {
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
        }catch(DuplicateClientException exception){
            System.out.println("it is not possible to add a duplicate customer! check surname or business person name!");
            clientFacade.addClient(client);
        }catch(ValidationException e){
            System.out.println(e.getMessage());
            clientFacade.addClient(client);
        }
    }
    public static ClientDto searchClient(Object clientDetailToSearch) {
        return clientFacade.getClient(clientDetailToSearch);
    }

    public void deleteClient(int id){
        clientFacade.deleteClientById(id);
    }

    public void updateClient(int id, ClientDto newClient) throws ValidationException {
        clientFacade.updateClient(id, newClient);
    }
    public void printAllClients(){
        clientFacade.printAllClients();
    }




}
