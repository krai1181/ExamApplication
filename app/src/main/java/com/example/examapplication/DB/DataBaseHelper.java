package com.example.examapplication.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

   public static final String TAG = "DataBaseHelper";


   public static final String TABLE_NAME = "movies";
   public static final int DATABASE_VERSION = 1;

   public static final String COL1 = "ID";
   public static final String COL2 = "title";
   public static final String COL3 = "image";
   public static final String COL4 = "rating";
   public static final String COL5 = "releaseYear";
   public static final String COL6 = "genre";


    public DataBaseHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "DataBaseHelper: Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "( ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL2 + " TEXT, "
                + COL3 + " TEXT, "
                + COL4 + " REAL, "
                + COL5 + " INTEGER, "
                + COL6 + " TEXT" + ")";
        db.execSQL(createTable);
        Log.d(TAG, "onCreate: ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean addData(String title,String image, double rating, int releaseYear, String genre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,title);
        contentValues.put(COL3,image);
        contentValues.put(COL4,rating);
        contentValues.put(COL5,releaseYear);
        contentValues.put(COL6,genre);

        Log.d(TAG, "addData: addind" + title + "and" + rating + releaseYear + genre + "to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            return false;
        }else
            return true;
    }

    public Cursor readFromData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COL2,COL3,COL4,COL5,COL6};
        Cursor cursor = db.query(TABLE_NAME,projection,null,null,null,null,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public void deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "deleteData: DELETE DB");
        db.execSQL("delete from "+ TABLE_NAME);

    }

    public boolean isTableExists(SQLiteDatabase db, String tableName) {
            if(db == null || !db.isOpen()) {
                db = getReadableDatabase();
            }

            if(!db.isReadOnly()) {
                db.close();
                db = getReadableDatabase();
            }


        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+ tableName +"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                Log.d(TAG, "isTableExists: " + cursor.getCount() + "moveToFirst" + cursor.moveToFirst());
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public boolean checkAlreadyExist(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL2 + " FROM " + TABLE_NAME + " WHERE " + COL2 + "='" + title + "'", null);
        if (cursor.moveToFirst()) {
            db.close();
            Log.d("Record  Already Exists", "Table is:" + TABLE_NAME + " ColumnName: " + title);
            return true;//record Exists
        }else {
            db.close();
            return false;
        }
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }





}
