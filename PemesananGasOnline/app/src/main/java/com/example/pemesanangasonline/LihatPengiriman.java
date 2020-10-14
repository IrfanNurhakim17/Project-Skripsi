package com.example.pemesanangasonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.pemesanangasonline.Adapter.AdapterPengiriman;
import com.example.pemesanangasonline.Model.DataPemesanan;
import com.example.pemesanangasonline.Model.ResponDataPemesanan;
import com.example.pemesanangasonline.api.ApiRequestData;
import com.example.pemesanangasonline.api.Retroserver;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LihatPengiriman extends AppCompatActivity {
    private RecyclerView mRecycler;
    private RecyclerView.Adapter madapter;
    private RecyclerView.LayoutManager mManager;
    private List<DataPemesanan> mItems = new ArrayList<>();

    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_pengiriman);

        linearLayout = (LinearLayout)findViewById(R.id.halamankosongP);
        mRecycler = (RecyclerView)findViewById(R.id.tampil_pengiriman);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        fetchUsers("");
    }

    public void fetchUsers(String key){
        ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
        Call<ResponDataPemesanan> getData = api.getpesanan(key, "req_pengiriman");
        getData.enqueue(new Callback<ResponDataPemesanan>() {
            @Override
            public void onResponse(Call<ResponDataPemesanan> call, Response<ResponDataPemesanan> response) {
                Log.d("RETRO", "RESPONSE : " + response.body().getKode());
                mItems = response.body().getResult();
                String kode = response.body().getKode();

                madapter = new AdapterPengiriman(LihatPengiriman.this, mItems);
                mRecycler.setAdapter(madapter);
                madapter.notifyDataSetChanged();

                if (kode.equals(""))
                {
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponDataPemesanan> call, Throwable t) {
                Log.d("RETRO", "respon gagal");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchUsers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchUsers(newText);
                return false;
            }
        });
        return true;
    }
}
