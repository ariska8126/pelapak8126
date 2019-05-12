package com.example.pelapak8126.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pelapak8126.Models.LaundryService;
import com.example.pelapak8126.R;

import java.util.List;

public class ServiceAdapter extends RecyclerView
        .Adapter<ServiceAdapter.MyViewHolder> {

    Context mContext;
    List<LaundryService> mData;

    public ServiceAdapter(Context mContext, List<LaundryService> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View row = LayoutInflater.from(mContext).inflate(R.layout.row_post_service,
                viewGroup, false);

        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvNamaLayanan.setText(mData.get(position).getNamaLayanan());
        holder.tvDesc.setText(mData.get(position).getDeskripsi());
        holder.tvBiaya.setText(mData.get(position).getBiayaPerkilo());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvNamaLayanan, tvDesc, tvBiaya;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvBiaya = itemView.findViewById(R.id.tv_biaya_rps);
            tvDesc = itemView.findViewById(R.id.tv_desc_rps);
            tvNamaLayanan = itemView.findViewById(R.id.tv_nama_layanan_rps);

        }
    }
}
