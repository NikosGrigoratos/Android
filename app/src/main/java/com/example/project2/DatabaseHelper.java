package com.example.project2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.Timestamp;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "location.db";
    public static final String TABLE_NAME = "location_data";
    public static final String COL_1 = "_id";
    public static final String COL_2 = "_lon";
    public static final String COL_3 = "_lat";
    public static final String COL_4 = "_unix_timestamp";

    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME + "(" + COL_1
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " INTEGER, " + COL_3 + " FLOAT, " + COL_4 + " FLOAT);";

    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("DB IS CREATED!!!!!!!!!!!!!!!!!!!!!!");
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*Drop a table if exists in case of upgradation*/
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
