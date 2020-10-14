package com.example.pemesanangasonline.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pemesanangasonline.DetailRiwayat;
import com.example.pemesanangasonline.HomePelanggan;
import com.example.pemesanangasonline.Model.DataPemesanan;
import com.example.pemesanangasonline.R;
import com.example.pemesanangasonline.RiwayatTransaksi;
import com.example.pemesanangasonline.Session.SessionManagement;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdapterRiwayatTransaksi extends RecyclerView.Adapter<AdapterRiwayatTransaksi.HolderData> {

    private List<DataPemesanan> mList;
    private Context ctx;

    public AdapterRiwayatTransaksi(Context ctx, List<DataPemesanan> mList)
    {
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_riwayat_transaksi,parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataPemesanan dp = mList.get(position);
        String nama = dp.getNama();
        String tanggal = dp.getTanggal_pemesanan();
        holder.id_pemesanan.setText(dp.getId_pemesanan());
        if (nama.length()>13){
            nama = nama.substring(0,14);
            holder.nama_user.setText(nama + "...");
        }
        else{
            holder.nama_user.setText(nama);
        }
        String wk = tanggal.substring(19,21);
        tanggal = tanggal.substring(0,19)+ " " + wk;
        holder.tanggal_pemesanan.setText(tanggal);
        holder.status_pemesanan.setText(dp.getKet_status());
        holder.dp = dp;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView id_pemesanan, nama_pemesan, no_telp, alamat, nama_barang, jumlah_barang, tanggal_pemesanan, biaya_antar, total_biaya, terima_pesanan, tolak_pesanan, statusPemesanan ;
        TextView  nama_user, status_pemesanan, harga_barang, metode_pembayaran, tanggal_pemesanan2;
        AppCompatImageView img1, img2, img3;
        DataPemesanan dp;
        Dialog dialog;
        ExpandListViewAdapter listViewAdapter;
        ExpandableListView expandableListView;
        List<String> parentlist;
        HashMap<String, List<String>> childlist;
        public HolderData(View v)
        {
            super(v);
            id_pemesanan = (TextView)v.findViewById(R.id.riwayat_idPemesanan);
            nama_user = (TextView)v.findViewById(R.id.riwayat_nama);
            tanggal_pemesanan = (TextView)v.findViewById(R.id.riwayat_tanggalPemesanan);
            status_pemesanan = (TextView)v.findViewById(R.id.riwayat_status);
            dialog = new Dialog(ctx);

            SessionManagement sessionManagement1 = new SessionManagement(ctx);
            String Username = sessionManagement1.getData();

            if (Username.equals("admin"))
            {
                nama_user.setVisibility(View.VISIBLE);
            }

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String aid_pemesanan = dp.getId_pemesanan();
                    String auser_id = dp.getUser_id();
                    String abarang_id = dp.getBarang_id();
                    String anama_pemesan = dp.getNama();
                    String ano_telp = dp.getNo_telp();
                    String aalamat = dp.getAlamat();
                    String anama_barang = dp.getNama_barang();
                    String ajumlah_pemesanan = dp.getJumlah_pemesanan();
                    String atanggal_pemesanan = dp.getTanggal_pemesanan();
                    String abiaya_antar = dp.getBiaya_antar();
                    String atotal_biaya = dp.getTotal_biaya();
                    String ametode_pembayaran = dp.getMetode_pembayaran();
                    String astatus_pemesanan = dp.getKet_status();
                    String astatus_id = dp.getStatus_id();

                    ShowDetailRiwayat(aid_pemesanan, auser_id, abarang_id, anama_pemesan, ano_telp, aalamat, anama_barang, ajumlah_pemesanan, atanggal_pemesanan, abiaya_antar, atotal_biaya, ametode_pembayaran ,astatus_pemesanan, astatus_id);
                }
            });

        }

        public void ShowDetailRiwayat(String idpemesanan, String auser_id, String abarang_id, String namapemesan, String notelp, String alamat1, String namabarang, String jumlahbarang, String tanggalpemesanan, String biayaantar, String totalbiaya, String m_bayar,String status_Pemesanan, String status_id ){
            dialog.setContentView(R.layout.activity_detail_riwayat);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            nama_pemesan = (TextView)dialog.findViewById(R.id.detail_riwayat_namaPemesan);
            nama_barang= (TextView)dialog.findViewById(R.id.detail_riwayat_namaBarang);
            jumlah_barang = (TextView)dialog.findViewById(R.id.detail_riwayat_jumlahBarang);
            tanggal_pemesanan = (TextView)dialog.findViewById(R.id.detail_riwayat_tanggalPemesanan);
            tanggal_pemesanan2 = (TextView)dialog.findViewById(R.id.detail_riwayat_tanggalPemesanan2);
            biaya_antar = (TextView)dialog.findViewById(R.id.detail_riwayat_biayaAntar);
            total_biaya = (TextView)dialog.findViewById(R.id.detail_riwayat_totalBiaya);
            statusPemesanan = (TextView)dialog.findViewById(R.id.detail_riwayat_status);
            img1 = (AppCompatImageView) dialog.findViewById(R.id.icon_status_berhasil);
            img2 = (AppCompatImageView)dialog.findViewById(R.id.icon_status_gagal);
            img3 = (AppCompatImageView)dialog.findViewById(R.id.close);
            harga_barang = (TextView) dialog.findViewById(R.id.total_harga_barang);
            metode_pembayaran = (TextView) dialog.findViewById(R.id.metode_pembayaran);
            expandableListView = dialog.findViewById(R.id.expandlistview);

            showList(idpemesanan,namapemesan,notelp,alamat1);

            listViewAdapter = new ExpandListViewAdapter(ctx,parentlist,childlist);
            expandableListView.setAdapter(listViewAdapter);

            if (status_id.equals("5")){
                img1.setVisibility(View.VISIBLE);
            }

            if (status_id.equals("6") || status_id.equals("7")){
                img2.setVisibility(View.VISIBLE);
            }

            SessionManagement sessionManagement = new SessionManagement(ctx);
            String Barang = sessionManagement.getHarga_Barang();



            nama_pemesan.setText(namapemesan);
            nama_barang.setText(namabarang);
            jumlah_barang.setText(jumlahbarang);

            String tanggal = tanggalpemesanan.substring(0,10);
            tanggal_pemesanan.setText(tanggal);

            tanggal = tanggalpemesanan.substring(11,19);
            String wk = tanggalpemesanan.substring(19,21);
            tanggal_pemesanan2.setText(tanggal + " " + wk);

            biayaantar = insertString(biayaantar);
            biaya_antar.setText("Rp"+biayaantar);
            totalbiaya = insertString(totalbiaya);
            total_biaya.setText("Rp"+totalbiaya);
            metode_pembayaran.setText(m_bayar);
            statusPemesanan.setText(status_Pemesanan);

            int barang;
            int jmlb;
            jmlb = Integer.parseInt(jumlahbarang);
            barang = Integer.parseInt(Barang);
            jmlb = barang * jmlb;
            Barang = Integer.toString(jmlb);

            Barang = insertString(Barang);
            harga_barang.setText("Rp"+Barang);

            img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        private void showList(String id_pemesanan, String nama_pemesan, String telp, String alamat){
            parentlist = new ArrayList<String>();
            childlist = new HashMap<String, List<String>>();


            parentlist.add("Detail Pengiriman");

            List<String> child1 = new ArrayList<>();
            child1.add(id_pemesanan);
            child1.add(nama_pemesan);
            child1.add(telp);
            child1.add(alamat);

            childlist.put(parentlist.get(0), child1);

        }


        public String insertString(String originalString){
            String newString = new String();
            String stringToBeInserted = ".";
            for (int i = 0; i < originalString.length(); i++) {

                newString += originalString.charAt(i);

                if (originalString.length() == 6) {
                    if (i == 2) {
                        newString += stringToBeInserted;
                    }

                }
                if (originalString.length() == 5) {
                    if (i == 1) {
                        newString += stringToBeInserted;
                    }

                }
                if (originalString.length() == 4) {
                    if (i == 0) {
                        newString += stringToBeInserted;
                    }
                }
            }

            // return the modified String
            return newString;
        }


    }
}
