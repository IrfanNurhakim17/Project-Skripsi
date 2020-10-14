package com.example.pemesanangasonline.Model;

import java.util.List;

public class ResponDataBarang {
    String kode, pesan;

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

    List<DataBarang> result;

    public List<DataBarang> getResult() {
        return result;
    }

    public void setResult(List<DataBarang> result) {
        this.result = result;
    }
}
