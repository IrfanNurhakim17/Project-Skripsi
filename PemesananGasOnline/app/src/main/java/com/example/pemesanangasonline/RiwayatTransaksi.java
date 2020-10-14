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
import android.widget.SearchView;
import android.widget.Toast;

import com.example.pemesanangasonline.Adapter.AdapterRiwayatTransaksi;
import com.example.pemesanangasonline.Model.DataPemesanan;
import com.example.pemesanangasonline.Model.DataUser;
import com.example.pemesanangasonline.Model.ResponDataPemesanan;
import com.example.pemesanangasonline.Session.SessionManagement;
import com.example.pemesanangasonline.api.ApiRequestData;
import com.example.pemesanangasonline.api.Retroserver;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatTransaksi extends AppCompatActivity {
    private RecyclerView mRecycler;
    private RecyclerView.Adapter madapter;
    private RecyclerView.LayoutManager mManager;
    private List<DataPemesanan> mItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_transaksi);

        mRecycler = (RecyclerView)findViewById(R.id.tampil_riwayat);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        fetchUsers("");

    }

    public void fetchUsers(String key){
        SessionManagement sessionManagement1 = new SessionManagement(RiwayatTransaksi.this);
        String Username = sessionManagement1.getData();

        if (Username.equals("admin"))
        {
            ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
            Call<ResponDataPemesanan> getData = api.getpesanan(key, "req_riwayat");
            getData.enqueue(new Callback<ResponDataPemesanan>() {
                @Override
                public void onResponse(Call<ResponDataPemesanan> call, Response<ResponDataPemesanan> response) {
                    Log.d("RETRO", "RESPONSE : " + response.body().getKode());
                    mItems = response.body().getResult();

                    madapter = new AdapterRiwayatTransaksi(RiwayatTransaksi.this, mItems);
                    mRecycler.setAdapter(madapter);
                    madapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<ResponDataPemesanan> call, Throwable t) {
                    Log.d("RETRO", "respon gagal");
                    Toast.makeText(RiwayatTransaksi.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        else
        {
            final ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
            Call<DataUser> sendData = api.getprofil(Username);
            sendData.enqueue(new Callback<DataUser>() {
                @Override
                public void onResponse(Call<DataUser> call, Response<DataUser> response) {
                    Log.d("RETRO", "response : " + response.body().toString());
                    final String id_user = response.body().getId_user();

                    Call<ResponDataPemesanan> getriwayat = api.getriwayatpelanggan(id_user, "riwayat_transaksi");
                    getriwayat.enqueue(new Callback<ResponDataPemesanan>() {
                        @Override
                        public void onResponse(Call<ResponDataPemesanan> call, Response<ResponDataPemesanan> response) {
                            Log.d("RETRO", "RESPONSE : " + response.body().getKode());
                            mItems = response.body().getResult();

                            madapter = new AdapterRiwayatTransaksi(RiwayatTransaksi.this, mItems);
                            mRecycler.setAdapter(madapter);
                            madapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<ResponDataPemesanan> call, Throwable t) {
                            Log.d("RETRO", "respon gagal");
                        }
                    });
                }

                @Override
                public void onFailure(Call<DataUser> call, Throwable t) {
                    Log.d("RETRO", "Failure : " + "Gagal Mengirim Data");
                }
            });

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SessionManagement sessionManagement1 = new SessionManagement(RiwayatTransaksi.this);
        String Username = sessionManagement1.getData();

        if (Username.equals("admin")) {
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
        }
            return true;
    }
}
