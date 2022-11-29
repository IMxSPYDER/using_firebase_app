package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninActivity extends AppCompatActivity {
    Button in,up;
    TextInputLayout username_in,password_in;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://devarsh-c6e45-default-rtdb.asia-southeast1.firebasedatabase.app/");

    //FirebaseDatabase rootNode;
    //DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        in = findViewById(R.id.in);
        up = findViewById(R.id.up);
        username_in = findViewById(R.id.username);
        password_in = findViewById(R.id.password);



        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //rootNode = FirebaseDatabase.getInstance();
                //reference = rootNode.getReference("users");

                final String username = username_in.getEditText().getText().toString();
                final String password = password_in.getEditText().getText().toString();


                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(SigninActivity.this, "Please enter username or password", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){

                                final String getPassword = snapshot.child(username).child("regpassword").getValue(String.class);
                                if (getPassword.equals(password)){
                                    Toast.makeText(SigninActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();

                                    final String nameFromDB = snapshot.child(username).child("regname").getValue(String.class);
                                    final String usernameFromDB = snapshot.child(username).child("regusername").getValue(String.class);
                                    final String emailFromDB = snapshot.child(username).child("regemail").getValue(String.class);
                                    final String phoneFromDB = snapshot.child(username).child("regphone").getValue(String.class);

                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                                    intent.putExtra("regname", nameFromDB);
                                    intent.putExtra("regemail", emailFromDB);
                                    intent.putExtra("regusername", usernameFromDB);
                                    intent.putExtra("regphone", phoneFromDB);

                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(SigninActivity.this, "Wrong Username", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(SigninActivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SigninActivity.this,SignupActivity.class));
            }
        });
    }
}