package com.example.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    TextInputLayout name,username,email,phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phoneNo);

        showAllUserData();

    }

    private void showAllUserData() {

        Intent intent = getIntent();
        String user_name = intent.getStringExtra("regname");
        String user_username = intent.getStringExtra("regusername");
        String user_email = intent.getStringExtra("regemail");
        String user_phone = intent.getStringExtra("regphone");

        name.getEditText().setText(user_name);
        username.getEditText().setText(user_username);
        email.getEditText().setText(user_email);
        phone.getEditText().setText(user_phone);
    }
}