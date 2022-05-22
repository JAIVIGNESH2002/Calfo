package com.example.canicall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.canicall.ui.main.fragTwo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

public class codeGenerator extends AppCompatActivity {
    private ImageView showCode;
    private DatabaseReference reference;
    private DatabaseReference reference2;
    private DatabaseReference reference3;
    private Button saveCon;
    static boolean flag = true;
    private String friendNo;
    private String number2;
    private String fName;
    private static final String TAG = "CONTACT_TAG";
    private static final int WRITE_CONTACT_PERMISSION=100;
    private String[] contactPermissions;
    private boolean isWritePermissionEnabled(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this,contactPermissions,WRITE_CONTACT_PERMISSION);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0){
            boolean havePermission=grantResults[0]==PackageManager.PERMISSION_GRANTED;
            if(havePermission){
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Permission denied!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_generator);
        showCode = findViewById(R.id.showCode);
        //Listing the permissions needed...
        contactPermissions  = new String[]{
                Manifest.permission.WRITE_CONTACTS
        };
        //Getting the details from the shared preferences...
        SharedPreferences sharedPreferences = getSharedPreferences(getDet.SHARED_PREFS, MODE_PRIVATE);
        String number = sharedPreferences.getString(getDet.number,"")+" "+sharedPreferences.getString(getDet.name,"");
        number2=sharedPreferences.getString(getDet.number, "");

        //Generating the QR code......
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix matrix = multiFormatWriter.encode(number, BarcodeFormat.QR_CODE, 350, 350);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(matrix);
            showCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        reference = FirebaseDatabase.getInstance().getReference().child(number2)
                .child("modified");
        reference2 = FirebaseDatabase.getInstance().getReference().child(number2)
                .child("friend");
        reference3 = FirebaseDatabase.getInstance().getReference().child(number2)
                .child("fName");

        //Getting the friend number...
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                friendNo = snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Getting the friend name...
        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fName = snapshot.getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Listening for data change "modified"....
        reference.addValueEventListener(new ValueEventListener() { //Event listener is like a async thread....
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString().equals("true")) {
                    FirebaseDatabase.getInstance().getReference().child(number2).child("modified").setValue(false);
                   // new fragTwo().addNewUser(new userDetails(R.drawable.ic_launcher_background,fName,"Idle"));
//                    FirebaseDatabase.getInstance().getReference().child(number2).child("friends").setValue(fName);
                    if (flag){ //To trigger the intent exactly once.
                        flag = false;
//                        Intent t = new Intent(getApplicationContext(), result.class);
//                        t.putExtra("uNo",friendNo+" "+fName);
//                        startActivity(t);
                        Intent intent = new Intent(ContactsContract.Intents.SHOW_OR_CREATE_CONTACT,
                                ContactsContract.Contacts.CONTENT_URI);
                        intent.setData(Uri.parse("tel:"+friendNo));
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}