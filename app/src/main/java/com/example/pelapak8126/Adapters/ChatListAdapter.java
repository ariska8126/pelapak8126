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

import com.bumptech.glide.Glide;
import com.example.pelapak8126.Activities.ChatActivity;
import com.example.pelapak8126.Models.NewChatList;
import com.example.pelapak8126.R;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {

    Context mContext;
    List<NewChatList> mData;

    public ChatListAdapter(Context mContext, List<NewChatList> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_chatlist,
                viewGroup,
                false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.tv_msg.setText(mData.get(i).getMsg());
        myViewHolder.tv_guestName.setText(mData.get(i).getNamaGuest());
        myViewHolder.tv_time.setText(mData.get(i).getTimeStamp());
        Glide.with(mContext).load(mData.get(i).getGuestPhoto()).into(myViewHolder.imgv_guestPhoto);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_guestName, tv_msg, tv_time;
        ImageView imgv_guestPhoto;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_guestName = itemView.findViewById(R.id.tv_guestName_cl);
            tv_msg = itemView.findViewById(R.id.tv_msg_cl);
            tv_time = itemView.findViewById(R.id.tv_time_cl);
            imgv_guestPhoto = itemView.findViewById(R.id.imgv_guestPhoto_cl);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent chatRoom = new Intent(mContext, ChatActivity.class);
                    int position = getAdapterPosition();

                    chatRoom.putExtra("idGuest",mData.get(position).getGuestId());
                    chatRoom.putExtra("photoGuest", mData.get(position).getGuestPhoto());
                    chatRoom.putExtra("namaGuest", mData.get(position).getNamaGuest());

                    mContext.startActivity(chatRoom);

                }
            });
        }
    }
}
