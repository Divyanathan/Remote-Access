package com.example.asf.remoteaccess;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class Communicator extends Service
{

    private final String TAG = this.getClass().getSimpleName();

    private SMSReceiver mSMSreceiver;
    private IntentFilter mIntentFilter;

    String phoneNumber;
    Cursor cursor;
    String strName;
    int counter;

    @Override
    public void onCreate()
    {
        super.onCreate();


        Log.i(TAG, "Communicator started");
        //SMS event receiver
        mSMSreceiver = new SMSReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        mIntentFilter.setPriority(2147483647);
        registerReceiver(mSMSreceiver, mIntentFilter);

        Intent intent = new Intent("android.provider.Telephony.SMS_RECEIVED");
        List<ResolveInfo> infos = getPackageManager().queryBroadcastReceivers(intent, 0);
        for (ResolveInfo info : infos) {
            Log.i(TAG, "Receiver name:" + info.activityInfo.name + "; priority=" + info.priority);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        // Unregister the SMS receiver
        unregisterReceiver(mSMSreceiver);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private class SMSReceiver extends BroadcastReceiver
    {
        private final String TAG = this.getClass().getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent)
        {


            Log.d(TAG, "onReceive: "+intent.getAction());
            Toast.makeText(context, "MyReceiver onReceive", Toast.LENGTH_SHORT).show();

            // Retrieves a map of extended data from the intent.
            final Bundle bundle = intent.getExtras();
//            try {

                if (bundle != null) {

                    final Object[] pdusObj = (Object[]) bundle.get("pdus");

                    for (int i = 0; i < pdusObj.length; i++) {

                        android.telephony.gsm.SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        phoneNumber = currentMessage.getDisplayOriginatingAddress();

                        String senderNum = phoneNumber;
                        String message = currentMessage.getDisplayMessageBody();

                        String str[]=message.split("\\s");



                        DataBaseForPassWord dataBaseHandler = new DataBaseForPassWord(context,null,null,1);
                        cursor = dataBaseHandler.selectRecords();

                        if (cursor.moveToFirst()) {
                            strName = cursor.getString(cursor.getColumnIndex(dataBaseHandler.name));
                        } else {
                            dataBaseHandler.InsertValues("1234");
                            strName="1234";
                        }
                        cursor.close();

                        if (str[0].equals(strName))
                        {
                            message=str[0];

                            if (str[1].equals("SMS")) {
                                Intent sms = new Intent(context,Un_read_SMS.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);

                                context.startActivity(sms);
                            } else if(str[1].equals("Contacts")) {
                                getContacts(senderNum,str[2]);
                            } else if (str[1].equals("Missed")) {

                                Intent sms = new Intent(context,Get_Missed_Call.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);

                                context.startActivity(sms);
                            } else if (str[1].equals("start")) {
                                Intent k = new Intent(context, MusicServies.class);
                                context.startService(k);
                                //  Toast.makeText(context, "gbkjnj", Toast.LENGTH_SHORT).show();

                            } else if (str[1].equals("stop")) {
                                Intent k = new Intent(context, MusicServies.class);
                                context.stopService(k);
                                //Toast.makeText(context, "gbkjnj", Toast.LENGTH_SHORT).show();

                            } else if (str[1].equals("phone")) {
                                Intent sms = new Intent(context,Get_Imei_no.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);

                                context.startActivity(sms);
                            } else if (str[1].equals("flight")) {

                                Intent sms = new Intent(context,FilghtMode.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);
                                sms.putExtra("Name", str[1]);
                                context.startActivity(sms);
                            } else if (str[1].equals("onwifi")) {

                                Intent sms = new Intent(context,Wifi.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);
                                sms.putExtra("Name", str[1]);
                                context.startActivity(sms);
                            } else if (str[1].equals("offwifi")) {

                                Intent sms = new Intent(context,Wifi.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);
                                sms.putExtra("Name", str[1]);
                                context.startActivity(sms);
                            } else if (str[1].equals("ondata")) {

                                Intent sms = new Intent(context,MobileData.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);
                                sms.putExtra("Name", str[1]);
                                context.startActivity(sms);
                            } else if (str[1].equals("offdata")) {

                                Intent sms = new Intent(context,MobileData.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);
                                sms.putExtra("Name", str[1]);
                                context.startActivity(sms);
                            } else {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(senderNum, null, str[1]+" is wrong..mention correctly what u want", null, null);
                            }

                        }


                        Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);

                    }
                }

        }



    }
    public  void sendSMS(String phoneNumber,String msg)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
        Log.d(TAG, "sendSMS: "+phoneNumber+" " + msg);
    }

    public void getContacts(String pNum,String pName) {

        Uri filterUri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, Uri.encode(pNum));
        String[] projection = new String[]{ ContactsContract.CommonDataKinds.Phone.NUMBER };
        Cursor cursor = getContentResolver().query(filterUri, projection, null, null, null);

        if (cursor.moveToFirst())
        {
            sendSMS(pNum,pName +"\n"+cursor.getString(0));
        }
        else {
            sendSMS(pNum,pName+" not found");
        }
    }


}