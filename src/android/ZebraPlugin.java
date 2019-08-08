package cordova.zebra.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cordova.zebra.plugin.MainExecutor;

/**
 * This class echoes a string called from JavaScript.
 */
public class ZebraPlugin extends CordovaPlugin {

  MainExecutor mMainExecutor = new MainExecutor();

  public void sendZplOverTcp(final String theIpAddress,final String contentText,CallbackContext callbackContext){
    mMainExecutor.sendZplOverTcp(theIpAddress,contentText,new MainExecutor.StatusReporter() {
                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                            callbackContext.error(e.getLocalizedMessage());
                        }

                        @Override
                        public void onSuccess(String message){
                          callbackContext.success(message);
                        }
                    });
  }

  public void sendCpclOverTcp(final String theIpAddress,final String contentText,CallbackContext callbackContext){
    mMainExecutor.sendCpclOverTcp(theIpAddress,contentText,new MainExecutor.StatusReporter() {
                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                            callbackContext.error(e.getLocalizedMessage());
                        }

                        @Override
                        public void onSuccess(String message){
                          callbackContext.success(message);
                        }
                    });
  }

  public void printConfigLabelUsingDnsName(final String dnsName,CallbackContext callbackContext){
      mMainExecutor.printConfigLabelUsingDnsName(dnsName,new MainExecutor.StatusReporter() {
                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                            callbackContext.error(e.getLocalizedMessage());
                        }

                        @Override
                        public void onSuccess(String message){
                          callbackContext.success(message);
                        }
                    });
  }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }else if (action.equals("sendZplOverTcp")) {
          String ip = args.getString(0);
          String message = args.getString(1);
          this.sendZplOverTcp(ip,message,callbackContext);
          return true;
        }else if (action.equals("sendCpclOverTcp")) {
          String ip = args.getString(0);
          String message = args.getString(1);
          this.sendCpclOverTcp(ip,message,callbackContext);
          return true;
        }else if (action.equals("printConfigLabelUsingDnsName")) {
          String dnsName = args.getString(0);
          this.printConfigLabelUsingDnsName(dnsName,callbackContext);
          return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
