package com.example.canicall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class codeScanner extends AppCompatActivity {
    CodeScanner codeScanner;
    CodeScannerView scanView;
    public static String uNum;
    public static String uName;
    private String[] arr;
    private String[] uDet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_scanner);
        scanView = findViewById(R.id.scan_view);
        codeScanner = new CodeScanner(this,scanView);
        checkPerm();
        SharedPreferences pref = getSharedPreferences(getDet.SHARED_PREFS,MODE_PRIVATE);
        String userNumFromPref = pref.getString(getDet.number,"default");
        String userNameFromPref = pref.getString(getDet.name,"default");
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        uDet=result.getText().split(" ");
                        uNum=uDet[0]; uName=uDet[1];
                        FirebaseDatabase.getInstance()
                                .getReference()
                                .child(uNum)
                                .child("friend")
                                .setValue(userNumFromPref);
                        FirebaseDatabase.getInstance()
                                .getReference()
                                .child(userNumFromPref)
                                .child("friend")
                                .setValue(uNum);
                        FirebaseDatabase.getInstance()
                                .getReference()
                                .child(userNumFromPref)
                                .child("fName")
                                .setValue(uName);
                        FirebaseDatabase.getInstance()
                                .getReference()
                                .child(uNum)
                                .child("fName")
                                .setValue(userNameFromPref);

                        FirebaseDatabase.getInstance()
                                .getReference()
                                .child(uNum)
                                .child("modified")
                                .setValue(true);
                        FirebaseDatabase.getInstance()
                                .getReference()
                                .child(uNum)
                                .child("friends")
                                .child(userNumFromPref).setValue("yes");
                        arr=result.getText().toString().split(" ");
                        String resUserNum=arr[0];
                        String resUserName=arr[1];
                        Intent intent = new Intent(ContactsContract.Intents.SHOW_OR_CREATE_CONTACT,
                                ContactsContract.Contacts.CONTENT_URI);
                        intent.setData(Uri.parse("tel:"+resUserNum));
                        intent.putExtra(ContactsContract.Intents.Insert.NAME,"HELLO");
                        startActivity(intent);

//                        Intent i = new Intent(getApplicationContext(),result.class);
//                        i.putExtra("uNo",result.getText().toString());
//                        startActivity(i);
                    }
                });
            }
        });
        scanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPerm();
            }
        });

    }

    private void checkPerm() {
        Dexter.withContext(com.example.canicall.codeScanner.this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        checkPerm();
//
//    }

}