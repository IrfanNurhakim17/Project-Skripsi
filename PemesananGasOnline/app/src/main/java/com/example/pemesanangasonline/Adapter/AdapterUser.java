package com.example.pemesanangasonline.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pemesanangasonline.DetailPelanggan;
import com.example.pemesanangasonline.Model.DataUser;
import com.example.pemesanangasonline.R;

import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.HolderData> {

    private List<DataUser> mList;
    private Context ctx;

    public AdapterUser(Context ctx, List<DataUser> mList)
    {
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lihat_user,parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataUser du= mList.get(position);
        holder.id_user.setText(du.getId_user());
        holder.username.setText(du.getUsername());
        holder.nama.setText(du.getNama());
        holder.no_telp.setText(du.getNo_telp());
        holder.du = du;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView id_user, username, nama, no_telp;
        DataUser du;
        public HolderData(View v)
        {
            super(v);
            id_user = (TextView)v.findViewById(R.id.tvid_user);
            username = (TextView)v.findViewById(R.id.tvnama_username);
            nama = (TextView)v.findViewById(R.id.tvnama_lengkap);
            no_telp= (TextView)v.findViewById(R.id.tv_notelp);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goInput = new Intent(ctx, DetailPelanggan.class);
                    goInput.putExtra("id_user", du.getId_user());
                    goInput.putExtra("username", du.getUsername());
                    goInput.putExtra("password", du.getPassword());
                    goInput.putExtra("nama", du.getNama());
                    goInput.putExtra("no_telp", du.getNo_telp());
                    goInput.putExtra("alamat", du.getAlamat());
                    goInput.putExtra("role_id", du.getRole_id());
                    ctx.startActivity(goInput);
                }
            });

        }
    }
}
