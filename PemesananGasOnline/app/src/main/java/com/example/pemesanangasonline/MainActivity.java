package com.example.pemesanangasonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pemesanangasonline.Model.DataUser;
import com.example.pemesanangasonline.Model.ResponLogin;
import com.example.pemesanangasonline.Session.SessionManagement;
import com.example.pemesanangasonline.Session.User;
import com.example.pemesanangasonline.api.ApiRequestData;
import com.example.pemesanangasonline.api.Retroserver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button btnmasuk;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        int UserID = sessionManagement.getSession();

        SessionManagement sessionManagement1 = new SessionManagement(MainActivity.this);
        final String Username = sessionManagement1.getData();

        push_notification(Username);


        if (UserID != -1){

            if (Username.equals("admin"))
            {
                Intent homeAdmin = new Intent(MainActivity.this, HomeAdmin.class);
                homeAdmin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeAdmin);
            }
            else
            {
                Intent homeAdmin = new Intent(MainActivity.this, HomePelanggan.class);
                homeAdmin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeAdmin);
            }

        }


        username = (EditText)findViewById(R.id.edit_username);
        password = (EditText)findViewById(R.id.edit_password);
        btnmasuk = (Button)findViewById(R.id.btn_masuk);
        pd = new ProgressDialog(this);

        btnmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage(" send data ...");
                pd.setCancelable(false);
                pd.show();
                final String ausername = username.getText().toString();
                String apassword = password.getText().toString();

                ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);

                Call<ResponLogin> sendDataLogin = api.sendData(ausername,apassword);
                sendDataLogin.enqueue(new Callback<ResponLogin>() {
                    @Override
                    public void onResponse(Call<ResponLogin> call, Response<ResponLogin> response) {
                        pd.hide();
                        Log.d("RETRO", "response : " + response.body().toString());
                        String kode = response.body().getKode();

                        if (kode.equals("1"))
                        {
                            User user = new User(12,ausername);

                            SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                            sessionManagement.saveSession(user);

                            Intent homeAdmin = new Intent(MainActivity.this, HomeAdmin.class);
                            homeAdmin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(homeAdmin);
                            Toast.makeText(MainActivity.this, "Berhasil Login Sebagai Admin", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        if (kode.equals("2"))
                        {
                            User user = new User(12,ausername);

                            SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                            sessionManagement.saveSession(user);

                            Intent homeAdmin = new Intent(MainActivity.this, HomePelanggan.class);
                            homeAdmin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(homeAdmin);
                            Toast.makeText(MainActivity.this, "Berhasil Login Sebagai Pelanggan", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        if (kode.equals("3"))
                        {
                            Toast.makeText(MainActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
                        }
                        if (kode.equals("101"))
                        {
                            Toast.makeText(MainActivity.this, "Username Tidak Ada", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponLogin> call, Throwable t) {
                        pd.hide();
                        Log.d("RETRO", "Failure : " + "Gagal Mengirim Data");
                        Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    public void push_notification(final String Username)
    {
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                final String token = instanceIdResult.getToken();

                final ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
                Call<DataUser> sendData = api.getprofil(Username);
                sendData.enqueue(new Callback<DataUser>() {
                    @Override
                    public void onResponse(Call<DataUser> call, Response<DataUser> response) {
                        final String id_user = response.body().getId_user();

                        Call<DataUser> sendToken = api.getToken(token,id_user);
                        sendToken.enqueue(new Callback<DataUser>() {
                            @Override
                            public void onResponse(Call<DataUser> call, Response<DataUser> response) {
                                Log.e("Berhasil",token);
                            }

                            @Override
                            public void onFailure(Call<DataUser> call, Throwable t) {
                                Log.e("Error", t.getMessage());
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<DataUser> call, Throwable t) {

                    }
                });
            }
        });
    }
}
