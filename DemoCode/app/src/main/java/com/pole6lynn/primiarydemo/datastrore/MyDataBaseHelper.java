package com.pole6lynn.primiarydemo.datastrore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    public static final String CRETAE_BOOK = "create boo table(" +
            "id integer primary key autoincrement," +
            "author text," +
            "price real" +
            "pages integer," +
            "name text)";

    private Context mContext;

    public MyDataBaseHelper(Context context, String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRETAE_BOOK);
        Toast.makeText(mContext, "create table success.",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists BOOK");

    }
}
