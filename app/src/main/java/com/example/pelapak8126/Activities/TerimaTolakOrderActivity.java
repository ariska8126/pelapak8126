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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TerimaTolakOrderActivity extends AppCompatActivity {

    TextView tv_layanan, tv_nama_guest, tv_timeStamp, tv_antarJemput, tv_setrika, tv_desc;
    Button btn_terima, btn_tolak;
    ImageView imgv_photoGuest;

    //saveToDB
    String namaPelapak, photoGuest, longitudeLaundry, latitudeLaundry, longitudeGuest,
            latitudeGuest, namaGuest, idGuest, idLaundry, setrika, antarjemput,
            deskripsi, layanan, transKey, photoPelapak, alamatPelapak, namaLaundry;
    Object timeStamp;
    Float berat;
    int biaya;
    String proses, statusBayar;

    DatabaseReference postReference, deleteReference, reqordReff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terima_tolak_order);

//        reqordReff = FirebaseDatabase.getInstance().getReference("RequestOrder");

        //init view
        tv_desc = findViewById(R.id.tv_deskripsi_rro);
        tv_layanan = findViewById(R.id.tv_pakat_layanan_rro);
        tv_nama_guest = findViewById(R.id.tv_namaGuest_rro);
        tv_timeStamp= findViewById(R.id.tv_timeStamp_rro);
        tv_antarJemput = findViewById(R.id.tv_antarJemput_rro);
        tv_setrika = findViewById(R.id.tv_setrika_rro);

        btn_terima = findViewById(R.id.btn_yes_tto);
        btn_tolak = findViewById(R.id.btn_no_tto);

        imgv_photoGuest = findViewById(R.id.imgv_photoGuest_rro);

        //get intent
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
        antarjemput = getIntent().getExtras().getString("antarjemput");
//        timeStamp = getIntent().getExtras().getString("timeStamp");
        deskripsi = getIntent().getExtras().getString("deskripsi");
        layanan = getIntent().getExtras().getString("layanan");
        transKey = getIntent().getExtras().getString("requestOrderKey");
        photoPelapak = getIntent().getExtras().getString("photoPelapak");
        alamatPelapak = getIntent().getExtras().getString("alamatPelapak");
        namaLaundry = getIntent().getExtras().getString("namaLaundry");

        //set value
        proses = "Masuk Antrian";

        //bind view
        tv_layanan.setText(layanan);
        tv_nama_guest.setText(namaGuest);
//        tv_timeStamp.setText(timeStamp);
        tv_antarJemput.setText(antarjemput);
        tv_setrika.setText(setrika);
        tv_desc.setText(deskripsi);
        Glide.with(this).load(photoGuest).into(imgv_photoGuest);

        //on click
        btn_tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set status to di tolak

                finish();
            }
        });

        btn_terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set save to database
                deleteReference = FirebaseDatabase.getInstance()
                        .getReference("RequestOrder").child(transKey);
                deleteReference.removeValue();

                //revisi
                Transaksi transaksi = new Transaksi(namaPelapak,photoGuest,longitudeLaundry,
                        latitudeLaundry,longitudeGuest,latitudeGuest, namaGuest,idGuest,idLaundry,
                        setrika,antarjemput,deskripsi,layanan,transKey,photoPelapak,alamatPelapak,namaLaundry,proses);

                postToDatabase(transaksi);

                //updateStatus
//                reqordReff.child(transKey).child("status").setValue(proses);

            }
        });

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
