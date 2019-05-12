package com.example.pelapak8126.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pelapak8126.Models.Feedback;
import com.example.pelapak8126.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class FeedbackAdapter extends RecyclerView
        .Adapter<FeedbackAdapter.MyViewHolder> {

    Context mContext;
    List<Feedback> mData;

    FirebaseUser currentUser;

    public FeedbackAdapter(Context mContext, List<Feedback> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View row = LayoutInflater.from(mContext).inflate(
                R.layout.row_feedback, viewGroup, false);

        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_comment.setText(mData.get(position).getKomentar());
        Glide.with(mContext).load(mData.get(position).getPhotoGuest())
                .into(holder.imgv_guest_photo);
        holder.tv_nama_guest.setText(mData.get(position).getNamaGuest());
        holder.tv_layanan.setText(mData.get(position).getLayanan());
        holder.rb_feedback.setRating(mData.get(position).getRate());
        holder.tv_time.setText(mData.get(position).getTimeStamp());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_nama_guest, tv_comment, tv_time, tv_layanan;
        RatingBar rb_feedback;
        ImageView imgv_guest_photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_comment = itemView.findViewById(R.id.tv_comment_fbc);
            tv_time = itemView.findViewById(R.id.tv_time_fbc);
            tv_nama_guest = itemView.findViewById(R.id.tv_guest_name_fbc);
            tv_layanan = itemView.findViewById(R.id.tv_layanan_fbc);
            rb_feedback = (RatingBar) itemView.findViewById(R.id.ratingBar_rate_fbc);
            imgv_guest_photo = itemView.findViewById(R.id.imgv_guest_photo_fbc);
        }
    }
}
