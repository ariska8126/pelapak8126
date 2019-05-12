package com.example.pelapak8126.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pelapak8126.Adapters.ChatAdapter;
import com.example.pelapak8126.Models.Chat;
import com.example.pelapak8126.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    ImageView imgv_photoUser;
    TextView tv_userName;

    FirebaseUser user;
    DatabaseReference chatReference;

    String userId, photoGuest, namaGuest;

    ChatAdapter chatAdapter;
    List<Chat> mData;

    RecyclerView rv_chat;

    ImageButton btn_send;
    EditText edt_pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getSupportActionBar().hide();

        chatReference = FirebaseDatabase.getInstance().getReference("Chat");

        imgv_photoUser = findViewById(R.id.imgv_userPhoto_ac);
        tv_userName = findViewById(R.id.tv_username_ac);

        btn_send = findViewById(R.id.imgv_send);
        edt_pesan = findViewById(R.id.edt_pesan);

        rv_chat = findViewById(R.id.rv_chat);
        rv_chat.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        rv_chat.setLayoutManager(linearLayoutManager);

        user = FirebaseAuth.getInstance().getCurrentUser();
        //get intent
        userId = getIntent().getExtras().getString("idGuest");
        photoGuest = getIntent().getExtras().getString("photoGuest");
        namaGuest = getIntent().getExtras().getString("namaGuest");

        Glide.with(this).load(photoGuest).into(imgv_photoUser);
        tv_userName.setText(namaGuest);


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = edt_pesan.getText().toString();
                if (!msg.equals("")){
                    sendMessage(user.getUid(), userId, msg);
                } else {
                    Toast.makeText(ChatActivity.this, "anda tidak bisa mengirim pesan kosong!",
                            Toast.LENGTH_SHORT).show();
                }

                edt_pesan.setText("");
            }
        });

        readMessage(user.getUid(), userId, user.getPhotoUrl().toString());

    }

    private void sendMessage(String sender, String receiver, String message){

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        chatReference.push().setValue(hashMap);

    }

    private void readMessage(final String myId, final String userId, final String imgUrl){

        mData = new ArrayList<>();

        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mData.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myId) && chat.getSender().equals(userId)
                            || chat.getReceiver().equals(userId) && chat.getSender().equals(myId)){

                        mData.add(chat);
                    }

                    chatAdapter = new ChatAdapter(ChatActivity.this, mData, imgUrl);
                    rv_chat.setAdapter(chatAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
