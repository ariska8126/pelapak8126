package com.example.pelapak8126.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
        holder.namaGuest.setText(mData.get(position).getNamaGuest());
        holder.tv_layanan.setText(mData.get(position).getLayanan());
        holder.tv_time.setText(mData.get(position).getTimeStamp());
        holder.tv_desc.setText(mData.get(position).getDeskripsi());
        holder.tv_status.setText(mData.get(position).getProses());
        Glide.with(mContext).load(mData.get(position).getPhotoGuest()).into(holder.imgv_guestPhoto);

        String keterangan = mData.get(position).getProses();

        String anjem = mData.get(position).getAntarjemput();
        if (anjem.equals("Ya")){
            if (keterangan.equals("Selesai")){

                holder.tv_keterangan.setText("klik untuk navigasi");
            } else {

                holder.tv_keterangan.setText("klik untuk update progres");
            }
        } else {

            if (keterangan.equals("Selesai")){

                holder.tv_keterangan.setText("menunggu di jemput pemilik");
            } else {

                holder.tv_keterangan.setText("klik untuk update progres");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namaGuest, tv_layanan, tv_time, tv_desc, tv_status, tv_keterangan;
        ImageView imgv_guestPhoto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_desc = itemView.findViewById(R.id.tv_desc_rro);
            tv_time = itemView.findViewById(R.id.tv_alamat_rro);
            tv_layanan = itemView.findViewById(R.id.tv_layanan_rro);
            namaGuest = itemView.findViewById(R.id.tv_namaGuest_rro);
            tv_status = itemView.findViewById(R.id.tv_status_rowo);
            tv_keterangan = itemView.findViewById(R.id.tv_keterangan);


            imgv_guestPhoto = itemView.findViewById(R.id.imgv_guest_rro);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    String proses = mData.get(position).getProses();
                    String antar = mData.get(position).getAntarjemput();
                    if (antar.equals("Ya")){

                        if (proses.equals("Selesai")){

                            String longitudeGuest = mData.get(position).getLongitudeGuest();
                            String latitudeGuest = mData.get(position).getLatitudeGuest();
                            navigate(longitudeGuest, latitudeGuest);
                        }else {

                            Intent ubahProgresOrder = new Intent(mContext, UbahProgressOrderActivity.class);

                            ubahProgresOrder.putExtra("orderKey", mData.get(position).getTransKey());
                            ubahProgresOrder.putExtra("longitudeG", mData.get(position).getLongitudeGuest());
                            ubahProgresOrder.putExtra("latitudeG", mData.get(position).getLatitudeGuest());

                            mContext.startActivity(ubahProgresOrder);
                        }
                    } else {

                        Intent ubahProgresOrder = new Intent(mContext, UbahProgressOrderActivity.class);

                        ubahProgresOrder.putExtra("orderKey", mData.get(position).getTransKey());
                        ubahProgresOrder.putExtra("longitudeG", mData.get(position).getLongitudeGuest());
                        ubahProgresOrder.putExtra("latitudeG", mData.get(position).getLatitudeGuest());

                        mContext.startActivity(ubahProgresOrder);
                    }
                }
            });
        }
    }

    private void navigate(String longitudeGuest, String latitudeGuest ){

        String googleMap = "com.google.android.apps.maps";
        Uri gmmIntentUri;
        Intent mapIntent;

        String longitudeG = longitudeGuest;
        String latitudeG = latitudeGuest;

        String tujuan = latitudeG+", "+longitudeG;

        gmmIntentUri = Uri.parse("google.navigation:q="+tujuan);
        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage(googleMap);

        if (mapIntent.resolveActivity(mContext.getPackageManager()) != null){

            mContext.startActivity(mapIntent);
        }else {

        }
    }
}
