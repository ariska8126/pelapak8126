package com.example.pelapak8126.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.pelapak8126.Adapters.RequestOrderAdapter;
import com.example.pelapak8126.Adapters.ServiceAdapter;
import com.example.pelapak8126.Models.Distance;
import com.example.pelapak8126.Models.LaundryService;
import com.example.pelapak8126.Models.RequestOrder;
import com.example.pelapak8126.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.snapshot.ChildKey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView rv_requestOrder;
    RequestOrderAdapter requestOrderAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference laundryPelapak;
    DatabaseReference distanceReference;


    FirebaseAuth auth;
    FirebaseUser user;

    ToggleButton toggleButton, tgb_anjem;
    Boolean toggleButtoState;

    TextView tv_statusBuka;

    //test
    TextView tv_path;

    List<RequestOrder> requestOrderList;
    List<Distance> distanceList;
    TextView tv_username, tv_email;
    ImageView imgv_user;
    String statusBuka, antarJemput;
    Switch sw_clop;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.orderByChild("idLaundry").equalTo(user.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        requestOrderList = new ArrayList<>();
                        for (DataSnapshot reqOrSnap: dataSnapshot.getChildren()) {


                            RequestOrder requestOrder = reqOrSnap.getValue(RequestOrder.class);

                            Long timeStamp = Long.valueOf(requestOrder.getTimeStamp());
                            requestOrder.setTimeStamp(getDate(timeStamp));
                            if (requestOrder.getStatus().equals("Menunggu Konfirmasi")){

                                requestOrderList.add(requestOrder);
                            }

                        }

                        requestOrderAdapter = new RequestOrderAdapter(getActivity(), requestOrderList);
                        rv_requestOrder.setAdapter(requestOrderAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

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

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View requestOrderView = inflater.inflate(R.layout.fragment_home, container, false);

        rv_requestOrder = requestOrderView.findViewById(R.id.rv_request_order);
        rv_requestOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_requestOrder.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("RequestOrder");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        laundryPelapak = firebaseDatabase.getReference("OwnerLaundry").child(user.getUid());

        distanceReference = firebaseDatabase.getReference(" Distance");

        tv_username = requestOrderView.findViewById(R.id.tv_uname_fh);
        tv_email = requestOrderView.findViewById(R.id.tv_emaul_fh);

        imgv_user = requestOrderView.findViewById(R.id.imgv_user_fh);

        Glide.with(this).load(user.getPhotoUrl()).into(imgv_user);
        tv_email.setText(user.getEmail());
        tv_username.setText(user.getDisplayName());

        toggleButton = requestOrderView.findViewById(R.id.toggleButton);
        tgb_anjem = requestOrderView.findViewById(R.id.tgb_anjem_fh);

//        toggleButtoState = toggleButton.isChecked();

        //update buka tutup
        laundryPelapak.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                statusBuka = dataSnapshot.child("statusBuka").getValue().toString();
                antarJemput = dataSnapshot.child("statusJemput").getValue().toString();

                if (statusBuka.equals("buka")){

                    toggleButton.setChecked(true);
                } else {

                   toggleButton.setChecked(false);
                }

                if (antarJemput.equals("anjem")){

                    tgb_anjem.setChecked(true);
                } else {

                    tgb_anjem.setChecked(false);
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

                    laundryPelapak.child("statusBuka").setValue("buka");

                    //distance
                    distanceReference.orderByChild("idLaundry").equalTo(user.getUid())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    distanceList = new ArrayList<>();
                                    for (DataSnapshot lapakSnapshot: dataSnapshot.getChildren()){
                                        Distance distance = lapakSnapshot.getValue(Distance.class);
                                        distance.setStatusBuka("buka");
                                        distanceList.add(distance);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                } else {
                    laundryPelapak.child("statusBuka").setValue("tutup");
                    //distance
                    distanceReference.orderByChild("idLaundry").equalTo(user.getUid())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    distanceList = new ArrayList<>();
                                    for (DataSnapshot lapakSnapshot: dataSnapshot.getChildren()){
                                        Distance distance = lapakSnapshot.getValue(Distance.class);
                                        distance.setStatusBuka("tutup");
                                        distanceList.add(distance);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                }
            }
        });

        tgb_anjem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (tgb_anjem.isChecked()){

                    laundryPelapak.child("statusJemput").setValue("anjem");

                } else {
                    laundryPelapak.child("statusJemput").setValue("tidak");

                }
            }
        });



        return requestOrderView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
