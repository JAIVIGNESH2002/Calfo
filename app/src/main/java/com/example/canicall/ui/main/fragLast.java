package com.example.canicall.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.canicall.R;
import com.example.canicall.codeGenerator;
import com.example.canicall.codeScanner;

public class fragLast extends Fragment {
    private TextView scnButton;
    private TextView genButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_last,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scnButton=getView().findViewById(R.id.scnBtn);
        genButton=getView().findViewById(R.id.genBtn);

        //Passing the intent to scanner activity
        scnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(getContext(),codeScanner.class);
                startActivity(n);
            }
        });
        //Passing the intent to generator activity
        genButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent m = new Intent(getContext(),codeGenerator.class);
                startActivity(m);
            }
        });

    }
}
