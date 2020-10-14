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

import com.example.pemesanangasonline.Model.DataUser;
import com.example.pemesanangasonline.Model.ResponDataUser;
import com.example.pemesanangasonline.Session.SessionManagement;
import com.example.pemesanangasonline.api.ApiRequestData;
import com.example.pemesanangasonline.api.Retroserver;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilAdmin extends AppCompatActivity {
    LinearLayout ubah_profil, ubah_password, logout;
    TextView nama_user, username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_admin);

        SessionManagement sessionManagement = new SessionManagement(ProfilAdmin.this);
        int UserID = sessionManagement.getSession();
        String Username = sessionManagement.getData();

        if (UserID != -1){

            if (Username.equals("admin"))
            {

            }
            else
            {
                Intent homeAdmin = new Intent(ProfilAdmin.this, ProfilPelanggan.class);
                homeAdmin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeAdmin);
            }

        }

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
                        Intent goUbah = new Intent(ProfilAdmin.this, UbahProfil.class);
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
                        Intent goUbah = new Intent(ProfilAdmin.this, UbahPassword.class);
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
                SessionManagement sessionManagement = new SessionManagement(ProfilAdmin.this);
                sessionManagement.removeSession();
                Intent homeAdmin = new Intent(ProfilAdmin.this, MainActivity.class);
                homeAdmin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeAdmin);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.profil);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.beranda:
                        startActivity(new Intent(getApplicationContext()
                                ,HomeAdmin.class));
                        return true;
                    case R.id.data_plg:
                        startActivity(new Intent(getApplicationContext()
                                ,DataPelanggan.class));
                        return true;
                    case R.id.profil:
                        return true;
                }
                return false;
            }
        });
    }
}
