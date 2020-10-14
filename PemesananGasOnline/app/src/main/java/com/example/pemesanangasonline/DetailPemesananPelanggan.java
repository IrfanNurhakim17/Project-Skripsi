package com.example.pemesanangasonline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pemesanangasonline.Model.DataPemesanan;
import com.example.pemesanangasonline.Model.DataUser;
import com.example.pemesanangasonline.Model.ResponDataPemesanan;
import com.example.pemesanangasonline.Session.SessionManagement;
import com.example.pemesanangasonline.Session.User;
import com.example.pemesanangasonline.api.ApiRequestData;
import com.example.pemesanangasonline.api.Retroserver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPemesananPelanggan extends AppCompatActivity {
    TextView id_pemesanan, nama_pemesan, no_telp, alamat,
            nama_barang, jumlah_barang, tanggal_pemesanan,
            biaya_antar, total_biaya, total_harga, metode_pembayaran;
    LinearLayout ln, hk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemesanan_pelanggan);

        id_pemesanan = (TextView)findViewById(R.id.detail_idPemesananP);
        nama_pemesan = (TextView)findViewById(R.id.detail_namaPemesanP);
        no_telp = (TextView)findViewById(R.id.detail_noTelpP);
        alamat = (TextView)findViewById(R.id.detail_alamatP);
        nama_barang= (TextView)findViewById(R.id.detail_namabarangP);
        jumlah_barang = (TextView)findViewById(R.id.detail_jumlahBarangP);
        tanggal_pemesanan = (TextView)findViewById(R.id.detail_tanggalPemesananP);
        biaya_antar = (TextView)findViewById(R.id.detail_biayaAntarP);
        total_biaya = (TextView)findViewById(R.id.detail_totalBiayaP);
        total_harga = (TextView)findViewById(R.id.detail_totalharga);
        metode_pembayaran = (TextView)findViewById(R.id.detail_metode_pembayaran);
        ln = (LinearLayout) findViewById(R.id.bungkus_detail_pemesanan);
        hk = (LinearLayout) findViewById(R.id.halamankosong);

        SessionManagement sessionManagement = new SessionManagement(DetailPemesananPelanggan.this);
        final String Username = sessionManagement.getData();

        final ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
        Call<DataUser> sendData = api.getprofil(Username);
        sendData.enqueue(new Callback<DataUser>() {
            @Override
            public void onResponse(Call<DataUser> call, Response<DataUser> response) {
                Log.d("RETRO", "response : " + response.body().toString());
                final String id_user = response.body().getId_user();
                Call<DataPemesanan> getData = api.getPesananPelanggan(id_user,"detail_pemesanan");
                getData.enqueue(new Callback<DataPemesanan>() {
                    @Override
                    public void onResponse(Call<DataPemesanan> call, Response<DataPemesanan> response) {
                        Log.d("RETRO", "response : " + response.body().toString());
                        final String aid_pemesanan = response.body().getId_pemesanan();
                        final String anama_pemesan = response.body().getNama();
                        final String ano_telp = response.body().getNo_telp();
                        final String aalamat = response.body().getAlamat();
                        final String anama_barang = response.body().getNama_barang();
                        final String ajumlah_pemesanan = response.body().getJumlah_pemesanan();
                        final String atanggal_pemesanan = response.body().getTanggal_pemesanan();
                        String abiaya_antar = response.body().getBiaya_antar();
                        String atotal_biaya = response.body().getTotal_biaya();
                        String ametode_pembayaran = response.body().getMetode_pembayaran();
                        SessionManagement sessionManagement = new SessionManagement(DetailPemesananPelanggan.this);
                        String Barang = sessionManagement.getHarga_Barang();

                        if (aid_pemesanan == null)
                        {
                            ln.setVisibility(View.GONE);
                            hk.setVisibility(View.VISIBLE);
                        }

                        else
                        {
                            id_pemesanan.setText(aid_pemesanan);
                            nama_pemesan.setText(anama_pemesan);
                            no_telp.setText(ano_telp);
                            alamat.setText(aalamat);
                            nama_barang.setText(anama_barang);
                            jumlah_barang.setText(ajumlah_pemesanan + " Tabung");
                            tanggal_pemesanan.setText(atanggal_pemesanan);
                            Barang = insertString(Barang);
                            abiaya_antar = insertString(abiaya_antar);
                            atotal_biaya = insertString(atotal_biaya);
                            total_harga.setText("Rp"+Barang);
                            biaya_antar.setText("Rp"+abiaya_antar);
                            total_biaya.setText("Rp"+atotal_biaya);
                            metode_pembayaran.setText(ametode_pembayaran);
                        }
                    }

                    @Override
                    public void onFailure(Call<DataPemesanan> call, Throwable t) {
                        Log.d("RETRO", "Failure : " + "Gagal Mengirim Data");
                    }
                });

            }

            @Override
            public void onFailure(Call<DataUser> call, Throwable t) {
                Log.d("RETRO", "Failure : " + "Gagal Mengirim Data");
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
