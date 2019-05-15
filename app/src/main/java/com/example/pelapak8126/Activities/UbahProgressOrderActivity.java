package com.example.pelapak8126.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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

    private String key, layanan, namaGuest;
    private String cuciValue;

    FirebaseUser user;
    DatabaseReference transReff;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    String googleMap = "com.google.android.apps.maps";
    Uri gmmIntentUri;
    Intent mapIntent;
    String tujuan, latitudeGuest, longitudeGuest;

    DatabaseReference laundryPelapak;
    FirebaseDatabase firebaseDatabase;
    String proses;

    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_progress_order);

        getSupportActionBar().hide();

        key = getIntent().getExtras().getString("orderKey");
        latitudeGuest = getIntent().getExtras().getString(" latitudeG");
        longitudeGuest = getIntent().getExtras().getString("longitudeG");

        tv_orderKey = findViewById(R.id.tv_transKey_upo);
        tv_namaGuest = findViewById(R.id.tv_namaGuest_upo);
        tv_dateIn = findViewById(R.id.tv_dateIN_upo);
        tv_layanan = findViewById(R.id.tv_layanan_upo);

        btn_cancel = findViewById(R.id.btn_cancel_upo);
        btn_update = findViewById(R.id.btn_update_upo);

        rdg_cuci = findViewById(R.id.radioGroup_prosesCuci_upo);

        toggleButton = findViewById(R.id.tgb_bayran_upo);

        user = FirebaseAuth.getInstance().getCurrentUser();
        transReff = FirebaseDatabase.getInstance().getReference("Transaksi")
                .child(key);

        firebaseDatabase = FirebaseDatabase.getInstance();
        laundryPelapak = firebaseDatabase.getReference("OwnerLaundry").child(user.getUid());

        transReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                proses = dataSnapshot.child("statusBayar").getValue().toString();
                if (proses.equals("Sudah Dibayar")){
                    toggleButton.setChecked(true);
                } else {
                    toggleButton.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (toggleButton.isChecked()){
                    transReff.child("statusBayar").setValue("Sudah Dibayar");
                }else {
                    transReff.child("statusBayar").setValue("Belum Dibayar");
                }
            }
        });

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

        //onclick
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                transReff.child("proses").setValue(cuciValue);

                if (cuciValue.equals("Selesai")){
                    dialogForm();
                } else{
                    Toast.makeText(UbahProgressOrderActivity.this, "perubahan berhasil di simpan!", Toast.LENGTH_SHORT).show();
                    updateUI();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    private void dialogForm(){

        dialog = new AlertDialog.Builder(UbahProgressOrderActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.popup_navigate, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                navigateAnjem();
                updateUI();
            }
        });

        dialog.setNegativeButton("Nanti", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                updateUI();
            }
        });

        dialog.show();
    }

    private void navigateAnjem(){

        tujuan = latitudeGuest+", "+longitudeGuest;

        gmmIntentUri = Uri.parse("google.navigation:q="+tujuan);
        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage(googleMap);

        if (mapIntent.resolveActivity(getPackageManager()) != null){

            startActivity(mapIntent);
        }else {

            Toast.makeText(UbahProgressOrderActivity.this, "Google Maps Belum Terinstall",
                    Toast.LENGTH_SHORT).show();
        }

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
