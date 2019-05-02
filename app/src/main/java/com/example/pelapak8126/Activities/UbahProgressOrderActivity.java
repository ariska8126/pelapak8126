package com.example.pelapak8126.Activities;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UbahProgressOrderActivity extends AppCompatActivity {

    private RadioGroup rdg_cuci;
    private RadioButton rdb_cuci;
    private Button btn_cancel, btn_update;
    private TextView tv_orderKey, tv_namaGuest, tv_dateIn, tv_layanan;

    private String key, layanan;
    private String cuciValue;

    FirebaseUser user;
    DatabaseReference transReff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_progress_order);

        //get intent value
        key = getIntent().getExtras().getString("orderKey");
        layanan = getIntent().getExtras().getString("layanan");

        //firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        transReff = FirebaseDatabase.getInstance().getReference("Transaksi");

        //init view
        tv_orderKey = findViewById(R.id.tv_transKey_upo);
        tv_namaGuest = findViewById(R.id.tv_namaGuest_upo);
        tv_dateIn = findViewById(R.id.tv_dateIN_upo);
        tv_layanan = findViewById(R.id.tv_layanan_upo);

        btn_cancel = findViewById(R.id.btn_cancel_upo);
        btn_update = findViewById(R.id.btn_update_upo);

        rdg_cuci = findViewById(R.id.radioGroup_prosesCuci_upo);

        //bind intent to view
        tv_orderKey.setText(key);
        tv_layanan.setText(layanan);

        //radiogrup init set value

        //onclick
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                transReff.child(key).child("proses").setValue(cuciValue);

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
