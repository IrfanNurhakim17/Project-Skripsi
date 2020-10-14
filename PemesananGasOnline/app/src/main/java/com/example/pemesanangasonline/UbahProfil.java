package com.example.pemesanangasonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pemesanangasonline.Model.ResponDataUser;
import com.example.pemesanangasonline.api.ApiRequestData;
import com.example.pemesanangasonline.api.Retroserver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahProfil extends AppCompatActivity {
    TextView username, ubah_profil;
    EditText nama, no_telp, alamat, password_lama, password_baru;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_profil);

        nama = (EditText) findViewById(R.id.profil_ubah_nama);
        no_telp = (EditText)findViewById(R.id.profil_ubah_notelp);
        alamat = (EditText)findViewById(R.id.profil_ubah_Alamat);
        ubah_profil = (TextView)findViewById(R.id.ubah_data_profil);
        password_lama = (EditText)findViewById(R.id.profil_password_lama);
        password_baru = (EditText)findViewById(R.id.profil_password_baru);

        Intent data = getIntent();
        final String aid_user = data.getStringExtra("id_user");
        final String ausername = data.getStringExtra("username");
        final String apassword = data.getStringExtra("password");
        String anama = data.getStringExtra("nama");
        String ano_telp = data.getStringExtra("no_telp");
        String aalamat = data.getStringExtra("alamat");
        final String arole_id = data.getStringExtra("role_id");

        nama.setText(anama);
        no_telp.setText(ano_telp);
        alamat.setText(aalamat);

        ubah_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bnama = nama.getText().toString();
                String bno_telp= no_telp.getText().toString();
                String balamat = alamat.getText().toString();

                if (bnama.equals("") || bno_telp.equals("") || balamat.equals(""))
                {
                    Toast.makeText(UbahProfil.this, "Form Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
                    Call<ResponDataUser> ubahProfil = api.updateuser(aid_user,ausername,apassword, bnama, balamat, bno_telp,arole_id);
                    ubahProfil.enqueue(new Callback<ResponDataUser>() {
                        @Override
                        public void onResponse(Call<ResponDataUser> call, Response<ResponDataUser> response) {
                            Log.d("Retro", "Response");
                            Toast.makeText(UbahProfil.this,response.body().getPesan(),Toast.LENGTH_SHORT).show();
                            if (arole_id.equals("1"))
                            {
                                startActivity(new Intent(UbahProfil.this, ProfilAdmin.class));
                            }

                            if (arole_id.equals("2"))
                            {
                                startActivity(new Intent(UbahProfil.this, ProfilPelanggan.class));
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponDataUser> call, Throwable t) {
                            Log.d("Retro", "Data Error");

                        }
                    });
                }



            }
        });





    }
}
