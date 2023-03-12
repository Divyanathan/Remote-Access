package com.example.asf.remoteaccess;


import android.content.BroadcastReceiver;
import android.content.ContentResolver;
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
                        
                        String password = str[0];

                        if (password.equalsIgnoreCase(strName)) {
                            message = password;
                            
                            String content = str[1].trim();
                            
                            if (content.equalsIgnoreCase("SMS")) {
                                Intent sms = new Intent(context, Un_read_SMS.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);

                                context.startActivity(sms);
                            } else if (content.equalsIgnoreCase("Contacts")) {
                                getContacts(senderNum, str[2].trim());
                            } else if (content.equalsIgnoreCase("Missed")) {

                                Intent sms = new Intent(context, Get_Missed_Call.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);

                                context.startActivity(sms);
                            } else if (content.equalsIgnoreCase("start")) {
                                Intent k = new Intent(context, MusicServies.class);
                                context.startService(k);

                            } else if (content.equalsIgnoreCase("stop")) {
                                Intent k = new Intent(context, MusicServies.class);
                                context.stopService(k);

                            } else if (content.equalsIgnoreCase("phone")) {
                                Intent sms = new Intent(context, Get_Imei_no.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);

                                context.startActivity(sms);
                            } else if (content.equalsIgnoreCase("flight")) {

                                Intent sms = new Intent(context, FilghtMode.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);
                                sms.putExtra("Name", content);
                                context.startActivity(sms);
                            } else if (content.equalsIgnoreCase("onwifi")) {

                                Intent sms = new Intent(context, Wifi.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);
                                sms.putExtra("Name", content);
                                context.startActivity(sms);
                            } else if (content.equalsIgnoreCase("offwifi")) {

                                Intent sms = new Intent(context, Wifi.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);
                                sms.putExtra("Name", content);
                                context.startActivity(sms);
                            } else if (content.equalsIgnoreCase("ondata")) {

                                Intent sms = new Intent(context, MobileData.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);
                                sms.putExtra("Name", content);
                                context.startActivity(sms);
                            } else if (content.equalsIgnoreCase("offdata")) {

                                Intent sms = new Intent(context, MobileData.class);
                                sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                sms.putExtra("number", senderNum);
                                sms.putExtra("Name", content);
                                context.startActivity(sms);
                            } else {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(senderNum, null, content + " is wrong..mention correctly what u want", null, null);
                            }

                        }


                        Log.i(TAG, "senderNum: " + senderNum + "; message: " + message);

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

        Log.d(TAG, "getContacts: ");
        Uri filterUri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor = mContext.getContentResolver().query(filterUri, null, ContactsContract.Contacts.DISPLAY_NAME + " = ?", new String[] { pName }, null,null);

        if (cursor.moveToFirst())
        {
            Log.d(TAG, "getContacts: moveToFirst()" );
            String value="Name:- "+cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))+"\n";
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

            Log.d(TAG, "getContacts: id "+id);


                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER )));
                Log.d(TAG, "getContacts: has phone number "+ hasPhoneNumber);
                if (hasPhoneNumber > 0) {
                    Log.d(TAG, "getContacts: hasPhoneNumber");
                    Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                    String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
                    //This is to read multiple phone numbers associated with the same contact
                    Cursor phoneCursor = mContext.getContentResolver().query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { id }, null);
                    Log.d(TAG, "getContacts: count "+ phoneCursor.getCount());
                    while (phoneCursor.moveToNext()) {
                        Log.d(TAG, "getContacts: phoneCursor.moveToNext()");
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        value = value + "Phone no:- " + phoneNumber + "\n";
                    }
                    phoneCursor.close();
                }
            Log.d(TAG, "getContacts: value "+ value );

            sendSMS(pNum,pName +"\n"+value);
        } else {
            sendSMS(pNum,pName+" not found");
        }
        cursor.close();
    }





}