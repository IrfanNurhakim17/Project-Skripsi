package com.example.pemesanangasonline.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retroserver {
    private static final String base_url = "http://192.168.0.13/Pemesanan_Gas/";

    String url = "http://192.168.0.13/Pemesanan_Gas/gambar/";

    private static Retrofit retrofit;

    public static Retrofit getClient()
    {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public String url()
    {
        return url;
    }
}
