package com.example.pemesanangasonline;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class FirebaseInstanceIDService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("new token", s);
    }

    public void registerToken(String token) {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token",token)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.0.13/Pemesanan_Gas/User/regist_token.php")
                .post(body)
                .build();

        try{
            client.newCall(request).execute();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
