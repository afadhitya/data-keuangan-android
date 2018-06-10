package com.example.apple.datakeuangan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBControllerHutang extends SQLiteOpenHelper {
    public DBControllerHutang(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, "DataKeuangan.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE PENYIMPANAN (ID_PENYIMPANAN INTEGER PRIMARY KEY AUTOINCREMENT, NAMA_PENYIMPANAN TEXT, ISI_PENYIMPANAN INTEGER);");
        db.execSQL("CREATE TABLE HUTANG (ID_HUTANG INTEGER PRIMARY KEY AUTOINCREMENT, KETERANGAN_HUTANG TEXT, ISI_HUTANG INTEGER);");
        db.execSQL("CREATE TABLE HISTORY (ID_HISTORY INTEGER PRIMARY KEY AUTOINCREMENT, TANGGAL TEXT, HARI TEXT, KETERANGAN_HISTORY TEXT, JUMLAH_HISTORY INTEGER, JENIS_HISTORY TEXT, ID_PENYIMPANAN INTEGER, FOREIGN KEY(ID_PENYIMPANAN) REFERENCES PENYIMPANAN(ID_PENYIMPANAN));");
        db.execSQL("INSERT INTO PENYIMPANAN (NAMA_PENYIMPANAN, ISI_PENYIMPANAN) VALUES ('Dompet', '100000');");
        db.execSQL("INSERT INTO HUTANG (KETERANGAN_HUTANG, ISI_HUTANG) VALUES ('PKM', '25000');");
        db.execSQL("INSERT INTO HISTORY(TANGGAL, HARI, KETERANGAN_HISTORY, JUMLAH_HISTORY, JENIS_HISTORY, ID_PENYIMPANAN) VALUES ('2/6/2018','SABTU','MAKAN','50000','PENGELUARAN','1');");
//        db.execSQL("CREATE TABLE HUTANG (ID_HUTANG INTEGER PRIMARY KEY AUTOINCREMENT, KETERANGAN_HUTANG TEXT, ISI_HUTANG INTEGER);");
//        db.execSQL("INSERT INTO HUTANG (KETERANGAN_HUTANG, ISI_HUTANG) VALUES ('PKM', '25000');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS HUTANG;");
        onCreate(db);
    }

    public void insertHutang(String keterangan, int amount){
        ContentValues contentValues = new ContentValues();
        contentValues.put("KETERANGAN_HUTANG", keterangan);
        contentValues.put("ISI_HUTANG", amount);
        this.getWritableDatabase().insertOrThrow("HUTANG", "", contentValues);
    }

    public void deleteHutang(int id){
        this.getWritableDatabase().delete("HUTANG", "ID_HUTANG='"+id+"'", null);
    }

    public ArrayList<HutangClass> getDataHutang(){
        ArrayList<HutangClass> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuffer stringBuffer = new StringBuffer();
        Cursor cursor = db.rawQuery("SELECT * FROM HUTANG;", null);
        Log.d("TAG", "Tes");

//        cursor.moveToFirst();
//        for (int cc=0; cc < cursor.getCount(); cc++){
//            cursor.moveToPosition(cc);
//            data.add(new PenyimpananClass(cursor.getInt(1),cursor.getString(2),cursor.getInt(3)));
//        }
        if(cursor.moveToFirst()){
            do {
                try{
                    HutangClass hutangClass = new HutangClass();
                    int idHutang = cursor.getInt(cursor.getColumnIndexOrThrow("ID_HUTANG"));
                    String keteranganHutang = cursor.getString(cursor.getColumnIndexOrThrow("KETERANGAN_HUTANG"));
                    int isiHutang = cursor.getInt(cursor.getColumnIndexOrThrow("ISI_HUTANG"));
                    hutangClass.setIdHutang(idHutang);
                    hutangClass.setKeteranganHutang(keteranganHutang);
                    hutangClass.setJumlahHutang(isiHutang);
                    data.add(hutangClass);
                }catch(Exception e){
                    Log.e("MY_DEBUG_TAG", "Error " + e.toString());
                }

            } while (cursor.moveToNext());
        }
        db.close();


        for (HutangClass mo:data){
            Log.d("Hellomo", mo.getKeteranganHutang());
        }

        return data;

    }
}
