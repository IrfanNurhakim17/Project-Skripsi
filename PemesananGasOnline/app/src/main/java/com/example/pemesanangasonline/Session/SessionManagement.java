package com.example.pemesanangasonline.Session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.pemesanangasonline.Model.DataUser;
import com.example.pemesanangasonline.Model.ResponLogin;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";
    String Username = "username";
    String Harga_Barang = "barang";

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(User user){
        int id = user.getId();
        String username = user.getUsername();
        editor.putInt(SESSION_KEY,id).commit();
        editor.putString(Username,username).commit();

    }

    public void saveSession(Barang barang){
        String harga_barang = barang.getHarga_barang();
        editor.putString(Harga_Barang,harga_barang).commit();

    }

    public String getHarga_Barang()
    {
        return sharedPreferences.getString(Harga_Barang,"");
    }

    public String getData()
    {
        return sharedPreferences.getString(Username,"");
    }

    public int getSession(){
        return sharedPreferences.getInt(SESSION_KEY,-1);
    }

    public void removeSession(){
        editor.putInt(SESSION_KEY, -1).commit();
    }
}
