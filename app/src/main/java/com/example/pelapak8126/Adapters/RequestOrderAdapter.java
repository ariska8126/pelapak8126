package com.example.pelapak8126.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pelapak8126.Models.RequestOrder;
import com.example.pelapak8126.R;

import java.util.List;

public class RequestOrderAdapter extends RecyclerView
        .Adapter<RequestOrderAdapter.MyViewHolder> {

    Context mContext;
    List<RequestOrder> mData;

    public RequestOrderAdapter(Context mContext, List<RequestOrder> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View row = LayoutInflater.from(mContext).inflate(R.layout.row_request_order,
                viewGroup, false);

        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_setrika.setText(mData.get(position).getSetrika().toString());
        holder.tv_namaLayanan.setText(mData.get(position).getPaketLayanan());
        holder.tv_antarJemput.setText(mData.get(position).getAntarJemput().toString());
        holder.tv_deskripsi.setText(mData.get(position).getDeskripsi());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_namaLayanan, tv_deskripsi, tv_antarJemput, tv_setrika, tv_pewangi;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_antarJemput = itemView.findViewById(R.id.tv_antarJemput_rro);
            tv_deskripsi = itemView.findViewById(R.id.tv_deskripsi_rro);
            tv_namaLayanan = itemView.findViewById(R.id.tv_pakat_layanan_rro);
            tv_pewangi = itemView.findViewById(R.id.tv_pewangi_rro);
            tv_setrika = itemView.findViewById(R.id.tv_setrika_rro);

        }
    }
}
