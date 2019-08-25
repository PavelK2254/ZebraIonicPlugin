package com.pk.testapp;

import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.comm.TcpConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.zebra.sdk.graphics.ZebraImageI;
import com.zebra.sdk.graphics.ZebraImageFactory;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

import java.io.IOException;

public class ZplImagePrinter {

  private ZebraImageI getZebraImageFromBitmap(byte[] bitmapByteArray) throws IOException {
      Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapByteArray,0,bitmapByteArray.length);
      return ZebraImageFactory.getImage(bitmap);
  }

  public void printImage(final String ipAddress, byte[] bitmapByteArray) throws ConnectionException, ZebraPrinterLanguageUnknownException, IOException {
      Connection connection = new TcpConnection(ipAddress, TcpConnection.DEFAULT_ZPL_TCP_PORT);
      connection.open();
      ZebraPrinter printer = ZebraPrinterFactory.getInstance(connection);
      if(connection.isConnected()){
          int x = 0;
          int y = 0;
          printer.printImage(getZebraImageFromBitmap(bitmapByteArray),x,y,0,0,false);
          connection.close();
      }
  }
}
