package com.example.canicall.ui.main;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canicall.MainActivity;
import com.example.canicall.R;
import com.example.canicall.getDet;
import com.example.canicall.myAdapter;
import com.example.canicall.userDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class fragTwo extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private static final int callReqCode = 2;
    public myAdapter adapter;
    String userNumFromPref;
    String userNameFromPref;
    String phNum;
    public LinkedHashSet<String> frndNumbers = new LinkedHashSet<>();
    public static List<userDetails> userDetailsList;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences pref =getActivity().getSharedPreferences(getDet.SHARED_PREFS,MODE_PRIVATE);
        userNumFromPref = pref.getString(getDet.number,"default");
        userNameFromPref = pref.getString(getDet.name,"default");
        userDetailsList = new ArrayList<>();
        return inflater.inflate(R.layout.frag_two,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseReference fName = FirebaseDatabase.getInstance().getReference().child(userNumFromPref).child("friends");
        fName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:snapshot.getChildren()){
                    frndNumbers.add(s.getKey());
                    DatabaseReference ofFrndNum = FirebaseDatabase.getInstance().getReference().child(s.getKey());
                    userDetails newUser = new userDetails();
                    ofFrndNum.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot s : snapshot.getChildren()) {
                                if(s.getKey().equals("UserName")){
                                    newUser.setUserName(s.getValue().toString());
//                                    Toast.makeText(getActivity(), s.getValue().toString(), Toast.LENGTH_SHORT).show();

                                }else if(s.getKey().equals("status")) {
//                                    Toast.makeText(getActivity(), s.getValue().toString(), Toast.LENGTH_SHORT).show();
                                    newUser.setStatus(s.getValue().toString());
                                }
                            }
                            userDetailsList.add(newUser);
                            LinkedHashSet<userDetails> userSet = new LinkedHashSet<>(userDetailsList);
                            userDetailsList.clear();
                            userDetailsList.addAll(userSet);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        initRecyclerView();
    }
//    private String getFriendName(DatabaseReference fNumberRef) {
//        fNumberRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//               friendNameTemp = snapshot.getValue().toString();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        Toast.makeText(getActivity(), friendNameTemp, Toast.LENGTH_SHORT).show();
//        return friendNameTemp;
//
//    }
//    private String getFriendStatus(DatabaseReference fStatusRef){
//        fStatusRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                frndStatusTemp = snapshot.getValue().toString();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        Toast.makeText(getActivity(), frndStatusTemp, Toast.LENGTH_SHORT).show();
//        return frndStatusTemp;
//    }
//    private void initData() {
//
//    }
    private void initRecyclerView() {
        recyclerView = getView().findViewById(R.id.userList) ;
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new myAdapter(userDetailsList);
        recyclerView.setAdapter(adapter);
        adapter.setOnCallClickListner(new myAdapter.onCallClickListener() {
            @Override
            public void onCallClick(int position) {
                phNum = Arrays.asList(frndNumbers).get(position).toString();
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{
                            Manifest.permission.CALL_PHONE
                    },callReqCode);
                }else{
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phNum)));
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == callReqCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();
            } else {
                Toast.makeText(getActivity(), "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
}

    private void makeCall() {
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phNum)));
    }
    }
