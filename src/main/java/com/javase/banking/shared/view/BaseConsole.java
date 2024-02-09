package com.javase.banking.shared.view;

import com.javase.banking.accountservice.facade.AccountFacade;
import com.javase.banking.clientservice.clientfacade.ClientFacade;
import com.javase.banking.shared.utility.ScannerWrapperUtil;

public abstract class BaseConsole {
    protected final ScannerWrapperUtil scannerWrapper;
    protected final ClientFacade clientFacade;
    protected final AccountFacade accountFacade;
    protected BaseConsole(){
        clientFacade = ClientFacade.getInstance();
        accountFacade = AccountFacade.getInstance();
        scannerWrapper= ScannerWrapperUtil.getInstance();
    }


}
