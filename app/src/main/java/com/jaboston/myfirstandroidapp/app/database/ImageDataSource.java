package com.jaboston.myfirstandroidapp.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jaboston.myfirstandroidapp.app.database.models.Image;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by josephboston on 13/05/2014.
 */
public class ImageDataSource {

    //Database fields

    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = { DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_URL };

    public ImageDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Image createImage(String imageUrl) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_URL, imageUrl);
        long insertId = database.insert(DatabaseHelper.TABLE_IMAGES, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_IMAGES,
                allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Image newImage = cursorToImage(cursor);
        cursor.close();
        return newImage;
    }

    public void deleteImage(Image comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(DatabaseHelper.TABLE_IMAGES, DatabaseHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Image> getAllImages() {
        List<Image> comments = new ArrayList<Image>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_IMAGES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Image image = cursorToImage(cursor);
            comments.add(image);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Image cursorToImage(Cursor cursor) {
        Image image = new Image();
        image.setId(cursor.getLong(0));
        image.setUrl(cursor.getString(1));
        return image;
    }
}
