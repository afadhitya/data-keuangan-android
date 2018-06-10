package com.example.apple.datakeuangan;

import java.util.Date;

public class HistoryKeuanganClass {
    private int idHistory;
    private Date tanggalHistory;
    private String hariHistory;
    private String keteranganHistory;
    private String masukAtauKeluar;
    private int jumlahHistory;
    private int idPenyimpanan;

    public HistoryKeuanganClass() {
    }

    public int getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(int idHistory) {
        this.idHistory = idHistory;
    }

    public Date getTanggalHistory() {
        return tanggalHistory;
    }

    public void setTanggalHistory(Date tanggalHistory) {
        this.tanggalHistory = tanggalHistory;
    }

    public String getHariHistory() {
        return hariHistory;
    }

    public void setHariHistory(String hariHistory) {
        this.hariHistory = hariHistory;
    }

    public String getKeteranganHistory() {
        return keteranganHistory;
    }

    public void setKeteranganHistory(String keteranganHistory) {
        this.keteranganHistory = keteranganHistory;
    }

    public String getMasukAtauKeluar() {
        return masukAtauKeluar;
    }

    public void setMasukAtauKeluar(String masukAtauKeluar) {
        this.masukAtauKeluar = masukAtauKeluar;
    }

    public int getJumlahHistory() {
        return jumlahHistory;
    }

    public void setJumlahHistory(int jumlahHistory) {
        this.jumlahHistory = jumlahHistory;
    }

    public int getIdPenyimpanan() {
        return idPenyimpanan;
    }

    public void setIdPenyimpanan(int idPenyimpanan) {
        this.idPenyimpanan = idPenyimpanan;
    }
}
