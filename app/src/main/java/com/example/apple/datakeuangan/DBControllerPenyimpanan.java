package com.example.apple.datakeuangan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBControllerPenyimpanan extends SQLiteOpenHelper {
    public DBControllerPenyimpanan(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, "DataKeuangan.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE PENYIMPANAN (ID_PENYIMPANAN INTEGER PRIMARY KEY AUTOINCREMENT, NAMA_PENYIMPANAN TEXT, ISI_PENYIMPANAN INTEGER);");
        db.execSQL("INSERT INTO PENYIMPANAN (NAMA_PENYIMPANAN, ISI_PENYIMPANAN) VALUES ('Dompet', '100000');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PENYIMPANAN;");
        onCreate(db);
    }

    public void insertPenyimpanan(String nama, int amount){
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAMA_PENYIMPANAN", nama);
        contentValues.put("ISI_PENYIMPANAN", amount);
        this.getWritableDatabase().insertOrThrow("PENYIMPANAN", "", contentValues);
    }

    public void deletePenyimpanan(int id){
        this.getWritableDatabase().delete("PENYIMPANAN", "ID_PENYIMPANAN='"+id+"'", null);
    }

    public ArrayList<PenyimpananClass> getDataPenyimpanan(){
        ArrayList<PenyimpananClass> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuffer stringBuffer = new StringBuffer();
        Cursor cursor = db.rawQuery("SELECT * FROM PENYIMPANAN;", null);
        Log.d("TAG", "Tes");

//        cursor.moveToFirst();
//        for (int cc=0; cc < cursor.getCount(); cc++){
//            cursor.moveToPosition(cc);
//            data.add(new PenyimpananClass(cursor.getInt(1),cursor.getString(2),cursor.getInt(3)));
//        }
        if(cursor.moveToFirst()){
            do {
                try{
                    PenyimpananClass penyimpananClass = new PenyimpananClass();
                    int idPenyimpanan = cursor.getInt(cursor.getColumnIndexOrThrow("ID_PENYIMPANAN"));
                    String namaPenyimpanan = cursor.getString(cursor.getColumnIndexOrThrow("NAMA_PENYIMPANAN"));
                    int isiPenyimpanan = cursor.getInt(cursor.getColumnIndexOrThrow("ISI_PENYIMPANAN"));
                    penyimpananClass.setIdPenyimpanan(idPenyimpanan);
                    penyimpananClass.setNamaPenyimpanan(namaPenyimpanan);
                    penyimpananClass.setIsiPenyimpanan(isiPenyimpanan);
                    data.add(penyimpananClass);
                }catch(Exception e){
                    Log.e("MY_DEBUG_TAG", "Error " + e.toString());
                }

            } while (cursor.moveToNext());
        }
        db.close();


        for (PenyimpananClass mo:data){
            Log.d("Hellomo", mo.getNamaPenyimpanan());
        }

        return data;

    }
}
