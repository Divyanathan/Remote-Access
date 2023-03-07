package com.example.asf.remoteaccess;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    // Get the object of SmsManager


    String phoneNumber;
    Cursor cursor;
    String strName;
    String TAG = "MyReceiver";
    Context mContext;



    public void onReceive(Context context, Intent intent) {

        mContext = context;

        Log.d(TAG, "onReceive: "+intent.getAction());

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
            try {

                if (bundle != null) {

                    final Object[] pdusObj = (Object[]) bundle.get("pdus");

                    for (int i = 0; i < pdusObj.length; i++) {

                        android.telephony.gsm.SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        phoneNumber = currentMessage.getDisplayOriginatingAddress();

                        String senderNum = phoneNumber;
                        String message = currentMessage.getDisplayMessageBody();

                        String str[] = message.split("\\s");


                        DataBaseForPassWord dataBaseHandler = new DataBaseForPassWord(context, null, null, 1);
                        cursor = dataBaseHandler.selectRecords();

                        if (cursor.moveToFirst()) {
                            strName = cursor.getString(cursor.getColumnIndex(dataBaseHandler.name));
                        } else {
                            dataBaseHandler.InsertValues("1234");
                            strName = "1234";
                        }
                        cursor.close();

                        if (str[0].equalsIgnoreCase(strName)) {
                            message = str[0];

                            if (str[1].equalsIgnoreCase("SMS")) {
                                Intent sms = new Intent(context, Un_read_SMS.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);

                                context.startActivity(sms);
                            } else if (str[1].equalsIgnoreCase("Contacts")) {
                                getContacts(senderNum, str[2].trim());
                            } else if (str[1].equalsIgnoreCase("Missed")) {

                                Intent sms = new Intent(context, Get_Missed_Call.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);

                                context.startActivity(sms);
                            } else if (str[1].equalsIgnoreCase("start")) {
                                Intent k = new Intent(context, MusicServies.class);
                                context.startService(k);
                                //  Toast.makeText(context, "gbkjnj", Toast.LENGTH_SHORT).show();

                            } else if (str[1].equalsIgnoreCase("stop")) {
                                Intent k = new Intent(context, MusicServies.class);
                                context.stopService(k);
                                //Toast.makeText(context, "gbkjnj", Toast.LENGTH_SHORT).show();

                            } else if (str[1].equalsIgnoreCase("phone")) {
                                Intent sms = new Intent(context, Get_Imei_no.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);

                                context.startActivity(sms);
                            } else if (str[1].equalsIgnoreCase("flight")) {

                                Intent sms = new Intent(context, FilghtMode.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);
                                sms.putExtra("Name", str[1]);
                                context.startActivity(sms);
                            } else if (str[1].equalsIgnoreCase("onwifi")) {

                                Intent sms = new Intent(context, Wifi.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);
                                sms.putExtra("Name", str[1]);
                                context.startActivity(sms);
                            } else if (str[1].equalsIgnoreCase("offwifi")) {

                                Intent sms = new Intent(context, Wifi.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);
                                sms.putExtra("Name", str[1]);
                                context.startActivity(sms);
                            } else if (str[1].equalsIgnoreCase("ondata")) {

                                Intent sms = new Intent(context, MobileData.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);
                                sms.putExtra("Name", str[1]);
                                context.startActivity(sms);
                            } else if (str[1].equalsIgnoreCase("offdata")) {

                                Intent sms = new Intent(context, MobileData.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);
                                sms.putExtra("Name", str[1]);
                                context.startActivity(sms);
                            } else {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(senderNum, null, str[1] + " is wrong..mention correctly what u want", null, null);
                            }

                        }


                        Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);

                    }
                }
            }catch (Exception e){
                e.printStackTrace();
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
        Cursor cursor = mContext.getContentResolver().query(filterUri, projection, null, null, null);

        if (cursor.moveToFirst())
        {
            sendSMS(pNum,pName +"\n"+cursor.getString(0));
        }
        else {
            sendSMS(pNum,pName+" not found");
        }
    }



}