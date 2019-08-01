package cordova.zebra.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainExecutor extends TCPConnectionManager{

  public interface errorReporter{
        void onError(Exception e);
    }


    public void sendZplOverTcp(final String theIpAddress, final errorReporter onError){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MainExecutor.this.sendZplOverTcp(theIpAddress);
                } catch (Exception e) {
                    onError.onError(e);
                }
            }
        });
        t.start();
    }

    public void sendCpclOverTcp(final String theIpAddress, final errorReporter OnError){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MainExecutor.this.sendCpclOverTcp(theIpAddress);
                } catch (Exception e) {
                    OnError.onError(e);
                }
            }
        }).start();
    }

    public void printConfigLabelUsingDnsName(final String dnsName,final errorReporter OnError){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MainExecutor.this.printConfigLabelUsingDnsName(dnsName);
                } catch (Exception e) {
                    OnError.onError(e);
                }
            }
        }).start();
    }

}
