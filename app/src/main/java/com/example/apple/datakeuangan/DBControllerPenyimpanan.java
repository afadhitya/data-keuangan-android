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
        db.execSQL("CREATE TABLE HUTANG (ID_HUTANG INTEGER PRIMARY KEY AUTOINCREMENT, KETERANGAN_HUTANG TEXT, ISI_HUTANG INTEGER);");
        db.execSQL("CREATE TABLE HISTORY (ID_HISTORY INTEGER PRIMARY KEY AUTOINCREMENT, TANGGAL TEXT, HARI TEXT, KETERANGAN_HISTORY TEXT, JUMLAH_HISTORY INTEGER, JENIS_HISTORY TEXT, ID_PENYIMPANAN INTEGER, FOREIGN KEY(ID_PENYIMPANAN) REFERENCES PENYIMPANAN(ID_PENYIMPANAN));");
        db.execSQL("INSERT INTO PENYIMPANAN (NAMA_PENYIMPANAN, ISI_PENYIMPANAN) VALUES ('Dompet', '100000');");
        db.execSQL("INSERT INTO HUTANG (KETERANGAN_HUTANG, ISI_HUTANG) VALUES ('PKM', '25000');");
        db.execSQL("INSERT INTO HISTORY(TANGGAL, HARI, KETERANGAN_HISTORY, JUMLAH_HISTORY, JENIS_HISTORY, ID_PENYIMPANAN) VALUES ('2/6/2018','SABTU','MAKAN','50000','PENGELUARAN','1');");
        db.execSQL("CREATE VIEW HISTORY_VIEW AS SELECT ID_HISTORY, TANGGAL, HARI, KETERANGAN_HISTORY, JUMLAH_HISTORY, JENIS_HISTORY, H.ID_PENYIMPANAN, NAMA_PENYIMPANAN FROM HISTORY H, PENYIMPANAN P WHERE H.ID_PENYIMPANAN=P.ID_PENYIMPANAN;");
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

    public boolean updateData(int id, int isi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String idStr = Integer.toString(id);
//        contentValues.put("ID_PENYIMPANAN",id);
//        contentValues.put("NAMA_PENYIMPANAN",name);
        contentValues.put("ISI_PENYIMPANAN",isi);
        db.update("PENYIMPANAN", contentValues, "ID_PENYIMPANAN = ?",new String[] { idStr });
        return true;
    }

    public PenyimpananClass getPenyimpanan(int id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("PENYIMPANAN",
                new String[]{"ID_PENYIMPANAN", "NAMA_PENYIMPANAN", "ISI_PENYIMPANAN"},
                "ID_PENYIMPANAN = ?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        PenyimpananClass penyimpananClass = new PenyimpananClass(
                cursor.getInt(cursor.getColumnIndex("ID_PENYIMPANAN")),
                cursor.getString(cursor.getColumnIndex("NAMA_PENYIMPANAN")),
                cursor.getInt(cursor.getColumnIndex("ISI_PENYIMPANAN")));

        // close the db connection
        cursor.close();

        return penyimpananClass;
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
