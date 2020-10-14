package com.example.pemesanangasonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pemesanangasonline.Model.DataBarang;
import com.example.pemesanangasonline.Model.DataPemesanan;
import com.example.pemesanangasonline.Model.DataUser;
import com.example.pemesanangasonline.Model.ResponDataBarang;
import com.example.pemesanangasonline.Session.Barang;
import com.example.pemesanangasonline.Session.SessionManagement;
import com.example.pemesanangasonline.Session.User;
import com.example.pemesanangasonline.api.ApiRequestData;
import com.example.pemesanangasonline.api.Retroserver;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePelanggan extends AppCompatActivity {
    TextView nama_barang, jumlah_barang, harga_barang, buat_pesanan, detail_pesanan, riwayat_pesanan, username;
    DataBarang db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pelanggan);

        username = (TextView)findViewById(R.id.username);
        nama_barang = (TextView)findViewById(R.id.tvNama_BarangP);
        jumlah_barang = (TextView)findViewById(R.id.tvJumlah_StokP);
        buat_pesanan = (TextView)findViewById(R.id.buat_pemesanan);
        detail_pesanan = (TextView)findViewById(R.id.Lihat_Detail_Pesanan);
        riwayat_pesanan = (TextView)findViewById(R.id.riwayat_transaksiP);

        SessionManagement sessionManagement = new SessionManagement(HomePelanggan.this);
        final String Username = sessionManagement.getData();

        final ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);


        Call<DataUser> sendData = api.getprofil(Username);
        sendData.enqueue(new Callback<DataUser>() {
            @Override
            public void onResponse(Call<DataUser> call, Response<DataUser> response) {
                Log.d("RETRO", "response : " + response.body().toString());
                final String id_user = response.body().getId_user();
                String nama = response.body().getNama();
                username.setText(nama);

                Call<DataPemesanan> getPemesanan = api.getPesananPelanggan(id_user, "detail_pemesanan");
                getPemesanan.enqueue(new Callback<DataPemesanan>() {
                    @Override
                    public void onResponse(Call<DataPemesanan> call, Response<DataPemesanan> response) {
                        Log.d("RETRO", "response : " + response.body().toString());
                        final String astatus_id = response.body().getStatus_id();



                Call<DataBarang> getData = api.tampilBarang();
                getData.enqueue(new Callback<DataBarang>() {
                    @Override
                    public void onResponse(Call<DataBarang> call, Response<DataBarang> response) {
                        Log.d("RETRO", "response : " + response.body().toString());
                        final String anama_barang = response.body().getNama_barang();
                        final String ajumlah_barang = response.body().getJumlah_barang();
                        final String aharga_barang = response.body().getHarga_barang();
                        final String id_barang = response.body().getId_barang();

                        Barang barang= new Barang(aharga_barang);

                        SessionManagement sessionManagement = new SessionManagement(HomePelanggan.this);
                        sessionManagement.saveSession(barang);

                        nama_barang.setText(anama_barang);
                        jumlah_barang.setText(ajumlah_barang);


                        buat_pesanan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(astatus_id != null)
                                {
                                    Toast.makeText(HomePelanggan.this, "Proses Pemesanan Sedang Berlangsung", Toast.LENGTH_SHORT).show();
                                }

                                else if(ajumlah_barang.equals("0"))
                                {
                                    Toast.makeText(HomePelanggan.this, "Stok Habis", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Intent goPesan = new Intent(HomePelanggan.this, BuatPemesanan.class);
                                    goPesan.putExtra("id_barang", id_barang);
                                    goPesan.putExtra("nama_barang", anama_barang);
                                    goPesan.putExtra("harga_barang", aharga_barang);
                                    goPesan.putExtra("jumlah_barang", ajumlah_barang);
                                    startActivity(goPesan);
                                }

                            }
                        });


                    }

                    @Override
                    public void onFailure(Call<DataBarang> call, Throwable t) {
                        Log.d("RETRO", "Failure : " + "Gagal Mengirim Data");

                    }
                });

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

        detail_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePelanggan.this, DetailPemesananPelanggan.class));
            }
        });

        riwayat_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePelanggan.this, RiwayatTransaksi.class));
            }
        });

        //tombol navigasi bawah
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_pelanggan);

        bottomNavigationView.setSelectedItemId(R.id.beranda_pelanggan);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.beranda_pelanggan:
                        return true;
                    case R.id.notif:

                        SessionManagement sessionManagement = new SessionManagement(HomePelanggan.this);
                        final String Username = sessionManagement.getData();

                        final ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
                        Call<DataUser> sendData = api.getprofil(Username);
                        sendData.enqueue(new Callback<DataUser>() {
                            @Override
                            public void onResponse(Call<DataUser> call, Response<DataUser> response) {
                                final String id_user = response.body().getId_user();

                                Call<DataPemesanan> getData = api.getPesananPelanggan(id_user, "detail_pemesanan");
                                getData.enqueue(new Callback<DataPemesanan>() {
                                    @Override
                                    public void onResponse(Call<DataPemesanan> call, Response<DataPemesanan> response) {
                                        final String abukti_transfer = response.body().getBukti_transfer();
                                        final String ametode_pembayaran = response.body().getMetode_pembayaran();


                                        if (ametode_pembayaran == null)
                                        {
                                            startActivity(new Intent(getApplicationContext()
                                                    ,NotifikasiPelanggan.class));
                                        }

                                        else
                                        {
                                            if (ametode_pembayaran.equals("Tunai"))
                                            {
                                                startActivity(new Intent(getApplicationContext()
                                                        ,NotifikasiPelanggan.class));
                                            }

                                            else
                                            {
                                                if (abukti_transfer.equals(""))
                                                {
                                                    Intent gotransfer = new Intent(getApplicationContext(), PembayaranTransfer.class);
                                                    gotransfer.putExtra("id_user", id_user);
                                                    startActivity(gotransfer);
                                                }
                                                else
                                                {
                                                    startActivity(new Intent(getApplicationContext()
                                                            ,NotifikasiPelanggan.class));
                                                }
                                            }
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<DataPemesanan> call, Throwable t) {
                                        Toast.makeText(HomePelanggan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<DataUser> call, Throwable t) {
                                Toast.makeText(HomePelanggan.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                        return true;
                    case R.id.profil_pelanggan:
                        startActivity(new Intent(getApplicationContext()
                                ,ProfilPelanggan.class));
                        return true;
                }
                return false;
            }
        });
    }


}
