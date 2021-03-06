package com.example.pelapak8126.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pelapak8126.Activities.TambahDetailOrderActivity;
import com.example.pelapak8126.Activities.TerimaTolakOrderActivity;
import com.example.pelapak8126.Fragments.HomeFragment;
import com.example.pelapak8126.Models.RequestOrder;
import com.example.pelapak8126.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        holder.tv_layanan.setText(mData.get(position).getPaketLayanan());
        holder.tv_namaGuest.setText(mData.get(position).getNamaGuest());
        holder.tv_deskripsi.setText(mData.get(position).getDeskripsi());
        holder.tv_timeStamp.setText(mData.get(position).getTimeStamp());
        holder.tv_status.setText(mData.get(position).getStatus());
        Glide.with(mContext).load(mData.get(position).getPhotoGuest()).into(holder.img_photoGuest);
        String status = mData.get(position).getStatus();
        if (status.equals("Menunggu Dijemput")){

            holder.tv_note.setText("klik untuk navigasi");

        } else if (status.equals("Menunggu Konfirmasi")){

            holder.tv_note.setText("klik untuk konfirmasi");
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_layanan, tv_deskripsi, tv_namaGuest, tv_timeStamp, tv_status, tv_note;
        ImageView img_photoGuest;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_status = itemView.findViewById(R.id.tv_status_rrr);
            tv_layanan = itemView.findViewById(R.id.tv_layanan_rro);
            tv_namaGuest = itemView.findViewById(R.id.tv_namaGuest_rro);
            tv_timeStamp = itemView.findViewById(R.id.tv_alamat_rro);
            tv_deskripsi = itemView.findViewById(R.id.tv_desc_rro);
            img_photoGuest = itemView.findViewById(R.id.imgv_guest_rro);
            tv_note = itemView.findViewById(R.id.tv_note_rrr);;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String status = mData.get(position).getStatus().toString();

                    DatabaseReference requestRef;
                    requestRef = FirebaseDatabase.getInstance()
                            .getReference("RequestOrder").child(mData.get(position).getOrderKey());

                    if (status.equals("Menunggu Konfirmasi")){

                        Intent tetoOrder = new Intent(mContext, TerimaTolakOrderActivity.class);


                        tetoOrder.putExtra("namaPelapak", mData.get(position).getNamaPelapak());
                        tetoOrder.putExtra("photoGuest", mData.get(position).getPhotoGuest());
                        tetoOrder.putExtra("longitudeLaundry", mData.get(position).getLongitudeLaundry());
                        tetoOrder.putExtra("latitudeLaundry", mData.get(position).getLatitudeLaundry());
                        tetoOrder.putExtra("longitudeGuest", mData.get(position).getLongitudeGuest());
                        tetoOrder.putExtra("latitudeGuest", mData.get(position).getLatitudeGuest());
                        tetoOrder.putExtra("namaGuest", mData.get(position).getNamaGuest());
                        tetoOrder.putExtra("idGuest", mData.get(position).getIdGuest());
                        tetoOrder.putExtra("idLaundry", mData.get(position).getIdLaundry());
                        tetoOrder.putExtra("setrika", mData.get(position).getSetrika());
                        tetoOrder.putExtra("antarjemput", mData.get(position).getAntarJemput());
                        tetoOrder.putExtra("timeStamp", mData.get(position).getTimeStamp());
                        tetoOrder.putExtra("deskripsi", mData.get(position).getDeskripsi());
                        tetoOrder.putExtra("layanan", mData.get(position).getPaketLayanan());
                        tetoOrder.putExtra("requestOrderKey", mData.get(position).getOrderKey());
                        tetoOrder.putExtra("photoPelapak", mData.get(position).getPhotoPelapak());
                        tetoOrder.putExtra("alamatPelapak", mData.get(position).getAlamatPelapak());
                        tetoOrder.putExtra("namaLaundry", mData.get(position).getNamaLaundry());

                        mContext.startActivity(tetoOrder);

                    } else if (status.equals("Menunggu Dijemput")){
                        String longitudeGuest = mData.get(position).getLongitudeGuest();
                        String latitudeGuest = mData.get(position).getLatitudeGuest();

                        requestRef.child("status").setValue("Sedang Di Jemput");
                        navigate(longitudeGuest, latitudeGuest);
                    } else if (status.equals("Sedang Di Jemput")){

                        Intent addDetail = new Intent(mContext, TambahDetailOrderActivity.class);
                        addDetail.putExtra("transkey", mData.get(position).getOrderKey());
                        mContext.startActivity(addDetail);
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
