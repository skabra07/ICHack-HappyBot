package com.example.suyash.emotionapi;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;


public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "emotions.db";
    public static final String TABLE_EMOTIONS = "emotions";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_EMOTION = "emotion";



    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_EMOTIONS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_TIME + " TEXT , " +
                COLUMN_EMOTION + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMOTIONS);
        onCreate(db);
    }

    public void addEmotion(Emotion emotion){
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMOTION, emotion.get_emotion());
        values.put(COLUMN_TIME,emotion.get_timestamp());
        SQLiteDatabase db = getWritableDatabase(); // db is database we are going to write to
        db.insert(TABLE_EMOTIONS, null, values);
        db.close();
    }

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EMOTIONS + " WHERE 1"; // selecting everything from the table where 1 (which means every condition is met

        //Cursor points to a location in your results - like a pointer
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("emotion")) != null) { // if the row doest have a empty value for the column productName then do the following
                dbString += c.getString(c.getColumnIndex("time"));
                dbString += " --- ";
                dbString += c.getString(c.getColumnIndex("emotion"));//get the String in the column productName and add it to dbSring
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }
}
