package com.example.pelapak8126.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pelapak8126.Models.Distance;
import com.example.pelapak8126.Models.OwnerLaundry;
import com.example.pelapak8126.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CompletingProfileActivity extends AppCompatActivity {

    private EditText edt_nama_laundry, edt_phone, edt_alamat;
    private TextView tv_email, tv_nama_pemilik;
    private ImageView imgv_user_photo, imgv_laundry_photo;
    private Boolean statusBuka = false;
    private Boolean statusJemput = false;

    private ProgressBar pb_fetch, pb_submit;

    private static float rate = 1;

    private Uri pickedImageUri = null;

    Double latitude;
    Double longitude;

    private static final int PReCode = 2;
    private static final int REQUESTCODE = 2;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    private static final int MY_PERMISSIONS_REQUEST_READ_ACCESS_COARSE_LOCATION = 1;
    TextView user_location;
    Button fetch, btn_submit;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completing_profile);

        //init view
        pb_fetch = findViewById(R.id.progressBar_getlokasi_cp);
        pb_submit = findViewById(R.id.progressBar_submit_cp);
        btn_submit = findViewById(R.id.btn_submit_cp);
        tv_nama_pemilik = findViewById(R.id.tv_name_cp);
        tv_email = findViewById(R.id.tv_email_cp);
        imgv_user_photo = findViewById(R.id.imgv_user_photo_cp);
        imgv_laundry_photo = findViewById(R.id.imgv_photo_laundry_cp);
        edt_nama_laundry = findViewById(R.id.edt_nama_laundry_cp);
        edt_phone = findViewById(R.id.edt_phone_cp);
        edt_alamat = findViewById(R.id.edt_alamat_cp);

        //set visibility
        pb_submit.setVisibility(View.INVISIBLE);
        pb_fetch.setVisibility(View.INVISIBLE);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        imgv_laundry_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkAndRequestForPermission();
            }
        });


        tv_nama_pemilik.setText(currentUser.getDisplayName());
        tv_email.setText(currentUser.getEmail());

        Glide.with(this).load(currentUser.getPhotoUrl()).into(imgv_user_photo);

        user_location = findViewById(R.id.user_location);
        fetch = findViewById(R.id.btn_fetch);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetch.setVisibility(View.INVISIBLE);
                pb_fetch.setVisibility(View.VISIBLE);

                fetchLocation();

            }

        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_submit.setVisibility(View.INVISIBLE);
                pb_submit.setVisibility(View.VISIBLE);

                if (!edt_nama_laundry.getText().toString().isEmpty()
                        && !edt_alamat.getText().toString().isEmpty()
                        && !edt_phone.getText().toString().isEmpty()
                        && longitude != null
                        && latitude != null
                        && imgv_laundry_photo != null
                ){

                    //simpan image to firebase storage
                    StorageReference laundryImagesReference = FirebaseStorage.getInstance().getReference().child("laundry_imgaes");
                    final StorageReference imageFilePath = laundryImagesReference.child(pickedImageUri.getLastPathSegment());

                    imageFilePath.putFile(pickedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String imageDownloadLink = uri.toString();

                                    //create post object
                                    OwnerLaundry ownerLaundry = new OwnerLaundry(currentUser.getUid(),
                                            edt_nama_laundry.getText().toString(),
                                            edt_alamat.getText().toString(),
                                            edt_phone.getText().toString(),
                                            latitude,
                                            longitude,
                                            rate,
                                            statusJemput.toString(),
                                            statusBuka.toString(),
                                            currentUser.getEmail(),
                                            currentUser.getDisplayName(),
                                            currentUser.getPhotoUrl().toString(),
                                            imageDownloadLink
                                    );

//                                    Distance distance = new Distance(currentUser.getPhotoUrl().toString(),
//                                            currentUser.getDisplayName(),
//                                            currentUser.getUid(),
//                                            rate, edt_alamat.getText().toString()
//                                    );

                                    //save to database
                                    simpanProfile(ownerLaundry);
//                                    simpanDistance(distance);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    //something goes wrong upload picture
                                    showMessage(e.getMessage());
                                    pb_submit.setVisibility(View.INVISIBLE);
                                    btn_submit.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    });

                } else {

                    showMessage("Pastikan semua kolom sudah anda isi, dan klik get location sudah di klik");
                    fetch.setVisibility(View.VISIBLE);
                    pb_fetch.setVisibility(View.INVISIBLE);
                    btn_submit.setVisibility(View.VISIBLE);
                    pb_submit.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
//distance
//    private void simpanDistance(Distance distance) {
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//    }

    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }

    private void simpanProfile(OwnerLaundry ownerLaundry) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("OwnerLaundry");

        //get owner uniqe ID and update owner key
        String key = myRef.getKey();
        ownerLaundry.setOwnerKey(key);

        //add owner
        myRef.child(currentUser.getUid()).setValue(ownerLaundry).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                showMessage("data anda telah dilengkapi");
                btn_submit.setVisibility(View.VISIBLE);
                pb_submit.setVisibility(View.INVISIBLE);
                updateUI();
            }
        });

    }

    private void updateUI() {

        Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivity);
        finish();
    }

    private void fetchLocation() {

        if (ContextCompat.checkSelfPermission(CompletingProfileActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(CompletingProfileActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                new AlertDialog.Builder(this).setTitle("Require Location Permission").setMessage("you have to give this permission to access the feature")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(CompletingProfileActivity.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_READ_ACCESS_COARSE_LOCATION);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create().show();

                pb_fetch.setVisibility(View.INVISIBLE);
                fetch.setVisibility(View.VISIBLE);

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(CompletingProfileActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_ACCESS_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {

            // Permission has already been granted
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                                pb_fetch.setVisibility(View.INVISIBLE);
                                fetch.setVisibility(View.VISIBLE);

                                user_location.setText("Latitude= "+latitude + "\nLongitude= "+longitude);

                            }
                        }
                    });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_ACCESS_COARSE_LOCATION){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){


            } else {


            }
        }
    }

    private void checkAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(CompletingProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(CompletingProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){

                Toast.makeText(CompletingProfileActivity.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();
            }

            else {
                ActivityCompat.requestPermissions(CompletingProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReCode);
            }
        }
        else {
            openGallery();
        }
    }

    private void openGallery() {

        Intent intentGallery = new Intent(Intent.ACTION_GET_CONTENT);
        intentGallery.setType("image/*");
        startActivityForResult(intentGallery, REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null){

            //user successfull pick a picture
            pickedImageUri = data.getData();
            imgv_laundry_photo.setImageURI(pickedImageUri);
        }
    }
}
