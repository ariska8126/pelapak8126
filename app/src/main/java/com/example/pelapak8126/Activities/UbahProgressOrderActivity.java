package com.example.pelapak8126.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelapak8126.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class UbahProgressOrderActivity extends AppCompatActivity {

    private RadioGroup rdg_cuci;
    private RadioButton rdb_cuci;
    private Button btn_cancel, btn_update;
    private TextView tv_orderKey, tv_namaGuest, tv_dateIn, tv_layanan;

    private String key, layanan, namaGuest, timeStamp;
    private String cuciValue;

    FirebaseUser user;
    DatabaseReference transReff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_progress_order);

        //get intent value
        key = getIntent().getExtras().getString("orderKey");

        //init view
        tv_orderKey = findViewById(R.id.tv_transKey_upo);
        tv_namaGuest = findViewById(R.id.tv_namaGuest_upo);
        tv_dateIn = findViewById(R.id.tv_dateIN_upo);
        tv_layanan = findViewById(R.id.tv_layanan_upo);

        btn_cancel = findViewById(R.id.btn_cancel_upo);
        btn_update = findViewById(R.id.btn_update_upo);

        rdg_cuci = findViewById(R.id.radioGroup_prosesCuci_upo);

        //firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        transReff = FirebaseDatabase.getInstance().getReference("Transaksi").child(key);

        transReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                layanan = dataSnapshot.child("layanan").getValue().toString();
                namaGuest = dataSnapshot.child("namaGuest").getValue().toString();
                Long timeStamp = Long.valueOf(dataSnapshot.child("timeStamp").getValue().toString());
                tv_orderKey.setText(key);
                tv_layanan.setText(layanan);
                tv_namaGuest.setText(namaGuest);
                tv_dateIn.setText(getDate(timeStamp));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //radiogrup init set value
        //onclick
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                transReff.child("proses").setValue(cuciValue);

                Toast.makeText(UbahProgressOrderActivity.this, "perubahan berhasil di simpan!", Toast.LENGTH_SHORT).show();
                updateUI();

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    private String getDate(Long timeStamp) {

        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone timeZone = TimeZone.getDefault();
            calendar.setTimeInMillis(timeStamp*1000);
            calendar.add(Calendar.MILLISECOND, timeZone.getOffset(calendar.getTimeInMillis()));
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date date = (Date) calendar.getTime();
            return sdf.format(date);
        }catch (Exception e){

        }
        return "";
    }

    public void rdbcuci(View v){
        int radioButtonIdcuci =rdg_cuci.getCheckedRadioButtonId();
        rdb_cuci= findViewById(radioButtonIdcuci);

        Toast.makeText(getBaseContext(), rdb_cuci.getText(), Toast.LENGTH_SHORT).show();
        cuciValue = rdb_cuci.getText().toString();
    }


    private void updateUI() {

        Intent homeAct = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeAct);
    }
}
