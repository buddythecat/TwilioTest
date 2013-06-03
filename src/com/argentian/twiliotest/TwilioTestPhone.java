package com.argentian.twiliotest;

/**
 * Created by snake on 6/2/13.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.twilio.client.Device;
import com.twilio.client.DeviceListener;
import com.twilio.client.PresenceEvent;
import com.twilio.client.Twilio;
import com.twilio.client.Connection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TwilioTestPhone implements Twilio.InitListener, DeviceListener{

    private static final String TAG = "TwilioTestPhone";

    protected Device mDevice;
    protected String mCapabilityToken;
    private Connection mConnection;

    class DownloadCapabilityTask extends AsyncTask<Void, Void, Void> {
    	private static final String TAG = "DownloadCapabilityTask";
    	
    	@Override
    	protected Void doInBackground(Void... params) {
    		try {
    			mDevice.updateCapabilityToken(HttpHelper.httpGet("http://argentiantech.net/auth.php"));
    		} catch (Exception e) {
    			 Log.e(TAG, "Failed to obtain compatibility token: " + e.getLocalizedMessage());
    			 return null;
    		}
            //Log.v(TAG, "Auth Token: " + params[0]);
            return null;
    	}
    	
    }
    
    public TwilioTestPhone(Context ctx){
        Twilio.initialize(ctx, this);
    }

    @Override
    public void onInitialized() {
        Log.d(TAG, "Twilio SDK is ready");

      mDevice = Twilio.createDevice(null, null);
      new DownloadCapabilityTask().execute();
      Log.v(TAG, "Created Twilio Device!!! " + mDevice.getCapabilityToken());
    }

    /**
     * Connects the phone to the twilio service and calls the passed target number
     * @param targetNumber - number to call
     */
    public void connect(String targetNumber){
    	UUID callId = UUID.randomUUID();
        Log.v(TAG, "Capability Token: " + mDevice.getCapabilityToken());
        //create a hashmap to store the number to call (key = PhoneNumber)
        Map<String,String> params = new HashMap<String, String>();
        params.put("PhoneNumber", targetNumber);
        params.put("CallID", callId.toString());
        //initialize connection
        mConnection = mDevice.connect(params, null);
        //ensure that the connection was established
        if(mConnection == null)
            Log.w(TAG, "Failed to create a new connection");
    }

    /**
     * Disconnects the TestPhone
     */
    public void disconnect(){
        if(mConnection != null){
            mConnection.disconnect();
            mConnection = null;
            Log.v(TAG, "Disconnected");
        }
    }

    /*
    _________________________________
    Status/Listener methods are below
    _________________________________
     */

    /**
     * callback for when the device is successfully registered with Twilio
     * @param inDevice - Twilio Device
     */
    public void onStartListening(Device inDevice){
        Log.i(TAG, "Device is now listening for incoming connections");
    }

    /**
     * Callback for when the device is no longer listening for incoming
     * connections due to an explicit request to stop.
     * @param inDevice
     */
    public void onStopListening(Device inDevice){
        Log.i(TAG, "Device is no longer listening for incoming connections");
    }

    /**
     * Callback for when the device is no longer listening for incoming
     * connections due to an explicit request to stop.
     * @param inDevice - Twilio Device
     * @param inErrorCode - error code provided from twilio
     * @param inErrorMessage - string error provided
     */
    public void onStopListening(Device inDevice, int inErrorCode, String inErrorMessage){
        Log.i(TAG, "Device is no longer listening for incoming connections due to error: "+inErrorCode+": "+inErrorMessage);
    }

    /**
     * Called to determine if the application wants to receive presence events.
     * ____NOTENOTENOTE___
     *  PRESENCE EVENTS ARE STATUS MESSAGES ABOUT OTHER DEVICES IN THE ACCOUNT
     * @param inDevice
     */
    public boolean receivePresenceEvents(Device inDevice){
        return false;
    }

    /**
     * called when any client on the account becomes available or unavailable
     * @param inDevice
     * @param inPresenceEvent
     */
    public void onPresenceChanged(Device inDevice, PresenceEvent inPresenceEvent){
        //empty method
    }

    @Override
    public void onError(Exception e) {
        Log.e(TAG, "Twilio SDK couldn't start: " + e.getLocalizedMessage());
    }

    protected void finalize(){
        if(mConnection != null)
            mConnection.disconnect();
        if(mDevice != null)
            mDevice.release();
    }
}
