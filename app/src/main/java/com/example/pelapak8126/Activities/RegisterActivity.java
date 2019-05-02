package com.example.pelapak8126.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pelapak8126.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterActivity extends AppCompatActivity {



    private EditText edt_namaPemilik, edt_emailAddress,
            edt_password, edt_password2;
    private ImageView imgV_userPhoto;
    private Button btn_registerUser;
    private ProgressBar loadingProgressBar_register;
    private FirebaseAuth mAuth;
    Uri pickedImageUri;
    static int REQUESTCODE = 1;
    static int PReqCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //init view
        edt_namaPemilik = findViewById(R.id.edt_namaPemilik_register);
        edt_emailAddress = findViewById(R.id.edt_email_register);
        edt_password = findViewById(R.id.edt_password_register);
        edt_password2 = findViewById(R.id.edt_password2_register);
        btn_registerUser = findViewById(R.id.btn_submit_ubp);
        imgV_userPhoto = findViewById(R.id.imgv_userPhoto_register);
        loadingProgressBar_register = findViewById(R.id.progressBar_Register);

        //set visibility
        loadingProgressBar_register.setVisibility(View.INVISIBLE);

        //firebase
        mAuth = FirebaseAuth.getInstance();

        //on click
        btn_registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_registerUser.setVisibility(View.INVISIBLE);
                loadingProgressBar_register.setVisibility(View.VISIBLE);

                final String name = edt_namaPemilik.getText().toString();
                final String email = edt_emailAddress.getText().toString();
                final String password = edt_password.getText().toString();
                final String password2 = edt_password2.getText().toString();


                if (email.isEmpty() || password.isEmpty() || name.isEmpty() || !password.equals(password2) || pickedImageUri == null ){

                    showMessage("pastikan semua kolom sudah diisi.");
                    btn_registerUser.setVisibility(View.VISIBLE);
                    loadingProgressBar_register.setVisibility(View.INVISIBLE);

                }
                else
                {
                    CreateUserAccount(email, password, name);
                }

            }
        });

        imgV_userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= 22){

                    checkAndRequestForPermission();
                }
                else
                {
                    openGallery();
                }
            }
        });
    }

    private void openGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESTCODE);
    }

    private void CreateUserAccount(String email, String password, final String name) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            showMessage("Account Created");

                            //update profile picture & name
                            updateUserInfo(name, pickedImageUri, mAuth.getCurrentUser());
                        }
                        else
                        {
                            showMessage("Account Creation Failed"+task.getException().getMessage().toString());
                            btn_registerUser.setVisibility(View.VISIBLE);
                            loadingProgressBar_register.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    private void updateUserInfo(final String name, Uri pickedImageUri,
                                final FirebaseUser currentUser) {

        //upload image to firebase storage and get url
        StorageReference mStorage = FirebaseStorage.getInstance()
                .getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage
                .child(pickedImageUri.getLastPathSegment());
        imageFilePath.putFile(pickedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //image upload success -> get url

                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        //uri contain image url
                        UserProfileChangeRequest profileUpadate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name).setPhotoUri(uri).build();

                        currentUser.updateProfile(profileUpadate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){

                                    //user info update success
                                    showMessage("Register Complete");
                                    updateUI();
                                }
                            }
                        });

                    }
                });
            }
        });

    }

    private void updateUI() {

        Intent ubahProfile = new Intent(getApplicationContext(), CompletingProfileActivity.class);
        startActivity(ubahProfile);
        finish();
    }



    private void checkAndRequestForPermission() {

        if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){

                Toast.makeText(RegisterActivity.this, "Please accept for request permission", Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(RegisterActivity.this, new  String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        }
        else
        {
            openGallery();
        }

    }

    private void showMessage(String s) {

        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null){

            //user success pick image -> save its reference to uri variabel
            pickedImageUri = data.getData();
            imgV_userPhoto.setImageURI(pickedImageUri);
        }

    }
}
