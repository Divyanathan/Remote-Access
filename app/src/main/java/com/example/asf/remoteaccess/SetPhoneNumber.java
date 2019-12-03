package com.example.asf.remoteaccess;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class SetPhoneNumber extends AppCompatActivity {
    EditText new_pass, confirm_pass,oldpasd;
    DataBaseForPhoneNumber dataBaseHandler;
    DataBaseForPassWord dataBaseForPassWord;
    Cursor cursor;
    String strName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_phone_number);
        dataBaseHandler = new DataBaseForPhoneNumber(this,null,null,1);
        dataBaseForPassWord=new DataBaseForPassWord(this,null,null,1);
        new_pass = (EditText) findViewById(R.id.pasd);
        oldpasd = (EditText) findViewById(R.id.oldpswd);
    }
    public String SelectData() {
        cursor = dataBaseForPassWord.selectRecords();


        if (cursor.moveToFirst()) {

            strName = cursor.getString(cursor.getColumnIndex(dataBaseForPassWord.name));


            dataBaseHandler.InsertValues("8951175146");
            //Toast.makeText(ChangePassWord.this, strName, Toast.LENGTH_SHORT).show();

        }
        else
        {
            dataBaseForPassWord.InsertValues("1234");
            strName="1234";
        }
        cursor.close();
        return strName;
    }
    public String SelectData1() {
        cursor = dataBaseHandler.selectRecords();


        if (cursor.moveToFirst()) {

            strName = cursor.getString(cursor.getColumnIndex(dataBaseHandler.name));


            //dataBaseHandler.InsertValues("8951175146");
            //Toast.makeText(ChangePassWord.this, strName, Toast.LENGTH_SHORT).show();

        }
        else
        {
            dataBaseForPassWord.InsertValues("1234");
            strName="1234";
        }
        cursor.close();
        return strName;
    }
    public void UpdatePassword(View view) {

        String getpassword=SelectData();
        if (oldpasd.getText().toString().equals(""))
        {
            Toast.makeText(SetPhoneNumber.this, "Enter  password first", Toast.LENGTH_SHORT).show();

        }

        else if (new_pass.getText().toString().equals(""))
        {
            Toast.makeText(SetPhoneNumber.this, "Enter  phone number first", Toast.LENGTH_SHORT).show();
        }

        else if (oldpasd.getText().toString().equals(getpassword)) {
                dataBaseHandler.updateRecord(new_pass.getText().toString(), "1");
                Toast.makeText(SetPhoneNumber.this, "phone number updated Successfully..!\n"+SelectData1(), Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(SetPhoneNumber.this, "Sry plz enter the old password correctly", Toast.LENGTH_SHORT).show();
        }

    }

}
