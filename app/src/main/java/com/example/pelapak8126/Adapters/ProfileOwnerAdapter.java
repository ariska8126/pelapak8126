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
import com.example.pelapak8126.Models.Pelapak;
import com.example.pelapak8126.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ProfileOwnerAdapter extends RecyclerView
        .Adapter<ProfileOwnerAdapter.MyViewHolder> {

    Context mContext;
    List<Pelapak> mData;

    FirebaseUser currentUser;

    public ProfileOwnerAdapter(Context mContext, List<Pelapak> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ProfileOwnerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View row = LayoutInflater.from(mContext)
                .inflate(R.layout.row_profile, parent, false);

        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileOwnerAdapter.MyViewHolder holder, int position) {

        holder.tv_mail.setText(currentUser.getEmail());
        holder.tv_phone.setText(mData.get(position).getPhone());
        holder.tv_alamat.setText(mData.get(position).getAlamat());
        holder.tv_namaLaundry.setText(mData.get(position).getNamaLaundry());
        holder.tv_nama.setText(currentUser.getDisplayName());
        holder.tv_uid.setText(mData.get(position).getUserId());
        Glide.with(mContext).load(currentUser.getPhotoUrl().toString()).into(holder.img_user_photo);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_namaLaundry, tv_alamat, tv_nama, tv_uid, tv_phone, tv_mail;
        ImageView img_user_photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_alamat = itemView.findViewById(R.id.tv_alamat_prof);
            tv_namaLaundry= itemView.findViewById(R.id.tv_nama_laundry_prof);
            tv_nama = itemView.findViewById(R.id.tv_nama_prof);
            tv_uid = itemView.findViewById(R.id.td_uid_prof);
            tv_phone = itemView.findViewById(R.id.tv_phone_prof);
            tv_mail = itemView.findViewById(R.id.tv_mail_prof);
            img_user_photo = itemView.findViewById(R.id.imgv_user_photo_prof);


            currentUser = FirebaseAuth.getInstance().getCurrentUser();

        }
    }
}
