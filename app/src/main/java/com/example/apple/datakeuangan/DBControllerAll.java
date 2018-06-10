package com.example.apple.datakeuangan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBControllerAll extends SQLiteOpenHelper {

    public DBControllerAll(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, "DataKeuangan.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PENYIMPANAN (ID_PENYIMPANAN INTEGER PRIMARY KEY AUTOINCREMENT, NAMA_PENYIMPANAN TEXT, ISI_PENYIMPANAN INTEGER);");
        db.execSQL("CREATE TABLE HUTANG (ID_HUTANG INTEGER PRIMARY KEY AUTOINCREMENT, KETERANGAN_HUTANG TEXT, ISI_HUTANG INTEGER);");
        db.execSQL("INSERT INTO PENYIMPANAN (NAMA_PENYIMPANAN, ISI_PENYIMPANAN) VALUES ('Dompet', '100000');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
