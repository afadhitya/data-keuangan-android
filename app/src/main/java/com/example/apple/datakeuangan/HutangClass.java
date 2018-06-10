package com.example.apple.datakeuangan;

public class HutangClass {
    private int idHutang;
    private String keteranganHutang;
    private int jumlahHutang;

    public HutangClass() {
    }

    public String getKeteranganHutang() {
        return keteranganHutang;
    }

    public void setKeteranganHutang(String keteranganHutang) {
        this.keteranganHutang = keteranganHutang;
    }

    public HutangClass(int idHutang, String keteranganHutang, int jumlahHutang) {

        this.idHutang = idHutang;
        this.keteranganHutang = keteranganHutang;
        this.jumlahHutang = jumlahHutang;
    }

    public int getIdHutang() {
        return idHutang;
    }

    public void setIdHutang(int idHutang) {
        this.idHutang = idHutang;
    }

    public int getJumlahHutang() {
        return jumlahHutang;
    }

    public void setJumlahHutang(int jumlahHutang) {
        this.jumlahHutang = jumlahHutang;
    }
}
