package com.example.pemesanangasonline;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.pemesanangasonline.Session.SessionManagement;
import com.example.pemesanangasonline.Session.User;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getData().get("username"),
                remoteMessage.getData().get("status"),
                remoteMessage.getData().get("status_id"));
    }

    private void showNotification(String username, String status, String status_id) {

        if (status_id.equals("1"))
        {
            Intent i = new Intent(this,PermintaanPesanan.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle("Gas Online")
                    .setContentText("Permintaan Pesanan "+ status +"...")
                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                    .setContentIntent(pendingIntent);

            SessionManagement sessionManagement1 = new SessionManagement(FirebaseMessagingService.this);
            String Username = sessionManagement1.getData();



            if (Username.equals(username))
            {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(0,builder.build());
            }
        }

        else if (status_id.equals("2") || status_id.equals("3") || status_id.equals("4"))
        {
            Intent i = new Intent(this,NotifikasiPelanggan.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle("Gas Online")
                    .setContentText("Pesanan " + status)
                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                    .setContentIntent(pendingIntent);

            SessionManagement sessionManagement1 = new SessionManagement(FirebaseMessagingService.this);
            String Username = sessionManagement1.getData();



            if (Username.equals(username))
            {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(0,builder.build());
            }

        }

        else if (status_id.equals("5") || status_id.equals("6") || status_id.equals("7"))
        {
            Intent i = new Intent(this,RiwayatTransaksi.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle("Gas Online")
                    .setContentText("Pesanan " + status)
                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                    .setContentIntent(pendingIntent);

            SessionManagement sessionManagement1 = new SessionManagement(FirebaseMessagingService.this);
            String Username = sessionManagement1.getData();



            if (Username.equals(username))
            {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(0,builder.build());
            }
        }


    }
}
