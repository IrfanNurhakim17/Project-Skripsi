package com.example.pemesanangasonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pemesanangasonline.Model.DataPemesanan;
import com.example.pemesanangasonline.Model.DataUser;
import com.example.pemesanangasonline.Session.SessionManagement;
import com.example.pemesanangasonline.api.ApiRequestData;
import com.example.pemesanangasonline.api.Retroserver;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilPelanggan extends AppCompatActivity {
    LinearLayout ubah_profil, ubah_password, logout;
    TextView nama_user, username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_pelanggan);

        SessionManagement sessionManagement = new SessionManagement(ProfilPelanggan.this);
        String Username = sessionManagement.getData();

        nama_user = (TextView) findViewById(R.id.nama_profil);
        username = (TextView) findViewById(R.id.profil_username);
        ubah_profil = (LinearLayout) findViewById(R.id.ubah_profil);
        ubah_password = (LinearLayout) findViewById(R.id.ubah_password);
        logout = (LinearLayout) findViewById(R.id.profil_logout);

        ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
        Call<DataUser> getData = api.getprofil(Username);
        getData.enqueue(new Callback<DataUser>() {
            @Override
            public void onResponse(Call<DataUser> call, Response<DataUser> response) {
                Log.d("RETRO", "response : " + response.body().toString());
                final String ausername = response.body().getUsername();
                final String nama = response.body().getNama();
                final String id_user = response.body().getId_user();
                final String alamat = response.body().getAlamat();
                final String no_telp = response.body().getNo_telp();
                final String password = response.body().getPassword();
                final String role_id = response.body().getRole_id();
                nama_user.setText(nama);
                username.setText(ausername);

                ubah_profil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent goUbah = new Intent(ProfilPelanggan.this, UbahProfil.class);
                        goUbah.putExtra("id_user", id_user);
                        goUbah.putExtra("username", ausername);
                        goUbah.putExtra("password", password);
                        goUbah.putExtra("nama", nama);
                        goUbah.putExtra("no_telp", no_telp);
                        goUbah.putExtra("alamat", alamat);
                        goUbah.putExtra("role_id", role_id);
                        startActivity(goUbah);
                    }
                });

                ubah_password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent goUbah = new Intent(ProfilPelanggan.this, UbahPassword.class);
                        goUbah.putExtra("id_user", id_user);
                        goUbah.putExtra("username", ausername);
                        goUbah.putExtra("password", password);
                        goUbah.putExtra("nama", nama);
                        goUbah.putExtra("no_telp", no_telp);
                        goUbah.putExtra("alamat", alamat);
                        goUbah.putExtra("role_id", role_id);
                        startActivity(goUbah);
                    }
                });

            }

            @Override
            public void onFailure(Call<DataUser> call, Throwable t) {
                Log.d("RETRO", "Failure : " + "Gagal Mengirim Data");
            }
        });





        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement = new SessionManagement(ProfilPelanggan.this);
                sessionManagement.removeSession();
                Intent homeAdmin = new Intent(ProfilPelanggan.this, MainActivity.class);
                homeAdmin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeAdmin);
            }
        });

        //tombol navigasi bawah
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_pelanggan);

        bottomNavigationView.setSelectedItemId(R.id.profil_pelanggan);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.beranda_pelanggan:
                        startActivity(new Intent(getApplicationContext()
                                ,HomePelanggan.class));
                        return true;
                    case R.id.notif:
                        SessionManagement sessionManagement = new SessionManagement(ProfilPelanggan.this);
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
                                        Toast.makeText(ProfilPelanggan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<DataUser> call, Throwable t) {
                                Toast.makeText(ProfilPelanggan.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                        return true;
                    case R.id.profil_pelanggan:
                        return true;
                }
                return false;
            }
        });
    }
}
