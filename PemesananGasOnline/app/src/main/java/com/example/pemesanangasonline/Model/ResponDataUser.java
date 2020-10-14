package com.example.pemesanangasonline.Model;

import java.util.List;

public class ResponDataUser {
    String kode, pesan;
    List<DataUser> result;

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

    public List<DataUser> getResult() {
        return result;
    }

    public void setResult(List<DataUser> result) {
        this.result = result;
    }
}
