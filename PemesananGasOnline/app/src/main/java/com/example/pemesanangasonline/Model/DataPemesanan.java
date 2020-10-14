package com.example.pemesanangasonline.Model;

public class DataPemesanan {
    String id_pemesanan, user_id, barang_id, nama, no_telp, nama_barang, alamat, jumlah_pemesanan, tanggal_pemesanan, biaya_antar, total_biaya, metode_pembayaran, bukti_transfer, ket_status, status_id;

    public String getMetode_pembayaran() {
        return metode_pembayaran;
    }

    public void setMetode_pembayaran(String metode_pembayaran) {
        this.metode_pembayaran = metode_pembayaran;
    }

    public String getBukti_transfer() {
        return bukti_transfer;
    }

    public void setBukti_transfer(String bukti_transfer) {
        this.bukti_transfer = bukti_transfer;
    }

    public String getStatus_id() {
        return status_id;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getKet_status() {
        return ket_status;
    }

    public void setKet_status(String ket_status) {
        this.ket_status = ket_status;
    }

    public String getJumlah_pemesanan() {
        return jumlah_pemesanan;
    }

    public void setJumlah_pemesanan(String jumlah_pemesanan) {
        this.jumlah_pemesanan = jumlah_pemesanan;
    }

    public String getId_pemesanan() {
        return id_pemesanan;
    }

    public void setId_pemesanan(String id_pemesanan) {
        this.id_pemesanan = id_pemesanan;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBarang_id() {
        return barang_id;
    }

    public void setBarang_id(String barang_id) {
        this.barang_id = barang_id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }



    public String getTanggal_pemesanan() {
        return tanggal_pemesanan;
    }

    public void setTanggal_pemesanan(String tanggal_pemesanan) {
        this.tanggal_pemesanan = tanggal_pemesanan;
    }

    public String getBiaya_antar() {
        return biaya_antar;
    }

    public void setBiaya_antar(String biaya_antar) {
        this.biaya_antar = biaya_antar;
    }

    public String getTotal_biaya() {
        return total_biaya;
    }

    public void setTotal_biaya(String total_biaya) {
        this.total_biaya = total_biaya;
    }
}
