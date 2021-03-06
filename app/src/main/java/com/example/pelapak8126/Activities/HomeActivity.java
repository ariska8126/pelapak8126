package com.example.pelapak8126.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pelapak8126.Fragments.AboutFragment;
import com.example.pelapak8126.Fragments.ChatListFragment;
import com.example.pelapak8126.Fragments.FeedbackFragment;
import com.example.pelapak8126.Fragments.GuideFragment;
import com.example.pelapak8126.Fragments.HomeFragment;
import com.example.pelapak8126.Fragments.LayananFragment;
import com.example.pelapak8126.Fragments.OrderFragment;
import com.example.pelapak8126.Fragments.ProfileFragment;
import com.example.pelapak8126.Models.LaundryService;
import com.example.pelapak8126.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.view.View.INVISIBLE;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseUser currentUser;

    Dialog popupScreenAddService;
    private EditText edt_nama_layanan, edt_desc, edt_biaya;
    private Button btn_simpan;
    private ProgressBar progressBarSimpan;
    private ImageView imgv_user_photo;

    FloatingActionButton fab, fab_service;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!amIConnected()){
            dialogForm();
        }

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        initPopup();

        fab_service = findViewById(R.id.fab2);
        fab = findViewById(R.id.fab);

        fab_service.setVisibility(INVISIBLE);

        fab_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupScreenAddService.show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fab.setVisibility(INVISIBLE);
                getSupportActionBar().setTitle("Pesan Masuk");
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ChatListFragment()).commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateNavHeader();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }

    private void dialogForm(){

        dialog = new AlertDialog.Builder(HomeActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.popup_nointernet, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.show();
    }

    private void updateNavHeader() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);

        TextView navUsername = headerView.findViewById(R.id.nav_username);
        TextView navUserEmail = headerView.findViewById(R.id.nav_user_mail);

        ImageView navUserPhoto = headerView.findViewById(R.id.nav_user_photo);

        navUsername.setText(currentUser.getDisplayName());
        navUserEmail.setText(currentUser.getEmail());

        Glide.with(this).load(currentUser.getPhotoUrl()).into(navUserPhoto);

    }

    private void initPopup() {

        popupScreenAddService = new Dialog(this);
        popupScreenAddService.setContentView(R.layout.popup_add_service);
        popupScreenAddService.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        popupScreenAddService.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.WRAP_CONTENT);
        popupScreenAddService.getWindow().getAttributes().gravity = Gravity.CENTER;

        edt_biaya = popupScreenAddService.findViewById(R.id.edt_biayaperkilo_tl);
        edt_desc = popupScreenAddService.findViewById(R.id.edt_desc_tl);
        edt_nama_layanan = popupScreenAddService.findViewById(R.id.edt_nama_layanan_tl);

        btn_simpan = popupScreenAddService.findViewById(R.id.btn_simpan_tl);
        imgv_user_photo = popupScreenAddService.findViewById(R.id.imgv_user_photo_tl);
        progressBarSimpan = popupScreenAddService.findViewById(R.id.progressBar_popup_tl);
        progressBarSimpan.setVisibility(View.INVISIBLE);

        Glide.with(HomeActivity.this).load(currentUser
                .getPhotoUrl()).into(imgv_user_photo);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_simpan.setVisibility(View.INVISIBLE);
                progressBarSimpan.setVisibility(View.VISIBLE);

                if (!edt_biaya.getText().toString().isEmpty()
                        && !edt_desc.getText().toString().isEmpty()
                        && !edt_nama_layanan.getText().toString().isEmpty()){

                    LaundryService laundryService = new LaundryService(
                            edt_nama_layanan.getText().toString(),
                            edt_desc.getText().toString(),
                            edt_biaya.getText().toString(),
                            currentUser.getUid(),
                            currentUser.getPhotoUrl().toString());

                    addPostFirebaseDatabase(laundryService);

                } else{

                    showMessage("Please verify all Fields");
                    btn_simpan.setVisibility(View.VISIBLE);
                    progressBarSimpan.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void addPostFirebaseDatabase(LaundryService laundryService) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("LaundryService").push();

        String key = myRef.getKey();
        laundryService.setServiceKey(key);

        myRef.setValue(laundryService).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("Post added");
                progressBarSimpan.setVisibility(View.INVISIBLE);
                btn_simpan.setVisibility(View.VISIBLE);
                popupScreenAddService.dismiss();
            }
        });
    }

    private void showMessage(String s) {

        Toast.makeText(HomeActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            fab.setVisibility(View.VISIBLE);
            fab_service.setVisibility(INVISIBLE);
            // Handle the camera action
            getSupportActionBar().setTitle("Home");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new HomeFragment()).commit();

        } else if (id == R.id.nav_profile) {

            fab.setVisibility(INVISIBLE);
            fab_service.setVisibility(INVISIBLE);
            getSupportActionBar().setTitle("Profile");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new ProfileFragment()).commit();

        } else if (id == R.id.nav_layanan) {

            fab.setVisibility(INVISIBLE);
            fab_service.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("Layanan");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new LayananFragment()).commit();

        } else if (id == R.id.nav_feedback) {

            fab.setVisibility(INVISIBLE);
            fab_service.setVisibility(INVISIBLE);
            getSupportActionBar().setTitle("Feedback");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new FeedbackFragment()).commit();

        } else if (id == R.id.nav_guide) {

            fab.setVisibility(INVISIBLE);
            fab_service.setVisibility(INVISIBLE);
            getSupportActionBar().setTitle("Guide");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new GuideFragment()).commit();

        } else if (id == R.id.nav_about) {

            fab.setVisibility(INVISIBLE);
            fab_service.setVisibility(INVISIBLE);
            getSupportActionBar().setTitle("About");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new AboutFragment()).commit();

        }else if (id == R.id.nav_order) {

            fab.setVisibility(INVISIBLE);
            fab_service.setVisibility(INVISIBLE);
            getSupportActionBar().setTitle("Cucian");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new OrderFragment()).commit();

        }  else if (id == R.id.nav_logout) {

            FirebaseAuth.getInstance().signOut();
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean amIConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo !=null && activeNetworkInfo.isConnected();
    }
}
