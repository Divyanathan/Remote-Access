package com.example.asf.remoteaccess;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SentSms extends AppCompatActivity {


    TextView text;
    final int MY_PERMISSIONS_REQUEST_READ_CONTACT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_sms);

        text=(TextView)findViewById(R.id.missedcalltext);
        ensurePermissions();
    }

    public void getSentMessage() {
        StringBuffer sb=new StringBuffer();
        ContentResolver contentResolver = getContentResolver();
        // Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null,null , null, null);

        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/sent"), null,null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");

        if (smsInboxCursor.moveToFirst()) {

            //arrayAdapter.clear();
            do {
                String str = "\nSMS To: " + smsInboxCursor.getString(indexAddress) +
                        "\n" + smsInboxCursor.getString(indexBody) + "\n________________________________________________\n";
                // Toast.makeText(SmsActivity.this, str, Toast.LENGTH_SHORT).show();
                //  arrayAdapter.add(str);
                sb.append(str);
            } while (smsInboxCursor.moveToNext());
        }
        else
        {
            Toast.makeText(SentSms.this, "no message fr u", Toast.LENGTH_SHORT).show();

        }


        // Toast.makeText(SmsActivity.this, sb, Toast.LENGTH_SHORT).show();

        text.setText(sb);


    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    // getContactDeatails();
                    getSentMessage();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void ensurePermissions() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACT);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            getSentMessage();
//            getLoaderManager().initLoader(1,null,this);
            // getContactDeatails();
        }
    }

}
