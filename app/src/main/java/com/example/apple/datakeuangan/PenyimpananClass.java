package com.example.apple.datakeuangan;

public class PenyimpananClass {
    private int idPenyimpanan;
    private String namaPenyimpanan;
    private int isiPenyimpanan;

    public PenyimpananClass() {
    }

    public PenyimpananClass(int idPenyimpanan, String namaPenyimpanan, int isiPenyimpanan) {
        this.idPenyimpanan = idPenyimpanan;
        this.namaPenyimpanan = namaPenyimpanan;
        this.isiPenyimpanan = isiPenyimpanan;
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

    public int getIsiPenyimpanan() {
        return isiPenyimpanan;
    }

    public void setIsiPenyimpanan(int isiPenyimpanan) {
        this.isiPenyimpanan = isiPenyimpanan;
    }
}
