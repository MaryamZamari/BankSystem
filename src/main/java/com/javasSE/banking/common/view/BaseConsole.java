package com.javasSE.banking.common.view;

import com.javasSE.banking.accountService.facade.AccountFacade;
import com.javasSE.banking.clientService.clientFacade.ClientFacade;
import com.javasSE.banking.common.utility.FileIOUtil;
import com.javasSE.banking.common.utility.ScannerWrapperUtil;

import java.io.File;

public abstract class BaseConsole {
    protected final ScannerWrapperUtil scannerWrapper;
    protected final ClientFacade clientFacade;
    protected final AccountFacade accountFacade;
    protected final FileIOUtil fileIO;
    protected BaseConsole(){
        clientFacade = ClientFacade.getInstance();
        accountFacade = AccountFacade.getInstance();
        scannerWrapper= ScannerWrapperUtil.getInstance();
        fileIO = FileIOUtil.getInstance();
    }


}
