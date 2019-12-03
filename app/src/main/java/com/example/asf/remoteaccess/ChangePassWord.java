package com.example.asf.remoteaccess;


import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassWord extends AppCompatActivity {

    EditText new_pass, confirm_pass,oldpasd;
    DataBaseForPassWord dataBaseHandler;
    Cursor cursor;
    String strName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_word);

        dataBaseHandler = new DataBaseForPassWord(this,null,null,1);
        new_pass = (EditText) findViewById(R.id.pasd);
        oldpasd = (EditText) findViewById(R.id.oldpswd);
        confirm_pass = (EditText) findViewById(R.id.psdconfirm);
    }



    public String SelectData() {
        cursor = dataBaseHandler.selectRecords();


        if (cursor.moveToFirst()) {

            strName = cursor.getString(cursor.getColumnIndex(dataBaseHandler.name));


            //Toast.makeText(ChangePassWord.this, strName, Toast.LENGTH_SHORT).show();

        }
        else
        {
            dataBaseHandler.InsertValues("1234");
            strName="1234";
        }
        cursor.close();
        return strName;
    }
    public void UpdatePassword(View view) {

        String getpassword=SelectData();
        if (oldpasd.getText().toString().equals(""))
        {
            Toast.makeText(ChangePassWord.this, "Enter old password first", Toast.LENGTH_SHORT).show();

        }

        else if (new_pass.getText().toString().equals(""))
        {
            Toast.makeText(ChangePassWord.this, "Enter  password first", Toast.LENGTH_SHORT).show();
        }
        else if (confirm_pass.getText().toString().equals(""))
        {
            Toast.makeText(ChangePassWord.this, "Enter confirm password first", Toast.LENGTH_SHORT).show();
        }
        else if (oldpasd.getText().toString().equals(getpassword)) {
            if (new_pass.getText().toString().equals(confirm_pass.getText().toString())) {
                dataBaseHandler.updateRecord(new_pass.getText().toString(), "1");
                Toast.makeText(ChangePassWord.this, "password updated Successfully..!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ChangePassWord.this, "enter the password correctly!", Toast.LENGTH_LONG).show();

            }
        }
        else
        {
            Toast.makeText(ChangePassWord.this, "Sry plz enter the old password correctly", Toast.LENGTH_SHORT).show();
        }

    }


}

