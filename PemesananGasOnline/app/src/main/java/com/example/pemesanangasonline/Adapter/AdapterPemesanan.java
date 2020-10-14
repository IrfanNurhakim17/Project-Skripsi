package com.example.pemesanangasonline.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pemesanangasonline.DetailPemesanan;
import com.example.pemesanangasonline.Model.DataPemesanan;
import com.example.pemesanangasonline.R;

import java.util.List;

public class AdapterPemesanan extends RecyclerView.Adapter<AdapterPemesanan.HolderData> {

    private List<DataPemesanan> mList;
    private Context ctx;

    public AdapterPemesanan(Context ctx, List<DataPemesanan> mList)
    {
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_permintaan_pemesanan,parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataPemesanan dp = mList.get(position);
        String tanggal = dp.getTanggal_pemesanan();
        String bukti_transfer = dp.getBukti_transfer();
        String metode_bayar = dp.getMetode_pembayaran();
        if (tanggal != null)
        {
            String wp = tanggal.substring(11,13);
            String wpm = tanggal.substring(14,16);
            String wk = tanggal.substring(19,21);
            tanggal = tanggal.substring(0,10) + " " + wp +":" + wpm + " " + wk;

                holder.tanggal_pemesanan.setText(tanggal.substring(0,10)+ " " + wp +":" + wpm + " " + wk);


        }
        else{
            holder.tanggal_pemesanan.setText(tanggal);
        }

        if (metode_bayar.equals("Transfer"))
        {
            if (bukti_transfer.equals(""))
            {
                holder.bukti_bayar.setText("Belum Upload");
                try{
                    holder.bukti_bayar.setTextColor(Color.RED);
                } catch (Exception e)
                {
                    e.getMessage();
                }
            }
            else
            {
                holder.bukti_bayar.setText("Sudah Upload");


            }
        }
        else
        {
            holder.bukti_bayar.setText("Tidak ada");
        }

        holder.id_pemesanan.setText(dp.getId_pemesanan());
        holder.nama_user.setText(dp.getNama());
        holder.pembayaran.setText(dp.getMetode_pembayaran());
        holder.status_pemesanan.setText(dp.getKet_status()+"...");
        holder.dp = dp;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView id_pemesanan, nama_user, tanggal_pemesanan, status_pemesanan, pembayaran, bukti_bayar;
        DataPemesanan dp;
        public HolderData (View v)
        {
            super(v);

            id_pemesanan = (TextView)v.findViewById(R.id.tvid_pemesanan);
            nama_user = (TextView)v.findViewById(R.id.tvnama_user);
            tanggal_pemesanan = (TextView)v.findViewById(R.id.tvtanggal_pemesanan);
            status_pemesanan = (TextView)v.findViewById(R.id.tvstatusPemesanan);
            pembayaran = (TextView)v.findViewById(R.id.d_metode_pembayaran);
            bukti_bayar = (TextView)v.findViewById(R.id.detail_bukti_transfer);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goInput = new Intent(ctx, DetailPemesanan.class);
                    goInput.putExtra("id_pemesanan", dp.getId_pemesanan());
                    goInput.putExtra("user_id", dp.getUser_id());
                    goInput.putExtra("barang_id", dp.getBarang_id());
                    goInput.putExtra("nama_pemesan", dp.getNama());
                    goInput.putExtra("no_telp", dp.getNo_telp());
                    goInput.putExtra("alamat", dp.getAlamat());
                    goInput.putExtra("nama_barang", dp.getNama_barang());
                    goInput.putExtra("jumlah_pemesanan", dp.getJumlah_pemesanan());
                    goInput.putExtra("tanggal_pemesanan", dp.getTanggal_pemesanan());
                    goInput.putExtra("biaya_antar", dp.getBiaya_antar());
                    goInput.putExtra("total_biaya", dp.getTotal_biaya());
                    goInput.putExtra("metode_pembayaran", dp.getMetode_pembayaran());
                    goInput.putExtra("bukti_transfer", dp.getBukti_transfer());
                    goInput.putExtra("status_pemesanan", dp.getKet_status());
                    goInput.putExtra("status_id", dp.getStatus_id());
                    ctx.startActivity(goInput);
                }
            });
        }
    }
}
