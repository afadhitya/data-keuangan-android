package com.example.apple.datakeuangan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBControllerHistoryKeuangan extends SQLiteOpenHelper {
    public DBControllerHistoryKeuangan(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, "DataKeuangan.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE PENYIMPANAN (ID_PENYIMPANAN INTEGER PRIMARY KEY AUTOINCREMENT, NAMA_PENYIMPANAN TEXT, ISI_PENYIMPANAN INTEGER);");
        db.execSQL("CREATE TABLE HUTANG (ID_HUTANG INTEGER PRIMARY KEY AUTOINCREMENT, KETERANGAN_HUTANG TEXT, ISI_HUTANG INTEGER);");
        db.execSQL("CREATE TABLE HISTORY (ID_HISTORY INTEGER PRIMARY KEY AUTOINCREMENT, TANGGAL TEXT, HARI TEXT, KETERANGAN_HISTORY TEXT, JUMLAH_HISTORY INTEGER, JENIS_HISTORY TEXT, ID_PENYIMPANAN INTEGER, TEMPAT TEXT, FOREIGN KEY(ID_PENYIMPANAN) REFERENCES PENYIMPANAN(ID_PENYIMPANAN));");
        db.execSQL("INSERT INTO PENYIMPANAN (NAMA_PENYIMPANAN, ISI_PENYIMPANAN) VALUES ('Dompet', '100000');");
        db.execSQL("INSERT INTO HUTANG (KETERANGAN_HUTANG, ISI_HUTANG) VALUES ('PKM', '25000');");
        db.execSQL("INSERT INTO HISTORY(TANGGAL, HARI, KETERANGAN_HISTORY, JUMLAH_HISTORY, JENIS_HISTORY, ID_PENYIMPANAN) VALUES ('2/6/2018','SABTU','MAKAN','50000','PENGELUARAN','1');");
        db.execSQL("CREATE VIEW HISTORY_VIEW AS SELECT ID_HISTORY, TANGGAL, HARI, KETERANGAN_HISTORY, JUMLAH_HISTORY, JENIS_HISTORY, H.ID_PENYIMPANAN, NAMA_PENYIMPANAN, TEMPAT FROM HISTORY H, PENYIMPANAN P WHERE H.ID_PENYIMPANAN=P.ID_PENYIMPANAN;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS HISTORY;");
        onCreate(db);
    }

    public long insertHistory(HistoryKeuanganClass historyKeuanganClass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TANGGAL", historyKeuanganClass.getTanggalHistory());
        contentValues.put("HARI", historyKeuanganClass.getHariHistory());
        contentValues.put("KETERANGAN_HISTORY", historyKeuanganClass.getKeteranganHistory());
        contentValues.put("JUMLAH_HISTORY", historyKeuanganClass.getJumlahHistory());
        contentValues.put("JENIS_HISTORY", historyKeuanganClass.getMasukAtauKeluar());
        contentValues.put("ID_PENYIMPANAN", historyKeuanganClass.getIdPenyimpanan());
        contentValues.put("TEMPAT", historyKeuanganClass.getTempat());
        long id = db.insert("HISTORY", "", contentValues);
        db.close();
        return id;
    }

    public void deleteHistory(int id){
        this.getWritableDatabase().delete("HISTORY", "ID_HISTORY='"+id+"'", null);
    }

    public HistoryKeuanganClass getHistory(int id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("HISTORY_VIEW",
                new String[]{"ID_HISTORY", "TANGGAL", "HARI", "KETERANGAN_HISTORY", "JUMLAH_HISTORY", "JENIS_HISTORY", "ID_PENYIMPANAN", "NAMA_PENYIMPANAN", "TEMPAT"},
                "ID_HISTORY = ?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        HistoryKeuanganClass historyKeuanganClass = new HistoryKeuanganClass(
                cursor.getInt(cursor.getColumnIndex("ID_HISTORY")),
                cursor.getString(cursor.getColumnIndex("TANGGAL")),
                cursor.getString(cursor.getColumnIndex("HARI")),
                cursor.getString(cursor.getColumnIndex("KETERANGAN_HISTORY")),
                cursor.getString(cursor.getColumnIndex("JENIS_HISTORY")),
                cursor.getInt(cursor.getColumnIndex("JUMLAH_HISTORY")),
                cursor.getInt(cursor.getColumnIndex("ID_PENYIMPANAN")),
                cursor.getString(cursor.getColumnIndex("NAMA_PENYIMPANAN")),
                cursor.getString(cursor.getColumnIndex("TEMPAT")));

        // close the db connection
        cursor.close();

        return historyKeuanganClass;
    }

    public ArrayList<HistoryKeuanganClass> getDataHistory(){
        ArrayList<HistoryKeuanganClass> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuffer stringBuffer = new StringBuffer();
        Cursor cursor = db.rawQuery("SELECT * FROM HISTORY_VIEW ORDER BY TANGGAL DESC, ID_HISTORY DESC;", null);
        Log.d("TAG", "Tes");

//        cursor.moveToFirst();
//        for (int cc=0; cc < cursor.getCount(); cc++){
//            cursor.moveToPosition(cc);
//            data.add(new PenyimpananClass(cursor.getInt(1),cursor.getString(2),cursor.getInt(3)));
//        }
        if(cursor.moveToFirst()){
            do {
                try{
                    HistoryKeuanganClass historyKeuanganClass = new HistoryKeuanganClass();
                    int idHistory = cursor.getInt(cursor.getColumnIndexOrThrow("ID_HISTORY"));
                    String tanggal = cursor.getString(cursor.getColumnIndexOrThrow("TANGGAL"));
                    String hari = cursor.getString(cursor.getColumnIndexOrThrow("HARI"));
                    String keterangan = cursor.getString(cursor.getColumnIndexOrThrow("KETERANGAN_HISTORY"));
                    int jumlah = cursor.getInt(cursor.getColumnIndexOrThrow("JUMLAH_HISTORY"));
                    String jenis = cursor.getString(cursor.getColumnIndexOrThrow("JENIS_HISTORY"));
                    int idPenyimpanan = cursor.getInt(cursor.getColumnIndexOrThrow("ID_PENYIMPANAN"));
                    String namaPenyimpanan = cursor.getString(cursor.getColumnIndexOrThrow("NAMA_PENYIMPANAN"));
                    String tempat = cursor.getString(cursor.getColumnIndexOrThrow("TEMPAT"));

                    historyKeuanganClass.setIdHistory(idHistory);
                    historyKeuanganClass.setTanggalHistory(tanggal);
                    historyKeuanganClass.setHariHistory(hari);
                    historyKeuanganClass.setKeteranganHistory(keterangan);
                    historyKeuanganClass.setJumlahHistory(jumlah);
                    historyKeuanganClass.setMasukAtauKeluar(jenis);
                    historyKeuanganClass.setIdPenyimpanan(idPenyimpanan);
                    historyKeuanganClass.setNamaPenyimpanan(namaPenyimpanan);
                    historyKeuanganClass.setTempat(tempat);
                    data.add(historyKeuanganClass);
                }catch(Exception e){
                    Log.e("MY_DEBUG_TAG", "Error " + e.toString());
                }

            } while (cursor.moveToNext());
        }
        db.close();


        for (HistoryKeuanganClass mo:data){
            Log.d("Hellomo", mo.getKeteranganHistory());
        }

        return data;

    }
}
