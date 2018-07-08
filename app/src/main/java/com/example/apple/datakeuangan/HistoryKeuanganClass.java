package com.example.apple.datakeuangan;

import java.util.Date;

public class HistoryKeuanganClass {
    private int idHistory;
    private String tanggalHistory;
    private String hariHistory;
    private String keteranganHistory;
    private String masukAtauKeluar;
    private int jumlahHistory;
    private int idPenyimpanan;
    private String namaPenyimpanan;
    private String tempat;

    public HistoryKeuanganClass() {
    }

    public HistoryKeuanganClass(int idHistory, String tanggalHistory, String hariHistory, String keteranganHistory, String masukAtauKeluar, int jumlahHistory, int idPenyimpanan, String namaPenyimpanan, String tempat) {
        this.idHistory = idHistory;
        this.tanggalHistory = tanggalHistory;
        this.hariHistory = hariHistory;
        this.keteranganHistory = keteranganHistory;
        this.masukAtauKeluar = masukAtauKeluar;
        this.jumlahHistory = jumlahHistory;
        this.idPenyimpanan = idPenyimpanan;
        this.namaPenyimpanan = namaPenyimpanan;
        this.tempat = tempat;
    }

    public int getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(int idHistory) {
        this.idHistory = idHistory;
    }

    public String getTanggalHistory() {
        return tanggalHistory;
    }

    public void setTanggalHistory(String tanggalHistory) {
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

    public String getNamaPenyimpanan() {
        return namaPenyimpanan;
    }

    public void setNamaPenyimpanan(String namaPenyimpanan) {
        this.namaPenyimpanan = namaPenyimpanan;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }
}
