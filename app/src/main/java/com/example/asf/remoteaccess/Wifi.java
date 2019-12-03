package com.example.asf.remoteaccess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;

public class Wifi extends AppCompatActivity {

    String str;
    String phoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        Intent intent = getIntent();
        phoneNo = intent.getStringExtra("number");

        str=intent.getStringExtra("Name");
        toggleWiFi();
    }

    public void toggleWiFi() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (str.equals("onwifi") && !wifiManager.isWifiEnabled())
            {
                wifiManager.setWifiEnabled(true);
                SendSMS(phoneNo, "wifi is on now");
            }


           else if (str.equals("onwifi") && wifiManager.isWifiEnabled())
                {
                    //wifiManager.setWifiEnabled(true);
                    SendSMS(phoneNo, "wifi is alredy on");
                }

             else if (str.equals("offwifi") && wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(false);
            SendSMS(phoneNo, "wifi is off now");
            }
        else if (str.equals("offwifi") && !wifiManager.isWifiEnabled()) {
            //wifiManager.setWifiEnabled(false);
            SendSMS(phoneNo, "wifi is already off");
        }
        }

    public  void SendSMS(String phoneNumber,String msg)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
        Wifi.this.finish();
    }
}
