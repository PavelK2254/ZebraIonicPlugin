package cordova.zebra.plugin;

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

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class USBPrinter extends ZebraPlugin {

    public void findPrinters(Context context, final byte[] message) throws RuntimeException {
        // Find connected printers
        UsbManager mUsbManager;
        PendingIntent mPermissionIntent;
        UsbDiscoveryHandler handler = new UsbDiscoveryHandler();
        String ACTION_USB_PERMISSION = "com.pk.zebraprintusb.USB_PERMISSION";
        mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        UsbDiscoverer.findPrinters(context.getApplicationContext(), handler);
        mPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
        try {
            while (!handler.discoveryComplete) {
                Thread.sleep(100);
            }

            if (handler.printers != null && handler.printers.size() > 0) {
                if (mUsbManager.hasPermission(handler.printers.get(0).device)) {
                    try {
                        printOverUSB(message,handler.printers.get(0));
                    } catch (Exception e) {
                        throw new RuntimeException("Zebra plugin exception: " + Arrays.toString(e.getStackTrace()));
                    }

                }else{
                    // throw new RuntimeException("No permission for USB");
                    mUsbManager.requestPermission(handler.printers.get(0).device, mPermissionIntent);
                }





            } else {
                throw new RuntimeException("Could not find printers");
            }
        } catch (Exception e) {
            throw new RuntimeException("Zebra plugin exception: " + e);
        }
    }

    private ZebraImageI getZebraImageFromBitmap(byte[] bitmapByteArray) throws IOException {
        if (bitmapByteArray == null) {
            throw new RuntimeException("bitmapByteArray is null");
        }
        Bitmap mBitmap = BitmapFactory.decodeByteArray(bitmapByteArray, 0, bitmapByteArray.length);
        if (mBitmap == null) {
            throw new IllegalArgumentException("Bitmap is null, bitmapByteArray length: " + bitmapByteArray.length
                    + " Bitmap array text: " + Arrays.toString(bitmapByteArray));
        }
        return ZebraImageFactory.getImage(mBitmap);
    }

    private void printOverUSB(byte[] bitmapByteArray,DiscoveredPrinterUsb discoveredPrinterUsb)
            throws ConnectionException, ZebraPrinterLanguageUnknownException, IOException {
        Connection connection = discoveredPrinterUsb.getConnection();
        try {
            if(connection == null){
                throw new RuntimeException("Connection is NULL");
            }
            connection.open();
            if (connection.isConnected()) {
                ZebraPrinter printer = null;
                try {
                    printer = ZebraPrinterFactory.getInstance(connection);
                } catch (ConnectionException e) {
                    e.printStackTrace();
                    throw new ConnectionException(e);
                } catch (ZebraPrinterLanguageUnknownException e) {
                    e.printStackTrace();
                    throw new ZebraPrinterLanguageUnknownException(e.getMessage());
                }
                System.out.println("Printer status:" + printer.getCurrentStatus().toString());
                int x = 10;
                int y = 10;
                int width = 0;
                int heigth = 0;
                printer.printImage(getZebraImageFromBitmap(bitmapByteArray), x, y, width, heigth, false);
            } else {
                throw new ConnectionException("Could not open connection to a printer");
            }
        } catch (ConnectionException e) {
            throw new ConnectionException(e.getMessage() + e.getLocalizedMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (ConnectionException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static class UsbDiscoveryHandler implements DiscoveryHandler {
        public boolean discoveryComplete = false;
        public List<DiscoveredPrinterUsb> printers;

        public UsbDiscoveryHandler() {
            printers = new LinkedList<>();
        }

        public void foundPrinter(final DiscoveredPrinter printer) {
            printers.add((DiscoveredPrinterUsb) printer);
        }

        public void discoveryFinished() {
            discoveryComplete = true;
        }

        public void discoveryError(String message) {
            discoveryComplete = true;
            throw new RuntimeException("discoveryError: " + message);
        }
    }
}
