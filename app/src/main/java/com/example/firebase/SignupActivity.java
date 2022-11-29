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

public class SignupActivity extends AppCompatActivity {
    Button regbtn,reglgn;
    TextInputLayout regname,regusername,regemail,regpassword,regphone,regconpassword;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://devarsh-c6e45-default-rtdb.asia-southeast1.firebasedatabase.app/");

    //FirebaseDatabase rootNode;
    //DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        regbtn = findViewById(R.id.reg_Btn);
        reglgn = findViewById(R.id.btn_Login);
        regname = findViewById(R.id.reg_name);
        regusername = findViewById(R.id.reg_username);
        regemail = findViewById(R.id.reg_email);
        regpassword = findViewById(R.id.reg_password);
        regphone = findViewById(R.id.reg_phoneNo);
        regconpassword = findViewById(R.id.reg_conpassword);

        //
        // FirebaseDatabase database = FirebaseDatabase.getInstance();

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //rootNode = FirebaseDatabase.getInstance();
                //reference = rootNode.getReference("users");

                String name = regname.getEditText().getText().toString();
                String username = regusername.getEditText().getText().toString();
                String username_db = regusername.getEditText().getText().toString();
                String email = regemail.getEditText().getText().toString();
                String phoneNo = regphone.getEditText().getText().toString();
                String password = regpassword.getEditText().getText().toString();
                String conpassword = regconpassword.getEditText().getText().toString();

                UserHelpetClass helperClass = new UserHelpetClass(name,username,email,phoneNo,password,conpassword);

                if (name.isEmpty() || username.isEmpty() || email.isEmpty() || phoneNo.isEmpty() || conpassword.isEmpty() || password.isEmpty()){
                    Toast.makeText(SignupActivity.this, "Please Fill all the feilds", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(conpassword)){
                    Toast.makeText(SignupActivity.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)){
                                Toast.makeText(SignupActivity.this, "Username is already registerned", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child("users").child(username).child("regusername").setValue(username_db);
                                databaseReference.child("users").child(username).child("regname").setValue(name);
                                databaseReference.child("users").child(username).child("regemail").setValue(email);
                                databaseReference.child("users").child(username).child("regphone").setValue(phoneNo);
                                databaseReference.child("users").child(username).child("regpassword").setValue(password);
                                databaseReference.child("users").child(username).child("regconpassword").setValue(conpassword);

                                Toast.makeText(SignupActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        reglgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


}