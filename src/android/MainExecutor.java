package cordova.zebra.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainExecutor extends TCPConnectionManager{

  public interface StatusReporter{
        void onError(Exception e);
        void onSuccess(String message);
    }


    public void sendZplOverTcp(final String theIpAddress,final String contentText ,final StatusReporter onStatusUpdate){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MainExecutor.this.sendZplOverTcp(theIpAddress,contentText);
                    onStatusUpdate.onSuccess("Successfully sent " + contentText);
                } catch (Exception e) {
                    onStatusUpdate.onError(e);
                }
            }
        });
        t.start();
    }

    public void sendCpclOverTcp(final String theIpAddress, final String contentText,final StatusReporter onStatusUpdate){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MainExecutor.this.sendCpclOverTcp(theIpAddress,contentText);
                    onStatusUpdate.onSuccess("Successfully sent " + contentText);
                } catch (Exception e) {
                    onStatusUpdate.onError(e);
                }
            }
        }).start();
    }

    public void printConfigLabelUsingDnsName(final String dnsName,final StatusReporter onStatusUpdate){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MainExecutor.this.printConfigLabelUsingDnsName(dnsName);
                    onStatusUpdate.onSuccess("Successfully sent Config Label");
                } catch (Exception e) {
                    onStatusUpdate.onError(e);
                }
            }
        }).start();
    }

}
