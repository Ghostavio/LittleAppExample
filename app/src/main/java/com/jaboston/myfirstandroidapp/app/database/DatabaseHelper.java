package com.jaboston.myfirstandroidapp.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by josephboston on 13/05/2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // TABLES
    public static final String TABLE_IMAGES = "images";

    // COLUMNS
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_URL = "url";

    private static final String DATABASE_NAME = "jbapp";
    public static final int DATABASE_VERSION = 1;

    // CREATE DATABASES
    private static final String DATABASE_CREATE = "create table "
    + TABLE_IMAGES + "("
    + COLUMN_ID + " integer primary key autoincrement, "
    + COLUMN_URL + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        onCreate(db);
    }

}
