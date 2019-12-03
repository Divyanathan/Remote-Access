package com.example.asf.remoteaccess;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Un_read_SMS extends AppCompatActivity {


    String phoneNo;
    String str="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_read__sms);
        Intent intent = getIntent();
        phoneNo = intent.getStringExtra("number");
        refreshSmsInbox(phoneNo);
    }
    public void refreshSmsInbox(String no) {
        ContentResolver contentResolver = getContentResolver();
        // Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null,null , null, null);

        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null,"read = 0", null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (smsInboxCursor.moveToFirst()) {
            //arrayAdapter.clear();
            do {
                str = str + "SMS From: " + smsInboxCursor.getString(indexAddress) +
                        "\n" + smsInboxCursor.getString(indexBody) + "\n\n";
                // Toast.makeText(Un_read_SMS.this, str, Toast.LENGTH_SHORT).show();
                //  arrayAdapter.add(str);
            } while (smsInboxCursor.moveToNext());
            SendSMS(no, str);
        }
        else
        {
            SendSMS(no, "no message for u today");
        }
    }
    public  void SendSMS(String phoneNumber,String msg)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
        Un_read_SMS.this.finish();
    }
}

