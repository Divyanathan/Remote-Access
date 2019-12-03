package com.example.asf.remoteaccess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class FilghtMode extends AppCompatActivity {

    String phoneNo;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filght_mode);



        Intent intent = getIntent();
        phoneNo = intent.getStringExtra("number");

        str=intent.getStringExtra("Name");

        boolean state = isAirplaneMode();
        // toggle the state
        if (state) {
            if (str.equals("onflight"))
            {
             SendSMS(phoneNo,"already on");
            }
            else {
                toggleAirplaneMode(0, state);
                SendSMS(phoneNo, "Flight mode of");

            }
        }
        else {
            if (str.equals("offlight"))
            {
                SendSMS(phoneNo,"already of");
            }
            else {
                toggleAirplaneMode(1, state);
                SendSMS(phoneNo,"Flight mode on");

            }

        }


        // update UI at first time loading

        // set click event for button


    }

    public  void SendSMS(String phoneNumber,String msg)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
        FilghtMode.this.finish();
    }
    public void toggleAirplaneMode(int value, boolean state) {
        // toggle airplane mode
        // check the version
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) { // if
            // less
            // then
            // version
            // 4.2

            Settings.System.putInt(getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, value);
        } else {
            Settings.Global.putInt(getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, value);
        }
        // broadcast an intent to inform
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", !state);
        sendBroadcast(intent);
    }


    public boolean isAirplaneMode() {
        // check the version
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {// if
            // less
            // than
            // version
            // 4.2

            return Settings.System.getInt(getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;

        }
    }

}
