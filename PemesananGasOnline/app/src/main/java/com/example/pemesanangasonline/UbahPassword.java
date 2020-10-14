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

public class UbahPassword extends AppCompatActivity {
    TextView password_lama, password_baru, ubah_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);


        password_lama = (EditText)findViewById(R.id.profil_password_lama);
        password_baru = (EditText)findViewById(R.id.profil_password_baru);
        ubah_password = (TextView)findViewById(R.id.profil_ubah_password);

        Intent data = getIntent();
        final String aid_user = data.getStringExtra("id_user");
        final String ausername = data.getStringExtra("username");
        final String apassword = data.getStringExtra("password");
        final String anama = data.getStringExtra("nama");
        final String ano_telp = data.getStringExtra("no_telp");
        final String aalamat = data.getStringExtra("alamat");
        final String arole_id = data.getStringExtra("role_id");


        ubah_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String apwrd_lama = password_lama.getText().toString();
                String apwrd_baru = password_baru.getText().toString();

                if (apwrd_lama.equals(apassword))
                {

                    ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
                    Call<ResponDataUser> ubahProfil = api.updateuser(aid_user,ausername,apwrd_baru, anama, aalamat, ano_telp, arole_id);
                    ubahProfil.enqueue(new Callback<ResponDataUser>() {
                        @Override
                        public void onResponse(Call<ResponDataUser> call, Response<ResponDataUser> response) {
                            Log.d("Retro", "Response");
                            Toast.makeText(UbahPassword.this,"Berhasil Merubah Data",Toast.LENGTH_SHORT).show();
                            if (arole_id.equals("1"))
                            {
                                startActivity(new Intent(UbahPassword.this, ProfilAdmin.class));
                            }
                            if (arole_id.equals("2"))
                            {
                                startActivity(new Intent(UbahPassword.this, ProfilPelanggan.class));
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponDataUser> call, Throwable t) {
                            Log.d("Retro", "Data Error");
                        }
                    });

                }
                else
                {
                    Toast.makeText(UbahPassword.this, "Password Lama Tidak Cocok", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
