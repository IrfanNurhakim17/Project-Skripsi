package com.example.pemesanangasonline.Model;

public class ResponDataGambar {
    public String kode;
    public String pesan;

    public ResponDataGambar(String kode, String pesan) {
        this.kode = kode;
        this.pesan = pesan;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}
