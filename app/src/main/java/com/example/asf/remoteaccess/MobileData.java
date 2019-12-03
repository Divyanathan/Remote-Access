package com.example.asf.remoteaccess;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MobileData extends AppCompatActivity {

    boolean state;
    String phoneNo;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_data);

        Intent intent = getIntent();
        phoneNo = intent.getStringExtra("number");

        str=intent.getStringExtra("Name");

        state = isMobileDataEnable();


        if (str.equals("ondata")) {

                if (state) {

                    SendSMS(phoneNo, "already mobile data is on");
                } else {
                    SendSMS(phoneNo, "mobile data is on now");

                    toggleMobileDataConnection(true);
                }


        }

         else if(str.equals("offdata")) {

            if(state)
            {
                SendSMS(phoneNo, "mobile data is off now");

                toggleMobileDataConnection(false);



            }

            else {
                SendSMS(phoneNo,"already mobile data is off");

            }
        }
    }


    // load controls



    public  void SendSMS(String phoneNumber,String msg)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
        MobileData.this.finish();
    }

    public boolean isMobileDataEnable() {

        boolean mobileDataEnabled = false; // Assume disabled
        ConnectivityManager cm = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // method is callable
            // get the setting for "mobile data"
            mobileDataEnabled = (Boolean) method.invoke(cm);
        } catch (Exception e) {
            // Some problem accessible private API and do whatever error
            // handling here as you want..
        }
        return mobileDataEnabled;
    }

    public boolean toggleMobileDataConnection(boolean ON) {
        try {
            // create instance of connectivity manager and get system service

            final ConnectivityManager conman = (ConnectivityManager) this
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            // define instance of class and get name of connectivity manager
            // system service class
            final Class conmanClass = Class
                    .forName(conman.getClass().getName());
            // create instance of field and get mService Declared field
            final Field iConnectivityManagerField = conmanClass
                    .getDeclaredField("mService");
            // Attempt to set the value of the accessible flag to true
            iConnectivityManagerField.setAccessible(true);
            // create instance of object and get the value of field conman
            final Object iConnectivityManager = iConnectivityManagerField
                    .get(conman);
            // create instance of class and get the name of iConnectivityManager
            // field
            final Class iConnectivityManagerClass = Class
                    .forName(iConnectivityManager.getClass().getName());
            // create instance of method and get declared method and type
            final Method setMobileDataEnabledMethod = iConnectivityManagerClass
                    .getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            // Attempt to set the value of the accessible flag to true
            setMobileDataEnabledMethod.setAccessible(true);
            // dynamically invoke the iConnectivityManager object according to
            // your need (true/false)
            setMobileDataEnabledMethod.invoke(iConnectivityManager, ON);
        } catch (Exception e) {
        }
        return true;
    }

}
