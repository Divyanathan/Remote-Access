package com.example.asf.remoteaccess;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    // Get the object of SmsManager


    String phoneNumber;
    Cursor cursor;
    String strName;



    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    //Assign the phone number
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    //Split the message by space
                    String str[]=message.split("\\s");



                    //Access the Database calss to retrive the password
                    DataBaseForPassWord dataBaseHandler = new DataBaseForPassWord(context,null,null,1);
                    cursor = dataBaseHandler.selectRecords();

                    if (cursor.moveToFirst()) {

                        strName = cursor.getString(cursor.getColumnIndex(dataBaseHandler.name));


//                        Toast.makeText(context,strName, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(ChangePassWord.this, strName, Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        dataBaseHandler.InsertValues("1234");
                        strName="1234";
                    }
                    cursor.close();

                    //password verification
                    if (str[0].equals(strName))
                    {
                        message=str[0];

                        if (str[1].equals("SMS"))
                        {
                            Intent sms = new Intent(context,Un_read_SMS.class);
                            sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            sms.putExtra("number", senderNum);

                            context.startActivity(sms);
                        }

                        else if(str[1].equals("Contacts")) {

                            //call the  Get_Contact activy to send SMS
                            Intent contact = new Intent(context, Get_Contact.class);
                            contact.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            contact.putExtra("number", senderNum);
                            contact.putExtra("Name", str[2]);
                            context.startActivity(contact);
                        }
                        else if (str[1].equals("Missed"))
                        {

                            //call the Get_Missed_Call activty to send Missed call
                            Intent sms = new Intent(context,Get_Missed_Call.class);
                            sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            sms.putExtra("number", senderNum);

                            context.startActivity(sms);
                        }
                        else if (str[1].equals("start"))
                        {
                            Intent k = new Intent(context, MusicServies.class);
                            context.startService(k);
                          //  Toast.makeText(context, "gbkjnj", Toast.LENGTH_SHORT).show();

                        }
                        else if (str[1].equals("stop"))
                        {
                            Intent k = new Intent(context, MusicServies.class);
                            context.stopService(k);
                            //Toast.makeText(context, "gbkjnj", Toast.LENGTH_SHORT).show();

                        }
                        else if (str[1].equals("phone"))
                        {
                            Intent sms = new Intent(context,Get_Imei_no.class);
                            sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            sms.putExtra("number", senderNum);

                            context.startActivity(sms);
                        }
                        else if (str[1].equals("flight"))
                        {

                            Intent sms = new Intent(context,FilghtMode.class);
                            sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            sms.putExtra("number", senderNum);
                            sms.putExtra("Name", str[1]);
                            context.startActivity(sms);
                        }

                        else if (str[1].equals("onwifi"))
                        {

                            Intent sms = new Intent(context,Wifi.class);
                            sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            sms.putExtra("number", senderNum);
                            sms.putExtra("Name", str[1]);
                            context.startActivity(sms);
                        }
                        else if (str[1].equals("offwifi"))
                        {

                            Intent sms = new Intent(context,Wifi.class);
                            sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            sms.putExtra("number", senderNum);
                            sms.putExtra("Name", str[1]);
                            context.startActivity(sms);
                        }
                        else if (str[1].equals("ondata"))
                        {

                            Intent sms = new Intent(context,MobileData.class);
                            sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            sms.putExtra("number", senderNum);
                            sms.putExtra("Name", str[1]);
                            context.startActivity(sms);
                        }
                        else if (str[1].equals("offdata"))
                        {

                            Intent sms = new Intent(context,MobileData.class);
                            sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            sms.putExtra("number", senderNum);
                            sms.putExtra("Name", str[1]);
                            context.startActivity(sms);
                        }
                        else
                        {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(senderNum, null, str[1]+" is wrong..mention correctly what u want", null, null);
                        }

                    }


                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);

                    // Show Alert

                    int duration = Toast.LENGTH_LONG;
                    /*Toast toast = Toast.makeText(context,
                            "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();*/
                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }



}