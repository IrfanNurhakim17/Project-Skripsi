package com.example.pemesanangasonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.pemesanangasonline.Session.SessionManagement;
import com.example.pemesanangasonline.Session.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeAdmin extends AppCompatActivity {
    TextView kelola_stok, permintaan_pesanan, detail_pengiriman, riwayat_transaksi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);



        kelola_stok = (TextView) findViewById(R.id.kl_stok);
        permintaan_pesanan = (TextView) findViewById(R.id.req_pesanan);
        detail_pengiriman = (TextView) findViewById(R.id.detail_kirim);
        riwayat_transaksi = (TextView) findViewById(R.id.riwayat_transaksi);

        kelola_stok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kelola_stok = new Intent(HomeAdmin.this, KelolaStokGas.class);
                startActivity(kelola_stok);
            }
        });

        permintaan_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent permintaan_pesanan = new Intent(HomeAdmin.this, PermintaanPesanan.class);
                startActivity(permintaan_pesanan);
            }
        });

        detail_pengiriman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lihat_pengiriman = new Intent(HomeAdmin.this, LihatPengiriman.class);
                startActivity(lihat_pengiriman);
            }
        });

        riwayat_transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lihat_riwayat_transaksi = new Intent(HomeAdmin.this, RiwayatTransaksi.class);
                startActivity(lihat_riwayat_transaksi);
            }
        });



        //tombol navigasi bawah
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.beranda);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.beranda:
                        return true;
                    case R.id.data_plg:
                        startActivity(new Intent(getApplicationContext()
                                ,DataPelanggan.class));

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
