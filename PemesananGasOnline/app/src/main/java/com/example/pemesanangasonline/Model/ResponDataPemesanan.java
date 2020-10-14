package com.example.pemesanangasonline.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponDataPemesanan {

    String kode;
    String pesan;

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

    List<DataPemesanan> result;

    public List<DataPemesanan> getResult() {
        return result;
    }

    public void setResult(List<DataPemesanan> result) {
        this.result = result;
    }
}
