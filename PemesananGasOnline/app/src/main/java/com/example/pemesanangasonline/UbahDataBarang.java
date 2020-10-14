package com.example.pemesanangasonline;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pemesanangasonline.Model.ResponDataBarang;
import com.example.pemesanangasonline.Model.ResponLogin;
import com.example.pemesanangasonline.api.ApiRequestData;
import com.example.pemesanangasonline.api.Retroserver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahDataBarang extends AppCompatActivity {
    TextView idbarang, ubahDataBarang, tambahDataBarang, tampilDataBarang, hapusDataBarang;
    EditText namaBarang, jumlahBarang, hargaBarang;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_data_barang);
        idbarang = (TextView) findViewById(R.id.tv_idBarang);
        namaBarang = (EditText) findViewById(R.id.edit_namaBarang);
        jumlahBarang = (EditText) findViewById(R.id.edit_jumlahBarang);
        hargaBarang = (EditText) findViewById(R.id.edit_hargaBarang);
        ubahDataBarang = (TextView) findViewById(R.id.btn_ubahBarang);
        tambahDataBarang = (TextView) findViewById(R.id.btn_tambahBarang);
        tampilDataBarang = (TextView) findViewById(R.id.btn_tampilBarang);
        hapusDataBarang = (TextView) findViewById(R.id.btn_hapusBarang);

        Intent data = getIntent();
        final String id_barang = data.getStringExtra("id");
        final String nama_barang = data.getStringExtra("nama_barang");
        final String jumlah_barang = data.getStringExtra("jumlah_barang");
        final String harga_barang = data.getStringExtra("harga_barang");

        idbarang.setText(id_barang);
        namaBarang.setText(nama_barang);
        jumlahBarang.setText(jumlah_barang);
        hargaBarang.setText(harga_barang);

        if (id_barang != null){
            tambahDataBarang.setVisibility(View.GONE);
        }

        if (id_barang == null){
            hapusDataBarang.setVisibility(View.GONE);
            ubahDataBarang.setVisibility(View.GONE);
            tampilDataBarang.setVisibility(View.GONE);
        }

        tambahDataBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aidbarang = idbarang.getText().toString();
                String anamabarang = namaBarang.getText().toString();
                String ajumlahbarang = jumlahBarang.getText().toString();
                String ahargabarang = hargaBarang.getText().toString();
                ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
                Call<ResponDataBarang> sendDataBarang = api.insertBarang(aidbarang,anamabarang,ajumlahbarang,ahargabarang);
                sendDataBarang.enqueue(new Callback<ResponDataBarang>() {
                    @Override
                    public void onResponse(Call<ResponDataBarang> call, Response<ResponDataBarang> response) {
                        Log.d("RETRO", "response : " + response.body().toString());
                        String kode = response.body().getKode();

                        if(kode.equals("1"))
                        {
                            Toast.makeText(UbahDataBarang.this, "Data Ditambahkan",Toast.LENGTH_SHORT).show();
                            Intent Gotampil = new Intent(UbahDataBarang.this, KelolaStokGas.class);
                            startActivity(Gotampil);
                        }
                        else
                        {
                            Toast.makeText(UbahDataBarang.this, "Gagal Menambahkan Data",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponDataBarang> call, Throwable t) {
                        Log.d("RETRO", "Failure : " + "Gagal Mengirim");
                    }
                });
            }
        });

        ubahDataBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
                Call<ResponDataBarang> update = api.updateBarang(idbarang.getText().toString(),namaBarang.getText().toString(),jumlahBarang.getText().toString(),hargaBarang.getText().toString());
                update.enqueue(new Callback<ResponDataBarang>() {
                    @Override
                    public void onResponse(Call<ResponDataBarang> call, Response<ResponDataBarang> response) {
                        Log.d("Retro", "Response");
                        Toast.makeText(UbahDataBarang.this,response.body().getPesan(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponDataBarang> call, Throwable t) {
                        Log.d("Retro", "Data Error");
                    }
                });
            }
        });

        tampilDataBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Goto = new Intent(UbahDataBarang.this, KelolaStokGas.class);
                startActivity(Goto);
            }
        });

        hapusDataBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
                Call<ResponDataBarang> del = api.deleteBarang(id_barang);
                del.enqueue(new Callback<ResponDataBarang>() {
                    @Override
                    public void onResponse(Call<ResponDataBarang> call, Response<ResponDataBarang> response) {
                        Log.d("Retro", "onResponse");
                        Toast.makeText(UbahDataBarang.this, response.body().getPesan(),Toast.LENGTH_SHORT).show();
                        Intent goTampil = new Intent(UbahDataBarang.this, KelolaStokGas.class);
                        startActivity(goTampil);
                    }

                    @Override
                    public void onFailure(Call<ResponDataBarang> call, Throwable t) {
                        Log.d("Retro", "OnFailure");
                    }
                });
            }
        });


    }
}
