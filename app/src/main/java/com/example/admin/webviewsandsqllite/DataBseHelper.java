package com.example.admin.webviewsandsqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Admin on 8/7/2017.
 */

public class DataBseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "MyDataBase";
    private static final int DATABASE_VERSION = 3;
    public static final String TABLE_NAME = "Contacts";
    public static final String CONTACT_ID = "Id";
    public static final String CONTACT_NAME = "Name";
    public static final String CONTACT_NUMBER ="Number";
    public static final String CONTACT_EMAIL ="Email";
    public static final String CONTACT_SOCIAL ="Social";
    public static final String CONTACT_PHOTO ="Photo";
    public static final String TAG = "SQLTAG";
    String[] args = null;
    public DataBseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("+
                CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CONTACT_NAME + " TEXT," +
                CONTACT_NUMBER + " TEXT," +
                CONTACT_EMAIL + " TEXT," +
                CONTACT_SOCIAL + " TEXT,"+
                CONTACT_PHOTO + " BLOB "+
                ")";
            sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void saveNewContact(Contact contact){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(CONTACT_NAME, contact.getName());
        contentValues.put(CONTACT_NUMBER,contact.getNumber());
        contentValues.put(CONTACT_EMAIL, contact.getEmail());
        contentValues.put(CONTACT_SOCIAL,contact.getSocial());
        contentValues.put(CONTACT_PHOTO,contact.getPhoto());
        database.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG, "saveNewContact: " + contentValues);
    }
    public ArrayList<Contact> getContacts(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "";
        if(!id.equals("-1")){
            query = "Select * from " +TABLE_NAME + " where " + CONTACT_ID + " = ?";
            args = new String[]{id};
        }
        else
            query = "Select * from " +TABLE_NAME ;
        Cursor cursor = database.rawQuery(query,args);
        ArrayList<Contact> contactList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                contactList.add(new Contact(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getBlob(5)));
            }while (cursor.moveToNext());
        }
        return contactList;
    }
}
