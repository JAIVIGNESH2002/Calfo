package com.example.canicall.ui.main;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.canicall.R;
import com.example.canicall.getDet;
import com.google.firebase.database.FirebaseDatabase;

public class fragOne extends Fragment {
    private TextView numView;
    private String userNumber;
    private EditText status;
    private Button stsUpdateBt;

    String userNumFromPref;
    String userNameFromPref;
    String customStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_one,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences pref =getActivity().getSharedPreferences(getDet.SHARED_PREFS,MODE_PRIVATE);
        userNumFromPref = pref.getString(getDet.number,"default");
        userNameFromPref = pref.getString(getDet.name,"default");
        SharedPreferences check = getActivity().getSharedPreferences("checkTime",MODE_PRIVATE);
        String notFirstTime = check.getString("UserDisplayFirst","default");
        if(notFirstTime.equals("yes")){
           ;
        }else{
            FirebaseDatabase.getInstance().getReference().child(userNumFromPref).child("status").setValue("Idle");
            SharedPreferences.Editor editr = check.edit();
            editr.putString("UserDisplayFirst","yes");
            editr.apply();
        }
        status = getActivity().findViewById(R.id.customStatus);
        stsUpdateBt = getActivity().findViewById(R.id.stsUptBtn);
        stsUpdateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stats = status.getText().toString();
                FirebaseDatabase.getInstance().getReference().child(userNumFromPref).child("status").setValue(stats);
            }
        });
    }
}

