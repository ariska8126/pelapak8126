package com.example.pelapak8126.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pelapak8126.Activities.UbahProgressOrderActivity;
import com.example.pelapak8126.Models.Transaksi;
import com.example.pelapak8126.R;

import java.util.List;

public class TransaksiAdapter extends RecyclerView
        .Adapter<TransaksiAdapter.MyViewHolder> {

    Context mContext;
    List<Transaksi> mData;

    public TransaksiAdapter(Context mContext, List<Transaksi> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View row = LayoutInflater.from(mContext).inflate(R.layout.row_order,
                viewGroup, false );
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //next update
//        holder.tv_orderKey.setText(mData.get(position).getTransaksiKey());
//        holder.tv_layanan.setText(mData.get(position).getLayanan());
//        holder.tv_guestname.setText(mData.get(position).getNamaGuest());
//        holder.tv_desc.setText(mData.get(position).getDeskripsi());

        //trial
        holder.tv_orderKey.setText(mData.get(position).getTransKey());
        holder.tv_layanan.setText(mData.get(position).getLayanan());
        holder.tv_guestname.setText(mData.get(position).getIdGuest());
        holder.tv_desc.setText(mData.get(position).getDeskripsi());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_orderKey, tv_layanan, tv_guestname, tv_desc;
        ImageView imgv_guestPhoto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_desc = itemView.findViewById(R.id.tv_desc_rro);
            tv_guestname = itemView.findViewById(R.id.tv_timeStamp_rro);
            tv_layanan = itemView.findViewById(R.id.tv_layanan_rro);
            tv_orderKey = itemView.findViewById(R.id.tv_namaGuest_rro);

            imgv_guestPhoto = itemView.findViewById(R.id.imgv_guest_rro);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent ubahProgresOrder = new Intent(mContext, UbahProgressOrderActivity.class);
                    int position = getAdapterPosition();

                    ubahProgresOrder.putExtra("orderKey", mData.get(position).getTransKey());
                    ubahProgresOrder.putExtra("namaGuest", mData.get(position).getNamaGuest());
                    ubahProgresOrder.putExtra("layanan", mData.get(position).getLayanan());

                    mContext.startActivity(ubahProgresOrder);

                }
            });

        }
    }
}
