package com.example.pemesanangasonline;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

public class DetailRiwayat extends Activity {
    TextView id_pemesanan, nama_pemesan, no_telp, alamat, nama_barang, jumlah_barang, tanggal_pemesanan, biaya_antar, total_biaya, terima_pesanan, tolak_pesanan, statusPemesanan ;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat);

        dialog = new Dialog(this);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        nama_pemesan = (TextView)findViewById(R.id.detail_riwayat_namaPemesan);
        nama_barang= (TextView)findViewById(R.id.detail_riwayat_namaBarang);
        jumlah_barang = (TextView)findViewById(R.id.detail_riwayat_jumlahBarang);
        tanggal_pemesanan = (TextView)findViewById(R.id.detail_riwayat_tanggalPemesanan);
        biaya_antar = (TextView)findViewById(R.id.detail_riwayat_biayaAntar);
        total_biaya = (TextView)findViewById(R.id.detail_riwayat_totalBiaya);
        statusPemesanan = (TextView)findViewById(R.id.detail_riwayat_status);

        Intent data = getIntent();
        final String aid_pemesanan = data.getStringExtra("id_pemesanan");
        final String auser_id = data.getStringExtra("user_id");
        final String abarang_id = data.getStringExtra("barang_id");
        String anama_pemesan = data.getStringExtra("nama_pemesan");
        String ano_telp = data.getStringExtra("no_telp");
        String aalamat = data.getStringExtra("alamat");
        String anama_barang = data.getStringExtra("nama_barang");
        final String ajumlah_pemesanan = data.getStringExtra("jumlah_pemesanan");
        final String atanggal_pemesanan = data.getStringExtra("tanggal_pemesanan");
        final String abiaya_antar = data.getStringExtra("biaya_antar");
        final String atotal_biaya = data.getStringExtra("total_biaya");
        final String astatus_pemesanan = data.getStringExtra("status_id");

        id_pemesanan.setText(aid_pemesanan);
        nama_pemesan.setText(anama_pemesan);
        no_telp.setText(ano_telp);
        alamat.setText(aalamat);
        nama_barang.setText(anama_barang);
        jumlah_barang.setText(ajumlah_pemesanan);
        tanggal_pemesanan.setText(atanggal_pemesanan);
        biaya_antar.setText(abiaya_antar);
        total_biaya.setText(atotal_biaya);
        statusPemesanan.setText(astatus_pemesanan);
    }
}
