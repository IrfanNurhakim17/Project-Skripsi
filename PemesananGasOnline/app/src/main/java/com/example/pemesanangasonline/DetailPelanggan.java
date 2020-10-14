package com.example.pemesanangasonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pemesanangasonline.Model.DataUser;
import com.example.pemesanangasonline.Model.ResponDataUser;
import com.example.pemesanangasonline.api.ApiRequestData;
import com.example.pemesanangasonline.api.Retroserver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPelanggan extends AppCompatActivity {
    TextView txt_id_user, bid_user, id_user, username, ubah_user, hapus_user, tambah_user;
    EditText etusername, password, nama, no_telp, alamat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pelanggan);

        txt_id_user = (TextView)findViewById(R.id.txt_iduser);
        bid_user = (TextView) findViewById(R.id.b_iduser);
        id_user = (TextView)findViewById(R.id.detail_iduser);
        etusername = (EditText) findViewById(R.id.etdetail_username);
        username = (TextView)findViewById(R.id.detail_username);
        password = (EditText)findViewById(R.id.detail_password);
        nama = (EditText)findViewById(R.id.detail_nama);
        no_telp = (EditText)findViewById(R.id.detail_notelp);
        alamat= (EditText)findViewById(R.id.detail_Alamat);
        ubah_user = (TextView)findViewById(R.id.ubah_data);
        hapus_user = (TextView)findViewById(R.id.hapus_data);
        tambah_user = (TextView)findViewById(R.id.tambah_data);

        Intent data = getIntent();
        final String aid_user = data.getStringExtra("id_user");
        final String ausername = data.getStringExtra("username");
        final String apassword = data.getStringExtra("password");
        String anama = data.getStringExtra("nama");
        String ano_telp = data.getStringExtra("no_telp");
        String aalamat = data.getStringExtra("alamat");
        final String arole_id = data.getStringExtra("role_id");

        if (aid_user == null)
        {
            hapus_user.setVisibility(View.GONE);
            ubah_user.setVisibility(View.GONE);
            etusername.setVisibility(View.VISIBLE);
            username.setVisibility(View.GONE);
            id_user.setVisibility(View.GONE);
            txt_id_user.setVisibility(View.GONE);
            bid_user.setVisibility(View.GONE);
        }
        else
        {
            tambah_user.setVisibility(View.GONE);
        }
//
        id_user.setText(aid_user);
        username.setText(ausername);
        password.setText(apassword);
        nama.setText(anama);
        no_telp.setText(ano_telp);
        alamat.setText(aalamat);

        tambah_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bid_user = " ";
                String busername = etusername.getText().toString();
                String bpassword = password.getText().toString();
                String bnama = nama.getText().toString();
                String bno_telp = no_telp.getText().toString();
                String balamat = alamat.getText().toString();
                String brole_id = "2";

                if (busername.equals("") || bpassword.equals("") || bnama.equals("") || bno_telp.equals("") || balamat.equals(""))
                {
                    Toast.makeText(DetailPelanggan.this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
                    Call<ResponDataUser> goTambah = api.insertUser(bid_user, busername, bpassword, bnama, balamat, bno_telp, brole_id);
                    goTambah.enqueue(new Callback<ResponDataUser>() {
                        @Override
                        public void onResponse(Call<ResponDataUser> call, Response<ResponDataUser> response) {
                            Log.d("RETRO", "response : " + response.body().toString());
                            String kode = response.body().getKode();
                            String pesan = response.body().getPesan();

                            if(kode.equals("1"))
                            {
                                Toast.makeText(DetailPelanggan.this, "Data Ditambahkan",Toast.LENGTH_SHORT).show();
                                Intent Gotampil = new Intent(DetailPelanggan.this, DataPelanggan.class);
                                startActivity(Gotampil);
                            }
                            if(kode.equals("3"))
                            {
                                Toast.makeText(DetailPelanggan.this, pesan,Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponDataUser> call, Throwable t) {
                            Log.d("RETRO", "Failure : " + "Gagal Mengirim");
                            Toast.makeText(DetailPelanggan.this, "Gagal Mengirim Data",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        ubah_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
                String bpassword = password.getText().toString();
                String bnama = nama.getText().toString();
                String bno_telp = no_telp.getText().toString();
                String balamat = alamat.getText().toString();
                Call<ResponDataUser> updateData = api.updateuser(aid_user,ausername,bpassword,bnama, balamat, bno_telp, arole_id);
                updateData.enqueue(new Callback<ResponDataUser>() {
                    @Override
                    public void onResponse(Call<ResponDataUser> call, Response<ResponDataUser> response) {
                        Log.d("Retro", "Response");
                        Toast.makeText(DetailPelanggan.this,response.body().getPesan(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponDataUser> call, Throwable t) {
                        Log.d("Retro", "Data Error");
                    }
                });
            }
        });

        hapus_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
                Call<ResponDataUser> del = api.deleteUser(aid_user);
                del.enqueue(new Callback<ResponDataUser>() {
                    @Override
                    public void onResponse(Call<ResponDataUser> call, Response<ResponDataUser> response) {
                        Log.d("Retro", "onResponse");
                        Toast.makeText(DetailPelanggan.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                        Intent goTampil = new Intent(DetailPelanggan.this, LihatPelanggan.class);
                        startActivity(goTampil);
                    }

                    @Override
                    public void onFailure(Call<ResponDataUser> call, Throwable t) {
                        Log.d("Retro", "OnFailure");
                        Toast.makeText(DetailPelanggan.this, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });




    }
}
