package com.example.pelapak8126.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pelapak8126.Models.Transaksi;
import com.example.pelapak8126.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TerimaTolakOrderActivity extends AppCompatActivity {

    TextView tv_layanan, tv_nama_guest, tv_timeStamp, tv_antarJemput, tv_setrika, tv_desc, tv_alamat;
    Button btn_terima, btn_tolak;
    ImageView imgv_photoGuest;

    //saveToDB
    String namaPelapak, photoGuest, longitudeLaundry, latitudeLaundry, longitudeGuest,
            latitudeGuest, namaGuest, idGuest, idLaundry, setrika, antarJemput,
            deskripsi, paketLayanan, transKey, photoPelapak, alamatPelapak, namaLaundry, timeStamp;
    Float berat;
    int biaya;
    String proses, statusBayar;

    DatabaseReference postReference, deleteReference, reqordReff;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terima_tolak_order);

        //init view
        tv_desc = findViewById(R.id.tv_deskripsi_rro);
        tv_layanan = findViewById(R.id.tv_pakat_layanan_rro);
        tv_nama_guest = findViewById(R.id.tv_namaGuest_rro);
        tv_timeStamp= findViewById(R.id.tv_time_in_rro);
        tv_antarJemput = findViewById(R.id.tv_antarJemput_rro);
        tv_setrika = findViewById(R.id.tv_setrika_rro);
        tv_alamat = findViewById(R.id.tv_alamat_rro);

        btn_terima = findViewById(R.id.btn_yes_tto);
        btn_tolak = findViewById(R.id.btn_no_tto);

        imgv_photoGuest = findViewById(R.id.imgv_photoGuest_rro);

        //get intent
        transKey = getIntent().getExtras().getString("requestOrderKey");
        namaPelapak = getIntent().getExtras().getString("namaPelapak");
        photoGuest = getIntent().getExtras().getString("photoGuest");
        longitudeLaundry = getIntent().getExtras().getString("longitudeLaundry");
        latitudeLaundry = getIntent().getExtras().getString("latitudeLaundry");
        longitudeGuest = getIntent().getExtras().getString("longitudeGuest");
        latitudeGuest = getIntent().getExtras().getString("latitudeGuest");
        namaGuest = getIntent().getExtras().getString("namaGuest");
        idGuest = getIntent().getExtras().getString("idGuest");
        idLaundry = getIntent().getExtras().getString("idLaundry");
        setrika = getIntent().getExtras().getString("setrika");
        antarJemput = getIntent().getExtras().getString("antarjemput");
        deskripsi = getIntent().getExtras().getString("deskripsi");
        paketLayanan = getIntent().getExtras().getString("layanan");
        photoPelapak = getIntent().getExtras().getString("photoPelapak");
        alamatPelapak = getIntent().getExtras().getString("alamatPelapak");
        namaLaundry = getIntent().getExtras().getString("namaLaundry");
        timeStamp = getIntent().getExtras().getString("timeStamp");

        //set value
        proses = "Masuk Antrian";

        //bind view
        tv_layanan.setText(paketLayanan);
        tv_nama_guest.setText(namaGuest);
        tv_antarJemput.setText(antarJemput);
        tv_setrika.setText(setrika);
        tv_desc.setText(deskripsi);
        tv_timeStamp.setText(timeStamp);
        Glide.with(TerimaTolakOrderActivity.this).load(photoGuest).into(imgv_photoGuest);

        //on click
        btn_tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqordReff = FirebaseDatabase.getInstance()
                        .getReference("RequestOrder").child(transKey);
                reqordReff.child("status").setValue("DI Tolak");

                finish();
            }
        });

        btn_terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //revisi
                Transaksi transaksi = new Transaksi(namaPelapak,photoGuest,longitudeLaundry,
                        latitudeLaundry,longitudeGuest,latitudeGuest, namaGuest,idGuest,idLaundry,
                        setrika,antarJemput,deskripsi,paketLayanan,transKey,photoPelapak,
                        alamatPelapak,namaLaundry,getCurrentTimeStamp(),proses);

                postToDatabase(transaksi);

                //set save to database
                deleteReference = FirebaseDatabase.getInstance()
                        .getReference("RequestOrder").child(transKey);
                deleteReference.removeValue();


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

    private String getCurrentTimeStamp() {
        Long timeStamp = System.currentTimeMillis()/1000;
        return timeStamp.toString();
    }

    private void postToDatabase(Transaksi transaksi) {

        //Firebase
        postReference = FirebaseDatabase.getInstance()
                .getReference("Transaksi");

        postReference.child(transKey).setValue(transaksi).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                showMessage("Transaksi Diterima");
                updateUI();
            }
        });

    }

    private void updateUI() {
        Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivity);
    }

    private void showMessage(String s) {

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
