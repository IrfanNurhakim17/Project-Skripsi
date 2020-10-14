package com.example.pemesanangasonline.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pemesanangasonline.Model.DataBarang;
import com.example.pemesanangasonline.R;
import com.example.pemesanangasonline.UbahDataBarang;

import java.util.List;

public class AdapterBarang extends RecyclerView.Adapter<AdapterBarang.HolderData> {

    private List<DataBarang> mList;
    private Context ctx;

    public AdapterBarang (Context ctx, List<DataBarang> mList)
    {
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_kelola_stok_gas,parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataBarang db = mList.get(position);
        holder.id.setText(db.getId_barang());
        holder.nama_barang.setText(db.getNama_barang());
        holder.jumlah_barang.setText(db.getJumlah_barang());
        holder.harga_barang.setText(db.getHarga_barang());
        holder.db = db;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class HolderData extends RecyclerView.ViewHolder{
        TextView id, nama_barang, jumlah_barang, harga_barang, ubahBarang;
        DataBarang db;
        public HolderData (View v)
        {
            super(v);

            id = (TextView) v.findViewById(R.id.tvid_barang);
            nama_barang = (TextView) v.findViewById(R.id.tvNama_Barang);
            jumlah_barang = (TextView) v.findViewById(R.id.tvJumlah_Stok);
            harga_barang = (TextView) v.findViewById(R.id.tvHarga_Barang);
            ubahBarang = (TextView) v.findViewById(R.id.ubah_barang);

            ubahBarang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goInput = new Intent(ctx, UbahDataBarang.class);
                    goInput.putExtra("id", db.getId_barang());
                    goInput.putExtra("nama_barang", db.getNama_barang());
                    goInput.putExtra("jumlah_barang", db.getJumlah_barang());
                    goInput.putExtra("harga_barang", db.getHarga_barang());
                    ctx.startActivity(goInput);
                }
            });

        }
    }

}
