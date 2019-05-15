package com.example.pelapak8126.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pelapak8126.Models.Transaksi;
import com.example.pelapak8126.R;
import com.google.android.gms.tasks.OnSuccessListener;
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

    String namaPelapak, photoGuest, longitudeLaundry, latitudeLaundry, longitudeGuest,
            latitudeGuest, namaGuest, idGuest, idLaundry, setrika, antarJemput,
            deskripsi, paketLayanan, transKey, photoPelapak, alamatPelapak, namaLaundry, timeStamp;
    String proses;

    DatabaseReference postReference, requestRef;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    String googleMap = "com.google.android.apps.maps";
    Uri gmmIntentUri;
    Intent mapIntent;
    String tujuan;

    TextView tv_note, tv_biaya, tv_berat, tv_bc1, tv_cost1, tv_sb, tv_sb1, tvbc2, tvcost2;
    EditText edt_berat, edt_cost, edt_note;
    Switch sw_statusBayar;
    String berat, biaya, note, bayaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terima_tolak_order);

        getSupportActionBar().hide();

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

        postReference = FirebaseDatabase.getInstance()
                .getReference("Transaksi");
        requestRef = FirebaseDatabase.getInstance()
                .getReference("RequestOrder").child(transKey);

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

        //if no jemput
        tv_note = findViewById(R.id.tvnote);
        tv_biaya = findViewById(R.id.tvcost);
        tv_berat = findViewById(R.id.tvbc);
        tv_bc1 = findViewById(R.id.tvbc1);
        tv_cost1 = findViewById(R.id.tvcost1);
        tv_sb = findViewById(R.id.tvsbupo);
        tv_sb1 = findViewById(R.id.tvsb1);
        tvcost2 = findViewById(R.id.tvcost2);
        tvbc2 = findViewById(R.id.tvbc2);

        edt_berat = findViewById(R.id.edt_bc_tdo);
        edt_cost = findViewById(R.id.edt_cost_tdo);
        edt_note = findViewById(R.id.edt_notePelapak_tto);
        sw_statusBayar = findViewById(R.id.sw_bayar_tdo);

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

        btn_tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRef.child("status").setValue("Di Tolak");
                finish();
            }
        });

        if (antarJemput.equals("Ya")){

            tv_note.setVisibility(View.INVISIBLE);
            tv_biaya.setVisibility(View.INVISIBLE);
            tv_berat.setVisibility(View.INVISIBLE);
            tv_bc1.setVisibility(View.INVISIBLE);
            tv_cost1.setVisibility(View.INVISIBLE);
            tv_sb.setVisibility(View.INVISIBLE);
            tv_sb1.setVisibility(View.INVISIBLE);

            edt_berat.setVisibility(View.INVISIBLE);
            edt_cost .setVisibility(View.INVISIBLE);
            edt_note .setVisibility(View.INVISIBLE);
            sw_statusBayar .setVisibility(View.INVISIBLE);
            tvcost2.setVisibility(View.INVISIBLE);
            tvbc2.setVisibility(View.INVISIBLE);

            btn_terima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        requestRef.child("status").setValue("Menunggu Dijemput");
                        dialogForm();
                }
            });


        } else {

            tv_note.setVisibility(View.VISIBLE);
            tv_biaya.setVisibility(View.VISIBLE);
            tv_berat.setVisibility(View.VISIBLE);
            tv_bc1.setVisibility(View.VISIBLE);
            tv_cost1.setVisibility(View.VISIBLE);
            tv_sb.setVisibility(View.VISIBLE);
            tv_sb1.setVisibility(View.VISIBLE);
            tvcost2.setVisibility(View.VISIBLE);
            tvbc2.setVisibility(View.VISIBLE);

            edt_berat.setVisibility(View.VISIBLE);
            edt_cost .setVisibility(View.VISIBLE);
            edt_note .setVisibility(View.VISIBLE);
            sw_statusBayar .setVisibility(View.VISIBLE);

            btn_terima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    berat = edt_berat.getText().toString();
                    biaya = edt_cost.getText().toString();
                    note = edt_note.getText().toString();
                    bayaran = sw_statusBayar.getText().toString();

                    Transaksi transaksi = new Transaksi(namaPelapak,photoGuest,longitudeLaundry,
                            latitudeLaundry,longitudeGuest,latitudeGuest, namaGuest,idGuest,idLaundry,
                            setrika,antarJemput,deskripsi,paketLayanan,transKey,photoPelapak,
                            alamatPelapak,namaLaundry,getCurrentTimeStamp(),proses,
                            berat, biaya, note, bayaran);

                    postToDatabase(transaksi);
                    updateUI();
                    requestRef.removeValue();

                }
            });
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

    private String getCurrentTimeStamp() {
        Long timeStamp = System.currentTimeMillis()/1000;
        return timeStamp.toString();
    }

    private void postToDatabase(Transaksi transaksi) {

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

    //modif v1
    private void dialogForm(){

        dialog = new AlertDialog.Builder(TerimaTolakOrderActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.popup_navigate, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestRef.child("status").setValue("Sedang Di Jemput");
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

            Toast.makeText(TerimaTolakOrderActivity.this, "Google Maps Belum Terinstall", Toast.LENGTH_SHORT).show();
        }

    }
}
