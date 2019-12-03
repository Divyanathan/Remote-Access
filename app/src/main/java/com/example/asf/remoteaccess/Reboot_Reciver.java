package com.example.asf.remoteaccess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

public class

        Reboot_Reciver extends BroadcastReceiver {
    public Reboot_Reciver() {
    }

    DataBaseForPhoneNumber dataBaseForPhoneNumber;
    String str;
    Cursor cursor;
    @Override
    public void onReceive(Context context, Intent intent) {

        dataBaseForPhoneNumber=new DataBaseForPhoneNumber(context,null,null,1);
        String phone;
        cursor = dataBaseForPhoneNumber.selectRecords();

        if (cursor.moveToFirst()) {

            str = cursor.getString(cursor.getColumnIndex(dataBaseForPhoneNumber.name));


            Intent sms = new Intent(context,Get_Imei_no.class);
            sms.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sms.putExtra("number", str);

            context.startActivity(sms);
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            //Toast.makeText(ChangePassWord.this, strName, Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(context, "Plz set the phone number to send the Details ", Toast.LENGTH_SHORT).show();
        }
        cursor.close();


    }
}
