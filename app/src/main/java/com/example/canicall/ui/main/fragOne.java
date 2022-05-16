package com.example.canicall.ui.main;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.canicall.R;

import java.util.ArrayList;

public class fragOne extends Fragment {
    private TextView numView;
    private String userNumber;
    private Button editBt;
    private Button updateBt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_one,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        numView=getView().findViewById(R.id.numView);
        editBt=getView().findViewById(R.id.editBut);
        updateBt=getView().findViewById(R.id.updateBt);
        updateView();
        editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),getDet.class);
                startActivity(intent);
            }
        });
        updateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateView();
            }
        });

    }
    private void updateView() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getDet.SHARED_PREFS, Context.MODE_PRIVATE);
        userNumber=sharedPreferences.getString(getDet.number,"");
        numView.setText(userNumber);
    }
}
