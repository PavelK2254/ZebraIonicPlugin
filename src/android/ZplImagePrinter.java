package com.pk.testapp;

import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.comm.TcpConnection;
import com.zebra.sdk.graphics.ZebraImageI;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

import java.io.IOException;

public class ZplImagePrinter {

    public void printImage(final String ipAddress,ZebraImageI zebraImageI) throws ConnectionException, ZebraPrinterLanguageUnknownException, IOException {
        Connection connection = new TcpConnection(ipAddress, TcpConnection.DEFAULT_ZPL_TCP_PORT);
        connection.open();
        ZebraPrinter printer = ZebraPrinterFactory.getInstance(connection);
        if(connection.isConnected()){
            int x = 0;
            int y = 0;
            printer.printImage(zebraImageI,x,y,0,0,false);
            connection.close();
        }
    }
}
