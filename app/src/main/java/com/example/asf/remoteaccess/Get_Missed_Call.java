package com.example.asf.remoteaccess;


import android.content.Intent;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;

import java.util.Date;

public class Get_Missed_Call extends AppCompatActivity {

    String sb="";
    String phoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missed__call);
        Intent intent = getIntent();
        phoneNo = intent.getStringExtra("number");
        getCallDetails(phoneNo);
    }
    private void getCallDetails(String no) {

        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);


        int count=0;
        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));

            String callDuration = managedCursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                /*case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;*/
                case CallLog.Calls.MISSED_TYPE:
                    if (count<3) {
                        dir = "MISSED";
                        sb="Phone Number:" + phNumber + " Date:"  + callDayTime+"\n";
                        SendSMS(no,sb);
                        count++;
                    }
                    /*if (count<5) {
                        temp[count]=phNumber;
                        for (int i=0;i<=count;i++) {
                            if (!temp[i].equals(temp[count])) {
                                dir = "MISSED";
                                sb.append("\nPhone Number:‐‐‐ " + phNumber + " \nCall Date:‐‐‐ " + callDayTime);
                                sb.append("\n‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐‐");
                                count++;
                            }
                        }
                    }*/
                    break;
            }

        }

        Toast.makeText(Get_Missed_Call.this,sb, Toast.LENGTH_SHORT).show();
//managedCursor.close();


    }
    public  void SendSMS(String phoneNumber,String msg)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
        Get_Missed_Call.this.finish();
    }
}
