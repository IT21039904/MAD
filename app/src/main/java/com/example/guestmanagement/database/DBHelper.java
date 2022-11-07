package com.guest.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.guest.CHEventConstants;
import com.guest.CHEventModel;
import com.guest.NConstants;
import com.guest.NModelRecord;
import com.guest.Session.Session;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "guest.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String  SQL_CREATE_ENTRIES = "CREATE TABLE " + notesMaster.notes.TABLE_NAME + " (" +
                notesMaster.notes._ID + " INTEGER PRIMARY KEY," +
                notesMaster.notes.COLUMN_FIRST + " Text," +
                notesMaster.notes.COLUMN_LAST + " Text," +
                notesMaster.notes.COLUMN_CONTACT + " Number," +
                notesMaster.notes.COLUMN_EMAIL + " Text," +
                notesMaster.notes.COLUMN_PARTICIPANT + " Number," +
                notesMaster.notes.COLUMN_CONFIRM + " Text," +
                notesMaster.notes.COLUMN_NOTE + " Text)" ;

        db.execSQL(SQL_CREATE_ENTRIES); //Create note table


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + notesMaster.notes.TABLE_NAME);
    }

    //note control ******************************************************************************************************************************************

    public long addnote(String first, String last, String mail,
                        String email, String participant, String confirm){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(notesMaster.notes.COLUMN_FIRST first);
        values.put(notesMaster.notes.COLUMN_LAST, last);
        values.put(notesMaster.notes.COLUMN_CONTACT, contact);
        values.put(notesMaster.notes.COLUMN_EMAIL, email);
        values.put(notesMaster.notes.COLUMN_PARTICIPANT, participant);
        values.put(notesMaster.notes.COLUMN_CONFIRM, confirm);
        values.put(notesMaster.notes.COLUMN_NOTE, "note");

        long newnoteId = db.insert(notesMaster.notes.TABLE_NAME,
                null, values);
        return newnoteId;
    }



    public String[] loginnote(String email, String confirm){

        SQLiteDatabase db = getReadableDatabase();

        String[] projection ={
                notesMaster.notes._ID,
                notesMaster.notes.COLUMN_FIRST,
                notesMaster.notes.COLUMN_LAST,
                notesMaster.notes.COLUMN_CONTACT,
                notesMaster.notes.COLUMN_EMAIL,
                notesMaster.notes.COLUMN_PARTICIPANT,
                notesMaster.notes.COLUMN_CONFIRM,
                notesMaster.notes.COLUMN_NOTE,
        };

        String sortOrder = notesMaster.notes.COLUMN_FIRST + " DESC";

        Cursor cursor = db.query(
                notesMaster.notes.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
              sortOrder
        );

        while(cursor.moveToNext()){
            String em = cursor.getString(cursor.getColumnIndexOrThrow(notesMaster.notes.COLUMN_LAST));
            String fn = cursor.getString(cursor.getColumnIndexOrThrow(notesMaster.notes.COLUMN_CONTACT));
            String type = cursor.getString(cursor.getColumnIndexOrThrow(notesMaster.notes.COLUMN_MAIL);
            String id = cursor.getString(cursor.getColumnIndexOrThrow(notesMaster.notes._PARTICIPANT));
            String pw = cursor.getString(cursor.getColumnIndexOrThrow(notesMaster.notes.COLUMN_CONFIRM));

        }
        cursor.close();
        String empty[] = null;
        return empty;
    }
    public boolean getAdmin(){

        SQLiteDatabase db = getReadableDatabase();

        String[] projection ={
                notesMaster.notes._ID,
                notesMaster.notes.COLUMN_FIRST,
                notesMaster.notes.COLUMN_LAST,
                notesMaster.notes.COLUMN_CONTACT,
                notesMaster.notes.COLUMN_EMAIL,
                notesMaster.notes.COLUMN_PARTICIPANT,
                notesMaster.notes.COLUMN_CONFIRM,
                notesMaster.notes.COLUMN_NOTE,
        };



        String sortOrder = notesMaster.notes.COLUMN_FIRST + " DESC";

        Cursor cursor = db.query(
                notesMaster.notes.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        while(cursor.moveToNext()){
            String type = cursor.getString(cursor.getColumnIndexOrThrow(notesMaster.notes.COLUMN_NOTE));

            if(type.equals("administrator")) {
                return true;
            }

        }
        cursor.close();
        return false;
    }

    public long addAdmin(){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(notesMaster.notes.COLUMN_FIRST, "Admin");
        values.put(notesMaster.notes.COLUMN_LAST, "1976/01/01");
        values.put(notesMaster.notes.COLUMN_CONTACT, "123456789");
        values.put(notesMaster.notes.COLUMN_EMAIL, "admin@pansala.com");
        values.put(notesMaster.notes.COLUMN_PARTICIPANT, "0713168965");
        values.put(notesMaster.notes.COLUMN_CONFIRM, "Admin123");
        values.put(notesMaster.notes.COLUMN_NOTE, "administrator");

        long newnoteId = db.insert(notesMaster.notes.TABLE_NAME, null, values);
        return newnoteId;
    }

    public void CreateGuestTempleTable(String Table_Name)
    {
        SQLiteDatabase db = getWritableDatabase();

        String  SQL_CREATE_noteTEMPLE = "CREATE TABLE IF NOT EXISTS " + Table_Name + " (" +
                TempleMaster.noteTemple._ID + " INTEGER PRIMARY KEY," +
                TempleMaster.noteTemple.COLUMN_TEMPLEID + " Text)" ;

        db.execSQL(SQL_CREATE_noteTEMPLE);
        db.close();
    }

    public Cursor getGuestData(int id){

        SQLiteDatabase db = getReadableDatabase();

        String[] projection ={
                notesMaster.notes._ID,
                notesMaster.notes.COLUMN_FIRST,
                notesMaster.notes.COLUMN_LAST,
                notesMaster.notes.COLUMN_CONTACT,
                notesMaster.notes.COLUMN_EMAIL,
                notesMaster.notes.COLUMN_PARTICIPANT,
                notesMaster.notes.COLUMN_CONFIRM,
                notesMaster.notes.COLUMN_NOTE,
        };

        String selection = notesMaster.notes._ID + "=" + id;

        Cursor cursor = db.query(
                notesMaster.notes.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
                return cursor;
        }
        return null;
    }

    // Update method
    public boolean updateInfo(int id, String first, String last, String mail,
                              String email, String participant, String confirm) {

        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(notesMaster.notes.COLUMN_FIRST, first);
        values.put(notesMaster.notes.COLUMN_LAST, last);
        values.put(notesMaster.notes.COLUMN_CONTACT, mail);
        values.put(notesMaster.notes.COLUMN_EMAIL, email);
        values.put(notesMaster.notes.COLUMN_PARTICIPANT, participant);
        values.put(notesMaster.notes.COLUMN_CONFIRM, confirm);
        values.put(notesMaster.notes.COLUMN_NOTE, "note");

        // Which row to update
        String selection = notesMaster.notes._ID + " LIKE ?";
        // Specify arguments n place holder
        String[] selectionArgs = {String.valueOf(id)};


        int count = db.update(notesMaster.notes.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteAccount(int id) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = notesMaster.notes._ID + " LIKE ?";

        String[] selectionArgs = {String.valueOf(id)};
        int count = db.delete(notesMaster.notes.TABLE_NAME, selection, selectionArgs);

        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }


   