package com.example.securepass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "credentialManager";
    private static final String TABLE_CREDENTIALS = "credentials";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_URL = "url";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CREDENTIALS_TABLE = "CREATE TABLE " + TABLE_CREDENTIALS + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TITLE + " TEXT, "
                + KEY_USERNAME + " TEXT, "
                + KEY_PASSWORD + " TEXT, "
                +KEY_URL + " TEXT" + " ) ";

        db.execSQL(CREATE_CREDENTIALS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDENTIALS);

        onCreate(db);
    }

    public boolean addCredential(Credential credential){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, credential.getTitle());
        values.put(KEY_USERNAME, credential.getUsername());
        values.put(KEY_PASSWORD, credential.getPassword());
        values.put(KEY_URL, credential.getUrl());

        long result = db.insert(TABLE_CREDENTIALS, null, values);
        db.close();
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }

    Credential getCredential(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CREDENTIALS, new String[]{KEY_ID,KEY_TITLE,KEY_USERNAME,KEY_PASSWORD,KEY_URL}, KEY_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        Credential credential = new Credential(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return credential;
    }

    public List<Credential> getAllCredentials(){
        List<Credential> credentialList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CREDENTIALS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                Credential credential = new Credential();
                credential.setId(Integer.parseInt(cursor.getString(0)));
                credential.setTitle(cursor.getString(1));
                credential.setUsername(cursor.getString(2));
                credential.setPassword(cursor.getString(3));
                credential.setUrl(cursor.getString(4));

                credentialList.add(credential);
            }while (cursor.moveToNext());
        }
        return credentialList;
    }

    public int updateCredential(Credential credential){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, credential.getTitle());
        values.put(KEY_USERNAME, credential.getUsername());
        values.put(KEY_PASSWORD, credential.getPassword());
        values.put(KEY_URL, credential.getUrl());

        return db.update(TABLE_CREDENTIALS, values, KEY_ID + "=?",
                new String[]{String.valueOf(credential.getId())});
    }

    public void deleteCredential(Credential credential){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CREDENTIALS, KEY_ID + "=?",
                new String[]{String.valueOf(credential.getId())});
        db.close();
    }

    public int getCredentialCount(){
        String countQuery = "SELECT * FROM " + TABLE_CREDENTIALS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        //db.close();

        return cursor.getCount();
    }

    public List<String> getAllURLs(){
        List<String> URLsList = new ArrayList<>();
        List<Credential> credentials = this.getAllCredentials();
        for (Credential c : credentials){
            String url = c.getUrl();
            URLsList.add(url);
        }
        return URLsList;
    }
}