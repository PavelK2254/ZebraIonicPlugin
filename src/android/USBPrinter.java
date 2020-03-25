package cordova.zebra.plugin;

import android.content.Context;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.discovery.DiscoveredPrinter;
import com.zebra.sdk.printer.discovery.DiscoveredPrinterUsb;
import com.zebra.sdk.printer.discovery.DiscoveryHandler;
import com.zebra.sdk.printer.discovery.UsbDiscoverer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.zebra.sdk.graphics.ZebraImageFactory;
import com.zebra.sdk.graphics.ZebraImageI;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;
import java.io.IOException;

import java.util.LinkedList;
import java.util.List;

 class USBPrinter {

   private DiscoveredPrinterUsb discoveredPrinterUsb;

       public void findPrinters(Context context,final byte[] message) throws RuntimeException{
                   // Find connected printers
                   UsbDiscoveryHandler handler = new UsbDiscoveryHandler();
                   UsbDiscoverer.findPrinters(context.getApplicationContext(), handler);

                   try {
                       while (!handler.discoveryComplete) {
                           Thread.sleep(100);
                       }

                       if (handler.printers != null && handler.printers.size() > 0) {
                           discoveredPrinterUsb = handler.printers.get(0);
                           printOverUSB(message);

                       }else{
                        throw new RuntimeException("Could not find printers");
                       }
                   } catch (Exception e) {
                       throw new RuntimeException("Error discovering printers: " + e.getLocalizedMessage());
                   }
       }

       private ZebraImageI getZebraImageFromBitmap(byte[] bitmapByteArray) throws IOException {
           Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapByteArray,0,bitmapByteArray.length);
           return ZebraImageFactory.getImage(bitmap);
       }

       private void printOverUSB(byte[] bitmapByteArray) throws ConnectionException, ZebraPrinterLanguageUnknownException, IOException {
               Connection connection = null;
                   connection = discoveredPrinterUsb.getConnection();
                   connection.open();
                   ZebraPrinter printer = ZebraPrinterFactory.getInstance(connection);
                   if(connection.isConnected()){
                       int x = 10;
                       int y = 10;
                       int width = 0;
                       int heigth = 0;
                       printer.printImage(getZebraImageFromBitmap(bitmapByteArray),x,y,width,heigth,false);
                       connection.close();
                   }
       }


  private class UsbDiscoveryHandler implements DiscoveryHandler {
          public List<DiscoveredPrinterUsb> printers;
          public boolean discoveryComplete = false;

          public UsbDiscoveryHandler() {
              printers = new LinkedList<DiscoveredPrinterUsb>();
          }

          public void foundPrinter(final DiscoveredPrinter printer) {
              printers.add((DiscoveredPrinterUsb) printer);
          }

          public void discoveryFinished() {
              discoveryComplete = true;
          }

          public void discoveryError(String message) {
              discoveryComplete = true;
          }
      }
}
