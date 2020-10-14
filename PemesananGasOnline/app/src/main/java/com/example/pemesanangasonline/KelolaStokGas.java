package com.example.pemesanangasonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pemesanangasonline.Adapter.AdapterBarang;
import com.example.pemesanangasonline.Model.DataBarang;
import com.example.pemesanangasonline.Model.ResponDataBarang;
import com.example.pemesanangasonline.api.ApiRequestData;
import com.example.pemesanangasonline.api.Retroserver;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaStokGas extends AppCompatActivity {
    private RecyclerView mRecycler;
    private RecyclerView.Adapter madapter;
    private RecyclerView.LayoutManager mManager;
    private List<DataBarang> mItems = new ArrayList<>();
    TextView tambahBarang, ubahBarang;
    CardView cardKosong;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_stok_gas);

        tambahBarang = (TextView) findViewById(R.id.tambah_barang);
        ubahBarang = (TextView) findViewById(R.id.ubah_barang);
        cardKosong = (CardView) findViewById(R.id.card_kosong);



        tambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goTambah = new Intent(KelolaStokGas.this, UbahDataBarang.class);
                startActivity(goTambah);
            }
        });

        mRecycler = (RecyclerView) findViewById(R.id.tampil_barang);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

            ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);

        Call<ResponDataBarang> getdata = api.getBarang();
        getdata.enqueue(new Callback<ResponDataBarang>() {
            @Override
            public void onResponse(Call<ResponDataBarang> call, Response<ResponDataBarang> response) {
                Log.d("RETRO", "RESPONSE : " + response.body().getKode());
                mItems = response.body().getResult();
                String kode = response.body().getKode();

                madapter = new AdapterBarang(KelolaStokGas.this,mItems);
                mRecycler.setAdapter(madapter);
                madapter.notifyDataSetChanged();

                if(kode.equals("1"))
                {
                    tambahBarang.setVisibility(View.GONE);
                    cardKosong.setVisibility(View.GONE);
                }

                if(kode.equals("0"))
                {
                    tambahBarang.setVisibility(View.VISIBLE);
                    cardKosong.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onFailure(Call<ResponDataBarang> call, Throwable t) {
                Log.d("RETRO", "FAILED : respon gagal");
            }
        });

    }
}
