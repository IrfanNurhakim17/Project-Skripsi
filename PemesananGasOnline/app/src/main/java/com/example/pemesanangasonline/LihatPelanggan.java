package com.example.pemesanangasonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.pemesanangasonline.Adapter.AdapterUser;
import com.example.pemesanangasonline.Model.DataUser;
import com.example.pemesanangasonline.Model.ResponDataUser;
import com.example.pemesanangasonline.api.ApiRequestData;
import com.example.pemesanangasonline.api.Retroserver;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LihatPelanggan extends AppCompatActivity {
    private RecyclerView mRecycler;
    private RecyclerView.Adapter madapter;
    private RecyclerView.LayoutManager mManager;
    private List<DataUser> mItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_pelanggan);

        mRecycler = (RecyclerView)findViewById(R.id.tampil_pelanggan);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
        Call<ResponDataUser> getData = api.getuser();
        getData.enqueue(new Callback<ResponDataUser>() {
            @Override
            public void onResponse(Call<ResponDataUser> call, Response<ResponDataUser> response) {
                Log.d("RETRO", "RESPONSE : " + response.body().getKode());
                mItems = response.body().getResult();

                madapter = new AdapterUser(LihatPelanggan.this, mItems);
                mRecycler.setAdapter(madapter);
                madapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponDataUser> call, Throwable t) {
                Log.d("RETRO", "respon gagal");
            }
        });
    }
}
