package com.example.asf.remoteaccess;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.net.IDN;

/**
 * Created by ASF on 16-Apr-16.
 */
public class DataBaseForPassWord extends SQLiteOpenHelper {

    static  String DataBaseName="Rgistration.db";
    public  static  final String Table_Name="Registration";
    public  static  final String Id="id";
    public  static  final String name="name";
    public  static  final String passwd="password";
    public  static  final String address="address";
    public  static  final String email="email";
    public  static  final String mobile="mobile";
    //  private static SQLiteDatabase database=null;
    private ContentValues values;
    private  SQLiteDatabase database;
    private Cursor cursor;

    public DataBaseForPassWord(Context context, String nam, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,DataBaseName,factory,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String Query="CREATE TABLE "+Table_Name+
                "("+
                Id +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                name+" TEXT);"
                ;

        db.execSQL(Query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);

        onCreate(db);
    }

    public void InsertValues(String user)
    {

        database = getWritableDatabase();
        values = new ContentValues();
        values.put(name, user);

        database.insert(Table_Name, null, values);
        database.close();

    }
    public Cursor selectRecords() {

        database = getReadableDatabase();
        cursor = database.rawQuery("select * from " + Table_Name, null);
        return cursor;
    }
    public void updateRecord(String pasd,String idd) {

        database = getWritableDatabase();
        values = new ContentValues();
        values.put(name, pasd);

        database.update(Table_Name, values, idd, null);
        database.close();
    }


}
