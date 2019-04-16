package com.example.pelapak8126.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pelapak8126.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_user_email, edt_password;
    private Button btn_login, btn_daftar;
    private ProgressBar login_progress;
    private Intent HomeActivity;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //intent
        HomeActivity = new Intent(this, HomeActivity.class);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        //init view
        edt_password = findViewById(R.id.edt_password_login);
        edt_user_email = findViewById(R.id.edt_usermail_login);
        btn_daftar = findViewById(R.id.btn_daftar_login);
        btn_login = findViewById(R.id.btn_login_login);
        login_progress = findViewById(R.id.progressBar_login);

        //visibility
        login_progress.setVisibility(View.INVISIBLE);

        //on click
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_daftar.setVisibility(View.INVISIBLE);
                btn_login.setVisibility(View.INVISIBLE);
                login_progress.setVisibility(View.VISIBLE);

                final String mail = edt_user_email.getText().toString();
                final String password = edt_password.getText().toString();

                if (mail.isEmpty() || password.isEmpty()){

                    showMessage("Pastikan semua kolom terisi!");
                    login_progress.setVisibility(View.INVISIBLE);
                    btn_login.setVisibility(View.VISIBLE);
                    btn_daftar.setVisibility(View.VISIBLE);
                } else {

                    signIn(mail, password);
                }
            }
        });

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerActivity = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerActivity);
                finish();
            }
        });
    }

    private void signIn(String mail, String password) {

        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            login_progress.setVisibility(View.INVISIBLE);
                            btn_login.setVisibility(View.VISIBLE);
                            btn_daftar.setVisibility(View.VISIBLE);
                            updateUI();
                        }
                        else
                        {
                            showMessage(task.getException().getMessage());
                            login_progress.setVisibility(View.INVISIBLE);
                            btn_login.setVisibility(View.VISIBLE);
                            btn_daftar.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void updateUI() {

        startActivity(HomeActivity);
        finish();
    }

    private void showMessage(String s) {

        Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){

            //user allready connected - direct to home
            updateUI();

        }
    }
}
