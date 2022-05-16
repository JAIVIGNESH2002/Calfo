package com.example.canicall.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.canicall.R;
import com.google.firebase.database.FirebaseDatabase;

public class getDet extends AppCompatActivity {
    private EditText userNoText;
    private EditText userName;
    private Button confirm;
    public static String userNum;
    public static String userNameStr;
    private String user_no;
    public static final String SHARED_PREFS="sharedPrefs";
    public static final String number = "number";
    public static final String name = "name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_det);
        userNoText = findViewById(R.id.userNo);
        confirm = findViewById(R.id.saveBt);
        userName = findViewById(R.id.userName);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_no = userNoText.getText().toString();
                userNameStr=userName.getText().toString();
                userNum=user_no;
                saveNum();
                FirebaseDatabase.getInstance().getReference().child(userNum).child("fName").setValue("");
                FirebaseDatabase.getInstance().getReference().child(userNum).child("friend").setValue("");
                FirebaseDatabase.getInstance().getReference().child(userNum).child("modified").setValue(false);
            }
        });


    }

    private void saveNum() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(number,userNum);
        editor.putString(name,userNameStr);
        editor.apply();
        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
    }
}