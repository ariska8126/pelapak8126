package com.example.pelapak8126.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.pelapak8126.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class TambahDetailOrderActivity extends AppCompatActivity {

    String transkey, berat, biaya, note, bayaran;
    EditText edt_biaya, edt_berat, edt_note;
    Switch sw_bayar;
    Button btn_submit;
    DatabaseReference requestRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_detail_order);

        transkey = getIntent().getExtras().getString("transkey");

        edt_berat = findViewById(R.id.edt_bc_tdo);
        edt_note = findViewById(R.id.edt_note_tdo);
        edt_biaya = findViewById(R.id.edt_cost_tdo);
        sw_bayar = findViewById(R.id.sw_bayar_tdo);
        btn_submit = findViewById(R.id.btn_submit_tdo);

        requestRef = FirebaseDatabase.getInstance().getReference("RequestOrder");

        sw_bayar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b == true){
                    bayaran = "Sudah Dibayar";
                    Toast.makeText(TambahDetailOrderActivity.this, "Meminta Setrika!", Toast.LENGTH_SHORT).show();
                } else {
                    bayaran = "Belum Dibayar";
                    Toast.makeText(TambahDetailOrderActivity.this, "Tidak Perlu Setrika!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (bayaran == null){
            bayaran = "Belum Dibayar";
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                berat = edt_berat.getText().toString();
                biaya = edt_biaya.getText().toString();
                note = edt_note.getText().toString();
                updateDetail(berat, biaya, note, bayaran, transkey);
                requestRef.child(transkey).child("status").setValue("Konfirmasi");
                updateUI();
            }
        });


    }

    private void updateUI() {
        Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivity);
    }

    private void updateDetail(String berat, String biaya, String note, String bayaran, String transkey){

//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("biaya", biaya);
//        hashMap.put("berat", berat);
//        hashMap.put("note", note);
//        hashMap.put("statusBayar", bayaran);

        requestRef.child(transkey).child("biaya").setValue(biaya);
        requestRef.child(transkey).child("berat").setValue(berat);
        requestRef.child(transkey).child("note").setValue(note);
        requestRef.child(transkey).child("statusBayar").setValue(bayaran);

    }
}
