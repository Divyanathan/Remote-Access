package com.example.asf.remoteaccess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class Reboot_Reciver extends BroadcastReceiver {
    public Reboot_Reciver() {
    }

    DataBaseForPhoneNumber dataBaseForPhoneNumber;
    String mPhoneNumber;
    Cursor cursor;
    String TAG = "Reboot_Reciver";
    @Override
    public void onReceive(Context context, Intent intent) {

        dataBaseForPhoneNumber=new DataBaseForPhoneNumber(context,null,null,1);
        String phone;
        cursor = dataBaseForPhoneNumber.selectRecords();

        if (cursor.moveToFirst()) {

            mPhoneNumber = cursor.getString(cursor.getColumnIndex(dataBaseForPhoneNumber.name));
            sendSMS(mPhoneNumber,"Your phone came online now make suer it's coming your register device");

        } else {
            Toast.makeText(context, "Plz set the phone number to send the Details ", Toast.LENGTH_SHORT).show();
        }
        cursor.close();


    }

    public  void sendSMS(String phoneNumber,String msg)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
        Log.d(TAG, "sendSMS: "+phoneNumber+" " + msg);
    }

}
