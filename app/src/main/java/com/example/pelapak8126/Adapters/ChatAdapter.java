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
import com.example.pelapak8126.Models.Chat;
import com.example.pelapak8126.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mData;

    private String imgUrl;

    public ChatAdapter(Context mContext, List<Chat> mData, String imgUrl) {
        this.mContext = mContext;
        this.mData = mData;
        this.imgUrl = imgUrl;
    }

    FirebaseUser user;

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder
            (@NonNull ViewGroup parent, int viewType) {

        if (viewType == MSG_TYPE_RIGHT){

            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right,
                    parent, false);
            return new MyViewHolder(view);
        } else {

            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left,
                    parent, false);
            return new MyViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {

        Chat chat = mData.get(position);
        holder.tv_message.setText(chat.getMessage());

        if (imgUrl.equals("default")){
            holder.imgv_photo.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(imgUrl).into(holder.imgv_photo);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_message;
        ImageView imgv_photo;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_message = itemView.findViewById(R.id.tv_showMessage);
            imgv_photo = itemView.findViewById(R.id.imgv_photoProfile);
        }
    }

    @Override
    public int getItemViewType(int position) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (mData.get(position).getSender().equals(user.getUid())){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }

    }
}
