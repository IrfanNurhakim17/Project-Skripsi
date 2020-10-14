package com.example.pemesanangasonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pemesanangasonline.Model.DataBarang;
import com.example.pemesanangasonline.Model.ResponDataPemesanan;
import com.example.pemesanangasonline.api.ApiRequestData;
import com.example.pemesanangasonline.api.Retroserver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPengiriman extends AppCompatActivity {
    TextView id_pemesanan, nama_pemesan, no_telp, alamat, nama_barang, jumlah_barang, tanggal_pemesanan, biaya_antar,
            total_biaya, terima_pesanan, metode_pembayaran, statusPemesanan, text_foto, total_harga ;
    Button lihat_foto, unggah;
    ImageView foto, foto_transfer;
    AppCompatImageView close_foto;
    Dialog dialog;
    RelativeLayout relativeLayout;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengiriman);

        id_pemesanan = (TextView)findViewById(R.id.detail_idpemesanan);
        nama_pemesan = (TextView)findViewById(R.id.detail_namaPemesan);
        no_telp = (TextView)findViewById(R.id.detail_noTelp);
        alamat = (TextView)findViewById(R.id.detail_alamat);
        nama_barang= (TextView)findViewById(R.id.detail_namaBarang);
        jumlah_barang = (TextView)findViewById(R.id.detail_jumlahBarang);
        tanggal_pemesanan = (TextView)findViewById(R.id.detail_tanggalPemesanan);
        biaya_antar = (TextView)findViewById(R.id.detail_biayaAntar);
        total_biaya = (TextView)findViewById(R.id.detail_totalBiaya);
        total_harga = (TextView)findViewById(R.id.detail_total_harga);
        statusPemesanan = (TextView)findViewById(R.id.detail_status);
        terima_pesanan = (TextView)findViewById(R.id.pesanan_berhasil);
        metode_pembayaran = (TextView)findViewById(R.id.detail_metodeP);
        text_foto = (TextView)findViewById(R.id.text_fotoP);
        lihat_foto = (Button) findViewById(R.id.lihat_fotoP);
//        unggah = (Button) findViewById(R.id.unggah);
        foto = (ImageView) findViewById(R.id.bukti_transferP);
        dialog = new Dialog(DetailPengiriman.this);
        relativeLayout = (RelativeLayout) findViewById(R.id.bungkus_tfP);
        linearLayout = (LinearLayout) findViewById(R.id.bungkus_bukti_tfP);

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
        final String ametode_pembayaran = data.getStringExtra("metode_pembayaran");
        final String abukti_transfer = data.getStringExtra("bukti_transfer");
        final String astatus_pemesanan = data.getStringExtra("status_pemesanan");
        final String astatus_id = data.getStringExtra("status_id");

        if (astatus_id.equals("2")){
            terima_pesanan.setText("Kirim Pesanan");
        }
        else if (astatus_id.equals("3"))
        {
            terima_pesanan.setText("Sampai Tujuan");
        }
        else
        {
            terima_pesanan.setText("Akhiri Pesanan");
        }
//
        id_pemesanan.setText(aid_pemesanan);
        nama_pemesan.setText(anama_pemesan);
        no_telp.setText(ano_telp);
        alamat.setText(aalamat);
        nama_barang.setText(anama_barang);
        jumlah_barang.setText(ajumlah_pemesanan);

        String tanggal = atanggal_pemesanan;
        String wp = tanggal.substring(11,13);
        String wpm = tanggal.substring(14,16);
        String wk = tanggal.substring(19,21);
        tanggal = tanggal.substring(0,10) + " " + wp +":" + wpm + " " + wk;
        tanggal_pemesanan.setText(tanggal);

        String ba = insertString(abiaya_antar);
        biaya_antar.setText("Rp"+ba);
        String tb = insertString(atotal_biaya);
        total_biaya.setText("Rp"+tb);
        metode_pembayaran.setText(ametode_pembayaran);
        statusPemesanan.setText(astatus_pemesanan);

        ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
        Call<DataBarang> getData = api.tampilBarang();
        getData.enqueue(new Callback<DataBarang>() {
            @Override
            public void onResponse(Call<DataBarang> call, Response<DataBarang> response) {
                String aharga_barang = response.body().getHarga_barang();
                int jb = Integer.parseInt(ajumlah_pemesanan);
                int harga = Integer.parseInt(aharga_barang);
                harga = harga * jb;

                aharga_barang = Integer.toString(harga);
                aharga_barang = insertString(aharga_barang);
                total_harga.setText("Rp"+aharga_barang);


            }

            @Override
            public void onFailure(Call<DataBarang> call, Throwable t) {

            }
        });

        if (ametode_pembayaran.equals("Tunai"))
        {
            linearLayout.setVisibility(View.GONE);
        }

        final Retroserver retroserver = new Retroserver();
        if (abukti_transfer != null)
        {
            text_foto.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);

            Glide.with(this)
                    .load(retroserver.url()+abukti_transfer)
                    .into(foto);

        }
//
        terima_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status_pemesanan = "";
                if (astatus_id.equals("2")){
                    status_pemesanan = "3";
                }
                else if (astatus_id.equals("3"))
                {
                    status_pemesanan = "4";
                }
                else
                {
                    status_pemesanan = "5";
                }

                ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);

                Call<ResponDataPemesanan> updateData = api.updatePemesanan(aid_pemesanan,auser_id,abarang_id,ajumlah_pemesanan,
                        atanggal_pemesanan,abiaya_antar,atotal_biaya, ametode_pembayaran, abukti_transfer, status_pemesanan);
                updateData.enqueue(new Callback<ResponDataPemesanan>() {
                    @Override
                    public void onResponse(Call<ResponDataPemesanan> call, Response<ResponDataPemesanan> response) {
                        String kode = response.body().getKode();
                        String pesan = response.body().getPesan();

                        if(kode.equals("1"))
                        {
                            Toast.makeText(DetailPengiriman.this, "Pesanan Berhasil!" ,Toast.LENGTH_SHORT).show();
                            Intent Gotampil = new Intent(DetailPengiriman.this, LihatPengiriman.class);
                            startActivity(Gotampil);
                        }
                        else
                        {
                            Toast.makeText(DetailPengiriman.this, "Gagal Menambahkan Data",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponDataPemesanan> call, Throwable t) {

                    }
                });
            }
        });
    }

    public String insertString(String originalString){
        String newString = new String();
        String stringToBeInserted = ".";
        for (int i = 0; i < originalString.length(); i++) {

            newString += originalString.charAt(i);

            if (originalString.length() == 6) {
                if (i == 2) {
                    newString += stringToBeInserted;
                }

            }
            if (originalString.length() == 5) {
                if (i == 1) {
                    newString += stringToBeInserted;
                }

            }
            if (originalString.length() == 4) {
                if (i == 0) {
                    newString += stringToBeInserted;
                }
            }
        }

        // return the modified String
        return newString;
    }
}
