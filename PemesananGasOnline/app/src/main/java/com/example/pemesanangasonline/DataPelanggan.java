package com.example.pemesanangasonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DataPelanggan extends AppCompatActivity {
    TextView tampil_user, tambah_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pelanggan);

        tampil_user = (TextView) findViewById(R.id.lihat_user);
        tambah_user = (TextView) findViewById(R.id.daftar_pelanggan);

        tampil_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goTampil = new Intent(DataPelanggan.this, LihatPelanggan.class);
                startActivity(goTampil);
            }
        });

        tambah_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goTambah = new Intent(DataPelanggan.this,DetailPelanggan.class);
                startActivity(goTambah);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.data_plg);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.beranda:
                        startActivity(new Intent(getApplicationContext()
                                ,HomeAdmin.class));
                        return true;
                    case R.id.data_plg:

                        return true;
                    case R.id.profil:
                        startActivity(new Intent(getApplicationContext()
                                ,ProfilAdmin.class));
                        return true;
                }
                return false;
            }
        });
    }
}
