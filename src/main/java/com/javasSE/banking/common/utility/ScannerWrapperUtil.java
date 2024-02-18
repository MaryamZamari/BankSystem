package com.javasSE.banking.common.utility;

import java.util.Scanner;
import java.util.function.Function;

public class ScannerWrapperUtil implements AutoCloseable{
    //creating a singleton for scanner
    private static final ScannerWrapperUtil INSTANCE;
    private final Scanner scanner;
    public static ScannerWrapperUtil getInstance(){
        return INSTANCE;
    }
    static{
        INSTANCE= new ScannerWrapperUtil();
    }

    private ScannerWrapperUtil(){
        scanner= new Scanner(System.in);
    }
    public <T> T getUserInput(String message, Function<String, T> converter) {
        System.out.println(message);
        T input;
        try {
            input = converter.apply(scanner.nextLine());
        } catch (Exception ex) {
            input= getUserInput(message, converter);
        }
        return input;
    }


    @Override
    public void close(){
        scanner.close();
    }


}
