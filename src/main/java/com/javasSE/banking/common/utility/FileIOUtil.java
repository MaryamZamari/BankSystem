package com.javasSE.banking.common.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasSE.banking.clientService.model.Client;
import com.javasSE.banking.common.exception.FileException;
import com.javasSE.banking.common.model.DocFile;
import com.javasSE.banking.common.model.FileType;

import java.io.*;
import java.util.List;
import java.util.function.Function;


/***
 *  File and saving related methods ===========
 */
public class FileIOUtil<T> {
    private List<T> list;
    private final ObjectMapper objectMapper;
    //SINGLETON implementation for FileIOUtil
    // 1. The private static instance
    private static final FileIOUtil INSTANCE = new FileIOUtil();

    // 2. Reference to the other singleton
    private final ScannerWrapperUtil scannerWrapper;

    // 3. PRIVATE constructor
    private FileIOUtil() {
        this.objectMapper = new ObjectMapper();
        this.scannerWrapper = ScannerWrapperUtil.getInstance();
    }

    // 4. Global access point
    public static FileIOUtil getInstance() {
        return INSTANCE;
    }


    public DocFile getFileDetailsFromUser() {
        DocFile file;
        FileType fileType = getFileType();
        String fileName = getFileNameFromUser();
        file = new DocFile(fileName, fileType);
        return file;
    }

    private FileType getFileType() {
        char type = scannerWrapper.getUserInput("what type of File? " +
                        "S: Serialised,  " +
                        "J: JSON. ",
                x -> {
                    try {
                        return x.toUpperCase().charAt(0);
                    } catch (IllegalStateException exception) {
                        System.out.println("You entered a wrong character by mistake, Enter a character from the menu");
                        throw new RuntimeException();
                    }
                });
        return switch (type) {
            case 'S' -> FileType.SERIALISED;
            case 'J' -> FileType.JSON;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }


    public String getFileNameFromUser() {
        return scannerWrapper
                .getUserInput("Enter the name of the file: ", Function.identity());
    }


    public void addData(String fileName) throws FileNotFoundException {
        try{
            list= objectMapper.readValue(new File(fileName + ".jason"),
                    new TypeReference<List<T>>() { });
        } catch (FileNotFoundException exception){
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveJson(String fileName) {
        try{
            File file= new File(fileName + ".json");
            if(!file.exists()){
                file.createNewFile();
            }
            objectMapper.writeValue(file, list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveSerialised(String fileName) throws FileException {
        try{
            File file= new File(fileName + ".crm");
            if(!file.exists()){
                file.createNewFile();
            }
            try(FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);){
                objectOutputStream.writeObject(list);
            }
        }catch(IOException exception){
            throw new FileException();
        }
    }


    public void loadData(DocFile file) throws FileNotFoundException {
        FileType type= file.getType();
        String fileName= file.getName();
        switch (type){
            case SERIALISED -> loadSerialised(fileName);
            case JSON -> loadJson(fileName);
        }
    }

    public void loadJson(String fileName) throws FileNotFoundException {
        try{
            list= objectMapper.readValue(new File(fileName + ".json"),
                    new TypeReference<List<T>>() { }); //to give it a more specific object
        } catch (FileNotFoundException exception){
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadSerialised(String fileName) throws FileNotFoundException {
        try(FileInputStream fileInputStream = new FileInputStream(fileName)){
            ObjectInputStream objectInputStream= new ObjectInputStream(fileInputStream);
            list= (List<T>) objectInputStream.readObject();
        } catch (FileNotFoundException exception){
            throw new FileNotFoundException();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
