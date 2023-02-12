package com.layoutexample.Shopping_list_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Shopping.db";
    public static final int VERSION = 1;

    private static DataBaseHelper instance = null;

    public static DataBaseHelper getInstance(Context context){
        if (instance == null)
            instance = new DataBaseHelper(context.getApplicationContext());

        return instance;
    }

    public DataBaseHelper(@Nullable Context context ) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE Items (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "item TEXT," +
                        "isChecked INTEGER" +
                        ")";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Items");
        onCreate(db);
    }

}


