package cordova.zebra.plugin;

import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.comm.TcpConnection;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

class TCPConnectionManager {

      String TAG = "ZebraPrinter: ";

     void sendZplOverTcp(String theIpAddress,String contentText) throws ConnectionException {
        // Instantiate connection for ZPL TCP port at given address
        Connection thePrinterConn = new TcpConnection(theIpAddress, TcpConnection.DEFAULT_ZPL_TCP_PORT);

       // try {
            // Open the connection - physical connection is established here.
            thePrinterConn.open();

            // This example prints "This is a ZPL test." near the top of the label.
            //String zplData = "^XA^FO20,20^A0N,25,25^FDThis is a ZPL test.^FS^XZ";
            System.out.println(TAG+"Printing " + contentText);
            String zplData = contentText;

            // Send the data to printer as a byte array.
            thePrinterConn.write(zplData.getBytes());
      //  } catch (ConnectionException e) {
            // Handle communications error here.
        //    e.printStackTrace();
      //  } finally {
            // Close the connection to release resources.
            thePrinterConn.close();
       // }
    }

     void sendCpclOverTcp(String theIpAddress,String contentText) throws ConnectionException {
        // Instantiate connection for CPCL TCP port at given address
        final   Connection thePrinterConn = new TcpConnection(theIpAddress, TcpConnection.DEFAULT_CPCL_TCP_PORT);

            // Open the connection - physical connection is established here.
            thePrinterConn.open();

            // This example prints "This is a CPCL test." near the top of the label.
          /*  String cpclData = "! 0 200 200 210 1\r\n"
                    + "TEXT 4 0 30 40 This is a CPCL test.\r\n"
                    + "FORM\r\n"
                    + "PRINT\r\n";*/
                    System.out.println(TAG+"Printing " + contentText);
                    String cpclData = contentText;

            // Send the data to printer as a byte array.
            thePrinterConn.write(cpclData.getBytes());

            // Close the connection to release resources.
            thePrinterConn.close();

    }

     void printConfigLabelUsingDnsName(String dnsName) throws Exception {
        Connection connection = new TcpConnection(dnsName, 9100);

            connection.open();
            ZebraPrinter p = ZebraPrinterFactory.getInstance(connection);
            p.printConfigurationLabel();
            System.out.println("Printing Config Label");
            // Close the connection to release resources.
            connection.close();


    }

}
