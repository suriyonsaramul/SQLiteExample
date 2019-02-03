package com.example.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ContactSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contactdb";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "contact";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";

    public ContactSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database Operations", "Database Created....");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT"
                + ");"
        );
        Log.d("Database Operations", "Table Created....");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(Contact contact) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_EMAIL, contact.getEmail());

        long row = db.insert(TABLE_NAME, null, values);
        if(row>0)
            result = true;

        db.close();
        return result;
    }

    public List<Contact> selectAll(){
        List<Contact> contacts = new ArrayList<Contact>();
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getString(0));
                contact.setName(cursor.getString(1));
                contact.setEmail(cursor.getString(2));

                contacts.add(contact);
            }while(cursor.moveToNext());
        }

        db.close();
        return contacts;
    }

    public boolean update(Contact contact){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_EMAIL, contact.getEmail());

        int row = db.update(TABLE_NAME, values,COLUMN_ID + " = ?",
                new String[]{ contact.getId() });

        if(row > 0)
            result = true;

        db.close();
        return result;
    }

    public boolean delete(Contact contact) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        int row = db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[] { contact.getId() });
        if(row > 0)
            result = true;
        db.close();
        return result;
    }

    public Contact selectById(int id) {
        Contact contact = new Contact();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            contact.setId(cursor.getString(0));
            contact.setName(cursor.getString(1));
            contact.setEmail(cursor.getString(2));
        }

        db.close();
        return contact;
    }

    public List<Contact> selectByName(String name) {
        List<Contact> contacts = new ArrayList<Contact>();
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_NAME + " LIKE '%" + name + "%'";

        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getString(0));
                contact.setName(cursor.getString(1));
                contact.setEmail(cursor.getString(2));

                contacts.add(contact);
            }while(cursor.moveToNext());
        }

        db.close();
        return contacts;
    }
}
