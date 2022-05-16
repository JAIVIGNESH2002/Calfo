package com.example.canicall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class result extends AppCompatActivity {
    private String resUserNum;
    private String resUserName;
    private String[] arr;
    private TextView sharedName;
    private TextView sharedNo;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        sharedName = findViewById(R.id.resName);
        sharedNo = findViewById(R.id.resNumTv);
        codeGenerator.flag=true;
        Intent j = getIntent();
        arr=j.getStringExtra("uNo").split(" ");
        resUserNum=arr[0];
        resUserName=arr[1];
        sharedName.setText(resUserName);
        sharedNo.setText(resUserNum);
        Toast.makeText(this, resUserName+"/"+resUserNum, Toast.LENGTH_SHORT).show();
    }
}