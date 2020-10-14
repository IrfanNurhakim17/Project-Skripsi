package com.example.pemesanangasonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.core.widget.ImageViewCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pemesanangasonline.Model.DataBarang;
import com.example.pemesanangasonline.Model.DataPemesanan;
import com.example.pemesanangasonline.Model.DataUser;
import com.example.pemesanangasonline.Model.ResponDataBarang;
import com.example.pemesanangasonline.Model.ResponDataPemesanan;
import com.example.pemesanangasonline.Session.SessionManagement;
import com.example.pemesanangasonline.api.ApiRequestData;
import com.example.pemesanangasonline.api.Retroserver;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifikasiPelanggan extends AppCompatActivity {
    TextView id_pemesanan, nama_pemesan, no_telp, alamat, tanggal_pemesanan, status, terima_barang, tolak_pesanan;
    LinearLayout notif_card,hk;
    AppCompatImageView img1,img2, img3,img4, img5,img6, img7,img8;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi_pelanggan);

        id_pemesanan = (TextView) findViewById(R.id.idpemesanan);
        terima_barang = (TextView) findViewById(R.id.terimabarang);
        tolak_pesanan = (TextView) findViewById(R.id.tolak_pesanan);
        notif_card = (LinearLayout) findViewById(R.id.bungkus_notif);
        hk = (LinearLayout) findViewById(R.id.halamankosong);
        img1 = (AppCompatImageView) findViewById(R.id.menunggukonfirmasi);
        img2 = (AppCompatImageView) findViewById(R.id.menunggukonfirmasi2);
        img3 = (AppCompatImageView) findViewById(R.id.sedangdiproses);
        img4 = (AppCompatImageView) findViewById(R.id.sedangdiproses2);
        img5 = (AppCompatImageView) findViewById(R.id.sedangdikirim);
        img6 = (AppCompatImageView) findViewById(R.id.sedangdikirim2);
        img7 = (AppCompatImageView) findViewById(R.id.sampaitujuan);
        img8 = (AppCompatImageView) findViewById(R.id.sampaitujuan2);
        pd = new ProgressDialog(NotifikasiPelanggan.this);

        SessionManagement sessionManagement = new SessionManagement(NotifikasiPelanggan.this);
        final String Username = sessionManagement.getData();

        final ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
        Call<DataUser> sendData = api.getprofil(Username);
        sendData.enqueue(new Callback<DataUser>() {
            @Override
            public void onResponse(Call<DataUser> call, Response<DataUser> response) {
                Log.d("RETRO", "response : " + response.body().toString());
                final String id_user = response.body().getId_user();

                Call<DataPemesanan> getData = api.getPesananPelanggan(id_user, "detail_pemesanan");
                getData.enqueue(new Callback<DataPemesanan>() {
                    @Override
                    public void onResponse(Call<DataPemesanan> call, Response<DataPemesanan> response) {
                        Log.d("RETRO", "response : " + response.body().toString());
                        final String aid_pemesanan = response.body().getId_pemesanan();
                        final String auser_id = response.body().getUser_id();
                        final String abarang_id = response.body().getBarang_id();
                        final String anama_pemesan = response.body().getNama();
                        final String ano_telp = response.body().getNo_telp();
                        final String aalamat = response.body().getAlamat();
                        final String anama_barang = response.body().getNama_barang();
                        final String ajumlah_pemesanan = response.body().getJumlah_pemesanan();
                        final String atanggal_pemesanan = response.body().getTanggal_pemesanan();
                        final String abiaya_antar = response.body().getBiaya_antar();
                        final String atotal_biaya = response.body().getTotal_biaya();
                        final String ametode_pembayaran = response.body().getMetode_pembayaran();
                        final String abukti_transfer = response.body().getBukti_transfer();
                        final String astatus = response.body().getKet_status();
                        final String astatus_id = response.body().getStatus_id();

                        if (aid_pemesanan == null)
                        {
                            terima_barang.setVisibility(View.GONE);
                            hk.setVisibility(View.VISIBLE);
                        }
                        else {

                                notif_card.setVisibility(View.VISIBLE);
                                id_pemesanan.setText(aid_pemesanan);

                                if (astatus_id.equals("1")) {
                                    img1.setVisibility(View.GONE);
                                    img2.setVisibility(View.VISIBLE);
                                    if (ametode_pembayaran.equals("Transfer"))
                                    {
                                        terima_barang.setVisibility(View.VISIBLE);
                                        tolak_pesanan.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        terima_barang.setVisibility(View.GONE);
                                        tolak_pesanan.setVisibility(View.VISIBLE);
                                    }
                                }
                                if (astatus_id.equals("2")) {
                                    img3.setVisibility(View.GONE);
                                    img4.setVisibility(View.VISIBLE);
                                }
                                if (astatus_id.equals("3")) {
                                    img5.setVisibility(View.GONE);
                                    img6.setVisibility(View.VISIBLE);
                                }
                                if (astatus_id.equals("4")) {
                                    img7.setVisibility(View.GONE);
                                    img8.setVisibility(View.VISIBLE);
                                }

                        }

                        tolak_pesanan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String bstatus_id = "6";
                                Call<ResponDataPemesanan> tolak = api.updatePemesanan(aid_pemesanan, auser_id, abarang_id, ajumlah_pemesanan, atanggal_pemesanan, abiaya_antar, atotal_biaya, ametode_pembayaran, abukti_transfer, bstatus_id);
                                tolak.enqueue(new Callback<ResponDataPemesanan>() {
                                    @Override
                                    public void onResponse(Call<ResponDataPemesanan> call, Response<ResponDataPemesanan> response) {
                                        Log.d("Retro", "onResponse");

                                        Call<DataBarang> getData = api.tampilBarang();
                                        getData.enqueue(new Callback<DataBarang>() {
                                            @Override
                                            public void onResponse(Call<DataBarang> call, Response<DataBarang> response) {
                                                Log.d("RETRO", "response : " + response.body().toString());
                                                final String anama_barang = response.body().getNama_barang();
                                                final String ajumlah_barang = response.body().getJumlah_barang();
                                                final String aharga_barang = response.body().getHarga_barang();
                                                final String id_barang = response.body().getId_barang();

                                                int ajumlahbarang = Integer.parseInt(ajumlah_barang);
                                                int jumlahpemesanan = Integer.parseInt(ajumlah_pemesanan);
                                                jumlahpemesanan = ajumlahbarang + jumlahpemesanan;
                                                String ajumlahpemesanan = Integer.toString(jumlahpemesanan);

                                                Call<ResponDataBarang> update = api.updateBarang(id_barang,anama_barang,ajumlahpemesanan,aharga_barang);
                                                update.enqueue(new Callback<ResponDataBarang>() {
                                                    @Override
                                                    public void onResponse(Call<ResponDataBarang> call, Response<ResponDataBarang> response) {
                                                        Log.d("Retro", "Response");
                                                        Toast.makeText(NotifikasiPelanggan.this, "Berhasil Batalkan Pesanan", Toast.LENGTH_SHORT).show();
                                                        Intent goTampil = new Intent(NotifikasiPelanggan.this, NotifikasiPelanggan.class);
                                                        startActivity(goTampil);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<ResponDataBarang> call, Throwable t) {
                                                        Log.d("Retro", "Data Error");
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
                                    public void onFailure(Call<ResponDataPemesanan> call, Throwable t) {
                                        Log.d("Retro", "OnFailure");
                                    }
                                });
                            }
                        });

                        terima_barang.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (astatus_id.equals("4"))
                                {
                                    String bstatus_id = "5";
                                    Call<ResponDataPemesanan> updateData = api.updatePemesanan(aid_pemesanan, auser_id, abarang_id, ajumlah_pemesanan, atanggal_pemesanan, abiaya_antar, atotal_biaya,
                                            ametode_pembayaran, abukti_transfer, bstatus_id);
                                    updateData.enqueue(new Callback<ResponDataPemesanan>() {
                                        @Override
                                        public void onResponse(Call<ResponDataPemesanan> call, Response<ResponDataPemesanan> response) {
                                            Log.d("Retro", "Response");
                                            String kode = response.body().getKode();
                                            String pesan = response.body().getPesan();

                                            Toast.makeText(NotifikasiPelanggan.this, "Pesanan Berhasil!", Toast.LENGTH_SHORT).show();
                                            Intent Gotampil = new Intent(NotifikasiPelanggan.this, NotifikasiPelanggan.class);
                                            startActivity(Gotampil);
                                        }

                                        @Override
                                        public void onFailure(Call<ResponDataPemesanan> call, Throwable t) {
                                            Log.d("Retro", "Data Error");
                                            Toast.makeText(NotifikasiPelanggan.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                else{
                                    Toast.makeText(NotifikasiPelanggan.this, "Pesanan Belum Sampai Tujuan", Toast.LENGTH_SHORT).show();
                                }


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



        //tombol navigasi bawah
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_pelanggan);

        bottomNavigationView.setSelectedItemId(R.id.notif);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.beranda_pelanggan:
                        startActivity(new Intent(getApplicationContext()
                                ,HomePelanggan.class));
                        return true;
                    case R.id.notif:
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
