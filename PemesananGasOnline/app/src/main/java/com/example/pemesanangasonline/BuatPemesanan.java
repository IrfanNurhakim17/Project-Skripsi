package com.example.pemesanangasonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pemesanangasonline.Model.DataUser;
import com.example.pemesanangasonline.Model.ResponDataBarang;
import com.example.pemesanangasonline.Model.ResponDataPemesanan;
import com.example.pemesanangasonline.Session.SessionManagement;
import com.example.pemesanangasonline.Session.User;
import com.example.pemesanangasonline.api.ApiRequestData;
import com.example.pemesanangasonline.api.Retroserver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuatPemesanan extends AppCompatActivity {
    TextView nama_pemesan, no_telp, alamat, nama_barang, jumlah_barang, biaya_antar, total_biaya, buat_pesanan, total_harga, jtotal_harga;
    Button min, plus;
    String total_biaya1, biaya_antar1;
    Dialog dialog;
    AppCompatImageView img_close;
    Button Tunai, Transfer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pemesanan);

        SessionManagement sessionManagement = new SessionManagement(BuatPemesanan.this);
        String Username = sessionManagement.getData();

        Intent data = getIntent();
        final String id_barang = data.getStringExtra("id_barang");
        final String anama_barang = data.getStringExtra("nama_barang");
        final String ajumlah_barang = data.getStringExtra("jumlah_barang");
        final String aharga_barang = data.getStringExtra("harga_barang");

        nama_pemesan = (TextView)findViewById(R.id.tv_namaPemesanP);
        no_telp = (TextView)findViewById(R.id.tv_noTelpP);
        alamat = (TextView)findViewById(R.id.tv_alamatP);
        nama_barang = (TextView)findViewById(R.id.tvnamaBarangP);
        jumlah_barang = (TextView)findViewById(R.id.tvjumlahBarangP);
        biaya_antar = (TextView)findViewById(R.id.tvbiayaAntarP);
        total_harga = (TextView)findViewById(R.id.totalharga);
        jtotal_harga = (TextView)findViewById(R.id.jumlah_total_harga);
        total_biaya = (TextView)findViewById(R.id.tvtotalBiayaP);
        plus = (Button) findViewById(R.id.btn_plus);
        min = (Button)findViewById(R.id.btn_min);
        buat_pesanan = (TextView) findViewById(R.id.buat_pemesananP);
        dialog = new Dialog(BuatPemesanan.this);

        ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
        Call<DataUser> getData = api.getprofil(Username);
        getData.enqueue(new Callback<DataUser>() {
            @Override
            public void onResponse(Call<DataUser> call, Response<DataUser> response) {
                Log.d("RETRO", "response : " + response.body().toString());
                final String nama = response.body().getNama();
                final String id_user = response.body().getId_user();
                final String aalamat = response.body().getAlamat();
                final String ano_telp = response.body().getNo_telp();

                nama_pemesan.setText(nama);
                no_telp.setText("(" + ano_telp + ")");
                alamat.setText(aalamat);
                nama_barang.setText(anama_barang);
                String th = insertString(aharga_barang);
                total_harga.setText("Rp" + th);
                jumlah_barang.setText("5");
                int harga = Integer.parseInt(aharga_barang);
                harga = harga * 5;
                String tb = Integer.toString(harga);
                tb = insertString(tb);
                jtotal_harga.setText("Rp" + tb);
                harga = harga + 2000;
                tb = Integer.toString(harga);
                total_biaya1 = tb;
                tb = insertString(tb);
                total_biaya.setText("Rp" + tb);
                biaya_antar.setText("Rp2.000");

                buat_pesanan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String status = "1";
                        String id_pemesanan = " ";
                        String tanggal_pemesanan = " ";
                        final String bjumlah_barang = jumlah_barang.getText().toString();
                        String abiaya_antar = "2000" ;
                        String atotal_biaya = total_biaya1;

                        if (atotal_biaya.equals("2000"))
                        {
                            Toast.makeText(BuatPemesanan.this, "Pilih Jumlah Barang", Toast.LENGTH_SHORT).show();
                        }

                        else
                        {

                            pilihPembayaran(id_pemesanan,id_user, id_barang, ajumlah_barang, bjumlah_barang, tanggal_pemesanan, abiaya_antar, atotal_biaya, status, aharga_barang, anama_barang);

                        }

                    }
                });

                min.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String jumlah = jumlah_barang.getText().toString();
                        String abiaya_antar = "2000";
                        String atotal_biaya;
                        int ibiaya_antar;
                        int ijumlah;
                        int iharga;
                        int itotal_biaya;
                        ibiaya_antar = Integer.parseInt(abiaya_antar);
                        ijumlah = Integer.parseInt(jumlah);
                        iharga = Integer.parseInt(aharga_barang);

                        if (jumlah.equals("5"))
                        {
                            itotal_biaya = iharga * ijumlah;
                            atotal_biaya = Integer.toString(itotal_biaya);
                            atotal_biaya = insertString(atotal_biaya);
                            jtotal_harga.setText("Rp" + atotal_biaya);
                            jumlah_barang.setText("5");
                            itotal_biaya = itotal_biaya + ibiaya_antar;
                            atotal_biaya = Integer.toString(itotal_biaya);
                            total_biaya1 = atotal_biaya;
                            atotal_biaya = insertString(atotal_biaya);
                            total_biaya.setText("Rp" + atotal_biaya);
                        }

                        if (ijumlah > 5)
                        {
                            ijumlah = ijumlah - 1;
                            itotal_biaya = (ijumlah * iharga);
                            atotal_biaya = Integer.toString(itotal_biaya);
                            atotal_biaya = insertString(atotal_biaya);
                            jtotal_harga.setText("Rp" + atotal_biaya);

                            itotal_biaya = itotal_biaya + ibiaya_antar;
                            atotal_biaya = Integer.toString(itotal_biaya);
                            jumlah= Integer.toString(ijumlah);
                            jumlah_barang.setText(jumlah);
                            total_biaya1 = atotal_biaya;
                            atotal_biaya = insertString(atotal_biaya);
                            total_biaya.setText("Rp" + atotal_biaya);

                        }

                    }
                });

                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String jumlah = jumlah_barang.getText().toString();
                        String abiaya_antar = "2000";
                        String atotal_biaya;
                        int ibiaya_antar;
                        int ijumlah;
                        int iharga;
                        int itotal_biaya;
                        int ijumlah_barang;
                        ijumlah_barang = Integer.parseInt(ajumlah_barang);
                        ibiaya_antar = Integer.parseInt(abiaya_antar);
                        ijumlah = Integer.parseInt(jumlah);
                        iharga = Integer.parseInt(aharga_barang);

                        if (ijumlah < 15)
                        {
                            if (ijumlah_barang == ijumlah)
                            {
                                itotal_biaya = (ijumlah * iharga);
                                atotal_biaya = Integer.toString(itotal_biaya);
                                atotal_biaya = insertString(atotal_biaya);
                                jtotal_harga.setText("Rp" + atotal_biaya);
                                itotal_biaya = itotal_biaya + ibiaya_antar;
                                atotal_biaya = Integer.toString(itotal_biaya);
                                jumlah= Integer.toString(ijumlah);
                                jumlah_barang.setText(jumlah);
                                total_biaya1 = atotal_biaya;
                                atotal_biaya = insertString(atotal_biaya);
                                total_biaya.setText("Rp" + atotal_biaya);
                            }

                            else
                            {
                                ijumlah = ijumlah + 1;
                                itotal_biaya = (ijumlah * iharga);
                                atotal_biaya = Integer.toString(itotal_biaya);
                                atotal_biaya = insertString(atotal_biaya);
                                jtotal_harga.setText("Rp" + atotal_biaya);
                                itotal_biaya = itotal_biaya + ibiaya_antar;
                                atotal_biaya = Integer.toString(itotal_biaya);
                                total_biaya1 = atotal_biaya;
                                atotal_biaya = insertString(atotal_biaya);
                                jumlah= Integer.toString(ijumlah);
                                jumlah_barang.setText(jumlah);
                                total_biaya.setText("Rp" + atotal_biaya);
                            }

                        }

                        if (jumlah.equals("15"))
                        {
                            jumlah_barang.setText("15");
                            ijumlah = Integer.parseInt(jumlah);
                            itotal_biaya = ijumlah * iharga;
                            atotal_biaya= Integer.toString(itotal_biaya);
                            atotal_biaya = insertString(atotal_biaya);
                            jtotal_harga.setText("Rp" + atotal_biaya);
                            itotal_biaya = itotal_biaya + ibiaya_antar;
                            atotal_biaya = Integer.toString(itotal_biaya);
                            total_biaya1 = atotal_biaya;
                            atotal_biaya = insertString(atotal_biaya);
                            total_biaya.setText("Rp" + atotal_biaya);
                        }

                    }
                });



            }

            @Override
            public void onFailure(Call<DataUser> call, Throwable t) {
                Log.d("RETRO", "Failure : " + "Gagal Mengirim Data");
            }
        });






    }


    public void pilihPembayaran(final String id_pemesanan, final String id_user,
                                final String id_barang, final String ajumlah_barang,
                                final String bjumlah_barang, final String tanggal_pemesanan,
                                final String abiaya_antar, final String atotal_biaya,
                                final String status, final String aharga_barang, final String anama_barang){
        dialog.setContentView(R.layout.layout_metode_pembayaran);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        Tunai = (Button)dialog.findViewById(R.id.tunai);
        Transfer = (Button)dialog.findViewById(R.id.transfer);
        img_close = (AppCompatImageView)dialog.findViewById(R.id.close1);


        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Tunai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String metode_pembayaran = "Tunai";
                String bukti_transfer = "";
                final ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
                Call<ResponDataPemesanan> sendData = api.tambahPesanan(id_pemesanan,id_user,id_barang, bjumlah_barang,tanggal_pemesanan
                        ,abiaya_antar,atotal_biaya, metode_pembayaran, bukti_transfer, status);
                sendData.enqueue(new Callback<ResponDataPemesanan>() {
                    @Override
                    public void onResponse(Call<ResponDataPemesanan> call, Response<ResponDataPemesanan> response) {
                        Log.d("RETRO", "RESPONSE : " + response.body().toString());
                        int cjumlah_barang = Integer.parseInt(ajumlah_barang);
                        int djumlah_barang = Integer.parseInt(bjumlah_barang);
                        int hasil = cjumlah_barang - djumlah_barang;
                        String ahasil = Integer.toString(hasil);

                        Call<ResponDataBarang> updateData = api.updateBarang(id_barang, anama_barang, ahasil, aharga_barang);
                        updateData.enqueue(new Callback<ResponDataBarang>() {
                            @Override
                            public void onResponse(Call<ResponDataBarang> call, Response<ResponDataBarang> response) {
                                Log.d("RETRO", "response : " + response.body().toString());
                                Toast.makeText(BuatPemesanan.this, "Berhasil Memesan", Toast.LENGTH_SHORT).show();
                                Intent goHome = new Intent(BuatPemesanan.this, DetailPemesananPelanggan.class);
                                startActivity(goHome);
                            }
                            @Override
                            public void onFailure(Call<ResponDataBarang> call, Throwable t) {
                                Log.d("RETRO", "Failure : " + "Gagal Mengirim");
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ResponDataPemesanan> call, Throwable t) {
                        Log.d("RETRO", "Failure : " + "Gagal Mengirim");
                    }
                });
            }
        });

        Transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String metode_pembayaran = "Transfer";
                String bukti_transfer = "";
                final ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
                Call<ResponDataPemesanan> sendData = api.tambahPesanan(id_pemesanan,id_user,id_barang, bjumlah_barang,tanggal_pemesanan
                        ,abiaya_antar,atotal_biaya, metode_pembayaran, bukti_transfer, status);
                sendData.enqueue(new Callback<ResponDataPemesanan>() {
                    @Override
                    public void onResponse(Call<ResponDataPemesanan> call, Response<ResponDataPemesanan> response) {
                        Log.d("RETRO", "RESPONSE : " + response.body().toString());
                        int cjumlah_barang = Integer.parseInt(ajumlah_barang);
                        int djumlah_barang = Integer.parseInt(bjumlah_barang);
                        int hasil = cjumlah_barang - djumlah_barang;
                        String ahasil = Integer.toString(hasil);

                        Call<ResponDataBarang> updateData = api.updateBarang(id_barang, anama_barang, ahasil, aharga_barang);
                        updateData.enqueue(new Callback<ResponDataBarang>() {
                            @Override
                            public void onResponse(Call<ResponDataBarang> call, Response<ResponDataBarang> response) {
                                Log.d("RETRO", "response : " + response.body().toString());
                                Toast.makeText(BuatPemesanan.this, "Berhasil Memesan", Toast.LENGTH_SHORT).show();
                                Intent goBayar= new Intent(BuatPemesanan.this, PembayaranTransfer.class);
                                goBayar.putExtra("id_user",id_user );
                                startActivity(goBayar);
                            }
                            @Override
                            public void onFailure(Call<ResponDataBarang> call, Throwable t) {
                                Log.d("RETRO", "Failure : " + "Gagal Mengirim");
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ResponDataPemesanan> call, Throwable t) {
                        Log.d("RETRO", "Failure : " + "Gagal Mengirim");
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
